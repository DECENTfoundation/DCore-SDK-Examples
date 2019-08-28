package ch.decent.dcore.java.example.examples;

import ch.decent.sdk.api.blocking.DCoreApi;
import ch.decent.sdk.crypto.Credentials;
import ch.decent.sdk.model.TransactionConfirmation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UIAExample {

    private static final int PRECISION = 6;

    @Autowired
    private ConnectionExample connectionExample;
    @Autowired
    private LoginExample loginExample;

    /**
     * Example of creating new asset/currency in DCore block-chain.
     *
     * @param symbol Uppercase alphabetic string for your asset name.
     * @return Transaction confirmation.
     * @see <a href="https://en.bitcoin.it/wiki/Satoshi_(unit)">Number of decimal points in Satoshi number format</a>
     */
    public TransactionConfirmation create(String symbol) {
        final DCoreApi dcoreApi = connectionExample.connect();
        final Credentials credentials = loginExample.login();
        final byte convertedPrecision = (byte) PRECISION;
        final String someDescription = "New example asset.";

        return dcoreApi.getAssetApi()
            .create(
                credentials,
                symbol,
                convertedPrecision,
                someDescription);
    }

    /**
     * Issie the amount of asset/currency that will be released into circulation.
     *
     * @param symbol         String asset name that was issued by you.
     * @param amountOfAssets Amount of currency that will be released into circulation.
     * @return Transaction confirmation.
     */
    public TransactionConfirmation issue(String symbol, Integer amountOfAssets) {
        final DCoreApi dcoreApi = connectionExample.connect();
        final Credentials credentials = loginExample.login();

        return dcoreApi.getAssetApi()
            .issue(
                credentials,
                symbol,
                amountOfAssets);
    }
}
