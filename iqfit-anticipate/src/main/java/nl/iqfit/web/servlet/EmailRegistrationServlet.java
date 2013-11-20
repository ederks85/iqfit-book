package nl.iqfit.web.servlet;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.iqfit.modules.emailregistration.logic.core.EmailAlreadyExistsException;
import nl.iqfit.modules.emailregistration.logic.core.EmailInvalidException;
import nl.iqfit.modules.emailregistration.logic.facade.EmailRegistrationFacade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(urlPatterns={"/email-registration"})
public class EmailRegistrationServlet extends HttpServlet {

	private static final Logger logger = LoggerFactory.getLogger(EmailRegistrationServlet.class);

	//TODO alle injections private maken
	@Inject private EmailRegistrationFacade emailRegistrationFacade;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		final String emailAddress = request.getParameter("emailAddress");

		logger.info("EmailRegistrationServlet called for email address: {}", emailAddress);

		try {
			this.emailRegistrationFacade.registerEmailAddress(emailAddress);
		} catch (EmailAlreadyExistsException e) {
			logger.warn("Email address {} has already been registered");
		} catch (EmailInvalidException e) {
			logger.warn("Email address {} has invalid format");
		}
	}
}