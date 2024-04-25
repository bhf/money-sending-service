package com.neverless.sendingservice.transactiontracker;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.neverless.sendingservice.entities.AssetQty;
import com.neverless.sendingservice.entities.transactions.TransferRequest;
import com.neverless.sendingservice.entities.transactions.WithdrawRequest;
import com.neverless.sendingservice.transfer.TransferService;
import com.neverless.sendingservice.withdraw.WithdrawalService;

@ExtendWith(MockitoExtension.class)
class TransactionTrackerImplTest {


	@Test
	void testGetPendingTransfers() {
		WithdrawalService withdrawService=Mockito.mock(WithdrawalService.class);
		TransferService transferService=Mockito.mock(TransferService.class);
		TransactionTracker t=new TransactionTrackerImpl(withdrawService, transferService);
		PendingTransferTransactionSummary pending = t.getPendingTransfers(0);
		
		assertNull(pending);
	}

	@Test
	void testGetPendingWithdraws() {
		WithdrawalService withdrawService=Mockito.mock(WithdrawalService.class);
		TransferService transferService=Mockito.mock(TransferService.class);
		TransactionTracker t=new TransactionTrackerImpl(withdrawService, transferService);
		PendingTransferTransactionSummary pending = t.getPendingTransfers(0);
		
		assertNull(pending);
	}

	@Test
	void testAddPendingWithdraw() {
		WithdrawalService withdrawService=Mockito.mock(WithdrawalService.class);
		TransferService transferService=Mockito.mock(TransferService.class);
		
		TransactionTracker t=new TransactionTrackerImpl(withdrawService, transferService);
		
		UUID requestId=UUID.randomUUID();
		long assetId=1;
		AssetQty qty=new AssetQty();
		qty.assetId=assetId;
		qty.qty=100;
		long userId=1;
		
		WithdrawRequest withdrawReq=new WithdrawRequest(requestId, qty, assetId);
		t.addPendingWithdraw(withdrawReq, userId);
		
		PendingWithdrawTransactionSummary pendingWithdraws = t.getPendingWithdraws(userId);
		
		assertNotNull(pendingWithdraws);
		assertEquals(1, pendingWithdraws.pendingWithdraws.size());
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
	
	@Test
	void testAddMultiplePendingTransfer() {
		WithdrawalService withdrawService=Mockito.mock(WithdrawalService.class);
		TransferService transferService=Mockito.mock(TransferService.class);
		
		TransactionTracker t=new TransactionTrackerImpl(withdrawService, transferService);

		long assetId=1;
		AssetQty qty=new AssetQty();
		qty.assetId=assetId;
		qty.qty=100;
		long userId=1;
		
		t.addPendingTransfer(new TransferRequest(UUID.randomUUID(), qty, assetId), userId);
		t.addPendingTransfer(new TransferRequest(UUID.randomUUID(), qty, assetId), userId);
		
		PendingTransferTransactionSummary pendingTransfers = t.getPendingTransfers(userId);
		
		assertNotNull(pendingTransfers);
		assertEquals(2, pendingTransfers.pendingTransfers.size());
	}
	
	@Test
	void testAddMultiplePendingWithdraw() {
		WithdrawalService withdrawService=Mockito.mock(WithdrawalService.class);
		TransferService transferService=Mockito.mock(TransferService.class);
		
		TransactionTracker t=new TransactionTrackerImpl(withdrawService, transferService);
		
		long assetId=1;
		AssetQty qty=new AssetQty();
		qty.assetId=assetId;
		qty.qty=100;
		long userId=1;
		
		t.addPendingWithdraw(new WithdrawRequest(UUID.randomUUID(), qty, assetId), userId);
		t.addPendingWithdraw(new WithdrawRequest(UUID.randomUUID(), qty, assetId), userId);
		
		PendingWithdrawTransactionSummary pendingWithdraws = t.getPendingWithdraws(userId);
		
		assertNotNull(pendingWithdraws);
		assertEquals(2, pendingWithdraws.pendingWithdraws.size());
	}

}
