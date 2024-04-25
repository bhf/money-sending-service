package com.neverless.sendingservice.entities.transactions;

import java.util.UUID;

import com.neverless.sendingservice.entities.AssetQty;

public class WithdrawRequest {

	public final UUID id;
	public final AssetQty qty;
	public final long assetId;
	public final long userId;
	
	public WithdrawRequest(UUID id, AssetQty qty, long assetId, long userId) {
		super();
		this.id = id;
		this.qty = qty;
		this.assetId = assetId;
		this.userId=userId;
	}
	
	
}
