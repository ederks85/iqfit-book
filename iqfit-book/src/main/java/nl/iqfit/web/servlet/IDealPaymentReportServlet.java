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
import nl.iqfit.logic.facade.PaymentFacade;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servlet implementation class PaymentReportServlet
 */
@WebServlet("/idealpaymentreport")
public class IDealPaymentReportServlet extends HttpServlet {

	private static final Logger logger = LoggerFactory.getLogger(IDealPaymentReportServlet.class);

	@Inject OrderFacade orderFacade;
	@Inject PaymentFacade paymentFacade;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// retrieve transaction id from request and retrieve associated order from database
		final String transactionId = request.getParameter("transaction_id");
		logger.info("Received call on idealpaymentreport with transaction ID {}", transactionId);

		if (StringUtils.isBlank(transactionId)) {
			logger.warn("Received call on idealpaymentreport with INVALID transaction ID {}", transactionId);
			response.sendError(500, "Invalid transaction_id");
		} else {
			logger.info("Looking up order with transaction id {}", transactionId);
			final OrderDataDTO orderData = this.orderFacade.getOrderByTransactionID(transactionId);

			logger.info("Updating status for order with transaction id {} to payment pending.", transactionId);
			orderData.setOrderStatus(OrderStatus.PAY_PEND);
			this.orderFacade.updateOrder(orderData);

			// Now that has been confirmed that the payment status is available at the payment provider, retrieve and process these details
			try {
				logger.info("Processing payment details for order with transaction ID {}.", transactionId);
				this.paymentFacade.processIdealPaymentForOrder(orderData);
			} catch (Exception e) {
				// Catch any exception, log it and then consume it. The client (the payment provider) can not handle this.
				logger.error("Error while processing IDeal Payment Details for order with transaction ID: " + transactionId, e);
			}
		}
	}
}