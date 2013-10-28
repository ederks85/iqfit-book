package nl.iqfit.logic.db.entity;

import javax.ejb.Local;
import javax.ejb.Stateless;

import nl.iqfit.core.dto.OrderDataDTO;
import nl.iqfit.logic.order.OrderException;

/**
 * Class for handling the persistence of {@code OrderEntity} objects.
 * 
 * @author Edwin
 *
 */
@Local
@Stateless
public interface OrderDAO {

	/**
	 * Persist a new order with the provided data.
	 * 
	 * @param orderData The data to persist a new order with.
	 * 
	 * @throws OrderException when persisting the new order fails.
	 */
	public void persistNewOrder(OrderDataDTO orderData) throws OrderException;
}