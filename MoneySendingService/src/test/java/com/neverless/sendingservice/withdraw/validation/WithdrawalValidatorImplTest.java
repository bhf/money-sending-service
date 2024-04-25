package com.neverless.sendingservice.withdraw.validation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.neverless.sendingservice.entities.Address;
import com.neverless.sendingservice.entities.AssetBalance;
import com.neverless.sendingservice.entities.AssetQty;
import com.neverless.sendingservice.entities.UserAccountBalances;
import com.neverless.sendingservice.entities.UserEntity;
import com.neverless.sendingservice.entities.UserService;
import com.neverless.sendingservice.entities.transactions.TransferRequest;
import com.neverless.sendingservice.entities.transactions.WithdrawRequest;
import com.neverless.sendingservice.transactiontracker.TransactionTracker;
import com.neverless.sendingservice.transactiontracker.TransactionTrackerImpl;
import com.neverless.sendingservice.transfer.TransferService;
import com.neverless.sendingservice.withdraw.WithdrawalService;

class WithdrawalValidatorImplTest {

	@Test
	void testValidateWithdraw() {
		
		TransactionTracker transTracker = Mockito.mock(TransactionTracker.class);

		Map<Long, UserEntity> users = getUsers();
		UserAccountBalances balance=new UserAccountBalances();
		UserEntity user=new UserEntity(0, balance);
		users.put(0l, user);
		UserService userService = new UserService(users);
		WithdrawalValidator validator = new WithdrawalValidatorImpl(userService, transTracker);
		
		
		AssetQty assetQty=new AssetQty();
		long assetId=1;
		assetQty.assetId=assetId;
		assetQty.qty=100;
		long userId=0;
		Address destAddress=new Address(UUID.randomUUID());
		
		AssetBalance assetBal=new AssetBalance();
		assetBal.balance=assetQty.qty*5;
		balance.setAssetBalance(assetId, assetBal);
		
		WithdrawRequest req=new WithdrawRequest(UUID.randomUUID(), assetQty, assetId, userId, destAddress);
		WithdrawalValidationResults valid=validator.validateWithdraw(req);
		
		assertNotNull(valid);
		
	}

	@Test
	void testSourceAccountExists() {
		TransactionTracker transTracker=Mockito.mock(TransactionTracker.class);
		
		Map<Long, UserEntity> users=getUsers();
		UserService userService=new UserService(users);
		WithdrawalValidator validator=new WithdrawalValidatorImpl(userService, transTracker);
		
		AssetQty assetQty=new AssetQty();
		long assetId=1;
		assetQty.assetId=assetId;
		assetQty.qty=100;
		long userId=0;
		Address destAddress=new Address(UUID.randomUUID());
		WithdrawRequest req=new WithdrawRequest(UUID.randomUUID(), assetQty, assetId, userId, destAddress);
		boolean noUsers=validator.sourceAccountExists(req);
		
		
		// no user
		assertFalse(noUsers);
		
		UserAccountBalances balances=new UserAccountBalances();
		UserEntity userEntity=new UserEntity(userId, balances);
		users.put(userId, userEntity);
		
		// user exists but has no asset balance
		boolean userAddedWithoutAssetBalance=validator.sourceAccountExists(req);
		assertFalse(userAddedWithoutAssetBalance);
		
		AssetBalance assetBalance=new AssetBalance();
		balances.setAssetBalance(assetId, assetBalance);
		
		// user exists and has an asset balance
		boolean userAddedWithAssetBalance=validator.sourceAccountExists(req);
		assertTrue(userAddedWithAssetBalance);
	}

	private Map<Long, UserEntity> getUsers() {
		HashMap<Long, UserEntity> users = new HashMap<Long, UserEntity>();
		return users;
	}

	@Test
	void testDestinationAddressIsValid() {
		TransactionTracker transTracker=Mockito.mock(TransactionTracker.class);
		
		Map<Long, UserEntity> users=getUsers();
		UserService userService=new UserService(users);
		WithdrawalValidator validator=new WithdrawalValidatorImpl(userService, transTracker);
		
		AssetQty assetQty=new AssetQty();
		long assetId=1;
		assetQty.assetId=assetId;
		assetQty.qty=100;
		long userId=0;
		Address destAddress=null;
		WithdrawRequest req=new WithdrawRequest(UUID.randomUUID(), assetQty, assetId, userId, destAddress);
		
		boolean nullAddress= validator.destinationAddressIsValid(req);
		
		assertFalse(nullAddress);
		
		destAddress=new Address(UUID.randomUUID());
		req=new WithdrawRequest(UUID.randomUUID(), assetQty, assetId, userId, destAddress);
		
		boolean randomAddress= validator.destinationAddressIsValid(req);
		
		assertTrue(randomAddress);
	}

