package com.neverless.sendingservice.entities;

import java.util.Map;

public class UserService {

	final Map<Long, UserEntity> allUsers;

	public UserService(Map<Long, UserEntity> allUsers) {
		super();
		this.allUsers = allUsers;
	}

	public boolean isValid(long userID, long assetId) {

		if (allUsers.containsKey(userID)) {

			UserEntity user = allUsers.get(userID);
			return user.hasAssetBalance(assetId);
		}

		return false;
	}

	public long getUserAssetBalance(long userId, long assetId) {
		
		if (allUsers.containsKey(userId)) {

			UserEntity user = allUsers.get(userId);
			AssetBalance assetBalance = user.getAssetBalance(assetId);
			return assetBalance.balance;
		}
		return 0;
	}

}
