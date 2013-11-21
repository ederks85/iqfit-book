package nl.iqfit.modules.emailregistration.logic.emailregistration;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nl.iqfit.modules.emailregistration.logic.core.EmailAlreadyExistsException;
import nl.iqfit.modules.emailregistration.logic.core.EmailInvalidException;
import nl.iqfit.modules.emailregistration.logic.db.entity.EmailAddressEntity;

@Local(EmailRegistrationHandler.class)
@Stateless
public class EmailRegistrationHandlerSB implements EmailRegistrationHandler {

	private static final Logger logger = LoggerFactory.getLogger(EmailRegistrationHandlerSB.class);

	@PersistenceContext(unitName="iqfit-pu")
	private EntityManager entityManager;

	@Override
	public void addEmailAddress(final String emailAddress) throws EmailAlreadyExistsException, EmailInvalidException {

		logger.info("EmailRegistrationHandlerSB addEmailAddress called for email address: {}", emailAddress);

		if (StringUtils.isEmpty(emailAddress)) {
			logger.warn("EmailRegistrationHandlerSB addEmailAddress called with empty email address");
			throw new EmailInvalidException("Invalid email address: " + emailAddress);
		}

		try {
			EmailAddressEntity emailAddressEntity = new EmailAddressEntity();
			emailAddressEntity.setEmailAddress(emailAddress);
			this.entityManager.persist(emailAddressEntity);
		} catch (RuntimeException e) {
			if (e instanceof ConstraintViolationException) {
				logger.warn("Email Address of invalid format: {}", emailAddress);
				throw new EmailInvalidException("Email Address of invalid format: " + emailAddress);
			} else if (e instanceof EntityExistsException) {
				logger.warn("Email Address already exists: {}", emailAddress);
				throw new EmailAlreadyExistsException("Email Address already exists: " + emailAddress);
			} else {
				throw e;
			}
		}
	}
}