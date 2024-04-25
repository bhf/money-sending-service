package com.neverless.sendingservice.transactiontracker;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.neverless.sendingservice.entities.transactions.WithdrawRequest;
import com.neverless.sendingservice.withdraw.WithdrawalService;
import com.neverless.sendingservice.withdraw.WithdrawalService.WithdrawalId;
import com.neverless.sendingservice.withdraw.WithdrawalService.WithdrawalState;

public class PendingWithdrawTransactionSummary {
	
	List<WithdrawRequest> pendingWithdraws=new ArrayList<WithdrawRequest>();

	public void handleWithdrawRequest(WithdrawRequest req) {
		pendingWithdraws.add(req);
	}
	
	void processPending(WithdrawalService service) {
		Iterator<WithdrawRequest> it = pendingWithdraws.iterator();
		
		while(it.hasNext()) {
			WithdrawRequest req = it.next();
			WithdrawalState requestState = service.getRequestState(new WithdrawalId(req.id));
			
			if(requestState==WithdrawalState.COMPLETED || requestState==WithdrawalState.FAILED) {
				it.remove();
			}
		}
	}

}
