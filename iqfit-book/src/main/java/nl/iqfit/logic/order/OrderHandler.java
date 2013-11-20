package nl.iqfit.logic.order;

import javax.ejb.Local;
import javax.ejb.Stateless;

import nl.iqfit.core.dto.OrderDataDTO;

/**
 * Class that handles the operation of persisting and updating orders.
 * 
 * @author Edwin
 *
 */
@Local
@Stateless
public interface OrderHandler {

	/**
	 * Create a new order and persist it.
	 * 
	 * @param orderData	The data to create a new order with.
	 * 
	 * @return an {@code OrderDataDTO} that contains data associated with the newly placed order. Never null.
	 * @throws OrderException when placing the new order fails.
	 */
	OrderDataDTO placeNewOrder(OrderDataDTO orderData) throws OrderException;

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