package nl.iqfit.modules.emailregistration.logic.facade;

import javax.ejb.Local;
import javax.ejb.Stateless;

import nl.iqfit.modules.emailregistration.logic.core.EmailAlreadyExistsException;
import nl.iqfit.modules.emailregistration.logic.core.EmailInvalidException;

/**
 * Facade to handle email registrations.
 * 
 * @author Edwin
 *
 */
@Local
@Stateless
public interface EmailRegistrationFacade {

	/**
	 * Register a new email address for IQFit.
	 * 
	 * @param emailAddress The emailaddress.
	 * 
	 * @throws IllegalArgumentException when emailAddress is null or empty.
	 * @throws EmailAlreadyExistsException when emailAddress is already registered
	 * @throws EmailInvalidException when emaiAddress is of invalid format.
	 */
	void registerEmailAddress(String emailAddress) throws EmailAlreadyExistsException, EmailInvalidException;
}