package com.neverless.sendingservice.transfer.validation;

import com.neverless.sendingservice.entities.transactions.TransferRequest;

public class TransferValidatorImpl implements TransferValidator{

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
		
		return false;
	}

	@Override
	public boolean destinationAccountIsValid(TransferRequest req) {
		
		return false;
	}

	@Override
	public boolean qtyIsValid(TransferRequest req) {
		
		return false;
	}

	

}
