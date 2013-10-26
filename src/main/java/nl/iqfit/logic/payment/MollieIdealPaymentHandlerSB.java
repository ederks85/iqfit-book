package nl.iqfit.logic.payment;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import nl.iqfit.core.configuration.IQFitConfig;
import nl.iqfit.core.configuration.IQFitConfigurationFactory;
import nl.iqfit.core.dto.BankDataDTO;
import nl.iqfit.core.jaxb.Bank;
import nl.iqfit.core.jaxb.Banks;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MollieIdealPaymentHandlerSB implements IDealPaymentHandler {

	private static final Logger logger = LoggerFactory.getLogger(MollieIdealPaymentHandlerSB.class);

	@Override
	public Set<BankDataDTO> getIdealBankList() throws PaymentException {

		try {
			IQFitConfig config = new IQFitConfigurationFactory().getIQFitConfig();

			//FIXME update configuration
			URIBuilder uriBuilder = new URIBuilder()
				.setScheme("https")
				.setHost("secure.mollie.nl")
				.setPath("/xml/ideal")
				.addParameter("a", "banklist");

			final URI idealBankRetrievalURI = uriBuilder.build();
			logger.debug("URL for retrieving IDeal bank list from Mollie: {}", idealBankRetrievalURI.toString());

			HttpGet get = new HttpGet(idealBankRetrievalURI);

			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpResponse httpResponse = httpClient.execute(get);
			logger.debug("HTTP Response from retrieving IDeal bank list from Mollie: {}", httpResponse.getStatusLine().getStatusCode());

			final int statusCode = httpResponse.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				throw new PaymentException("Unexpected http status code while retrieving Mollie Ideal bank list: " + statusCode);
			}

			// Unmarshall XML response to BankDataDTO objects
			JAXBContext jbc = JAXBContext.newInstance(Banks.class, Bank.class);
			Unmarshaller u = jbc.createUnmarshaller();
			Banks foundBanks =  (Banks)u.unmarshal(new InputStreamReader(httpResponse.getEntity().getContent()));

			//FIXME why dafuq werkt dit niet??
			if (logger.isDebugEnabled()) {
				StringWriter sw = new StringWriter();

				Marshaller mc= jbc.createMarshaller();
				mc.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
				mc.marshal(foundBanks, sw);

				logger.debug("Retrieved banks from Mollie:\n" + sw.toString());
			}

			Set<BankDataDTO> bankDataDTOs = new HashSet<BankDataDTO>();
			for (Bank bank : foundBanks.getBanks()) {
				bankDataDTOs.add(new BankDataDTO(bank.getBankId(), bank.getBankName()));
			}
			return bankDataDTOs;
		} catch (ConfigurationException | JAXBException | IOException | URISyntaxException e) {
			final String message = "Error in OrderPageServlet";
			logger.error(message, e);
			throw new PaymentException(message, e);
		}
	}
}