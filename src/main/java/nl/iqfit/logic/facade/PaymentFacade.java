package nl.iqfit.logic.facade;

import java.util.Set;

import javax.ejb.Local;
import javax.ejb.Stateless;

import nl.iqfit.core.dto.BankDataDTO;
import nl.iqfit.core.dto.OrderDataDTO;
import nl.iqfit.logic.payment.PaymentException;

@Local
@Stateless
public interface PaymentFacade {

	/**
	 * Get the banks that are available for IDeal payments.
	 * 
	 * @return	The unmodifiable set of available banks and their associated data. Never null.
	 * 
	 * @throws	{@code PaymentException} when an exception occurred while retrieving the banks.
	 */
	Set<BankDataDTO> getIdealBankList() throws PaymentException;

	/**
	 * Prepare an IDeal payment at the payment provider.
	 * 
	 * @param order The order data needed for the payment to initialize.
	 * @param bank The bank that was chosen to prepare the payment at.
	 * 
	 * @return The URL that the bank has provided to redirect the client to after the payment was successfully initialized.
	 * 
	 * @throws PaymentException when initializing the payment fails.
	 */
	String initializeIdealPayment(OrderDataDTO order, BankDataDTO bank) throws PaymentException;

	/**
	 * After a client has performed a payment, retrieve and process the details and update the {@code OrderDataDTO} accordingly.
	 * 
	 * @param orderData	The {@code OrderDataDTO} where the payment details will be processed for.
	 * 
	 * @throws PaymentException when processing the payment details fails.
	 */
	void processIdealPaymentForOrder(final OrderDataDTO orderData) throws PaymentException;
}