package nl.iqfit.logic.facade;

import java.util.Set;

import javax.ejb.Local;
import javax.ejb.Stateless;

import nl.iqfit.core.dto.BankDataDTO;
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
}