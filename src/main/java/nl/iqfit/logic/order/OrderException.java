package nl.iqfit.logic.order;

/**
 * Exception to be thrown when persisting or updating an order fails.
 * 
 * @author Edwin
 *
 */
public class OrderException extends Exception {

	public OrderException(String message) {
		super(message);
	}

	public OrderException(String message, Throwable cause) {
		super(message, cause);
	}
}