package com.neverless.sendingservice;

import java.util.UUID;

public class WithdrawalId {

	final UUID id;

	public WithdrawalId(UUID id) {
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
