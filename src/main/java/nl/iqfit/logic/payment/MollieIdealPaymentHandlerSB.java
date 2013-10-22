package nl.iqfit.logic.payment;

import java.util.HashSet;
import java.util.Set;

import nl.iqfit.core.dto.BankDataDTO;

public class MollieIdealPaymentHandlerSB implements IDealPaymentHandler {

	@Override
	public Set<BankDataDTO> getIdealBankList() {
		Set<BankDataDTO> set = new HashSet<>();
		set.add(new BankDataDTO("01", "Test Bank1"));
		set.add(new BankDataDTO("02", "Test Bank2"));
		set.add(new BankDataDTO("03", "Test Bank3"));
		set.add(new BankDataDTO("04", "Test Bank4"));
		set.add(new BankDataDTO("05", "Test Bank5"));
		return set;
	}
}