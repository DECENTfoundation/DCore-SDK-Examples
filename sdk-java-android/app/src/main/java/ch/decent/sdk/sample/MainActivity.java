package ch.decent.sdk.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import ch.decent.sdk.DCoreApi;
import ch.decent.sdk.DCoreConstants;
import ch.decent.sdk.DCoreSdk;
import ch.decent.sdk.crypto.Credentials;
import ch.decent.sdk.model.*;
import ch.decent.sdk.net.TrustAllCerts;
import kotlin.Pair;
import okhttp3.OkHttpClient;
import org.slf4j.LoggerFactory;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        setup dcore api
        OkHttpClient client = TrustAllCerts.wrap(new OkHttpClient().newBuilder()).build();
        DCoreApi api = DCoreSdk.create(
                client,
                "wss://testnet-api.dcore.io/",
                "https://testnet-api.dcore.io/",
                LoggerFactory.getLogger("SDK_SAMPLE"));

//        create user credentials
        Credentials credentials = api.getAccountApi()
                .createCredentials("public-account-10", "5JMpT5C75rcAmuUB81mqVBXbmL1BKea4MYwVK6voMQLvigLKfrE")
                .blockingGet();

//        balance
        Pair<Asset, AssetAmount> balance = api.getBalanceApi()
                .getWithAsset(credentials.getAccount(), "DCT")
                .blockingGet();
        Log.i("BALANCE", balance.getFirst().format(balance.getSecond().getAmount()));

//        receiver
        Account receiver = api.getAccountApi().getByName("public-account-9")
                .blockingGet();
        Log.i("RECIEVER ACCOUNT", receiver.toString());

//        transfer
        TransactionConfirmation confirmation = api.getAccountApi()
                .transfer(credentials, receiver.getName(), DCoreConstants.DCT.amount(0.1), "hello memo")
                .blockingGet();

        Log.i("TRANSACTION", confirmation.toString());

//        verify
        ProcessedTransaction trx = api.getTransactionApi().get(confirmation)
                .blockingGet();
        Log.i("TRANSACTION EXIST", trx.toString());

//        history
        List<OperationHistory> history = api.getHistoryApi().listOperations(credentials.getAccount())
                .blockingGet();
        Log.i("HISTORY", history.toString());
    }
}
