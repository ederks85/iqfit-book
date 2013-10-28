package nl.iqfit.logic.order;

import java.util.Date;
import java.util.UUID;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;

import nl.iqfit.core.dto.OrderDataDTO;
import nl.iqfit.logic.db.entity.OrderDAO;

import org.apache.commons.lang.Validate;

@Local
@Stateless
public class OrderhandlerSB implements OrderHandler {

	@Inject OrderDAO orderDAO;

	@Override
	public OrderDataDTO placeNewOrder(final OrderDataDTO orderData) throws OrderException {
		Validate.notNull(orderData, "OrderData is null.");

		// Create a unique order number
		String uuid = UUID.randomUUID().toString();
		orderData.setOrderNumber(uuid);

		// Set order date (now)
		orderData.setOrderDate(new Date());

		// Persist the order
		this.orderDAO.persistNewOrder(orderData);
		
		// Return the order as it has been persisted for possible handling by the caller
		return orderData;
	}
}