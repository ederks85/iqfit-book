package nl.iqfit.logic.facade;

import javax.inject.Inject;

import org.apache.commons.lang.Validate;

import nl.iqfit.core.dto.OrderDataDTO;
import nl.iqfit.logic.order.OrderException;
import nl.iqfit.logic.order.OrderHandler;

public class OrderFacadeSB implements OrderFacade {

	@Inject OrderHandler orderHandler;

	@Override
	public OrderDataDTO insertOrder(final OrderDataDTO orderData) throws OrderException {
		Validate.notNull(orderData, "OrderData is null.");

		return this.orderHandler.placeNewOrder(orderData);
	}

	@Override
	public OrderDataDTO updateOrder(OrderDataDTO orderData) {
		Validate.notNull(orderData, "OrderData is null.");

		return this.orderHandler.updateOrder(orderData);
	}

	@Override
	public OrderDataDTO getOrderByTransactionID(String transactionId) {
		Validate.notEmpty(transactionId, "transactionId is null or empty.");

		return this.orderHandler.getOrderByTransactionID(transactionId);
	}

	@Override
	public OrderDataDTO getOrderByOrderNumber(String orderNumber) {
		Validate.notEmpty(orderNumber, "orderNumber is null or empty.");

		return this.orderHandler.getOrderByOrderNumber(orderNumber);
	}
}