	@Test
	void testSourceBalanceIsSufficientNoPendingTransaction() {

		TransactionTracker transTracker = Mockito.mock(TransactionTracker.class);

		Map<Long, UserEntity> users = getUsers();
		UserAccountBalances balance=new UserAccountBalances();
		UserEntity user=new UserEntity(0, balance);
		users.put(0l, user);
		UserService userService = new UserService(users);
		WithdrawalValidator validator = new WithdrawalValidatorImpl(userService, transTracker);
		
		
		AssetQty assetQty=new AssetQty();
		long assetId=1;
		assetQty.assetId=assetId;
		assetQty.qty=100;
		long userId=0;
		Address destAddress=null;
		WithdrawRequest req=new WithdrawRequest(UUID.randomUUID(), assetQty, assetId, userId, destAddress);

		
		AssetBalance assetBal=new AssetBalance();
		assetBal.balance=0;
		balance.setAssetBalance(assetId, assetBal);
		boolean insufficientBalance=validator.sourceBalanceIsSufficient(req);
		
		assertFalse(insufficientBalance);
		
		assetBal.balance=assetQty.qty;
		balance.setAssetBalance(assetId, assetBal);
		boolean equalBalance=validator.sourceBalanceIsSufficient(req);
		
		assertTrue(equalBalance);
		
		assetBal.balance=assetQty.qty*5;
		balance.setAssetBalance(assetId, assetBal);
		boolean largeBalance=validator.sourceBalanceIsSufficient(req);
		
		assertTrue(largeBalance);
		
	}
	
	@Test
	void testSourceBalanceIsInsufficientWithPendingTransaction() {
		
		WithdrawalService withdrawService=Mockito.mock(WithdrawalService.class);
		TransferService transferService=Mockito.mock(TransferService.class);

		TransactionTracker transTracker = new TransactionTrackerImpl(withdrawService, transferService);

		Map<Long, UserEntity> users = getUsers();
		UserAccountBalances balance=new UserAccountBalances();
		UserEntity user=new UserEntity(0, balance);
		users.put(0l, user);
		UserService userService = new UserService(users);
		WithdrawalValidator validator = new WithdrawalValidatorImpl(userService, transTracker);
		
		
		AssetQty assetQty=new AssetQty();
		long assetId=1;
		assetQty.assetId=assetId;
		assetQty.qty=100;
		long userId=0;
		Address destAddress=null;
		WithdrawRequest req=new WithdrawRequest(UUID.randomUUID(), assetQty, assetId, userId, destAddress);

		
		AssetBalance assetBal=new AssetBalance();
		assetBal.balance=100;
		balance.setAssetBalance(assetId, assetBal);
		
		// add a pending transaction sized to be equal to the balance
		transTracker.addPendingWithdraw(req, userId);
		
		boolean insufficientBalance=validator.sourceBalanceIsSufficient(req);
		assertFalse(insufficientBalance);
		
	}
	
	@Test
	void testSourceBalanceIsSufficientWithPendingTransaction() {
		
		WithdrawalService withdrawService=Mockito.mock(WithdrawalService.class);
		TransferService transferService=Mockito.mock(TransferService.class);

		TransactionTracker transTracker = new TransactionTrackerImpl(withdrawService, transferService);

		Map<Long, UserEntity> users = getUsers();
		UserAccountBalances balance=new UserAccountBalances();
		UserEntity user=new UserEntity(0, balance);
		users.put(0l, user);
		UserService userService = new UserService(users);
		WithdrawalValidator validator = new WithdrawalValidatorImpl(userService, transTracker);
		
		
		AssetQty assetQty=new AssetQty();
		long assetId=1;
		assetQty.assetId=assetId;
		assetQty.qty=100;
		long userId=0;
		Address destAddress=null;
		WithdrawRequest req=new WithdrawRequest(UUID.randomUUID(), assetQty, assetId, userId, destAddress);

		
		AssetBalance assetBal=new AssetBalance();
		assetBal.balance=1000;
		balance.setAssetBalance(assetId, assetBal);
		
		// add a pending transaction sized to be less than the (balance-orig withdraq qty)
		long qty = assetBal.balance-req.qty.qty-10;
		AssetQty validQty=new AssetQty();
		validQty.qty=qty;
		TransferRequest trans = new TransferRequest(UUID.randomUUID(), validQty, assetId,userId,userId+1);
		
		transTracker.addPendingTransfer(trans, userId);
		
		boolean sufficientBalance=validator.sourceBalanceIsSufficient(req);
		assertTrue(sufficientBalance);
		
	}

}
