package com.neverless.sendingservice.transfer.validation;

import com.neverless.sendingservice.entities.transactions.TransferRequest;

public interface TransferValidator {
	
	/**
	 * Whether the transfer is valid
	 * @return
	 */
	public TransferValidationResults transferIsValid(TransferRequest req);

	/**
	 * Check the source account exists and is valid
	 * @return
	 */
	public boolean sourceAccountIsValid(TransferRequest req);
	
	/**
	 * Check the destination account is valid and exists
	 * @return
	 */
	public boolean destinationAccountIsValid(TransferRequest req);
	
	/**
	 * Check the qty being transferred between users is valid
	 * given any pending transfers and any pending withdrawals
	 * @return
	 */
	public boolean qtyIsValid(TransferRequest req);
	
	
	
}
