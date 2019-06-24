package ch.decent.dcore.java.example.examples;

import ch.decent.sdk.DCoreApi;
import ch.decent.sdk.crypto.Credentials;
import ch.decent.sdk.model.AmountWithAsset;
import ch.decent.sdk.model.OperationHistory;
import io.reactivex.disposables.Disposable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BalanceAndHistoryExample {

    private final static String ASSET_SYMBOL = "DCT";

    @Autowired
    private ApiInitializationExample apiInitializationExample;
    @Autowired
    private LoginExample loginExample;

    public AmountWithAsset getBalanceBlocking() {

        final DCoreApi dcoreApi = apiInitializationExample.connect();
        final Credentials credentials = loginExample.login();

        return dcoreApi
            .getBalanceApi()
            .getWithAsset(credentials.getAccount(), ASSET_SYMBOL)
            .blockingGet();
    }

    public List<OperationHistory> getHistoryBlocking() {

        final DCoreApi dcoreApi = apiInitializationExample.connect();
        final Credentials credentials = loginExample.login();

        return dcoreApi
            .getHistoryApi()
            .listOperations(credentials.getAccount())
                .blockingGet();
    }

    public Disposable getBalanceAsync() {

        final DCoreApi dcoreApi = apiInitializationExample.connect();
        final Credentials credentials = loginExample.login();

        return dcoreApi
            .getBalanceApi()
            .getWithAsset(credentials.getAccount(), ASSET_SYMBOL)
            .subscribe($ -> {
                // execute your async callback
            });
    }
}
