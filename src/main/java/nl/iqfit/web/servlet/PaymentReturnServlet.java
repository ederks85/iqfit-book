package nl.iqfit.web.servlet;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.iqfit.core.dto.OrderDataDTO;
import nl.iqfit.logic.db.entity.OrderStatus;
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
			response.sendError(500, "Invalid transaction_id");
		} else {
			logger.info("Looking up order with transaction id {}", transactionId);

			// retrieve current status of provided order
			final OrderDataDTO orderData = this.orderFacade.getOrderByTransactionID(transactionId);
			logger.info("Found order data for transaction ID {}: {}", transactionId, orderData);

			// if payment successful, redirect to
			if (orderData == null) {
				logger.warn("No order found for transaction ID: {}.");
				response.sendError(500);
				return;
			}

			if (orderData.getOrderStatus() == OrderStatus.PAY_CONF) {
				logger.info("Order with transaction ID {} has status {}. Redirecting to payment pending view.", transactionId, orderData.getOrderStatus());
				request.getRequestDispatcher("/WEB-INF/views/payment/paymentpending.jsp").forward(request, response);
			} else if (orderData.getOrderStatus() == OrderStatus.PAYED) {
				logger.info("Order with transaction ID {} has status {}. Redirecting to payment payed view.", transactionId, orderData.getOrderStatus());
				request.getRequestDispatcher("/WEB-INF/views/payment/paymentsuccess.jsp").forward(request, response);
			} else if (orderData.getOrderStatus() == OrderStatus.ERROR) {
				logger.info("Order with transaction ID {} has status {}. Redirecting to payment failed view.", transactionId, orderData.getOrderStatus());
				request.getRequestDispatcher("/WEB-INF/views/payment/paymentfailed.jsp").forward(request, response);
			} else {
				logger.error("Unknown or invalid status for order with transaction ID: {}.", transactionId);
				response.sendError(500, "Unknown or invalid order status");
			}
		}
	}
}