package nl.iqfit.modules.emailregistration.logic.db.entity;

import java.util.UUID;

import javax.enterprise.util.Nonbinding;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.validator.constraints.Email;

/**
 * Entity that represents a persisted email address.
 * 
 * @author Edwin
 *
 */
@Entity
@Table(name="emailaddress")
public class EmailAddressEntity {

	private long id;
	private String emailAddress;
	private String uniqueID;

	public EmailAddressEntity() {
		this.uniqueID = UUID.randomUUID().toString().replace("-", "").toUpperCase();
	}

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@NotNull
	@Email
	public String getEmailAddress() {
		return this.emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	@NotNull
	@Column(name="uuid")
	public String getUniqueID() {
		return this.uniqueID;
	}

	public void setUniqueID(String uniqueID) {
		this.uniqueID = uniqueID;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}