package nl.iqfit.test.payment.ideal.mollie;

import java.net.URI;

import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;

import nl.iqfit.core.configuration.IQFitConfig;
import nl.iqfit.core.configuration.IQFitConfigurationFactory;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class MollieDelayedIDealReportCallSimulator {

	private static final Logger logger = LoggerFactory.getLogger(MollieDelayedIDealReportCallSimulator.class);

	@Resource
	TimerService timerService;

	public MollieDelayedIDealReportCallSimulator() {}

	public void setTimer(String transactionId) {
		logger.debug("MollieDelayedIDealReportCallSimulator timeout set for transaction ID: {}", transactionId);
		this.timerService.createTimer(10000, transactionId);
	}

	@Timeout
	public void performDelayedMolliePaymentReportCall(Timer timer) {
		final String transactionId = String.valueOf(timer.getInfo());

		logger.debug("MollieDelayedIDealReportCallSimulator timeout method called for transaction ID: {}", transactionId);
		logger.debug("Now performing delayed Mollie payment report call on IQFit report location");

		IQFitConfig config = new IQFitConfigurationFactory().getIQFitConfig();

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
				return;
			}
		} catch (Exception e) {
			logger.error("Error while building IQFit IDeal payment report path", e);
		}

		logger.debug("Performed delayed Mollie payment report call on IQFit report location");
	}
}
