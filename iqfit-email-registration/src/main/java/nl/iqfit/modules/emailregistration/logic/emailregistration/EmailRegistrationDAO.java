package nl.iqfit.modules.emailregistration.logic.emailregistration;

import javax.ejb.Local;
import javax.ejb.Stateless;

import nl.iqfit.modules.emailregistration.logic.db.entity.EmailAddressEntity;


/**
 * @author Edwin
 */
@Local
@Stateless
public interface EmailRegistrationDAO {

	/**
	 * Get an {@code EmailAddressEntity} by it's {@code EmailAddressEntity#getEmailAddress()} field.
	 * 
	 * @param emailAddress	The email address to match with.
	 * 
	 * @return null if not found.
	 */
	EmailAddressEntity getEmailRegistrationByEmailAddress(String emailAddress);
}