package com.neverless.sendingservice.transactiontracker;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.neverless.sendingservice.entities.transactions.TransferRequest;
import com.neverless.sendingservice.transfer.TransferService;
import com.neverless.sendingservice.transfer.TransferService.TransferId;
import com.neverless.sendingservice.transfer.TransferService.TransferState;

public class PendingTransferTransactionSummary {

	List<TransferRequest> pendingTransfers=new ArrayList<TransferRequest>();

	public void handleTransferRequest(TransferRequest req) {
		pendingTransfers.add(req);
	}
	
	void processPending(TransferService service) {
		Iterator<TransferRequest> it = pendingTransfers.iterator();
		
		while(it.hasNext()) {
			TransferRequest req = it.next();
			TransferState requestState = service.getRequestState(new TransferId(req.id));
			
			if(requestState==TransferState.COMPLETED || requestState==TransferState.FAILED) {
				it.remove();
			}
		}
	}


}
