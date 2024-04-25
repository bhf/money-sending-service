# money-sending-service
A service for sending money


### Sub-Projects
MoneySendingService - The actual core service logic, interactions are abstracted through the RESTDelegator allowing for any REST framework to be used

REST - A REST interface to the services using Springboot and OpenAPI

openapi.yml - the OpenAPI spec


### How to run
1. git clone https://github.com/bhf/money-sending-service
2. Install the MoneySendingService in your local maven repo
3. Run the REST interface via com.neverless.sendingservice.REST.RestApplication as a Springboot application

### Testing
Excluding API stubs (WithdrawalServiceStub and TransferServiceStub) the unit testing coverage is > 97%\

No e2e tests included in the timeframe
