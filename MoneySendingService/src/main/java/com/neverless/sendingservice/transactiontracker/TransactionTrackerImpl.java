package com.neverless.sendingservice.transactiontracker;

import java.util.HashMap;
import java.util.Map;

import com.neverless.sendingservice.entities.transactions.TransferRequest;
import com.neverless.sendingservice.entities.transactions.WithdrawRequest;
import com.neverless.sendingservice.transfer.TransferService;
import com.neverless.sendingservice.withdraw.WithdrawalService;

public class TransactionTrackerImpl implements TransactionTracker {

	final Map<Long, PendingTransferTransactionSummary> clientIdToPendingTransfers = new HashMap<Long, PendingTransferTransactionSummary>();
	final Map<Long, PendingWithdrawTransactionSummary> clientIdToPendingWithdraws = new HashMap<Long, PendingWithdrawTransactionSummary>();
	final WithdrawalService withdrawService;
	final TransferService transferService;

	public TransactionTrackerImpl(WithdrawalService withdrawService, TransferService transferService) {
		super();
		this.withdrawService = withdrawService;
		this.transferService = transferService;
	}
	
	private void processPendingWithdraws() {
		for(PendingWithdrawTransactionSummary s :clientIdToPendingWithdraws.values()) {
			s.processPending(withdrawService);
		}
	}

	private void processPendingTransfers() {
		for(PendingTransferTransactionSummary s :clientIdToPendingTransfers.values()) {
			s.processPending(transferService);
		}
	}
	
	@Override
	public PendingTransferTransactionSummary getPendingTransfers(long userId) {
		processPendingTransfers();
		return clientIdToPendingTransfers.get(userId);
	}

	@Override
	public PendingWithdrawTransactionSummary getPendingWithdraws(long userId) {
		processPendingWithdraws();
		return clientIdToPendingWithdraws.get(userId);
	}

	@Override
	public void addPendingWithdraw(WithdrawRequest req, long userId) {

		PendingWithdrawTransactionSummary s = null;

		if (clientIdToPendingWithdraws.containsKey(userId)) {
			s = clientIdToPendingWithdraws.get(userId);
		} else {
			s = new PendingWithdrawTransactionSummary();
			clientIdToPendingWithdraws.put(userId, s);
		}

		s.handleWithdrawRequest(req);
	}

	@Override
	public void addPendingTransfer(TransferRequest req, long userId) {

		PendingTransferTransactionSummary s = null;

		if (clientIdToPendingTransfers.containsKey(userId)) {
			s = clientIdToPendingTransfers.get(userId);
		} else {
			s = new PendingTransferTransactionSummary();
			clientIdToPendingTransfers.put(userId, s);
		}

		s.handleTransferRequest(req);
	}

}
