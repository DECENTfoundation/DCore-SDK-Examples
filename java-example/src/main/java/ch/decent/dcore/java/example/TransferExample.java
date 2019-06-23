package ch.decent.dcore.java.example;

import ch.decent.sdk.DCoreApi;
import ch.decent.sdk.DCoreConstants;
import ch.decent.sdk.crypto.Credentials;
import ch.decent.sdk.model.Account;
import ch.decent.sdk.model.AssetAmount;
import ch.decent.sdk.model.TransactionConfirmation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransferExample {

    @Autowired
    private ApiInitializationExample apiInitializationExample;
    @Autowired
    private LoginExample loginExample;

    public TransactionConfirmation transferTo(String accountName, Double amount, String someMessage) {

        final DCoreApi dcoreApi = apiInitializationExample.connect();
        final Credentials credentials = loginExample.login();
        final Account receiver = dcoreApi
            .getAccountApi()
            .getByName(accountName)
            .blockingGet();
        final AssetAmount assetAmount = DCoreConstants.DCT.amount(amount);

        return dcoreApi.getAccountApi()
            .transfer(
                credentials,
                receiver.getName(),
                assetAmount,
                someMessage)
            .blockingGet();
    }
}
