package nl.iqfit.logic.db.entity;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OrderDAOSB implements OrderDAO {

	private static final Logger logger = LoggerFactory.getLogger(OrderDAOSB.class);

	@PersistenceContext(unitName="iqfit-pu")
	private EntityManager entityManager;

	@Override
	public OrderEntity getOrderById(final long id) {
		logger.debug("Looking up order with id: {}", id);

		return this.entityManager.find(OrderEntity.class, id);
	}

	@Override
	public OrderEntity getOrderByOrderNumber(final String orderNumber) {
		Validate.notNull(orderNumber, "OrderNumber is null.");

		logger.debug("Looking up order with order number: {}", orderNumber);

		final Query query = this.entityManager.createQuery("SELECT o FROM OrderEntity o WHERE o.orderNumber = :orderNumber");
		query.setParameter("orderNumber", orderNumber);

		try {
			return (OrderEntity)query.getSingleResult();
		} catch (NoResultException e) {
			logger.debug("No order found with order number: {}", orderNumber);
			return null;
		}
	}

	@Override
	public OrderEntity getOrderByTransactionID(final String transactionId) {
		Validate.notNull(transactionId, "transactionId is null.");

		logger.debug("Looking up order with transaction id: {}", transactionId);

		final Query query = this.entityManager.createQuery("SELECT o FROM OrderEntity o WHERE o.transactionId = :transactionId");
		query.setParameter("transactionId", transactionId);

		try {
			return (OrderEntity)query.getSingleResult();
		} catch (NoResultException e) {
			logger.debug("No order found with  transaction id: {}", transactionId);
			return null;
		}
	}
}