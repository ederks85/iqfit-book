package nl.iqfit.logic.customer;

/**
 * Exception to be thrown when trying to use a duplicate email address.
 * 
 * @author Edwin
 *
 */
public class EmailAddressAlreadyExistsException extends Exception {

	public EmailAddressAlreadyExistsException(String message) {
		super(message);
	}
}