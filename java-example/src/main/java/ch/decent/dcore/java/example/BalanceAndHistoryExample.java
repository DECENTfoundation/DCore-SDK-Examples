package ch.decent.dcore.java.example;

import ch.decent.sdk.DCoreApi;
import ch.decent.sdk.crypto.Credentials;
import ch.decent.sdk.model.AmountWithAsset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BalanceAndHistoryExample {

    @Autowired
    private ApiInitializationExample apiInitializationExample;

    @Autowired
    private LoginExample loginExample;

    public AmountWithAsset getBalanceBlocking() {

        final DCoreApi dcoreApi = apiInitializationExample.connect();
        final Credentials credentials = loginExample.login();

        return dcoreApi.getBalanceApi()
            .getWithAsset(credentials.getAccount(), "DCT")
            .blockingGet();
    }

    public void getBalanceAsync() {

        final DCoreApi dcoreApi = apiInitializationExample.connect();
        final Credentials credentials = loginExample.login();

        dcoreApi.getBalanceApi()
            .getWithAsset(credentials.getAccount(), "DCT")
            .blockingGet(); // TODO async
    }

}
