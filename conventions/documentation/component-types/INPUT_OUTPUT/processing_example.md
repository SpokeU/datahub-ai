## [Create Connection](DataHubAPI.md#create-connection)

*A set of blocks Input -> Process -> Output. As one operations might have different flows we want to describe different inputs and outputs for each flow.*

*Each format of Input or output will have specific format according to what we are describing.*

*For instance we want to represent a database state after a request is processed*

### Create Postgres Connection

Input: 
[ConnectionRequest](DataHubAPI.md#connectionrequest)

Output:

*Any expected outputs after above input is processed. For instance:*
*-DB State*
*Rest integration calls*
*SQS messages put*
*etc.*


