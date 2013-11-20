package nl.iqfit.test.payment.ideal.mollie;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.iqfit.core.configuration.IQFitConfig;
import nl.iqfit.core.configuration.IQFitConfigurationFactory;

import org.apache.commons.lang.StringUtils;
import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(urlPatterns={"/test/ideal/mollie/paymentreport"})
public class MollieIDealPaymentReportSimulator extends HttpServlet {

	private final Logger logger = LoggerFactory.getLogger(MollieIDealPaymentReportSimulator.class);

	@Inject
	MollieDelayedIDealReportCallSimulator delayedCallSimulator;

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

			// Schedule a delayed call to the IQFit report location
			this.delayedCallSimulator.setTimer(transactionId);

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