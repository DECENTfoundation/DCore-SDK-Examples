package ch.decent.dcore.java.example;

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
public class UIATest {

    @Autowired
    private UIAExample uiaExample;

    @Test
    public void createAndIssueNewAssetIntoCirculation() {

        final String symbol = "EXAMPLE" + RandomStringUtils.randomAlphabetic(5).toUpperCase();

        final TransactionConfirmation creationConfirmation = uiaExample.create(symbol);

        Assert.assertNotNull(creationConfirmation);

        final Integer amountOfAssets = 1;
        final TransactionConfirmation issueConfirmation = uiaExample.issue(symbol, amountOfAssets);

        Assert.assertNotNull(issueConfirmation);
    }
}
