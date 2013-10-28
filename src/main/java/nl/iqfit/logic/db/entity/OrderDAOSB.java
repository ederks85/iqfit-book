package nl.iqfit.logic.db.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import nl.iqfit.core.dto.OrderDataDTO;
import nl.iqfit.logic.order.OrderException;

import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OrderDAOSB implements OrderDAO {

	private static final Logger logger = LoggerFactory.getLogger(OrderDAOSB.class);

	@PersistenceContext(unitName="iqfit-pu")
	private EntityManager entityManager;

	@Override
	public void persistNewOrder(final OrderDataDTO orderData) throws OrderException {
		logger.info("Persisting new order: {}", orderData);

		Validate.notNull(orderData, "OrderData is null.");

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
	}
}