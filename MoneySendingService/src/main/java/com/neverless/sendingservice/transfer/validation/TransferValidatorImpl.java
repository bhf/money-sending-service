package com.neverless.sendingservice.transfer.validation;

import com.neverless.sendingservice.entities.UserService;
import com.neverless.sendingservice.entities.transactions.TransferRequest;
import com.neverless.sendingservice.transactiontracker.PendingTransferTransactionSummary;
import com.neverless.sendingservice.transactiontracker.PendingWithdrawTransactionSummary;
import com.neverless.sendingservice.transactiontracker.TransactionTracker;

public class TransferValidatorImpl implements TransferValidator{

	final UserService userService;
	final TransactionTracker transTracker;
	
	public TransferValidatorImpl(UserService userService, TransactionTracker transactionTracker) {
		super();
		this.userService = userService;
		this.transTracker=transactionTracker;
	}

	@Override
	public TransferValidationResults transferIsValid(TransferRequest req) {

		TransferValidationResults res=new TransferValidationResults();
		res.sourceAccountValid=sourceAccountIsValid(req);
		res.destinationAccountValid=destinationAccountIsValid(req);
		res.qtyIsValid=qtyIsValid(req);
		return res;
	}

	@Override
	public boolean sourceAccountIsValid(TransferRequest req) {
		long userID = req.userId;
		long assetId = req.assetId;

		// check the user-asset combo exist

		boolean isValid = userService.isValid(userID, assetId);
		return isValid;
	}

	@Override
	public boolean destinationAccountIsValid(TransferRequest req) {
		
		long userID = req.destUser;
		long assetId = req.assetId;

		// check the user-asset combo exist
		//TODO: How to handle the case where the destination account
		// doesn't have an asset balance for the specific asset

		boolean isValid = userService.isValid(userID, assetId);
		return isValid;
		
	}

	@Override
	public boolean qtyIsValid(TransferRequest req) {
		
		long userId=req.userId;
		PendingTransferTransactionSummary pendingTransfers = transTracker.getPendingTransfers(userId);
		PendingWithdrawTransactionSummary pendingWithdraws = transTracker.getPendingWithdraws(userId);
		
		long assetId=req.assetId;
		long pendingTransferQty= null!=pendingTransfers? pendingTransfers.getPendingQty(assetId) :0 ;
		long pendingWithdrawQty=null!=pendingWithdraws? pendingWithdraws.getPendingQty(assetId) :0;
		long totalPendingQty = pendingTransferQty+pendingWithdrawQty;
		
		long existingAssetBalance=userService.getUserAssetBalance(userId, assetId);
		
		return existingAssetBalance>0 && existingAssetBalance >= totalPendingQty+req.qty.qty;
	}

	

}
