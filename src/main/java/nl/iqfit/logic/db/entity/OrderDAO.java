package nl.iqfit.logic.db.entity;

import javax.ejb.Local;
import javax.ejb.Stateless;

import nl.iqfit.core.dto.OrderDataDTO;

/**
 * Class for accessing order related data.
 * 
 * @author Edwin
 *
 */
@Local
@Stateless
public interface OrderDAO {

	/**
	 * Retrieve an order by it's unique index.
	 * 
	 * @param id the unique index id of the persisted order.
	 * 
	 * @return The found {@code OrderEntity} or {@code null} when nothing was found.
	 */
	OrderEntity getOrderById(long id);

	/**
	 * Retrieve an order by it's order number.
	 * 
	 * @param orderNumber the {@code OrderEntity#getOrderNumber()} of the persisted {@code OrderEntity}.
	 * 
	 * @return The found {@code OrderEntity} or {@code null} when nothing was found.
	 */
	OrderEntity getOrderByOrderNumber(String orderNumber);

	/**
	 * Retrieve an {@code OrderDataDTO} by it's {@code OrderDataDTO#getTransactionId()}.
	 * 
	 * @param transactionId The {@code OrderDataDTO#getTransactionId()}
	 * 
	 * @return The found {@code OrderDataDTO} or null when not found.
	 */
	OrderEntity getOrderByTransactionID(String transactionId);
}