package com.neverless.sendingservice.withdraw.validation;

import com.neverless.sendingservice.entities.transactions.WithdrawRequest;

public interface WithdrawalValidator {

	public WithdrawalValidationResults validateWithdraw(WithdrawRequest req);
	
	/**
	 * Check that the source account exists
	 * @return
	 */
	public boolean sourceAccountExists(WithdrawRequest req);
	
	/**
	 * Check the destination address is valid
	 * @return
	 */
	public boolean destinationAddressIsValid(WithdrawRequest req);
	
	/**
	 * Check that the source balance is sufficient
	 * given any pending transfers or withdraws
	 * @return
	 */
	public boolean sourceBalanceIsSufficient(WithdrawRequest req);
	
}
