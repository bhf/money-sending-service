package com.neverless.sendingservice.withdraw.validation;

import java.util.UUID;

import com.neverless.sendingservice.entities.UserService;
import com.neverless.sendingservice.entities.transactions.WithdrawRequest;

public class WithdrawalValidatorImpl implements WithdrawalValidator {

	final UserService userService;

	public WithdrawalValidatorImpl(UserService userService) {
		super();
		this.userService = userService;
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
		return true;
	}

	@Override
	public boolean sourceBalanceIsSufficient(WithdrawRequest req) {

		return false;
	}

}
