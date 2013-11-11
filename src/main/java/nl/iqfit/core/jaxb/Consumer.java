package nl.iqfit.core.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@XmlRootElement(name="consumer")
@XmlAccessorType(XmlAccessType.FIELD)
public class Consumer {

	@XmlElement(name="consumerName")
	private String consumerName;

	@XmlElement(name="consumerAccount")
	private String consumerAccount;

	@XmlElement(name="consumerCity")
	private String consumerCity;

	public String getConsumerName() {
		return this.consumerName;
	}

	public void setConsumerName(String consumerName) {
		this.consumerName = consumerName;
	}

	public String getConsumerAccount() {
		return this.consumerAccount;
	}

	public void setConsumerAccount(String consumerAccount) {
		this.consumerAccount = consumerAccount;
	}

	public String getConsumerCity() {
		return this.consumerCity;
	}

	public void setConsumerCity(String consumerCity) {
		this.consumerCity = consumerCity;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}