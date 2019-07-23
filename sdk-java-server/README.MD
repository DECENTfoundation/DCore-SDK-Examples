# DCore SDK Java Example

In this project you can find simple usage examples of [DECENT DCoreKt SDK library - https://github.com/DECENTfoundation/DCoreKt-SDK](https://github.com/DECENTfoundation/DCoreKt-SDK)

## List of Use Cases.

Class name | Use cases
--- | --- |
*AccountExample* | Search for the account by its name or create account.
*BalanceExample* | Get my balance or getting balance by account name.
*ConnectionExample* | Create connection to DCore test node
*GenerateKeysExample* | Example of Key Pair generation.
*HistoryExample* | Example of paginating thru history and fetching full history records useful for scheduled job purpose.
*LoginExample* | Logging into with your private key.
*MessagesExample* | Sending message to given account name and reading the history of my sent or received messages.
*SeedersExample* | List the content seeders by rating.
*TransferExample* | Transferring some amount of DCT from my account to given account name.
*UIAExample* | Example of creating and also issuing given amount of a new type of asset (UIA) into circulation.
*NFTExample* | Example of transferring non fungible tokens and also creating and issuing given amount of a new non fungible tokens.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

The project is written in Java with the usage of Spring Boot Framework.

```
Java 8
Maven 3
```

#### Used DCoreKt version

```
2.3.1
```

### Project structure

There are two separate folders in the source of the project.

1. ``` examples ``` - Generic examples where are implemented all use cases described above in *List of Use Cases*.

2. ``` app ``` - Real world examples with practical usage of examples from ``` examples ``` folder. Further explanation below:

Class name | Use cases
--- | --- |
*RestExample* | Example of running simple REST server together with our *examples*.
*ScheduledJobExample* | Example of running simple job with recursive execution of *examples*.

### Run Locally

1. You can run the project as a java application with simply running the main class ``` ch.decent.dcore.java.example.Application ``` in your favorite IDE.

2. Visit [http://localhost:8080/balance](http://localhost:8080/balance) to see balance response.

3. Visit [http://localhost:8080/createAccount](http://localhost:8080/createAccount) to create new user on blockchain.

List of REST examples:
```
http://localhost:8080/balance?accountName=public-account-10
http://localhost:8080/createAccount
```

## Tests

There are few integration tests that creat real calls on test-net and execute most of the examples in the project.

All tests run against real [DCore Test network](https://docs.decent.ch/Testnets/)

Class name | Use cases
--- | --- |
*BalanceTest* | Testing the balances on real accounts created on our test network.
*CreateAccountAndTransferTest* | Testing the account creation and executing the transaction with balance validation on our test network.
*HistoryTest* | Testing the pagination and full history on real account.
*MessagesTest* | Testing the account creation with sending message to the new account.
*UIATest* | Testing creation and issuing new asset into circulation.
*NFTTest* | Testing transfer and also creation and issuing new non fungible tokens.

## Deployment

1. If you wish to deploy the project simply execute: ``` mvn package ```

2. You can find build in: ``` target/app.jar ```

3. Start the app with simple command: ``` java -jar app.jar ```

## Quick Start Your Own Java Project

1. Setup your starting project (e.g.:[Spring BOOT](https://start.spring.io/))
2. Import [DECENT DCoreKt SDK library](https://github.com/DECENTfoundation/DCoreKt-SDK)

#### Maven

* Add Repository:
```

<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```
* Add Dependency:
```
<dependency>
    <groupId>com.github.DECENTfoundation</groupId>
    <artifactId>DCoreKt-SDK</artifactId>
    <version>2.3.1</version>
</dependency>
```

#### Gradle:

* Add Repository:
```
repositories {
    mavenCentral()
    maven { url 'https://jitpack.io' }
}
```
* Add Dependency:
```
dependencies {
        implementation 'com.github.DECENTfoundation:DCoreKt-SDK:2.3.1'
}
```