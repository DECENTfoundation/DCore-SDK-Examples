package ch.decent.dcore.java.example;

import ch.decent.dcore.java.example.examples.AccountExample;
import ch.decent.dcore.java.example.examples.NFTExample;
import ch.decent.sdk.model.TransactionConfirmation;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NFTTest {

    @Autowired
    private NFTExample nftExample;

    @Autowired
    private AccountExample accountExample;

    @Test
    public void createIssueAndTransferNFT() {

        final long timestamp = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
        final String newAccountName = "new-account-" + timestamp;

        accountExample.createAccount(newAccountName);

        final String symbol = "EXAMPLE" + RandomStringUtils.randomAlphabetic(5).toUpperCase();

        nftExample.create(symbol);
        nftExample.issue(symbol);
        TransactionConfirmation confirmation = nftExample.sendToken(newAccountName);

        Assert.assertNotNull(confirmation);
    }
}
