package nl.iqfit.core.jaxb;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="response")
@XmlAccessorType(XmlAccessType.FIELD)
public class Banks {

	@XmlElement(name="bank")
	private List<Bank> banks = null;

	public List<Bank> getBanks() {
		return this.banks;
	}

	public void setBanks(List<Bank> banks) {
		this.banks = banks;
	}
}