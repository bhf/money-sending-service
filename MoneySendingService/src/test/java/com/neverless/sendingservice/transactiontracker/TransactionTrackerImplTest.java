package com.neverless.sendingservice.transactiontracker;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.neverless.sendingservice.entities.AssetQty;
import com.neverless.sendingservice.entities.transactions.TransferRequest;
import com.neverless.sendingservice.transfer.TransferService;
import com.neverless.sendingservice.withdraw.WithdrawalService;

@ExtendWith(MockitoExtension.class)
class TransactionTrackerImplTest {


	@Test
	void testGetPendingTransfers() {
		
		
		
	}

	@Test
	void testGetPendingWithdraws() {
		//fail("Not yet implemented");
	}

	@Test
	void testAddPendingWithdraw() {
	
	}

	@Test
	void testAddPendingTransfer() {
		WithdrawalService withdrawService=Mockito.mock(WithdrawalService.class);
		TransferService transferService=Mockito.mock(TransferService.class);
		
		TransactionTracker t=new TransactionTrackerImpl(withdrawService, transferService);
		
		UUID requestId=UUID.randomUUID();
		long assetId=1;
		AssetQty qty=new AssetQty();
		qty.assetId=assetId;
		qty.qty=100;
		long userId=1;
		
		TransferRequest pendingReq=new TransferRequest(requestId, qty, assetId);
		t.addPendingTransfer(pendingReq, userId);
		
		PendingTransferTransactionSummary pendingTransfers = t.getPendingTransfers(userId);
		
		assertNotNull(pendingTransfers);
		assertEquals(1, pendingTransfers.pendingTransfers.size());
	}

}
