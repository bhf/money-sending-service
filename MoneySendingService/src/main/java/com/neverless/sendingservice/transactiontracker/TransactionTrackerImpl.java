package com.neverless.sendingservice.transactiontracker;

import com.neverless.sendingservice.entities.TransferRequest;
import com.neverless.sendingservice.entities.WithdrawRequest;

public class TransactionTrackerImpl implements TransactionTracker{

	@Override
	public PendingTransferTransactionSummary getPendingTransfers() {
		
		return null;
	}

	@Override
	public PendingWithdrawTransactionSummary getPendingWithdraws() {
		
		return null;
	}

	@Override
	public void addPendingWithdraw(WithdrawRequest req) {
		
		
	}

	@Override
	public void addPendingTransfer(TransferRequest req) {
		
		
	}


}
