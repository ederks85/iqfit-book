package nl.iqfit.web.servlet;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.iqfit.logic.facade.OrderFacade;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servlet implementation class PaymentReturnServlet
 */
@WebServlet("/paymentreturn")
public class PaymentReturnServlet extends HttpServlet {

	private static final Logger logger = LoggerFactory.getLogger(PaymentReturnServlet.class);

	@Inject OrderFacade orderFacade;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// retrieve transaction id from request and retrieve associated order from database
		final String transactionId = request.getParameter("transaction_id");
		logger.info("Received call on payment return with transaction id {}", transactionId);

		if (StringUtils.isBlank(transactionId)) {
			logger.warn("Received call on payment return with INVALID transaction id {}", transactionId);
		} else {
			logger.info("Looking up order with transaction id {}", transactionId);

			// retrieve current status of provided order

			// if payment successful, redirect to 
		}
	}
}