package nl.iqfit.core.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * JAXB class for unmarshalling a Mollie fetch-mode response a.k.a. initialize payment call.
 * 
 * @author Edwin
 *
 */
@XmlRootElement(name="order")
@XmlAccessorType(XmlAccessType.FIELD)
public class MollieResponseOrder {

	@XmlElement(name="transaction_id", required=true)
	private String transactionId;

	@XmlElement(name="amount", required=true)
	private String amount;

	@XmlElement(name="currency", required=true)
	private String currency;

	@XmlElement(name="payed")
	private String payed;

	@XmlElement(name="URL")
	private String url;

	@XmlElement(name="status")
	private String status;

	@XmlElement(name="consumer")
	private Consumer consumer;

	@XmlElement(name="message", required=true)
	private String message;

	public String getTransactionId() {
		return this.transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getAmount() {
		return this.amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return this.currency;
	}

	public String getPayed() {
		return this.payed;
	}

	public void setPayed(String payed) {
		this.payed = payed;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Consumer getConsumer() {
		return this.consumer;
	}

	public void setConsumer(Consumer consumer) {
		this.consumer = consumer;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}