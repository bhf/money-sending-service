package com.neverless.sendingservice.transfer;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ThreadLocalRandom;

import com.neverless.sendingservice.entities.AssetQty;


public class TransferServiceStub implements TransferService {
    private final ConcurrentMap<TransferId, Transfer> requests = new ConcurrentHashMap<>();

    /**
     * Request a Transfer for given address and amount. Completes at random moment between 1 and 10 seconds
     * @param id - a caller generated Transfer id, used for idempotency
     * @param address - an address withdraw to, can be any arbitrary string
     * @param amount - an amount to withdraw (please replace T with type you want to use)
     * @throws IllegalArgumentException in case there's different address or amount for given id
     */
    @Override
    public void requestTransfer(TransferId id, Address address, AssetQty amount) {
        final var existing = requests.putIfAbsent(id, new Transfer(finalState(), finaliseAt(), address, amount));
        if (existing != null && !Objects.equals(existing.address, address) && !Objects.equals(existing.amount, amount))
            throw new IllegalStateException("Transfer request with id[%s] is already present".formatted(id));
    }

    private TransferState finalState() {
        return ThreadLocalRandom.current().nextBoolean() ? TransferState.COMPLETED : TransferState.FAILED;
    }

    private long finaliseAt() {
        return System.currentTimeMillis() + ThreadLocalRandom.current().nextLong(1000, 10000);
    }

    /**
     * Return current state of Transfer
     * @param id - a Transfer id
     * @return current state of Transfer
     * @throws IllegalArgumentException in case there no Transfer for the given id
     */
    @Override
    public TransferState getRequestState(TransferId id) {
        final var request = requests.get(id);
        if (request == null)
            throw new IllegalArgumentException("Request %s is not found".formatted(id));
        return request.finalState();
    }

    record Transfer(TransferState state, long finaliseAt, Address address, AssetQty amount) {
        public TransferState finalState() {
            return finaliseAt <= System.currentTimeMillis() ? state : TransferState.PROCESSING;
        }
    }
}
