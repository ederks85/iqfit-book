package nl.iqfit.logic.facade;

import javax.ejb.Local;
import javax.ejb.Stateless;

import nl.iqfit.core.dto.OrderDataDTO;
import nl.iqfit.logic.order.OrderException;
import nl.iqfit.modules.emailregistration.logic.core.EmailAlreadyExistsException;

/**
 * Facade that handles retrieval, updating and persistence of orders.
 * 
 * @author Edwin
 *
 */
@Local
@Stateless
public interface OrderFacade {

	/**
	 * Create a new order and persist it.
	 * 
	 * @param orderData	The data to create a new order with.
	 * 
	 * @return an {@code OrderDataDTO} that contains data associated with the newly placed order. Never null.
	 * 
	 * @throws OrderException when placing the new order fails.
	 * @throws EmailAlreadyExistsException for now, one order per email is allowed.
	 */
	OrderDataDTO insertOrder(OrderDataDTO orderData) throws OrderException, EmailAlreadyExistsException;

	/**
	 * Update an existing order with the details provided.
	 * 
	 * @param orderData	The updated order data.
	 * 
	 * @return The order data, after it has been persisted.
	 */
	OrderDataDTO updateOrder(OrderDataDTO orderData);

	/**
	 * Retrieve an {@code OrderDataDTO} by it's {@code OrderDataDTO#getTransactionId()}.
	 * 
	 * @param transactionId The {@code OrderDataDTO#getTransactionId()}
	 * 
	 * @return The found {@code OrderDataDTO} or null when not found.
	 */
	OrderDataDTO getOrderByTransactionID(String transactionId);

	/**
	 * Retrieve an {@code OrderDataDTO} by it's {@code OrderDataDTO#getOrderNumber()}.
	 * 
	 * @param orderNumber The {@code OrderDataDTO#getOrderNumber()}
	 * 
	 * @return The found {@code OrderDataDTO} or null when not found.
	 */
	OrderDataDTO getOrderByOrderNumber(String orderNumber);
}