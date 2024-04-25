package com.neverless.sendingservice.entities;

public class UserEntity {

	private final long userId;
	private final UserAccountBalances balances;
	
	public UserEntity(long userId, UserAccountBalances balances) {
		super();
		this.userId = userId;
		this.balances = balances;
	} 
	
	public boolean hasAssetBalance(long asset) {
		return balances.hasAsset(asset);
	}

	public AssetBalance getAssetBalance(long assetId) {
		return balances.getAssetBalance(assetId);
		
	}
	
}
