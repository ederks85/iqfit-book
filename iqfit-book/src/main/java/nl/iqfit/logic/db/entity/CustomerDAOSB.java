package nl.iqfit.logic.db.entity;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomerDAOSB implements CustomerDAO {

	private static final Logger logger = LoggerFactory.getLogger(CustomerDAOSB.class);

	@PersistenceContext(unitName="iqfit-pu")
	private EntityManager entityManager;

	@Override
	public CustomerEntity getCustomerByEmailAddress(String emailAddress) {
		Validate.notNull(emailAddress, "EmailAddress is null.");

		logger.debug("Looking up customer with email address: {}", emailAddress);

		final Query query = this.entityManager.createQuery("SELECT c FROM CustomerEntity c WHERE c.emailAddress = :emailAddres");
		query.setParameter("emailAddres", emailAddress);

		try {
			return (CustomerEntity)query.getSingleResult();
		} catch (NoResultException e) {
			logger.debug("No customer found with email addres: {}", emailAddress);
			return null;
		}
	}
}