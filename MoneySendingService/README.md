### Assumptions from the API stub:

Withdraws are done from a single master account per currency or asset

Updating of the user account is handled by another service once the withdraw has occured, or the update to the user account occurs through manual confirmation of the withdraw

### Validation Design Alternatives

The validation services can send messages to the money transfer service to sendMoney and withdrawMoney, the money transfer service does not have to know about the user or their balance. The validation services then has to maintain state about pending withdraws alongside accessing user state- this could result in duplication of state in the validation service and the money transfer service and cause potential race conditions

The validation services are embedded in the money transfer service - the state of pending withdraws and transfer is now in one place, but the money transfer service now has to be aware of users and their balances before they call the API to do a transfer or a withdraw. This is less risky and no less scale-able than the alternative of seperating the validation services.
