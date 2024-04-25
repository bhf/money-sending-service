package com.neverless.sendingservice.withdraw.validation;

import java.util.UUID;

import com.neverless.sendingservice.entities.UserService;
import com.neverless.sendingservice.entities.transactions.WithdrawRequest;
import com.neverless.sendingservice.transactiontracker.PendingTransferTransactionSummary;
import com.neverless.sendingservice.transactiontracker.PendingWithdrawTransactionSummary;
import com.neverless.sendingservice.transactiontracker.TransactionTracker;

public class WithdrawalValidatorImpl implements WithdrawalValidator {

	final UserService userService;
	final TransactionTracker transTracker;

	public WithdrawalValidatorImpl(UserService userService, TransactionTracker transactionTracker) {
		super();
		this.userService = userService;
		this.transTracker=transactionTracker;
	}

	@Override
	public WithdrawalValidationResults validateWithdraw(WithdrawRequest req) {
		WithdrawalValidationResults res = new WithdrawalValidationResults();
		res.srcAccountValid = sourceAccountExists(req);
		res.destIsValid = destinationAddressIsValid(req);
		res.sufficientBalance = sourceBalanceIsSufficient(req);
		return res;
	}

	@Override
	public boolean sourceAccountExists(WithdrawRequest req) {
		long userID = req.userId;
		long assetId = req.assetId;

		// check the user-asset combo exist

		boolean isValid = userService.isValid(userID, assetId);
		return isValid;
	}

	@Override
	public boolean destinationAddressIsValid(WithdrawRequest req) {
		return null != req.destinationAddress && isValidDestination(req.destinationAddress.address, req.assetId);
	}

	/**
	 * Check the address the funds are being withdrawn too is valid
	 * for the asset
	 * @param address
	 * @param assetId
	 * @return
	 */
	private boolean isValidDestination(UUID address, long assetId) {
		//TODO: Implement this as an AssetAddressValidator
		return true;
	}

	/**
	 * Given any pending withdraws and transfers, is this withdraw request
	 * valid
	 */
	@Override
	public boolean sourceBalanceIsSufficient(WithdrawRequest req) {
		
		long userId=req.userId;
		PendingTransferTransactionSummary pendingTransfers = transTracker.getPendingTransfers(userId);
		PendingWithdrawTransactionSummary pendingWithdraws = transTracker.getPendingWithdraws(userId);
		
		long assetId=req.assetId;
		long pendingTransferQty= null!=pendingTransfers? pendingTransfers.getPendingQty(assetId) :0 ;
		long pendingWithdrawQty=null!=pendingWithdraws? pendingWithdraws.getPendingQty(assetId) :0;
		long totalPendingQty = pendingTransferQty+pendingWithdrawQty;
		
		long existingAssetBalance=userService.getUserAssetBalance(userId, assetId);
		
		return existingAssetBalance >= totalPendingQty;
	}

}
