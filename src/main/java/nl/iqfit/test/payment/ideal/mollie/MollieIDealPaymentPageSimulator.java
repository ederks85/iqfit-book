package nl.iqfit.test.payment.ideal.mollie;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.iqfit.core.configuration.IQFitConfig;
import nl.iqfit.core.configuration.IQFitConfigurationFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(urlPatterns={"/test/ideal/mollie/redirect"})
public class MollieIDealPaymentPageSimulator extends HttpServlet {

	private final Logger logger = LoggerFactory.getLogger(MollieIDealPaymentPageSimulator.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		IQFitConfig config = new IQFitConfigurationFactory().getIQFitConfig();

		// Mollie IDeal paymentp age simulator is only available for test environment
		if (!config.isTestEnvironment()) {
			logger.warn("MollieIDealPaymentPageSimulator called in test environment.");
			response.sendError(404);
			return;
		}

		logger.debug("MollieIDealPaymentPageSimulator: redirecting to simulated payment page");

		request.getRequestDispatcher("/WEB-INF/views/test/idealPaymentPage.jsp").forward(request, response);
	}
}