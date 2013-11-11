package nl.iqfit.test.payment.ideal.mollie;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.iqfit.core.configuration.IQFitConfig;
import nl.iqfit.core.configuration.IQFitConfigurationFactory;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(urlPatterns={"/test/ideal/mollie/paymentreport"})
public class MollieIDealPaymentReportSimulator extends HttpServlet {

	private final Logger logger = LoggerFactory.getLogger(MollieIDealPaymentReportSimulator.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		IQFitConfig config = new IQFitConfigurationFactory().getIQFitConfig();

		// Mollie IDeal payment report simulator is only available for test environment
		if (!config.isTestEnvironment()) {
			logger.warn("MollieIDealPaymentReportSimulator called in test environment.");
			response.sendError(404);
			return;
		}

		final String transactionId = request.getParameter("transaction_id");
		if (StringUtils.isBlank(transactionId)) {
			logger.warn("Invalid or unknown transaction id recieved  called in test environment on MollieIDealPaymentReportSimulator: {}", transactionId);
			response.sendError(500, "Invalid or unknown transaction id recieved  called in test environment on MollieIDealPaymentReportSimulator");
		} else {
			logger.debug("MollieIDealPaymentReportSimulator: calling IQFit idealPaymentReport for transaction ID: {}", transactionId);

			try {
				URIBuilder uriBuilder = new URIBuilder().
					setScheme(config.getIqFitURLScheme()).
					setHost(config.getIqFitURLHost()).
					setPath(config.getIqFitIdealPaymentReportPath()).
					setParameter(config.getMollieFetchModeTransactionIdParameter().getName(), transactionId);

					final URI iqfitPaymentReportURL = uriBuilder.build();
					logger.debug("URL for call to IQFit IDeal payment report: {}", iqfitPaymentReportURL.toString());

				HttpGet get = new HttpGet(iqfitPaymentReportURL);

				CloseableHttpClient httpClient = HttpClients.createDefault();
				HttpResponse httpResponse = httpClient.execute(get);
				logger.debug("HTTP Response {} from call to IQFit IDeal payment report for transaction ID: {}", httpResponse.getStatusLine().getStatusCode(), transactionId);

				final int statusCode = httpResponse.getStatusLine().getStatusCode();
				if (statusCode != HttpStatus.SC_OK) {
					logger.error("Invalid HTTP Response " + statusCode + " from call to IQFit IDeal payment report for transaction ID: " + transactionId);
					response.sendError(500);
					return;
				}
			} catch (URISyntaxException e) {
				logger.error("Error while building IQFit IDeal payment report path", e);
				response.sendError(500);
				return;
			}

			try {
				URIBuilder responseUriBuilder = new URIBuilder().
					setScheme(config.getIqFitURLScheme()).
					setHost(config.getIqFitURLHost()).
					setPath(config.getIqFitPaymentReturnPath()).
					setParameter(config.getMollieFetchModeTransactionIdParameter().getName(), transactionId);

				final URI responseUri = responseUriBuilder.build();
				logger.debug("MollieIDealPaymentReportSimulator: redirecting client to after payment return URL: {}", responseUri.toString());

				response.sendRedirect(response.encodeRedirectURL(responseUri.toString()));
			} catch (URISyntaxException e) {
				logger.error("Error while building IQFit payment return path", e);
				response.sendError(500);
			}
		}
	}
}