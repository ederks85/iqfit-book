package nl.iqfit.modules.emailregistration.logic.facade;

import javax.ejb.Local;
import javax.ejb.Stateless;

import nl.iqfit.modules.emailregistration.logic.core.EmailAlreadyExistsException;
import nl.iqfit.modules.emailregistration.logic.core.EmailInvalidException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Local(EmailRegistrationFacade.class)
@Stateless
public class EmailRegistrationFacadeSB implements EmailRegistrationFacade {

	private static final Logger logger = LoggerFactory.getLogger(EmailRegistrationFacadeSB.class);

	@Override
	public void registerEmailAddress(String emailAddress) throws EmailAlreadyExistsException, EmailInvalidException {
		logger.info("registerEmailAddress called for email address: {}", emailAddress);
	}
}