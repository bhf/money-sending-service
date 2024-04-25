package com.neverless.sendingservice;

import java.util.UUID;

import com.neverless.sendingservice.transfer.TransferService;
import com.neverless.sendingservice.transfer.TransferService.TransferId;
import com.neverless.sendingservice.transfer.TransferService.TransferState;
import com.neverless.sendingservice.transfer.TransferServiceStub;
import com.neverless.sendingservice.withdraw.WithdrawalService;
import com.neverless.sendingservice.withdraw.WithdrawalService.WithdrawalId;
import com.neverless.sendingservice.withdraw.WithdrawalService.WithdrawalState;
import com.neverless.sendingservice.withdraw.WithdrawalServiceStub;

public class RESTDelegator {

	private final WithdrawalService withdrawService=new WithdrawalServiceStub();
	private final TransferService transferService=new TransferServiceStub();
	

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

	
	
}
