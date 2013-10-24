package nl.iqfit.core.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Immutable DTO object that contains data about a bank.
 * 
 * @author Edwin
 *
 */
@XmlRootElement(name="bank")
@XmlAccessorType(XmlAccessType.FIELD)
public class Bank {

	@XmlElement(name="bank_id", required=true)
	private String bankId;

	@XmlElement(name="bank_name", required=true)
	private String bankName;

	public String getBankId() {
		return this.bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public String getBankName() {
		return this.bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}