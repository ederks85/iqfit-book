package nl.iqfit.logic.facade;

import javax.inject.Inject;

import org.apache.commons.lang.Validate;

import nl.iqfit.core.dto.OrderDataDTO;
import nl.iqfit.logic.order.OrderException;
import nl.iqfit.logic.order.OrderHandler;

public class OrderFacadeSB implements OrderFacade {

	@Inject OrderHandler orderHandler;

	@Override
	public OrderDataDTO placeNewOrder(final OrderDataDTO orderData) throws OrderException {
		Validate.notNull(orderData, "OrderData is null.");

		return this.orderHandler.placeNewOrder(orderData);
	}
}