package com.neverless.sendingservice.transfer.validation;

import com.neverless.sendingservice.entities.UserService;
import com.neverless.sendingservice.entities.transactions.TransferRequest;

public class TransferValidatorImpl implements TransferValidator{

	final UserService userService;
	
	public TransferValidatorImpl(UserService userService) {
		super();
		this.userService = userService;
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
		
		return false;
	}

	

}
