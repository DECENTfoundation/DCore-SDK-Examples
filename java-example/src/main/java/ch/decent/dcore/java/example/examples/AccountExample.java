package ch.decent.dcore.java.example.examples;

import ch.decent.sdk.DCoreApi;
import ch.decent.sdk.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountExample {

    @Autowired
    private ConnectionExample connectionExample;

    /**
     * Example of getting any account by its name.
     * @param accountName name of the account e.g. dw-account
     * @return Account instance for given account name
     */
    public Account getAccountByName(String accountName) {

        final DCoreApi dcoreApi = connectionExample.connect();

        return dcoreApi
            .getAccountApi()
            .getByName(accountName)
            .blockingGet();
    }
}
