package nl.iqfit.logic.order;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import nl.iqfit.core.dto.OrderDataDTO;
import nl.iqfit.logic.db.entity.CustomerDAO;
import nl.iqfit.logic.db.entity.CustomerEntity;
import nl.iqfit.logic.db.entity.OrderDAO;
import nl.iqfit.logic.db.entity.OrderEntity;
import nl.iqfit.modules.emailregistration.logic.core.EmailAlreadyExistsException;

import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Local
@Stateless
public class OrderhandlerSB implements OrderHandler {

	private static final Logger logger = LoggerFactory.getLogger(OrderhandlerSB.class);

	@Inject OrderDAO orderDAO;
	@Inject CustomerDAO customerDAO;

	@PersistenceContext(unitName="iqfit-pu")
	private EntityManager entityManager;

	@Override
	public OrderDataDTO placeNewOrder(final OrderDataDTO orderData) throws OrderException, EmailAlreadyExistsException {
		Validate.notNull(orderData, "OrderData is null.");

		logger.info("Persisting new order: {}", orderData);

		// Create a unique order number
		String uuid = UUID.randomUUID().toString().replace("-", "").toUpperCase();
		orderData.setOrderNumber(uuid);

		// Set order date (now)
		orderData.setOrderDate(new Date());

		Validate.notNull(orderData, "OrderData is null.");

		if (this.customerDAO.getCustomerByEmailAddress(orderData.getEmailAddress()) != null) {
			final String message = "Cannot place order because customer with email " + orderData.getEmailAddress() + "already exists.";
			logger.warn(message);
			throw new EmailAlreadyExistsException(message);
		}

		CustomerEntity customer = new CustomerEntity();
		customer.setFirstName(orderData.getFirstName());
		customer.setMiddleName(orderData.getMiddleName());
		customer.setSurName(orderData.getSurName());
		customer.setEmailAddress(orderData.getEmailAddress());

		this.entityManager.persist(customer);
		OrderEntity order = new OrderEntity();
		order.setOrderDate(orderData.getOrderDate());
		order.setOrderNumber(orderData.getOrderNumber());
		order.setCustomerId(customer.getId());

		Set<OrderEntity> orders = new HashSet<OrderEntity>();
		orders.add(order);
		customer.setOrders(orders);
		this.entityManager.merge(customer);

		// Update orderData DTO with order status recieved from the database
		orderData.setOrderStatus(order.getStatus());

		// Return the order as it has been persisted for possible handling by the caller
		return orderData;
	}

	@Override
	public OrderDataDTO updateOrder(final OrderDataDTO orderData) {
		Validate.notNull(orderData, "OrderData is null.");

		logger.info("Updating order: {}", orderData);

		final OrderEntity existingOrderData = this.orderDAO.getOrderByOrderNumber(orderData.getOrderNumber());
		if (existingOrderData == null) {
			throw new IllegalStateException("Order with order number " + orderData.getOrderNumber() + " was not found.");
		}
	
		existingOrderData.setOrderNumber(orderData.getOrderNumber());
		existingOrderData.setOrderDate(orderData.getOrderDate());
		existingOrderData.setStatus(orderData.getOrderStatus());
		existingOrderData.setTransactionId(orderData.getTransactionId());
		existingOrderData.setDownloaded(orderData.isDownloaded());

		return orderData;
	}

	@Override
	public OrderDataDTO getOrderByTransactionID(final String transactionId) {
		Validate.notEmpty(transactionId, "transactionId is null or empty.");

		logger.info("Looking up order by with transaction id: {}", transactionId);

		OrderEntity orderEntity = this.orderDAO.getOrderByTransactionID(transactionId);
		if (orderEntity == null) {
			logger.info("No order found with transaction id: {}", transactionId);
			return null;
		} else {
			logger.info("Found order with transaction id: {}", transactionId);

			OrderDataDTO orderDataDTO = new OrderDataDTO();
			orderDataDTO.setOrderNumber(orderEntity.getOrderNumber());
			orderDataDTO.setOrderDate(orderEntity.getOrderDate());
			orderDataDTO.setOrderStatus(orderEntity.getStatus());
			orderDataDTO.setTransactionId(orderEntity.getTransactionId());
			orderDataDTO.setDownloaded(orderEntity.isDownloaded());

			return orderDataDTO;
		}
	}

	@Override
	public OrderDataDTO getOrderByOrderNumber(final String orderNumber) {
		Validate.notEmpty(orderNumber, "orderNumber is null or empty.");

		logger.info("Looking up order by with order number: {}", orderNumber);

		OrderEntity orderEntity = this.orderDAO.getOrderByOrderNumber(orderNumber);
		if (orderEntity == null) {
			logger.info("No order found with order number: {}", orderNumber);
			return null;
		} else {
			logger.info("Found order with order number: {}", orderNumber);

			OrderDataDTO orderDataDTO = new OrderDataDTO();
			orderDataDTO.setOrderNumber(orderEntity.getOrderNumber());
			orderDataDTO.setOrderDate(orderEntity.getOrderDate());
			orderDataDTO.setOrderStatus(orderEntity.getStatus());
			orderDataDTO.setTransactionId(orderEntity.getTransactionId());
			orderDataDTO.setDownloaded(orderEntity.isDownloaded());

			return orderDataDTO;
		}
	}
}