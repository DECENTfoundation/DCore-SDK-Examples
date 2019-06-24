package ch.decent.dcore.java.example.examples;

import ch.decent.sdk.DCoreApi;
import ch.decent.sdk.crypto.Credentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoginExample {

    private final static String ACCOUNT_NAME = "public-account-10";
    private final static String PRIVATE_KEY = "5JMpT5C75rcAmuUB81mqVBXbmL1BKea4MYwVK6voMQLvigLKfrE";

    @Autowired
    private ApiInitializationExample apiInitializationExample;

    public Credentials login() {

        final DCoreApi dcoreApi = apiInitializationExample.connect();

        return dcoreApi
            .getAccountApi()
            .createCredentials(ACCOUNT_NAME,PRIVATE_KEY)
            .blockingGet();
    }
}
