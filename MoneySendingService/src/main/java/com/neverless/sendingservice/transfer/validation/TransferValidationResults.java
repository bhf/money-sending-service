package com.neverless.sendingservice.transfer.validation;

public class TransferValidationResults {

	public boolean sourceAccountValid;
	public boolean destinationAccountValid;
	public boolean qtyIsValid;
	
	public boolean isValid() {
		return sourceAccountValid && destinationAccountValid && qtyIsValid;
	}

}
