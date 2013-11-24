package nl.iqfit.modules.emailregistration.logic.emailregistration;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import nl.iqfit.modules.emailregistration.logic.db.entity.EmailAddressEntity;

import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Local(EmailRegistrationDAO.class)
@Stateless
public class EmailRegistrationDAOSB implements EmailRegistrationDAO {

	private static final Logger logger = LoggerFactory.getLogger(EmailRegistrationDAOSB.class);

	@PersistenceContext(unitName="iqfit-pu")
	private EntityManager entityManager;

	@Override
	public EmailAddressEntity getEmailRegistrationByEmailAddress(final String emailAddress) {
		Validate.notNull("emailAddress is null", emailAddress);

		logger.debug("Looking up email registration by email address: {}", emailAddress);

		final Query query = this.entityManager.createQuery("SELECT e FROM EmailAddressEntity e WHERE e.emailAddress = :emailAddress");
		query.setParameter("emailAddress", emailAddress);

		try {
			return (EmailAddressEntity)query.getSingleResult();
		} catch (NoResultException e) {
			logger.debug("No email registration found with email address: {}", emailAddress);
			return null;
		}
	}
}