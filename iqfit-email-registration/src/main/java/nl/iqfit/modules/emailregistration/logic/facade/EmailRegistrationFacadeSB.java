package nl.iqfit.modules.emailregistration.logic.facade;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;

import nl.iqfit.modules.emailregistration.logic.core.EmailAlreadyExistsException;
import nl.iqfit.modules.emailregistration.logic.core.EmailInvalidException;
import nl.iqfit.modules.emailregistration.logic.emailregistration.EmailRegistrationHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Local(EmailRegistrationFacade.class)
@Stateless
public class EmailRegistrationFacadeSB implements EmailRegistrationFacade {

	private static final Logger logger = LoggerFactory.getLogger(EmailRegistrationFacadeSB.class);

	@Inject private EmailRegistrationHandler emailRegistrationHandler;

	@Override
	public void registerEmailAddress(String emailAddress) throws EmailAlreadyExistsException, EmailInvalidException {
		logger.info("registerEmailAddress called for email address: {}", emailAddress);

		this.emailRegistrationHandler.addEmailAddress(emailAddress);
	}
}