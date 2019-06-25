package ch.decent.dcore.java.example;

import ch.decent.dcore.java.example.examples.*;
import ch.decent.sdk.model.Account;
import ch.decent.sdk.model.AmountWithAsset;
import ch.decent.sdk.model.OperationHistory;
import ch.decent.sdk.model.TransactionConfirmation;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CreateAccountAndTransferTest {

    @Autowired
    private CreateAccountExample createAccountExample;

    @Autowired
    private BalanceExample balanceExample;

    @Autowired
    private TransferExample transferExample;

    @Test
    public void createAccountAndTransfer() {
        final long timestamp = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
        final String newAccountName = "new-account-" + timestamp;
        final String newPublicKey = "DCT6TjLhr8uESvgtxrbWuXNAN3vcqzBMw5eyEup3PMiD2gnVxeuTb";

        createAccountExample.createAccount(newAccountName, newPublicKey);

        final AmountWithAsset balanceBeforeTransaction = balanceExample.getBalanceByAccountName(newAccountName);
        Assert.assertEquals(BigInteger.valueOf(0), balanceBeforeTransaction.getAmount().getAmount());

        transferExample.transferTo(newAccountName, 0.00000001D, "You are rich now!");

        final AmountWithAsset balanceAfterTransaction = balanceExample.getBalanceByAccountName(newAccountName);
        Assert.assertEquals(BigInteger.valueOf(1), balanceAfterTransaction.getAmount().getAmount());
    }
}
