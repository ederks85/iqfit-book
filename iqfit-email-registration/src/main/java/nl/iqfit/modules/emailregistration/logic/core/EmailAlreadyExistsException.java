package nl.iqfit.modules.emailregistration.logic.core;

/**
 * Throw this exception when an email address that is requested to be registered already is registered.
 * 
 * @author Edwin
 *
 */
public class EmailAlreadyExistsException extends Exception {

	public EmailAlreadyExistsException(String message) {
		super(message);
	}
}