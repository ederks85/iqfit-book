package nl.iqfit.core.configuration;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class IQFitConfig {

	private final String environment;

	private final String mollieURLScheme;
	private final String mollieURLHost;
	private final String mollieURLPath;
	private final String mollieURL;

	private final NameValuePair mollieTestModeParameter;

	IQFitConfig(Configuration configuration) {

		this.environment = configuration.getString("environment");

		this.mollieURLScheme = configuration.getString("payment.mollie.mollieURLScheme");
		this.mollieURLHost = configuration.getString("payment.mollie.mollieURLHost");
		this.mollieURLPath = configuration.getString("payment.mollie.mollieURLPath");
		this.mollieURL = configuration.getString("payment.mollie.mollieURL");

		this.mollieTestModeParameter = new BasicNameValuePair(configuration.getString("payment.mollie.modes[@paramName]"), configuration.getString("payment.mollie.modes.idealBankList[@paramValue]"));
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	public String getMollieURLScheme() {
		return this.mollieURLScheme;
	}

	public String getMollieURLHost() {
		return this.mollieURLHost;
	}

	public String getMollieURLPath() {
		return this.mollieURLPath;
	}

	public String getEnvironment() {
		return this.environment;
	}

	public String getMollieURL() {
		return this.mollieURL;
	}

	public NameValuePair getMollieTestModeParameter() {
		return this.mollieTestModeParameter;
	}
}