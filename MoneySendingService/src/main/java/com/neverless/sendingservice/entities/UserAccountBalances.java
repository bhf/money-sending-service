package com.neverless.sendingservice.entities;

import java.util.HashMap;
import java.util.Map;

public class UserAccountBalances {

	private final Map<Long, AssetBalance> balances=new HashMap<Long, AssetBalance>();

	public boolean hasAsset(long asset) {
		return balances.containsKey(asset);
	}

	public AssetBalance getAssetBalance(long assetId) {
		return balances.get(assetId);
	}

	public void setAssetBalance(long assetId, AssetBalance assetBalance) {
		balances.put(assetId, assetBalance);
		
	}
	
}
