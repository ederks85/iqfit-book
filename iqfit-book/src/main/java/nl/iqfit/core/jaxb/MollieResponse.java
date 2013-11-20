package nl.iqfit.core.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * JAXB class for unmarshalling a Mollie {@code MollieResponseOrder} object.
 * 
 * @author Edwin
 *
 */
@XmlRootElement(name="response")
@XmlAccessorType(XmlAccessType.FIELD)
public class MollieResponse {

	@XmlElement(name="order", required=true)
	private MollieResponseOrder order;

	public MollieResponseOrder getOrder() {
		return this.order;
	}

	public void setOrder(MollieResponseOrder order) {
		this.order = order;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}