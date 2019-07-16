package ch.decent.dcore.java.example.examples;

import ch.decent.sdk.DCoreApi;
import ch.decent.sdk.crypto.Credentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoginExample {

    private final static String ACCOUNT_NAME = "public-account-5";
    private final static String PRIVATE_KEY = "5JaDAz6qfDwY64SuFhF5VVr98k77tZvETMx1DZEYwCj2U9H2Xb3";

    @Autowired
    private ConnectionExample connectionExample;

    /**
     * Example of logging into the DECENT test network with private key.
     *
     * @return Credentials to use for other API calls.
     */
    public Credentials login() {

        final DCoreApi dcoreApi = connectionExample.connect();

        return dcoreApi
            .getAccountApi()
            .createCredentials(ACCOUNT_NAME, PRIVATE_KEY)
            .blockingGet();
    }
}
