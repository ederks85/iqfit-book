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
import nl.iqfit.core.dto.OrderDataDTO;
import nl.iqfit.core.util.RequestHelper;
import nl.iqfit.logic.facade.OrderFacade;
import nl.iqfit.logic.facade.PaymentFacade;
import nl.iqfit.logic.order.OrderException;
import nl.iqfit.logic.payment.PaymentException;
import nl.iqfit.modules.emailregistration.logic.core.EmailAlreadyExistsException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servlet for placing an order and preparing the payment at the payment provider.
 * 
 * @author Edwin
 */
@WebServlet("/placeorder")
public class PlaceOrderServlet extends HttpServlet {

	private static final Logger logger = LoggerFactory.getLogger(PlaceOrderServlet.class);

	@Inject OrderFacade orderFacade;
	@Inject PaymentFacade paymentFacade;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		final RequestHelper rh = new RequestHelper();
		logger.info("Recieved new order with parameters: {}", rh.getRequestParametersToString(request));

		final String firstName		= rh.getRequestParameter(request, "firstName");
		final String middleName		= rh.getRequestParameter(request, "middleName");
		final String surName		= rh.getRequestParameter(request, "surName");
		final String emailAddress	= rh.getRequestParameter(request, "emailAddress");
		final String chosenBank		= rh.getRequestParameter(request, "bankList");

		// check if the provided bank is a valid option for the payment provider
		BankDataDTO bank = null;
		try {
			Set<BankDataDTO> bankList = this.paymentFacade.getIdealBankList();
			for (BankDataDTO bankData : bankList) {

				logger.debug("Checking if chosen bank with ID {} is a valid payment option.");
				if (chosenBank.equals(bankData.getBankId())) {

					logger.debug("Valid bank {} matches chosen bank {}? {}", new Object[]{bankData.getBankId(), chosenBank, chosenBank.equals(bankData.getBankId())});
					bank = bankData;
					break;
				}
			}

			if (bank == null) {
				throw new IllegalStateException("Bank with ID " + chosenBank + " is not a valid option for payment.");
			}
		} catch (PaymentException e) {
			final String message = "Error while placing new order";

			logger.error(message, e);
			throw new ServletException(message, e);
		}
		

		// Persist order
		final OrderDataDTO orderData = new OrderDataDTO();
		orderData.setFirstName(firstName);
		orderData.setMiddleName(middleName);
		orderData.setSurName(surName);
		orderData.setEmailAddress(emailAddress);

		try {
			this.orderFacade.insertOrder(orderData);
		} catch (OrderException e) {
			final String message = "Error while placing new order";

			logger.error(message, e);
			response.sendError(500);
		} catch (EmailAlreadyExistsException e) {
			logger.warn(e.getMessage());

			request.setAttribute("firstName", firstName);
			request.setAttribute("middleName", middleName);
			request.setAttribute("surName", surName);
			request.setAttribute("emailAddress", emailAddress);
			request.setAttribute("bankList", chosenBank);
			request.setAttribute("error", "Dit email adres is al in gebruik. Probeer een andere email adres of neem contact met ons op."); //FIXME resource bundle voor errors definiëren?
			request.getRequestDispatcher("/orderpage").forward(request, response);
			return;
		}

		// prepare the IDeal payment at the payment provider.
		final String redirectUrl;
		try {
			redirectUrl = this.paymentFacade.initializeIdealPayment(orderData, bank);
			logger.info("Recieved redirectUrl {} for order {} and bank {}.", new Object[]{redirectUrl, orderData, bank});
		} catch (PaymentException e) {
			final String message = "Error while placing new order";

			logger.error(message, e);
			throw new ServletException(message, e);
		}

		// redirect client to provided bank page
		logger.info("Redirecting to redirectUrl {} for order {} and bank {}.", new Object[]{redirectUrl, orderData, bank});
		response.sendRedirect(response.encodeRedirectURL(redirectUrl));
	}
}