package nl.iqfit.web.servlet;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.iqfit.core.dto.OrderDataDTO;
import nl.iqfit.core.util.RequestHelper;
import nl.iqfit.logic.facade.OrderFacade;
import nl.iqfit.logic.order.OrderException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Edwin
 */
@WebServlet("/placeorder")
public class PlaceOrderServlet extends HttpServlet {

	private static final Logger logger = LoggerFactory.getLogger(PlaceOrderServlet.class);

	@Inject OrderFacade orderFacade;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		final RequestHelper rh = new RequestHelper();
		logger.info("Received new order with parameters: {}", rh.getRequestParametersToString(request));

		final String firstName		= rh.getRequestParameter(request, "firstName");
		final String middleName		= rh.getRequestParameter(request, "middleName");
		final String surName		= rh.getRequestParameter(request, "surName");
		final String emailAddress	= rh.getRequestParameter(request, "emailAddress");

		// Persist order
		final OrderDataDTO orderData = new OrderDataDTO();
		orderData.setFirstName(firstName);
		orderData.setMiddleName(middleName);
		orderData.setSurName(surName);
		orderData.setEmailAddress(emailAddress);

		try {
			this.orderFacade.placeNewOrder(orderData);

			// call backend to prepare payment.
			
			// process prepared payment details
			
			// redirect client to bank page
			response.sendRedirect(response.encodeRedirectURL("/bank"));
		} catch (OrderException e) {
			final String message = "Error while placing new order";

			logger.error(message, e);
			throw new ServletException(message, e);
		}
	}
}