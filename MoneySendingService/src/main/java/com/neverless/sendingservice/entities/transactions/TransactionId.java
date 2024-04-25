package com.neverless.sendingservice.entities.transactions;

import java.util.UUID;

public class TransactionId {

	final UUID id;

	public TransactionId(UUID id) {
		super();
		this.id = id;
	}
	
	@Override
	public String toString() {
		return id.toString();
	}
	
	@Override
	public int hashCode() {
		return id.hashCode();
	}
	
}
