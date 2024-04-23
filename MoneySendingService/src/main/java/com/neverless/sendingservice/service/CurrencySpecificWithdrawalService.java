package com.neverless.sendingservice.service;

/**
 * Withdrawal service for a specific currency
 * 
 */
public class CurrencySpecificWithdrawalService {

	final WithdrawalService service;
	final long currencyId;
	
	public CurrencySpecificWithdrawalService(WithdrawalService service, long currencyId) {
		super();
		this.service = service;
		this.currencyId = currencyId;
	}
	
	
	
}
