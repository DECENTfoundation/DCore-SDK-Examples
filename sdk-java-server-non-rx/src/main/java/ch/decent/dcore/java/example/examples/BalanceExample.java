package ch.decent.dcore.java.example.examples;

import ch.decent.sdk.api.blocking.DCoreApi;
import ch.decent.sdk.crypto.Credentials;
import ch.decent.sdk.model.Account;
import ch.decent.sdk.model.AmountWithAsset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BalanceExample {

    private final static String ASSET_SYMBOL = "DCT";

    @Autowired
    private ConnectionExample connectionExample;
    @Autowired
    private LoginExample loginExample;
    @Autowired
    private AccountExample accountExample;

    /**
     * Example of fetching balance for my account.
     *
     * @return Balance for my account.
     */
    public AmountWithAsset getMyBalance() {

        final DCoreApi dcoreApi = connectionExample.connect();
        final Credentials credentials = loginExample.login();

        return dcoreApi
            .getBalanceApi()
            .getWithAsset(credentials.getAccount(), ASSET_SYMBOL);
    }

    /**
     * Example of fetching balance by some account.
     *
     * @return Balance for searched account.
     */
    public AmountWithAsset getBalanceByAccountName(String accountName) {

        final DCoreApi dcoreApi = connectionExample.connect();
        final Account receiver = accountExample.getAccountByName(accountName);

        return dcoreApi
            .getBalanceApi()
            .getWithAsset(receiver.getId(), ASSET_SYMBOL);
    }
}
