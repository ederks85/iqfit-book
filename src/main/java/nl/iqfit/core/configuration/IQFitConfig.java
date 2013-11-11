package nl.iqfit.core.configuration;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class IQFitConfig {

	private final String environment;
	private final boolean testEnvironment;

	private final String iqFitURLScheme;
	private final String iqFitURLHost;
	private final String iqFitURL;

	private final String mollieURLScheme;
	private final String mollieURLHost;
	private final String mollieURLPath;
	private final String mollieURL;

	private final NameValuePair mollieTestModeParameter;

	private final NameValuePair mollieBankListModeParameter;

	private final NameValuePair mollieFetchModeParameter;
	private final NameValuePair mollieFetchModePartnerIdParameter;
	private final NameValuePair mollieFetchModeRequestAmountParameter;
	private final NameValuePair mollieFetchModeBankIdParameter;
	private final NameValuePair mollieFetchModeDescriptionParameter;
	private final NameValuePair mollieFetchModeLocalAfterPaymentReportURLParameter;
	private final NameValuePair mollieFetchModeLocalAfterPaymentClientReturnURLParameter;
	private final NameValuePair mollieFetchModeProfileKeyParameter;
	private final NameValuePair mollieFetchModeTransactionIdParameter;
	private final NameValuePair mollieFetchModeResponseAmountParameter;
	private final NameValuePair mollieFetchModeRemoteStartPaymentClientRedirectURLParameter;

	IQFitConfig(Configuration configuration) {

		this.environment = configuration.getString("environment");
		this.testEnvironment = configuration.getBoolean("testEnvironment");

		this.iqFitURLScheme = configuration.getString("IQFitURLScheme");
		this.iqFitURLHost = configuration.getString("IQFitURLHost");
		this.iqFitURL = configuration.getString("IQFitURL");

		this.mollieURLScheme = configuration.getString("payment.mollie.mollieURLScheme");
		this.mollieURLHost = configuration.getString("payment.mollie.mollieURLHost");
		this.mollieURLPath = configuration.getString("payment.mollie.mollieURLPath");
		this.mollieURL = configuration.getString("payment.mollie.mollieURL");

		this.mollieTestModeParameter = new BasicNameValuePair(configuration.getString("payment.mollie.modes.mode(0).test[@paramName]"), configuration.getString("payment.mollie.modes.mode(0).test[@paramValue]"));

		this.mollieBankListModeParameter = new BasicNameValuePair(configuration.getString("payment.mollie.modes.mode(1)[@paramName]"), configuration.getString("payment.mollie.modes.mode(1).idealBankList[@paramValue]"));

		this.mollieFetchModeParameter = new BasicNameValuePair(configuration.getString("payment.mollie.modes.mode(1)[@paramName]"), configuration.getString("payment.mollie.modes.mode(1).fetch[@paramValue]"));
		this.mollieFetchModePartnerIdParameter = new BasicNameValuePair(configuration.getString("payment.mollie.modes.mode(2).partnerId[@paramName]"), configuration.getString("payment.mollie.modes.mode(2).partnerId[@paramValue]"));
		this.mollieFetchModeRequestAmountParameter = new BasicNameValuePair(configuration.getString("payment.mollie.modes.mode(2).requestAmount[@paramName]"), configuration.getString("payment.mollie.modes.mode(2).requestAmount[@paramValue]"));
		this.mollieFetchModeBankIdParameter = new BasicNameValuePair(configuration.getString("payment.mollie.modes.mode(2).bankId[@paramName]"), configuration.getString("payment.mollie.modes.mode(2).bankId[@paramValue]"));
		this.mollieFetchModeDescriptionParameter = new BasicNameValuePair(configuration.getString("payment.mollie.modes.mode(2).description[@paramName]"), configuration.getString("payment.mollie.modes.mode(2).description[@paramValue]"));
		this.mollieFetchModeLocalAfterPaymentReportURLParameter = new BasicNameValuePair(configuration.getString("payment.mollie.modes.mode(2).localAfterPaymentReportURL[@paramName]"), configuration.getString("payment.mollie.modes.mode(2).localAfterPaymentReportURL[@paramValue]"));
		this.mollieFetchModeLocalAfterPaymentClientReturnURLParameter = new BasicNameValuePair(configuration.getString("payment.mollie.modes.mode(2).localAfterPaymentClientReturnURL[@paramName]"), configuration.getString("payment.mollie.modes.mode(2).localAfterPaymentClientReturnURL[@paramValue]"));
		this.mollieFetchModeProfileKeyParameter = new BasicNameValuePair(configuration.getString("payment.mollie.modes.mode(2).profileKey[@paramName]"), configuration.getString("payment.mollie.modes.mode(2).profileKey[@paramValue]"));
		this.mollieFetchModeTransactionIdParameter = new BasicNameValuePair(configuration.getString("payment.mollie.modes.mode(2).transactionId[@paramName]"), configuration.getString("payment.mollie.modes.mode(2).transactionId[@paramValue]"));
		this.mollieFetchModeResponseAmountParameter = new BasicNameValuePair(configuration.getString("payment.mollie.modes.mode(2).responseAmount[@paramName]"), configuration.getString("payment.mollie.modes.mode(2).responseAmount[@paramValue]"));
		this.mollieFetchModeRemoteStartPaymentClientRedirectURLParameter = new BasicNameValuePair(configuration.getString("payment.mollie.modes.mode(2).remoteStartPaymentClientRedirectURL[@paramName]"), configuration.getString("payment.mollie.modes.mode(2).remoteStartPaymentClientRedirectURL[@paramValue]"));
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	public String getEnvironment() {
		return this.environment;
	}

	public boolean isTestEnvironment() {
		return this.testEnvironment;
	}

	public String getIqFitURLScheme() {
		return this.iqFitURLScheme;
	}

	public String getIqFitURLHost() {
		return this.iqFitURLHost;
	}

	public String getIqFitURL() {
		return this.iqFitURL;
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

	public String getMollieURL() {
		return this.mollieURL;
	}

	public NameValuePair getMollieTestModeParameter() {
		return this.mollieTestModeParameter;
	}

	public NameValuePair getMollieBankListModeParameter() {
		return this.mollieBankListModeParameter;
	}

	public NameValuePair getMollieFetchModeParameter() {
		return this.mollieFetchModeParameter;
	}

	public NameValuePair getMollieFetchModePartnerIdParameter() {
		return this.mollieFetchModePartnerIdParameter;
	}

	public NameValuePair getMollieFetchModeRequestAmountParameter() {
		return this.mollieFetchModeRequestAmountParameter;
	}

	public NameValuePair getMollieFetchModeBankIdParameter() {
		return this.mollieFetchModeBankIdParameter;
	}

	public NameValuePair getMollieFetchModeDescriptionParameter() {
		return this.mollieFetchModeDescriptionParameter;
	}

	public NameValuePair getMollieFetchModeLocalAfterPaymentReportURLParameter() {
		return this.mollieFetchModeLocalAfterPaymentReportURLParameter;
	}

	public NameValuePair getMollieFetchModeLocalAfterPaymentClientReturnURLParameter() {
		return this.mollieFetchModeLocalAfterPaymentClientReturnURLParameter;
	}

	public NameValuePair getMollieFetchModeProfileKeyParameter() {
		return this.mollieFetchModeProfileKeyParameter;
	}

	public NameValuePair getMollieFetchModeTransactionIdParameter() {
		return this.mollieFetchModeTransactionIdParameter;
	}

	public NameValuePair getMollieFetchModeResponseAmountParameter() {
		return this.mollieFetchModeResponseAmountParameter;
	}

	public NameValuePair getMollieFetchModeRemoteStartPaymentClientRedirectURLParameter() {
		return this.mollieFetchModeRemoteStartPaymentClientRedirectURLParameter;
	}
}