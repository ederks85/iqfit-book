package nl.iqfit.logic.payment;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import nl.iqfit.core.configuration.IQFitConfig;
import nl.iqfit.core.configuration.IQFitConfigurationFactory;
import nl.iqfit.core.dto.BankDataDTO;
import nl.iqfit.core.dto.OrderDataDTO;
import nl.iqfit.core.jaxb.Bank;
import nl.iqfit.core.jaxb.Banks;
import nl.iqfit.core.jaxb.MollieResponse;
import nl.iqfit.logic.facade.OrderFacade;
import nl.iqfit.logic.order.OrderException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
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

	@Inject OrderFacade orderFacade;

	@Override
	public Set<BankDataDTO> getIdealBankList() throws PaymentException {

		try {
			IQFitConfig config = new IQFitConfigurationFactory().getIQFitConfig();

			URIBuilder uriBuilder = new URIBuilder()
				.setScheme(config.getMollieURLScheme())
				.setHost(config.getMollieURLHost())
				.setPath(config.getMollieURLPath())
				.addParameter(URLEncoder.encode(config.getMollieBankListModeParameter().getName(), "UTF-8"), URLEncoder.encode(config.getMollieBankListModeParameter().getValue(), "UTF-8"));

			if (config.isTestEnvironment()) {
				uriBuilder.addParameter(URLEncoder.encode(config.getMollieTestModeParameter().getName(), "UTF-8"), URLEncoder.encode(config.getMollieTestModeParameter().getValue(), "UTF-8"));
			}

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

			Validate.notNull(foundBanks, "Error while retrieveing IDeal bank list: no banks recieved");

			// Log response
			StringWriter sw = new StringWriter();

			Marshaller mc= jbc.createMarshaller();
			mc.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			mc.marshal(foundBanks, sw);

			logger.info("Retrieved banks from Mollie:\n" + sw.toString());


			Set<BankDataDTO> bankDataDTOs = new HashSet<BankDataDTO>();
			for (Bank bank : foundBanks.getBanks()) {
				bankDataDTOs.add(new BankDataDTO(bank.getBankId(), bank.getBankName()));
			}
			return bankDataDTOs;
		} catch (JAXBException | IOException | URISyntaxException e) {
			final String message = "Error in OrderPageServlet";
			logger.error(message, e);
			throw new PaymentException(message, e);
		}
	}

	@Override
	public String initializeIdealPayment(final OrderDataDTO order, final BankDataDTO bank) throws PaymentException {
		Validate.notNull(order, "Order is null.");
		Validate.notNull(bank, "Bank is null.");

		logger.info("Initializing payment for order {} at bank {}.", order, bank);

		try {
			final IQFitConfig config = new IQFitConfigurationFactory().getIQFitConfig();

			final URIBuilder uriBuilder = new URIBuilder()
				.setScheme(config.getMollieURLScheme())
				.setHost(config.getMollieURLHost())
				.setPath(config.getMollieURLPath())
				.addParameter(URLEncoder.encode(config.getMollieFetchModeParameter().getName(), "UTF-8"), URLEncoder.encode(config.getMollieFetchModeParameter().getValue(), "UTF-8"))
				.addParameter(URLEncoder.encode(config.getMollieFetchModePartnerIdParameter().getName(), "UTF-8"), URLEncoder.encode(config.getMollieFetchModePartnerIdParameter().getValue(), "UTF-8"))
				.addParameter(URLEncoder.encode(config.getMollieFetchModeRequestAmountParameter().getName(), "UTF-8"), URLEncoder.encode(config.getMollieFetchModeRequestAmountParameter().getValue(), "UTF-8"))
				.addParameter(URLEncoder.encode(config.getMollieFetchModeBankIdParameter().getName(), "UTF-8"), URLEncoder.encode(bank.getBankId(), "UTF-8"))
				.addParameter(URLEncoder.encode(config.getMollieFetchModeDescriptionParameter().getName(), "UTF-8"), URLEncoder.encode(config.getMollieFetchModeDescriptionParameter().getValue(), "UTF-8"))
				.addParameter(URLEncoder.encode(config.getMollieFetchModeLocalAfterPaymentReportURLParameter().getName(), "UTF-8"), URLEncoder.encode(config.getMollieFetchModeLocalAfterPaymentReportURLParameter().getValue(), "UTF-8"))
				.addParameter(URLEncoder.encode(config.getMollieFetchModeLocalAfterPaymentClientReturnURLParameter().getName(), "UTF-8"), URLEncoder.encode(config.getMollieFetchModeLocalAfterPaymentClientReturnURLParameter().getValue(), "UTF-8"));

			final URI mollieInitilizePaymentURL = uriBuilder.build();
			logger.debug("URL for initializing payment at Mollie: {}", mollieInitilizePaymentURL.toString());

			HttpGet get = new HttpGet(mollieInitilizePaymentURL);

			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpResponse httpResponse = httpClient.execute(get);
			logger.debug("HTTP Response from initializing payment at Mollie: {}", httpResponse.getStatusLine().getStatusCode());

			final int statusCode = httpResponse.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				throw new PaymentException("Unexpected http status code while initializing payment at Mollie: " + statusCode);
			}

			// Unmarshall XML response to MollieFetchModeResponse objects
			JAXBContext jbc = JAXBContext.newInstance(MollieResponse.class);
			Unmarshaller u = jbc.createUnmarshaller();
			MollieResponse response =  (MollieResponse)u.unmarshal(new InputStreamReader(httpResponse.getEntity().getContent()));

			Validate.notNull(response, "Error while initializing payment at Mollie: fetch mode response empty or invalid");

			// Log response
			StringWriter sw = new StringWriter();

			Marshaller mc= jbc.createMarshaller();
			mc.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			mc.marshal(response, sw);

			logger.info("Response from Mollie for initializing payment for order {}:\n{}", order.getOrderNumber(), sw.toString());


			// Validate response data
			final String transactionId = response.getOrder().getTransactionId();
			final String responseAmount = response.getOrder().getAmount();
			final String redirectURL = response.getOrder().getUrl();

			logger.debug("Recieved transactionId {} after initializing payment at Mollie for order {}", transactionId, order.getOrderNumber());
			logger.debug("Recieved response amount {} after initializing payment at Mollie for order {}", responseAmount, order.getOrderNumber());
			logger.debug("Recieved redirect URL {} after initializing payment at Mollie for order {}", redirectURL, order.getOrderNumber());

			if (StringUtils.isEmpty(transactionId)) {
				throw new OrderException("Recieved invalid or missing transactionId " + transactionId + " after initializing payment at Mollie for order " + order.getOrderNumber());
			}

			if (StringUtils.isEmpty(responseAmount) || !responseAmount.equals(config.getMollieFetchModeRequestAmountParameter().getValue())) {
				throw new OrderException("Recieved invalid, missing or unexpected response amount " + responseAmount + " after initializing payment at Mollie for order " + order.getOrderNumber());
			}

			if (StringUtils.isEmpty(redirectURL)) {
				throw new OrderException("Recieved invalid or missing client redirect URL " + redirectURL + " after initializing payment at Mollie for order " + order.getOrderNumber());
			}

			order.setTransactionId(response.getOrder().getTransactionId());
			this.orderFacade.updateOrder(order);

			return redirectURL;
		} catch (JAXBException | IOException | URISyntaxException | OrderException e) {
			final String message = "Error while initializing IDeal payment";
			logger.error(message, e);
			throw new PaymentException(message, e);
		}
	}
}