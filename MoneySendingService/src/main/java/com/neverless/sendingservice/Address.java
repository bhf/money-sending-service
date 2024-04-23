package com.neverless.sendingservice;

import java.util.UUID;

public class Address {

	final UUID address;

	public Address(UUID address) {
		this.address = address;
	}
	
	@Override
	public String toString() {
		return address.toString();
	}
	
	@Override
	public int hashCode() {
		return address.hashCode();
	}
	
}
