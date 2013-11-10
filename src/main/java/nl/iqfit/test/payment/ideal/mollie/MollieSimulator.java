package nl.iqfit.test.payment.ideal.mollie;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nl.iqfit.core.configuration.IQFitConfig;
import nl.iqfit.core.configuration.IQFitConfigurationFactory;

@WebServlet(urlPatterns={"/xml/ideal"})
public class MollieSimulator extends HttpServlet {

	private static final Logger logger = LoggerFactory.getLogger(MollieSimulator.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		IQFitConfig config = new IQFitConfigurationFactory().getIQFitConfig();

		// Mollie simulator is only available for test environment
		if (!config.isTestEnvironment()) {
			logger.warn("MollieSimulator called in test environment.");
			response.sendError(404);
			return;
		}

		final String type = request.getParameter(config.getMollieBankListModeParameter().getName());
		final String testMode = request.getParameter(config.getMollieTestModeParameter().getName());

		logger.debug("MollieSimulator: recieved type parameter: {}={}", config.getMollieBankListModeParameter().getName(), type);
		logger.debug("MollieSimulator: recieved testMode parameter: {}={}", config.getMollieTestModeParameter().getName(), testMode);

		response.setContentType("application.xml");
		if (config.getMollieBankListModeParameter().getValue().equals(type)) {
			request.getRequestDispatcher("/WEB-INF/views/test/bankList.jsp").forward(request, response);
		} 	else if (config.getMollieFetchModeParameter().getValue().equals(type)) {
			String transactionId = UUID.randomUUID().toString();
			request.setAttribute("transactionId", transactionId);

			String bankId = request.getParameter("bank_id");
			String amount = request.getParameter("amount");
			logger.debug("MollieSimulator: recieved fetchmode bank id parameter: {}={}", config.getMollieFetchModeBankIdParameter().getName(), bankId);
			logger.debug("MollieSimulator: recieved fetchmode amount parameter: {}={}", config.getMollieFetchModeBankIdParameter().getName(), amount);
			if (!StringUtils.isBlank(amount)) {
				request.setAttribute("amount", amount);
			}
			request.getRequestDispatcher("/WEB-INF/views/test/fetch.jsp").forward(request, response);;
//		} 	else if (config.getMollieCheckModeParameter().getValue().equals(type)) {
			
		} else {
			request.getRequestDispatcher("/WEB-INF/views/test/error.jsp").forward(request, response);;
		}
	}
}