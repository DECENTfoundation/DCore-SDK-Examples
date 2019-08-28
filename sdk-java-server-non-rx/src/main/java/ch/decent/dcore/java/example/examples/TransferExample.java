package ch.decent.dcore.java.example.examples;

import ch.decent.sdk.api.blocking.DCoreApi;
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
    private ConnectionExample connectionExample;
    @Autowired
    private LoginExample loginExample;
    @Autowired
    private AccountExample accountExample;

    /**
     * Example of transferring amount of DCTs to valid account name.
     *
     * @param accountName Valid account name.
     * @param amount Amount of DCT you wish to transfer to the account.
     * @param someMessage Payment reference.
     * @return Transaction confirmation.
     */
    public TransactionConfirmation transferTo(String accountName, Double amount, String someMessage) {

        final DCoreApi dcoreApi = connectionExample.connect();
        final Credentials credentials = loginExample.login();
        final Account receiver = accountExample.getAccountByName(accountName);
        final AssetAmount assetAmount = DCoreConstants.DCT.amount(amount);

        return dcoreApi.getAccountApi()
            .transfer(
                credentials,
                receiver.getName(),
                assetAmount,
                someMessage);
    }
}
