package com.neverless.sendingservice.transfer;

import java.util.UUID;

import com.neverless.sendingservice.entities.AssetQty;

public interface TransferService {

	/**
     * Request a transfer for given address and amount. Completes at random moment between 1 and 10 seconds
     * @param id - a caller generated transfer id, used for idempotency
     * @param address - an address withdraw to, can be any arbitrary string
     * @param amount - an amount to withdraw (please replace T with type you want to use)
     * @throws IllegalArgumentException in case there's different address or amount for given id
     */
    void requestTransfer(TransferId id, Address address, AssetQty amount);

    /**
     * Return current state of transfer
     * @param id - a transfer id
     * @return current state of transfer
     * @throws IllegalArgumentException in case there no transfer for the given id
     */
    TransferState getRequestState(TransferId id);

    enum TransferState {
        PROCESSING, COMPLETED, FAILED
    }

    record TransferId(UUID value) {}
    record Address(String value) {}
	
	
}
