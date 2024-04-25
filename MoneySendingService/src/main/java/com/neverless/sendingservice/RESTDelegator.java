package com.neverless.sendingservice;

import java.util.UUID;

import com.neverless.sendingservice.withdraw.WithdrawalService;
import com.neverless.sendingservice.withdraw.WithdrawalService.WithdrawalId;
import com.neverless.sendingservice.withdraw.WithdrawalService.WithdrawalState;
import com.neverless.sendingservice.withdraw.WithdrawalServiceStub;

public class RESTDelegator {

	private final WithdrawalService withdrawService=new WithdrawalServiceStub();
	

	public WithdrawalState getWithdrawStatus(String requestId) {
		WithdrawalId id=new WithdrawalId(UUID.fromString(requestId));
		WithdrawalState requestState = withdrawService.getRequestState(id);
		return requestState;
	}

	
	
}
