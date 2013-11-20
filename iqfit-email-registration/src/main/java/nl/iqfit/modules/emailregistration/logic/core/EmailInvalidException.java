package nl.iqfit.modules.emailregistration.logic.core;

/**
 * Throw this exception when an email address that is requested to be registered has an invalid format.
 * 
 * @author Edwin
 *
 */
public class EmailInvalidException extends Exception {

	public EmailInvalidException(String message) {
		super(message);
	}
}