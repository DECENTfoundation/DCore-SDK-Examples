package ch.decent.dcore.java.example.examples;

import ch.decent.sdk.DCoreSdk;
import ch.decent.sdk.api.blocking.DCoreApi;
import ch.decent.sdk.net.TrustAllCerts;
import okhttp3.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ConnectionExample {

    private static final Logger logger = LoggerFactory.getLogger("JavaExample");
    private static final String WEB_SOCKET_URL = "wss://testnet-socket.dcore.io/";
    private static final String HTTP_URL = "https://testnet.dcore.io/";

    private static DCoreApi holdConnection = null;

    /**
     * Example of creating and reusing the connecting to DECENT test network.
     *
     * @return Opened connection to network.
     */
    public DCoreApi connect() {

        final OkHttpClient.Builder clientBuilder = new OkHttpClient().newBuilder();
        final OkHttpClient httpClient = TrustAllCerts.wrap(clientBuilder).build();

        if (holdConnection == null) {
            holdConnection = DCoreSdk.createApiBlocking(
                httpClient,
                WEB_SOCKET_URL,
                HTTP_URL,
                logger
            );
        }

        return holdConnection;
    }
}
