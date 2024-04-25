package com.neverless.sendingservice.withdraw.validation;

import com.neverless.sendingservice.entities.transactions.WithdrawRequest;

public class WithdrawalValidatorImpl implements WithdrawalValidator{

	@Override
	public WithdrawalValidationResults validateWithdraw(WithdrawRequest req) {
		WithdrawalValidationResults res=new WithdrawalValidationResults();
		res.srcAccountValid=sourceAccountExists(req);
		res.destIsValid=destinationAddressIsValid(req);
		res.unequalSrcDest=unequalSourceDestination(req);
		res.sufficientBalance=sourceBalanceIsSufficient(req);
		return res;
	}

	@Override
	public boolean sourceAccountExists(WithdrawRequest req) {
		
		return false;
	}

	@Override
	public boolean destinationAddressIsValid(WithdrawRequest req) {
		
		return false;
	}

	@Override
	public boolean sourceBalanceIsSufficient(WithdrawRequest req) {
		
		return false;
	}

	@Override
	public boolean unequalSourceDestination(WithdrawRequest req) {
		return false;
	}

}
