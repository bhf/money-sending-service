package com.neverless.sendingservice.entities.transactions;

import java.util.UUID;

import com.neverless.sendingservice.entities.AssetQty;

public class TransferRequest {

	public final UUID id;
	public final AssetQty qty;
	public final long assetId;
	public long userId;
	public long destUser;
	
	public TransferRequest(UUID id, AssetQty qty, long assetId, long userId, long destinationUser) {
		super();
		this.id = id;
		this.qty = qty;
		this.assetId = assetId;
		this.userId=userId;
		this.destUser=destinationUser;
	}
	
}
