package nl.iqfit.core.configuration;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class IQFitConfig {

	private final String environment;

	private final String mollieURL;

	IQFitConfig(Configuration configuration) {

		this.environment = configuration.getString("environment");

		this.mollieURL = configuration.getString("payment.mollie.mollieURL");
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	public String getEnvironment() {
		return this.environment;
	}

	public String getMollieURL() {
		return this.mollieURL;
	}
}