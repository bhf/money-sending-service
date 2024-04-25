package com.neverless.sendingservice.transfer.validation;

import static org.junit.jupiter.api.Assertions.assertFalse;
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

class TransferValidatorImplTest {

	@Test
	void testTransferIsValid() {
		//fail("Not yet implemented");
	}
	
	private Map<Long, UserEntity> getUsers() {
		HashMap<Long, UserEntity> users = new HashMap<Long, UserEntity>();
		return users;
	}

	@Test
	void testSourceAccountIsValid() {
		TransactionTracker transTracker=Mockito.mock(TransactionTracker.class);
		
		Map<Long, UserEntity> users=getUsers();
		UserService userService=new UserService(users);
		TransferValidator validator=new TransferValidatorImpl(userService, transTracker);
		
		// no source user
		
		AssetQty assetQty=new AssetQty();
		long assetId=1;
		assetQty.assetId=assetId;
		assetQty.qty=100;
		long userId=0;
		long destUser=userId+1;
		TransferRequest req=new TransferRequest(UUID.randomUUID(), assetQty, assetId, userId, destUser);
		boolean noUsers=validator.sourceAccountIsValid(req);
		
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
		//fail("Not yet implemented");
	}

	@Test
	void testQtyIsValid() {
		//fail("Not yet implemented");
	}

}
