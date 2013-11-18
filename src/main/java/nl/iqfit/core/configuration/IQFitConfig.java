package nl.iqfit.core.configuration;

import java.util.Collections;
import java.util.List;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class IQFitConfig {

	private final String		environment;
	private final boolean		testEnvironment;

	private final String		iqFitURLScheme;
	private final String		iqFitURLHost;
	private final String		iqFitURL;
	private final String		iqFitIdealPaymentReportPath;
	private final String		iqFitPaymentReturnPath;

	private final String		mollieURLScheme;
	private final String		mollieURLHost;
	private final String		mollieURLPath;
	private final String		mollieURL;

	private final NameValuePair	mollieTestModeParameter;

	private final NameValuePair	mollieBankListModeParameter;

	private final NameValuePair	mollieFetchModeParameter;
	private final NameValuePair	mollieFetchModePartnerIdParameter;
	private final NameValuePair	mollieFetchModeRequestAmountParameter;
	private final NameValuePair	mollieFetchModeBankIdParameter;
	private final NameValuePair	mollieFetchModeDescriptionParameter;
	private final NameValuePair	mollieFetchModeLocalAfterPaymentReportURLParameter;
	private final NameValuePair	mollieFetchModeLocalAfterPaymentClientReturnURLParameter;
	private final NameValuePair	mollieFetchModeProfileKeyParameter;
	private final NameValuePair	mollieFetchModeTransactionIdParameter;
	private final NameValuePair	mollieFetchModeResponseAmountParameter;
	private final NameValuePair	mollieFetchModeRemoteStartPaymentClientRedirectURLParameter;

	private final NameValuePair	mollieCheckModeParameter;
	private final NameValuePair	mollieCheckModePartnerIdParameter;
	private final NameValuePair	mollieCheckModeRequestTransactionIdParameter;
	private final NameValuePair	mollieCheckModeResponseAmountParameter;
	private final String		mollieCheckModeResponseStatusOptionsParameterName;
	private final NameValuePair	mollieCheckModeResponseStatusOptionsSuccessParameter;
	private final NameValuePair	mollieCheckModeResponseStatusOptionsCancelParameter;
	private final NameValuePair	mollieCheckModeResponseStatusOptionsFailureParameter;
	private final NameValuePair	mollieCheckModeResponseStatusOptionsExpireParameter;
	private final NameValuePair	mollieCheckModeResponseStatusOptionsCheckedBeforeParameter;

	private final int 			paymentReturnTimeOutCounts;

	IQFitConfig(Configuration configuration) {

		this.environment = configuration.getString("environment");
		this.testEnvironment = configuration.getBoolean("testEnvironment");

		this.iqFitURLScheme = configuration.getString("IQFitURLScheme");
		this.iqFitURLHost = configuration.getString("IQFitURLHost");
		this.iqFitURL = configuration.getString("IQFitURL");
		this.iqFitIdealPaymentReportPath = configuration.getString("IQFitIdealPaymentReportPath");
		this.iqFitPaymentReturnPath = configuration.getString("IQFitPaymentReturnPath");

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

		this.mollieCheckModeParameter = new BasicNameValuePair(configuration.getString("payment.mollie.modes.mode(1)[@paramName]"), configuration.getString("payment.mollie.modes.mode(1).check[@paramValue]"));
		this.mollieCheckModePartnerIdParameter = new BasicNameValuePair(configuration.getString("payment.mollie.modes.mode(3).partnerId[@paramName]"), configuration.getString("payment.mollie.modes.mode(3).partnerId[@paramValue]"));
		this.mollieCheckModeRequestTransactionIdParameter = new BasicNameValuePair(configuration.getString("payment.mollie.modes.mode(3).requestTransactionId[@paramName]"), configuration.getString("payment.mollie.modes.mode(3).requestTransactionId[@paramValue]"));
		this.mollieCheckModeResponseAmountParameter = new BasicNameValuePair(configuration.getString("payment.mollie.modes.mode(3).responseAmount[@paramName]"), configuration.getString("payment.mollie.modes.mode(3).responseAmount[@paramValue]"));
		this.mollieCheckModeResponseStatusOptionsParameterName = configuration.getString("payment.mollie.modes.mode(3).status[@paramName]");
		this.mollieCheckModeResponseStatusOptionsSuccessParameter = new BasicNameValuePair(configuration.getString("payment.mollie.modes.mode(3).status[@paramName]"), configuration.getString("payment.mollie.modes.mode(3).status.successStatusOption[@paramValue]"));
		this.mollieCheckModeResponseStatusOptionsCancelParameter = new BasicNameValuePair(configuration.getString("payment.mollie.modes.mode(3).status[@paramName]"), configuration.getString("payment.mollie.modes.mode(3).status.cancelledCtatusOption[@paramValue]"));
		this.mollieCheckModeResponseStatusOptionsFailureParameter = new BasicNameValuePair(configuration.getString("payment.mollie.modes.mode(3).status[@paramName]"), configuration.getString("payment.mollie.modes.mode(3).status.failureStatusOption[@paramValue]"));
		this.mollieCheckModeResponseStatusOptionsExpireParameter = new BasicNameValuePair(configuration.getString("payment.mollie.modes.mode(3).status[@paramName]"), configuration.getString("payment.mollie.modes.mode(3).status.expiredStatusOption[@paramValue]"));
		this.mollieCheckModeResponseStatusOptionsCheckedBeforeParameter = new BasicNameValuePair(configuration.getString("payment.mollie.modes.mode(3).status[@paramName]"), configuration.getString("payment.mollie.modes.mode(3).status.checkedBeforeStatusOption[@paramValue]"));

		this.paymentReturnTimeOutCounts = configuration.getInt("payment.mollie.paymentReturnTimeOutCounts");
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

	public String getIqFitIdealPaymentReportPath() {
		return this.iqFitIdealPaymentReportPath;
	}

	public String getIqFitPaymentReturnPath() {
		return this.iqFitPaymentReturnPath;
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

	public NameValuePair getMollieCheckModeParameter() {
		return this.mollieCheckModeParameter;
	}

	public NameValuePair getMollieCheckModePartnerIdParameter() {
		return this.mollieCheckModePartnerIdParameter;
	}

	public NameValuePair getMollieCheckModeRequestTransactionIdParameter() {
		return this.mollieCheckModeRequestTransactionIdParameter;
	}

	public NameValuePair getMollieCheckModeResponseAmountParameter() {
		return this.mollieCheckModeResponseAmountParameter;
	}

	public String getMollieCheckModeResponseStatusOptionsParameterName() {
		return this.mollieCheckModeResponseStatusOptionsParameterName;
	}

	public NameValuePair getMollieCheckModeResponseStatusOptionsSuccessParameter() {
		return this.mollieCheckModeResponseStatusOptionsSuccessParameter;
	}

	public NameValuePair getMollieCheckModeResponseStatusOptionsCancelParameter() {
		return this.mollieCheckModeResponseStatusOptionsCancelParameter;
	}

	public NameValuePair getMollieCheckModeResponseStatusOptionsFailureParameter() {
		return this.mollieCheckModeResponseStatusOptionsFailureParameter;
	}

	public NameValuePair getMollieCheckModeResponseStatusOptionsExpireParameter() {
		return this.mollieCheckModeResponseStatusOptionsExpireParameter;
	}

	public NameValuePair getMollieCheckModeResponseStatusOptionsCheckedBeforeParameter() {
		return this.mollieCheckModeResponseStatusOptionsCheckedBeforeParameter;
	}

	public int getPaymentReturnTimeOutCounts() {
		return this.paymentReturnTimeOutCounts;
	}
}