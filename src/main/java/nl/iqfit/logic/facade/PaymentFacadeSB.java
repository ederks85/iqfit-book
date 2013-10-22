package nl.iqfit.logic.facade;

import java.util.Set;

import javax.inject.Inject;

import nl.iqfit.core.dto.BankDataDTO;
import nl.iqfit.logic.payment.IDealPaymentHandler;
import nl.iqfit.logic.payment.PaymentException;

public class PaymentFacadeSB implements PaymentFacade {

	@Inject IDealPaymentHandler idealPaymentHandler;

	@Override
	public Set<BankDataDTO> getIdealBankList() throws PaymentException {
		return this.idealPaymentHandler.getIdealBankList();
	}
}