package com.neverless.sendingservice.withdraw.validation;

public class WithdrawalValidationResults {

	public boolean srcAccountValid;
	public boolean destIsValid;
	public boolean sufficientBalance;

	public boolean isValid() {
		return srcAccountValid && destIsValid && sufficientBalance;
	}
	
}
