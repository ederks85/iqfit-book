package nl.iqfit.logic.facade;

import java.util.Set;

import javax.inject.Inject;

import nl.iqfit.core.dto.BankDataDTO;
import nl.iqfit.core.dto.OrderDataDTO;
import nl.iqfit.logic.payment.IDealPaymentHandler;
import nl.iqfit.logic.payment.PaymentException;

import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PaymentFacadeSB implements PaymentFacade {

	private static final Logger logger = LoggerFactory.getLogger(PaymentFacadeSB.class);

	@Inject IDealPaymentHandler idealPaymentHandler;

	@Override
	public Set<BankDataDTO> getIdealBankList() throws PaymentException {
		return this.idealPaymentHandler.getIdealBankList();
	}

	@Override
	public String initializePayment(OrderDataDTO order, BankDataDTO bank) throws PaymentException {
		Validate.notNull(order, "Order is null.");
		Validate.notNull(bank, "Bank is null.");

		logger.info("Placing new order {} at bank {}.", order, bank);
		//TODO params url encoding omdat Mollie dat voorschrijft
		return "/bank";
	}
}