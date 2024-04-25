package com.neverless.sendingservice;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.neverless.sendingservice.entities.Address;
import com.neverless.sendingservice.entities.AssetBalance;
import com.neverless.sendingservice.entities.AssetQty;
import com.neverless.sendingservice.entities.UserAccountBalances;
import com.neverless.sendingservice.entities.UserEntity;
import com.neverless.sendingservice.entities.UserService;
import com.neverless.sendingservice.entities.transactions.WithdrawRequest;
import com.neverless.sendingservice.transactiontracker.TransactionTracker;
import com.neverless.sendingservice.transactiontracker.TransactionTrackerImpl;
import com.neverless.sendingservice.transfer.TransferService;
import com.neverless.sendingservice.transfer.TransferService.TransferId;
import com.neverless.sendingservice.transfer.TransferService.TransferState;
import com.neverless.sendingservice.transfer.TransferServiceStub;
import com.neverless.sendingservice.withdraw.WithdrawalService;
import com.neverless.sendingservice.withdraw.WithdrawalService.WithdrawalId;
import com.neverless.sendingservice.withdraw.WithdrawalService.WithdrawalState;
import com.neverless.sendingservice.withdraw.WithdrawalServiceStub;
import com.neverless.sendingservice.withdraw.validation.WithdrawalValidationResults;
import com.neverless.sendingservice.withdraw.validation.WithdrawalValidator;
import com.neverless.sendingservice.withdraw.validation.WithdrawalValidatorImpl;

public class RESTDelegator {

	private final WithdrawalService withdrawService=new WithdrawalServiceStub();
	private final TransferService transferService=new TransferServiceStub();
	private final Map<Long, UserEntity> users=new HashMap<Long, UserEntity>();
	private final UserService userService=new UserService(users);
	private final TransactionTracker transactionTracker=new TransactionTrackerImpl(withdrawService, transferService);
	private final WithdrawalValidator withdrawValidator=new WithdrawalValidatorImpl(userService, transactionTracker);
	
	
	public RESTDelegator() {
		// add some users here
		UserAccountBalances balances=new UserAccountBalances();
		AssetBalance assetBalance=new AssetBalance();
		assetBalance.balance=100000;
		balances.setAssetBalance(1, assetBalance);
		UserEntity user=new UserEntity(0, balances);
		users.put(0l, user);
		
	}

	public WithdrawalState getWithdrawStatus(String requestId) {
		WithdrawalId id=new WithdrawalId(UUID.fromString(requestId));
		WithdrawalState requestState = withdrawService.getRequestState(id);
		return requestState;
	}


	public TransferState getTransferStatus(String requestId) {
		TransferId id=new TransferId(UUID.fromString(requestId));
		TransferState requestState = transferService.getRequestState(id);
		return requestState;
	}

	
	public WithdrawalValidationResults handleWithdrawRequest(String requestId, long assetId, long requestQty, long userId, String destAddress) {
		// validate the withdraw
		UUID id=UUID.fromString(requestId);
		AssetQty qty=new AssetQty();
		qty.assetId=assetId;
		qty.qty=requestQty;
		Address dest=new Address(UUID.fromString(destAddress));
		WithdrawRequest req=new WithdrawRequest(id, qty, assetId, userId, dest);
		WithdrawalValidationResults validateWithdraw = withdrawValidator.validateWithdraw(req);
		
		if(validateWithdraw.isValid()) {
			WithdrawalId withdrawalId=new WithdrawalId(id);
			
			com.neverless.sendingservice.withdraw.WithdrawalService.Address withdrawalAddress 
			= new com.neverless.sendingservice.withdraw.WithdrawalService.Address(
					destAddress);
			withdrawService.requestWithdrawal(withdrawalId, withdrawalAddress, qty);
		}
		
		return validateWithdraw;
		
	}
	
}
