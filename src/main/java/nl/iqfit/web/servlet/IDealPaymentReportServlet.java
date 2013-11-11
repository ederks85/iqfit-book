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
 * Servlet implementation class PaymentReportServlet
 */
@WebServlet("/idealpaymentreport")
public class IDealPaymentReportServlet extends HttpServlet {

	private static final Logger logger = LoggerFactory.getLogger(IDealPaymentReportServlet.class);

	@Inject OrderFacade orderFacade;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// retrieve transaction id from request and retrieve associated order from database
		final String transactionId = request.getParameter("transaction_id");
		logger.info("Received call on idealpaymentreport with transaction id {}", transactionId);

		if (StringUtils.isBlank(transactionId)) {
			logger.warn("Received call on idealpaymentreport with INVALID transaction id {}", transactionId);
		} else {
			logger.info("Looking up order with transaction id {}", transactionId);
			final OrderDataDTO orderData = this.orderFacade.getOrderByTransactionID(transactionId);

			logger.info("Updating status for order with transaction id {} to payment confirmed.", transactionId);
			orderData.setOrderStatus(OrderStatus.PAY_CONF);
			this.orderFacade.updateOrder(orderData);
			// call backend to retrieve payment details
			
			// call backend to update order with retrieved payment details
		}
	}
}