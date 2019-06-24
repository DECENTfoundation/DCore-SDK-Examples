package ch.decent.dcore.java.example.examples;

import ch.decent.sdk.DCoreApi;
import ch.decent.sdk.crypto.Credentials;
import ch.decent.sdk.model.Account;
import ch.decent.sdk.model.ChainObject;
import ch.decent.sdk.model.Message;
import ch.decent.sdk.model.TransactionConfirmation;
import kotlin.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class MessagesExample {

    @Autowired
    private ApiInitializationExample apiInitializationExample;
    @Autowired
    private LoginExample loginExample;

    public TransactionConfirmation sendTo(String accountName, String message) {

        final DCoreApi dcoreApi = apiInitializationExample.connect();
        final Credentials credentials = loginExample.login();
        final Account receiver = dcoreApi
            .getAccountApi()
            .getByName(accountName)
            .blockingGet();

        // Important - using kotlin.Pair instead of JavaFx Pair.
        final Pair<ChainObject, String> pairOfReceiverAndMessage = new Pair<>(receiver.getId(), message);
        final List<Pair<ChainObject, String>> messagesToSend = Collections.singletonList(pairOfReceiverAndMessage);

        return dcoreApi
            .getMessagingApi()
            .send(credentials, messagesToSend)
            .blockingGet();
    }

    public List<Message> readAllRecievedMessages() {

        final DCoreApi dcoreApi = apiInitializationExample.connect();
        final Credentials credentials = loginExample.login();
        final int numberOfMessages = Integer.MAX_VALUE;

        return dcoreApi
            .getMessagingApi()
            .getAllDecryptedForReceiver(credentials, numberOfMessages)
            .blockingGet();
    }
}
