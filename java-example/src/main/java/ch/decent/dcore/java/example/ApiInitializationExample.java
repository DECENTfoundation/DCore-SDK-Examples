package ch.decent.dcore.java.example;

import ch.decent.sdk.DCoreApi;
import ch.decent.sdk.DCoreSdk;
import ch.decent.sdk.net.TrustAllCerts;
import okhttp3.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ApiInitializationExample {

    private static final Logger logger = LoggerFactory.getLogger("SDK_SAMPLE");
    private static final String WEB_SOCKET_URL = "wss://testnet-api.dcore.io/";
    private static final String HTTP_URL = "https://testnet-api.dcore.io/";

    public DCoreApi connect() {

        final OkHttpClient.Builder clientBuilder =  new OkHttpClient().newBuilder();
        final OkHttpClient httpClient = TrustAllCerts.wrap(clientBuilder).build();

        return DCoreSdk.create(
            httpClient,
            WEB_SOCKET_URL,
            HTTP_URL,
            logger
        );
    }

}
