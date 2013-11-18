package nl.iqfit.web.filter;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.iqfit.core.configuration.IQFitConfig;
import nl.iqfit.core.configuration.IQFitConfigurationFactory;
import nl.iqfit.core.dto.OrderDataDTO;
import nl.iqfit.logic.db.entity.OrderStatus;
import nl.iqfit.logic.facade.OrderFacade;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Filter that checks if the {@code PaymentReturnServlet} can be called, based on a defined timeout.
 * 
 * @author Edwin
 *
 */
@WebFilter(urlPatterns={"/paymentreturn"})
public class PaymentReturnRetryFilter implements Filter {

	private static final Logger logger = LoggerFactory.getLogger(PaymentReturnRetryFilter.class);

	private IQFitConfig config;

	@Inject OrderFacade orderFacade;

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)resp;

		final String transactionId = request.getParameter("transaction_id");
		logger.info("Received call on payment return with transaction id {}", transactionId);

		if (this.config == null) {
			logger.error("IQFit configuration is null in PaymentReturnRetryFilter");
			response.sendError(500);
			return;
		}

		if (StringUtils.isBlank(transactionId)) {
			logger.warn("Received call on payment return filter with INVALID transaction id {}", transactionId);
			response.sendError(500, "Invalid transaction_id");
		} else {

			OrderDataDTO order = this.orderFacade.getOrderByTransactionID(transactionId);
			if (order == null) {
				logger.warn("Order with transaction ID {} was not found", transactionId);
				response.sendError(500);
				return;
			}
			logger.info("Found order {} with transaction ID {}", order, transactionId);

			// If the payment for the order has been confirmed, proceed
			if (order.getOrderStatus() == OrderStatus.PAY_SUCC) {
				chain.doFilter(request, response);
			} else {
				// If the payment for the order has not been confirmed, create a timeout counter and proceed to check the current payment status
				Integer counter = (Integer)request.getSession().getAttribute("paymentReturnTimeOutCounter");
				if (counter == null) {
					counter = new Integer(this.config.getPaymentReturnTimeOutCounts());
					logger.debug("Initializing paymentReturnTimeOutCounts to {} for transaction ID: {}", counter, transactionId);
	
					request.getSession().setAttribute("paymentReturnTimeOutCounter", counter);
					chain.doFilter(request, response);
				} else {
					if (counter >= 0) {
						counter = counter - 1;
						logger.debug("{} PaymentReturnTimeOutCounts left for transaction ID: {}", counter, transactionId);
						
						request.getSession().setAttribute("paymentReturnTimeOutCounter", counter);
						chain.doFilter(request, response);
					} else {
						request.getRequestDispatcher("/WEB-INF/views/payment/paymenttimeout.jsp").forward(request, response);
					}
				}
			}
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.config = new IQFitConfigurationFactory().getIQFitConfig();
	}

	@Override
	public void destroy() {
		this.config = null;
	}
}