package nl.iqfit.logic.db.entity;

import javax.ejb.Local;
import javax.ejb.Stateless;

@Local
@Stateless
public interface CustomerDAO {

	/**
	 * Retrieve a {@code CustomerEntity} by it's {@code CustomerEntity#getEmailAddress()} field.
	 * 
	 * @param emailAddress	The email address.
	 * 
	 * @return The found entity or null if not found.
	 */
	CustomerEntity getCustomerByEmailAddress(String emailAddress);
}