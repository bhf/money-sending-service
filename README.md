# money-sending-service
A service for sending money


### Sub-Projects
MoneySendingService - The actual core service logic, interactions are abstracted through the RESTDelegator allowing for any REST framework to be used

REST - A REST interface to the services using Springboot and OpenAPI

openapi.yml - the OpenAPI spec


### Testing
Excluding API stubs (WithdrawalServiceStub and TransferServiceStub) the unit testing coverage is > 97%\

