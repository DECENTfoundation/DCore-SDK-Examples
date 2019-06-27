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
    private ConnectionExample connectionExample;
    @Autowired
    private LoginExample loginExample;
    @Autowired
    private AccountExample accountExample;

    /**
     * Example of sending message to the given account name.
     *
     * @param accountName Valid account name.
     * @param message     Message you would like to send.
     * @return Confirmation of the transaction.
     */
    public TransactionConfirmation sendTo(String accountName, String message) {

        final DCoreApi dcoreApi = connectionExample.connect();
        final Credentials credentials = loginExample.login();
        final Account receiver = accountExample.getAccountByName(accountName);

        // Important - using kotlin.Pair instead of JavaFx Pair.
        final Pair<ChainObject, String> pairOfReceiverAndMessage = new Pair<>(receiver.getId(), message);
        final List<Pair<ChainObject, String>> messagesToSend = Collections.singletonList(pairOfReceiverAndMessage);

        return dcoreApi
            .getMessagingApi()
            .send(credentials, messagesToSend)
            .blockingGet();
    }

    /**
     * Example of reading last messages that you received.
     *
     * @return List of received messages.
     */
    public List<Message> readAllRecievedMessages() {

        final DCoreApi dcoreApi = connectionExample.connect();
        final Credentials credentials = loginExample.login();
        final int numberOfMessages = Integer.MAX_VALUE;

        return dcoreApi
            .getMessagingApi()
            .getAllDecryptedForReceiver(credentials, numberOfMessages)
            .blockingGet();
    }

    /**
     * Example of reading last messages that you sent.
     *
     * @return List of sent messages.
     */
    public List<Message> readAllSentMessages() {

        final DCoreApi dcoreApi = connectionExample.connect();
        final Credentials credentials = loginExample.login();
        final int numberOfMessages = Integer.MAX_VALUE;

        return dcoreApi
            .getMessagingApi()
            .getAllDecryptedForSender(credentials, numberOfMessages)
            .blockingGet();
    }
}
