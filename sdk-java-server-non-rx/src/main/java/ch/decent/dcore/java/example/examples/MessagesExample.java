package ch.decent.dcore.java.example.examples;

import ch.decent.sdk.api.blocking.DCoreApi;
import ch.decent.sdk.crypto.Credentials;
import ch.decent.sdk.model.Account;
import ch.decent.sdk.model.Message;
import ch.decent.sdk.model.MessageRequest;
import ch.decent.sdk.model.TransactionConfirmation;
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
        final MessageRequest messageToSend = new MessageRequest(receiver.getId(), message);
        final List<MessageRequest> messagesToSend = Collections.singletonList(messageToSend);

        return dcoreApi
            .getMessagingApi()
            .send(credentials, messagesToSend);
    }

    /**
     * Example of reading last messages that you received.
     *
     * @return List of received messages.
     */
    public List<Message> readAllRecievedMessages() {

        final DCoreApi dcoreApi = connectionExample.connect();
        final Credentials credentials = loginExample.login();
        final int numberOfMessages = 10;

        return dcoreApi
            .getMessagingApi()
            .findAllDecryptedForReceiver(credentials, numberOfMessages);
    }

    /**
     * Example of reading last messages that you sent.
     *
     * @return List of sent messages.
     */
    public List<Message> readAllSentMessages() {

        final DCoreApi dcoreApi = connectionExample.connect();
        final Credentials credentials = loginExample.login();
        final int numberOfMessages = 10;

        return dcoreApi
            .getMessagingApi()
            .findAllDecryptedForSender(credentials, numberOfMessages);
    }
}
