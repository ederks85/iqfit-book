package nl.iqfit.core.dto;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Immutable DTO object that contains data about a bank.
 * 
 * @author Edwin
 *
 */
public class BankDataDTO {

	private final String bankId;
	private final String bankName;

	public BankDataDTO(String bankId, String bankName) {
		Validate.notNull(bankId, "bankId is null");
		Validate.notNull(bankName, "bankName is null");

		this.bankId = bankId;
		this.bankName = bankName;
	}

	public String getBankId() {
		return this.bankId;
	}

	public String getBankName() {
		return this.bankName;
	}

	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bankId == null) ? 0 : bankId.hashCode());
		result = prime * result	+ ((bankName == null) ? 0 : bankName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BankDataDTO other = (BankDataDTO) obj;
		if (bankId == null) {
			if (other.bankId != null)
				return false;
		} else if (!bankId.equals(other.bankId))
			return false;
		if (bankName == null) {
			if (other.bankName != null)
				return false;
		} else if (!bankName.equals(other.bankName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}