package nl.iqfit.logic.facade;

import javax.ejb.Local;
import javax.ejb.Stateless;

import nl.iqfit.core.dto.OrderDataDTO;
import nl.iqfit.logic.order.OrderException;

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
	 * @throws OrderException when placing the new order fails.
	 */
	OrderDataDTO insertOrder(OrderDataDTO orderData) throws OrderException;

	/**
	 * Update an existing order with the details provided.
	 * 
	 * @param orderData	The updated order data.
	 * 
	 * @return The order data, after it has been persisted.
	 * 
	 * @throws OrderException when updating the order fails.
	 */
	OrderDataDTO updateOrder(OrderDataDTO orderData) throws OrderException;
}