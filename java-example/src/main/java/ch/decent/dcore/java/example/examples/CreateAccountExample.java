package ch.decent.dcore.java.example.examples;

import ch.decent.sdk.DCoreApi;
import ch.decent.sdk.crypto.Address;
import ch.decent.sdk.crypto.Credentials;
import ch.decent.sdk.model.AssetAmount;
import ch.decent.sdk.model.TransactionConfirmation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigInteger;

@Component
public class CreateAccountExample {

    private static final BigInteger AMOUNT_OF_DCT = BigInteger.ONE;

    @Autowired
    private ApiInitializationExample apiInitializationExample;
    @Autowired
    private LoginExample loginExample;

    public TransactionConfirmation createAccount(String newAccountName, String newPublicKey) {

        final DCoreApi dcoreApi = apiInitializationExample.connect();
        final Credentials credentials = loginExample.login();
        final Address decodedPublicKey = Address.decode(newPublicKey);
        final AssetAmount initialFee = new AssetAmount(AMOUNT_OF_DCT);

        return dcoreApi.getAccountApi().create(
            credentials,
            newAccountName,
            decodedPublicKey,
            initialFee
        ).blockingGet();
    }
}
