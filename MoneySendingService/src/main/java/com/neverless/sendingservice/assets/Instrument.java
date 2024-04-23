package com.neverless.sendingservice.assets;

public class Instrument {

	public final PrecisionDetails pricePrecision;
	public final PrecisionDetails qtyPrecision;
	public final long instrumentId;
	
	public Instrument(PrecisionDetails pricePrecision, PrecisionDetails qtyPrecision, long instrumentId) {
		super();
		this.pricePrecision = pricePrecision;
		this.qtyPrecision = qtyPrecision;
		this.instrumentId = instrumentId;
	}
	
	
	
}
