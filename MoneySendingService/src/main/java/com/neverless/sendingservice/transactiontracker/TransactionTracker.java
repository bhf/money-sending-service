package com.neverless.sendingservice.transactiontracker;

import com.neverless.sendingservice.entities.TransferRequest;
import com.neverless.sendingservice.entities.WithdrawRequest;

public interface TransactionTracker {

	/**
	 * Get a summary of pending transfers between 2 users
	 * @return
	 */
	public PendingTransferTransactionSummary getPendingTransfers();
	
	/**
	 * Get a summary of pending withdraws between 2 users
	 * @return
	 */
	public PendingWithdrawTransactionSummary getPendingWithdraws();
	
	/**
	 * Add a valid pending withdraw request
	 * @param req
	 */
	public void addPendingWithdraw(WithdrawRequest req);
	
	/**
	 * Add a valid transfer request
	 * @param req
	 */
	public void addPendingTransfer(TransferRequest req);
}
