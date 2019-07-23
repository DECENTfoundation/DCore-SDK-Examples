package ch.decent.dcore.java.example;

import ch.decent.dcore.java.example.examples.AccountExample;
import ch.decent.dcore.java.example.examples.MessagesExample;
import ch.decent.sdk.model.Message;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MessagesTest {

    @Autowired
    private AccountExample accountExample;
    @Autowired
    private MessagesExample messagesExample;

    @Test
    public void sendMessage() {

        final long timestamp = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
        final String newAccountName = "new-account-" + timestamp;

        accountExample.createAccount(newAccountName);

        messagesExample.sendTo(newAccountName, "Some message.");

        final List<Message> messages = messagesExample.readAllSentMessages();

        Assert.assertTrue(messages.size() > 0);
    }
}
