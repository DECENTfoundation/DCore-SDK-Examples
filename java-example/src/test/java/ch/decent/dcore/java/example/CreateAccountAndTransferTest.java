package ch.decent.dcore.java.example;

import ch.decent.dcore.java.example.examples.AccountExample;
import ch.decent.dcore.java.example.examples.BalanceExample;
import ch.decent.dcore.java.example.examples.TransferExample;
import ch.decent.sdk.model.AmountWithAsset;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CreateAccountAndTransferTest {

    @Autowired
    private AccountExample accountExample;

    @Autowired
    private BalanceExample balanceExample;

    @Autowired
    private TransferExample transferExample;

    @Test
    public void createAccountAndTransfer() {
        final long timestamp = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
        final String newAccountName = "new-account-" + timestamp;

        accountExample.createAccount(newAccountName);

        final AmountWithAsset balanceBeforeTransaction = balanceExample.getBalanceByAccountName(newAccountName);
        Assert.assertEquals(0, balanceBeforeTransaction.getAmount().getAmount());

        transferExample.transferTo(newAccountName, 0.00000001D, "You are rich now!");

        final AmountWithAsset balanceAfterTransaction = balanceExample.getBalanceByAccountName(newAccountName);
        Assert.assertEquals(1, balanceAfterTransaction.getAmount().getAmount());
    }
}
