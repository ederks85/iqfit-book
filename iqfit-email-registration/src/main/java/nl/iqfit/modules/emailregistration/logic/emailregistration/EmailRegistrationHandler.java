package nl.iqfit.modules.emailregistration.logic.emailregistration;

import javax.ejb.Local;
import javax.ejb.Stateless;

import nl.iqfit.modules.emailregistration.logic.core.EmailAlreadyExistsException;
import nl.iqfit.modules.emailregistration.logic.core.EmailInvalidException;

/**
 * @author Edwin
 *
 */
@Local
@Stateless
public interface EmailRegistrationHandler {

	/**
	 * Register a new email address.
	 * 
	 * @param emailAddress The email address.
	 * 
	 * @throws EmailAlreadyExistsException
	 * @throws EmailInvalidException
	 */
	void addEmailAddress(String emailAddress) throws EmailAlreadyExistsException, EmailInvalidException;
}