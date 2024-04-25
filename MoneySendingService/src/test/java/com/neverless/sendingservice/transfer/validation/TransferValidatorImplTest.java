package com.neverless.sendingservice.transfer.validation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.neverless.sendingservice.entities.AssetBalance;
import com.neverless.sendingservice.entities.AssetQty;
import com.neverless.sendingservice.entities.UserAccountBalances;
import com.neverless.sendingservice.entities.UserEntity;
import com.neverless.sendingservice.entities.UserService;
import com.neverless.sendingservice.entities.transactions.TransferRequest;
import com.neverless.sendingservice.transactiontracker.TransactionTracker;
import com.neverless.sendingservice.transactiontracker.TransactionTrackerImpl;
import com.neverless.sendingservice.transfer.TransferService;
import com.neverless.sendingservice.withdraw.WithdrawalService;

class TransferValidatorImplTest {

	@Test
	void testTransferIsValid() {
		TransactionTracker transTracker = Mockito.mock(TransactionTracker.class);

		Map<Long, UserEntity> users = getUsers();
		UserAccountBalances balance=new UserAccountBalances();
		UserEntity user=new UserEntity(0, balance);
		users.put(0l, user);
		UserService userService = new UserService(users);
		TransferValidator validator = new TransferValidatorImpl(userService, transTracker);
		
		
		AssetQty assetQty=new AssetQty();
		long assetId=1;
		assetQty.assetId=assetId;
		assetQty.qty=100;
		long userId=0;
		long destUser=userId+1;
		AssetBalance assetBal=new AssetBalance();
		assetBal.balance=assetQty.qty*5;
		balance.setAssetBalance(assetId, assetBal);
		
		TransferRequest req=new TransferRequest(UUID.randomUUID(), assetQty, assetId, userId, destUser);
		TransferValidationResults valid=validator.transferIsValid(req);
		
		assertNotNull(valid);
	}

	private Map<Long, UserEntity> getUsers() {
		HashMap<Long, UserEntity> users = new HashMap<Long, UserEntity>();
		return users;
	}

	@Test
	void testSourceAccountIsValid() {
		TransactionTracker transTracker = Mockito.mock(TransactionTracker.class);

		Map<Long, UserEntity> users = getUsers();
		UserService userService = new UserService(users);
		TransferValidator validator = new TransferValidatorImpl(userService, transTracker);

		// no source user

		AssetQty assetQty = new AssetQty();
		long assetId = 1;
		assetQty.assetId = assetId;
		assetQty.qty = 100;
		long userId = 0;
		long destUser = userId + 1;
		TransferRequest req = new TransferRequest(UUID.randomUUID(), assetQty, assetId, userId, destUser);
		boolean noUsers = validator.sourceAccountIsValid(req);

		assertFalse(noUsers);

		// source user exists but no balance
		UserAccountBalances balances = new UserAccountBalances();
		UserEntity userEntity = new UserEntity(userId, balances);
		users.put(userId, userEntity);

		boolean sourceExistsNoBalance = validator.sourceAccountIsValid(req);

		assertFalse(sourceExistsNoBalance);

		// source user exists and has a balance
		AssetBalance assetBalance = new AssetBalance();
		balances.setAssetBalance(assetId, assetBalance);

		// user exists and has an asset balance boolean
		boolean userAddedWithAssetBalance = validator.sourceAccountIsValid(req);
		assertTrue(userAddedWithAssetBalance);

	}

	@Test
	void testDestinationAccountIsValid() {

		TransactionTracker transTracker = Mockito.mock(TransactionTracker.class);

		Map<Long, UserEntity> users = getUsers();
		UserService userService = new UserService(users);
		TransferValidator validator = new TransferValidatorImpl(userService, transTracker);

		AssetQty assetQty = new AssetQty();
		long assetId = 1;
		assetQty.assetId = assetId;
		assetQty.qty = 100;
		long userId = 0;
		long destUser = userId + 1;

		// source user exists and has a balance

		UserAccountBalances balances = new UserAccountBalances();
		UserEntity userEntity = new UserEntity(userId, balances);
		users.put(userId, userEntity);

		AssetBalance assetBalance = new AssetBalance();
		balances.setAssetBalance(assetId, assetBalance);

		TransferRequest req = new TransferRequest(UUID.randomUUID(), assetQty, assetId, userId, destUser);

		// invalid destination user

		boolean noDestUser = validator.destinationAccountIsValid(req);

		assertFalse(noDestUser);

		// destination user exists but has no asset balance

		UserAccountBalances destBalances = new UserAccountBalances();
		UserEntity destUserEntity = new UserEntity(destUser, destBalances);
		users.put(destUser, destUserEntity);

		boolean destinationUserNoBalance = validator.destinationAccountIsValid(req);

		assertFalse(destinationUserNoBalance);

		// destination user exists and has a valid existing asset balance

		AssetBalance destAssetBalance = new AssetBalance();
		destBalances.setAssetBalance(assetId, destAssetBalance);

		boolean destinationUserValid = validator.destinationAccountIsValid(req);

		assertTrue(destinationUserValid);

	}

	@Test
	void testSourceBalanceIsInsufficientWithPendingTransaction() {

		WithdrawalService withdrawService = Mockito.mock(WithdrawalService.class);
		TransferService transferService = Mockito.mock(TransferService.class);

		TransactionTracker transTracker = new TransactionTrackerImpl(withdrawService, transferService);

		Map<Long, UserEntity> users = getUsers();
		UserAccountBalances balance = new UserAccountBalances();
		UserEntity user = new UserEntity(0, balance);
		users.put(0l, user);
		UserService userService = new UserService(users);
		TransferValidator validator = new TransferValidatorImpl(userService, transTracker);

		AssetQty assetQty = new AssetQty();
		long assetId = 1;
		assetQty.assetId = assetId;
		assetQty.qty = 100;
		long userId = 0;
		long destUser = userId + 1;

		TransferRequest req = new TransferRequest(UUID.randomUUID(), assetQty, assetId, userId, destUser);

		AssetBalance assetBal = new AssetBalance();
		assetBal.balance = 100;
		balance.setAssetBalance(assetId, assetBal);

		// add a pending transaction sized to be equal to the balance
		transTracker.addPendingTransfer(req, userId);

		boolean insufficientBalance = validator.qtyIsValid(req);
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
		TransferValidator validator = new TransferValidatorImpl(userService, transTracker);
		
		
		AssetQty assetQty=new AssetQty();
		long assetId=1;
		assetQty.assetId=assetId;
		assetQty.qty=100;
		long userId=0;
		long destUser=userId+1;
		TransferRequest req=new TransferRequest(UUID.randomUUID(), assetQty, assetId, userId, destUser);

		
		AssetBalance assetBal=new AssetBalance();
		assetBal.balance=1000;
		balance.setAssetBalance(assetId, assetBal);
		
		// add a pending transaction sized to be less than the (balance-orig withdraq qty)
		long qty = assetBal.balance-req.qty.qty-10;
		AssetQty validQty=new AssetQty();
		validQty.qty=qty;
		TransferRequest trans = new TransferRequest(UUID.randomUUID(), validQty, assetId,userId,userId+1);
		
		transTracker.addPendingTransfer(trans, userId);
		
		boolean sufficientBalance=validator.qtyIsValid(req);
		assertTrue(sufficientBalance);
		
	}

}
