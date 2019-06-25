package ch.decent.dcore.java.example;

import ch.decent.dcore.java.example.examples.CreateAccountExample;
import ch.decent.dcore.java.example.examples.MessagesExample;
import ch.decent.sdk.model.Message;
import ch.decent.sdk.model.OperationHistory;
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
    private CreateAccountExample createAccountExample;
    @Autowired
    private MessagesExample messagesExample;

    @Test
    public void sendMessage() {

        final long timestamp = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
        final String newAccountName = "new-account-" + timestamp;
        final String newPublicKey = "DCT6TjLhr8uESvgtxrbWuXNAN3vcqzBMw5eyEup3PMiD2gnVxeuTb";

        createAccountExample.createAccount(newAccountName, newPublicKey);

        messagesExample.sendTo(newAccountName, "Some message.");

        final List<Message> messages = messagesExample.readAllSentMessages();

        Assert.assertTrue(messages.size() > 0);
    }
}
