Assumptions from the API stub:

Withdraws are done from a single master account per currency or asset

Updating of the user account is handled by another service once the withdraw has occured, or the update to the user account occurs through manual confirmation of the withdraw

Send money assumptions:
- a validation service is validating the user has funds, including any pending user transfers and any pending withdrawls
- the user src and dst account exist

Withdrawl assumptions:
- a validation service is validating the user has funds to withdraw+pending withdrawl requests
- config to allow multiple withdraw requests on the same ccy, but by default it allows multiple withdrawl requests

The validation services can send messages to the money transfer service to sendMoney and withdrawMoney, the money transfer service does not have to know about the user or their balance. The validation services then has to maintain state about pending withdraws alongside accessing user state- this could result in duplication of state in the validation service and the money transfer service and cause potential race conditions

The validation services are embedded in the money transfer service - the state of pending withdraws and transfer is now in one place, but the money transfer service now has to be aware of users and their balances before they call the API to do a transfer or a withdraw. This is less risky and no less scale-able than the alternative of seperating the validation services.


API endpoints

1. sendMoney(UUID srcAccount, UUID dstAccount, ccy, qty)
- src account exists
- dst account exists
- UUID idempotency via monotonic nonce
- qty is valid
- can't send more than you have+any pending transactions

2. withdrawMoney(UUID srcAccount, withdrawlID, destinationAddress, qty)
- src account exists
- balance is sufficient
- pending withdraws from same account
- destination address is valid
- qty is valid given pending withdraws

3. getStatus(withdrawlID)
- withdrawl ID is known


Entities:
user
account
money model - asset mantissa and exponent