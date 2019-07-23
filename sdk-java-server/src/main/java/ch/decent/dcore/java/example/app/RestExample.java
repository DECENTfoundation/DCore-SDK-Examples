package ch.decent.dcore.java.example.app;

import ch.decent.dcore.java.example.examples.AccountExample;
import ch.decent.dcore.java.example.examples.BalanceExample;
import ch.decent.sdk.model.AmountWithAsset;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestExample {

    @Autowired
    private BalanceExample balanceExample;
    @Autowired
    private AccountExample accountExample;

    /**
     * Example of REST endpoint to get account balance.
     * @param accountName Valid account name.
     * @return Asset amount information
     */
    @RequestMapping("/balance")
    public AmountWithAsset getBalance(@RequestParam(value = "accountName", defaultValue = "public-account-10") String accountName) {
        return balanceExample.getBalanceByAccountName(accountName);
    }

    /**
     * Example REST endpoint of account creation.
     * @return New recently created account name.
     */
    @RequestMapping(value = "/createAccount")
    public String createAccount() {
        final String newAccountName = "new-example-account-" + RandomStringUtils.randomAlphabetic(10).toLowerCase();

        accountExample.createAccount(newAccountName);

        return accountExample.getAccountByName(newAccountName).getName();
    }
}
