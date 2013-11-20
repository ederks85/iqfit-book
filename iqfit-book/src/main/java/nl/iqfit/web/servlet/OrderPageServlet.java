package nl.iqfit.web.servlet;

import java.io.IOException;
import java.util.Set;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.iqfit.core.dto.BankDataDTO;
import nl.iqfit.logic.facade.PaymentFacade;
import nl.iqfit.logic.payment.PaymentException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servlet that redirects to the page that displays an order and providing the data to fill the page.
 */
@WebServlet("/orderpage")
public class OrderPageServlet extends HttpServlet {

	private static final Logger logger = LoggerFactory.getLogger(OrderPageServlet.class);

	@Inject PaymentFacade paymentFacade;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		logger.trace("OrderPageServlet called");

		try {
			Set<BankDataDTO> bankList = this.paymentFacade.getIdealBankList();
			logger.debug("Retrieved bank list: {}", bankList);

			request.setAttribute("bankList", bankList);
			
			// dispatch to order.jsp
			logger.trace("OrderPageServlet redirect to order.jsp");
			request.getRequestDispatcher("/WEB-INF/views/payment/order.jsp").forward(request, response);
		} catch (PaymentException e) {
			logger.error("Exception while retrieving IDeal bank list in OrderPageServlet", e);
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error while building order page.");
		}
	}
}