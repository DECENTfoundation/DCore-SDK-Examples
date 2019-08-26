# DCore SDK Swift iOS Example #

In this project you can find simple usage examples of [DECENT DCoreSwift SDK library - https://github.com/DECENTfoundation/DCoreSwift-SDK](https://github.com/DECENTfoundation/DCoreSwift-SDK)

## List of Use Cases ##

| Class name                     | Use cases                                                                                                                          |
|--------------------------------|------------------------------------------------------------------------------------------------------------------------------------|
| *BalanceHistoryViewController* | Example of loading balance. Example of paginating through history.                                                                 |
| *CreateUIAViewController*      | Example of creating and also issuing a new type of asset (UIA) into circulation.                                                   |
| *CreateAccountViewController*  | Example of creating account. Creating account from iOS app is however highly discouraged.                                          |
| *TransferViewController*       | Transferring some amount of DCT from my account to given account name or address or id.                                            |
| *MessagesListViewController*   | Reading the history of my sent or received messages.                                                                               |
| *MessageSendViewController*    | Sending message to given account id                                                                                                |
| *TestableViewController*       | Example of amount conversion between different UIAs, created specifically to showcase how to unit test logic related to DCore SDK. |

## Getting Started ##
Follow these instructions to successfuly run the iOS app on iOS Simulator.

### Prerequisites ###
### Used Swift version and XCode ###
```Swift
Swift 5
XCode 10.3
Deployment target iOS SDK 12.4
```
### Used DCore SDK Version ###
```Swift
3.1.2
```

#### Carthage ####
Project uses [Carthage](https://github.com/Carthage/Carthage) to resolve dependencies on external libraries. To start developing, install it using instructions in official documentation and run `carthage update --platform ios`

### Project Structure ###
All examples can be found in a group called `Examples`

### Tests ###
Check `TestableViewModelTests` to see an example of a unit test. In this unit test the specific DCore API is mocked and only functionality of the class is tested.

### About creating accounts from iOS app ###
Creating accounts from iOS app is highly discouraged. In order to create an account on DCore, the iOS app needs to have access to private key of account that will pay transaction fee for performing create account operation. 

Including the private key into the app binary is not safe, because the binary could get decompiled and the private key could be extracted from the binary. Other option might be to send the private key to the app via network, but this creates a risk of Man-In-The-Middle attack and having the private key leaked that way.

The recommended solution to creating accounts on DCore is to have a backend service dedicated to creating accounts. That way the private key stays hidden at the backend.
