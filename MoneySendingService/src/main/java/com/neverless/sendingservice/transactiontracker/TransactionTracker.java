package com.neverless.sendingservice.transactiontracker;

import com.neverless.sendingservice.entities.transactions.TransferRequest;
import com.neverless.sendingservice.entities.transactions.WithdrawRequest;

public interface TransactionTracker {

	/**
	 * Get a summary of pending transfers between 2 users
	 * @return
	 */
	public PendingTransferTransactionSummary getPendingTransfers(long userId);
	
	/**
	 * Get a summary of pending withdraws between 2 users
	 * @return
	 */
	public PendingWithdrawTransactionSummary getPendingWithdraws(long userId);
	
	/**
	 * Add a valid pending withdraw request
	 * @param req
	 */
	public void addPendingWithdraw(WithdrawRequest req, long userId);
	
	/**
	 * Add a valid transfer request
	 * @param req
	 */
	public void addPendingTransfer(TransferRequest req, long userId);
}
