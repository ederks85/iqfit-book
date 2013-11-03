package nl.iqfit.logic.facade;

import java.util.Set;

import javax.inject.Inject;

import nl.iqfit.core.dto.BankDataDTO;
import nl.iqfit.core.dto.OrderDataDTO;
import nl.iqfit.logic.db.entity.OrderStatus;
import nl.iqfit.logic.payment.IDealPaymentHandler;
import nl.iqfit.logic.payment.PaymentException;

public class PaymentFacadeSB implements PaymentFacade {

	@Inject IDealPaymentHandler idealPaymentHandler;
	@Inject OrderFacade orderFacade;

	@Override
	public Set<BankDataDTO> getIdealBankList() throws PaymentException {
		return this.idealPaymentHandler.getIdealBankList();
	}

	@Override
	public String initializeIdealPayment(final OrderDataDTO order, final BankDataDTO bank) throws PaymentException {
		try {
			final String redirectURL = this.idealPaymentHandler.initializeIdealPayment(order, bank);

			// IDeal payment has been successfully initialized, update order status 
			order.setOrderStatus(OrderStatus.PAY_INIT);

			return redirectURL;
		} catch (RuntimeException | PaymentException e) {
			order.setOrderStatus(OrderStatus.ERROR);
			this.orderFacade.updateOrder(order);
			throw e;
		}
	}
}