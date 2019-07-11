package ch.decent.dcore.java.example;

import ch.decent.dcore.java.example.examples.BalanceExample;
import ch.decent.sdk.model.AmountWithAsset;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BalanceTest {

    @Autowired
    private BalanceExample balanceExample;

    @Test
    public void getMyBalance() {
        final AmountWithAsset result = balanceExample.getMyBalance();

        Assert.assertEquals("DCT", result.getAsset().getSymbol());
        Assert.assertTrue(result.getAmount().getAmount() > 0);
    }

    @Test
    public void getBalanceByAccountName() {
        final AmountWithAsset result = balanceExample.getBalanceByAccountName("public-account-10");

        Assert.assertEquals("DCT", result.getAsset().getSymbol());
        Assert.assertTrue(result.getAmount().getAmount() > 0);
    }
}
