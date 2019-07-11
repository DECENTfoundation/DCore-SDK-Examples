package ch.decent.dcore.java.example.examples;

import ch.decent.sdk.DCoreApi;
import ch.decent.sdk.crypto.Credentials;
import ch.decent.sdk.model.*;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class NFTExample {

    private static final Long AMOUNT_OF_DCT_REQUIRED_FOR_CREATION = 100000L;

    @Autowired
    private ConnectionExample connectionExample;
    @Autowired
    private LoginExample loginExample;

    public TransactionConfirmation create(String symbol) {

        final DCoreApi dcoreApi = connectionExample.connect();
        final Credentials credentials = loginExample.login();

        final AssetAmount dctAssetAmount = new AssetAmount(AMOUNT_OF_DCT_REQUIRED_FOR_CREATION);
        final Fee initialFee = new Fee(dctAssetAmount.getAssetId(), AMOUNT_OF_DCT_REQUIRED_FOR_CREATION);

        return dcoreApi.getNftApi()
            .create(
                credentials,
                symbol,
                100,
                false,
                "ergre",
                MyNewNft.class,
                true)
            .blockingGet();
    }
}

