package ch.decent.dcore.java.example;

import ch.decent.dcore.java.example.examples.NFTExample;
import ch.decent.dcore.java.example.examples.UIAExample;
import ch.decent.sdk.model.TransactionConfirmation;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NFTTest {

    @Autowired
    private NFTExample nftExample;

    @Test
    public void getMyBalance() {

        final TransactionConfirmation creationConfirmation = nftExample.create("APPLE");

    }
}
