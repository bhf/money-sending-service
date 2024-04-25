package com.neverless.sendingservice.withdraw.validation;

import static org.junit.jupiter.api.Assertions.assertFalse;
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
import com.neverless.sendingservice.entities.transactions.WithdrawRequest;
import com.neverless.sendingservice.transactiontracker.TransactionTracker;

class WithdrawalValidatorImplTest {

	@Test
	void testValidateWithdraw() {
		//fail("Not yet implemented");
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
	void testSourceBalanceIsSufficient() {
		//fail("Not yet implemented");
	}

}
