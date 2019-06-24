package ch.decent.dcore.java.example.examples;

import ch.decent.sdk.DCoreApi;
import ch.decent.sdk.crypto.Credentials;
import ch.decent.sdk.model.Account;
import ch.decent.sdk.model.AmountWithAsset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BalanceExample {

    private final static String ASSET_SYMBOL = "DCT";

    @Autowired
    private ApiInitializationExample apiInitializationExample;
    @Autowired
    private LoginExample loginExample;

    /**
     * Example of fetching balance for my account.
     * @return Balance for my account.
     */
    public AmountWithAsset getMyBalance() {

        final DCoreApi dcoreApi = apiInitializationExample.connect();
        final Credentials credentials = loginExample.login();

        return dcoreApi
            .getBalanceApi()
            .getWithAsset(credentials.getAccount(), ASSET_SYMBOL)
            .blockingGet();
    }

    /**
     * Example of fetching balance by some account.
     * @return Balance for searched account.
     */
    public AmountWithAsset getBalanceByAccountName(String accountName) {

        final DCoreApi dcoreApi = apiInitializationExample.connect();

        final Account receiver = dcoreApi
            .getAccountApi()
            .getByName(accountName)
            .blockingGet();

        return dcoreApi
            .getBalanceApi()
            .getWithAsset(receiver.getId(), ASSET_SYMBOL)
            .blockingGet();
    }
}
