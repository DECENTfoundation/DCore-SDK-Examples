package ch.decent.dcore.java.example.examples;

import ch.decent.sdk.DCoreApi;
import ch.decent.sdk.crypto.Credentials;
import ch.decent.sdk.model.Account;
import ch.decent.sdk.model.Nft;
import ch.decent.sdk.model.TransactionConfirmation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NFTExample {

    private static final Boolean TOKEN_IS_TRANSFERABLE = true;
    private static final Boolean MAX_SUPPLY_IS_FIXED = true;
    private static final Long MAX_NUMBER_OF_TOKENS = 1000L;

    @Autowired
    private ConnectionExample connectionExample;
    @Autowired
    private LoginExample loginExample;
    @Autowired
    private AccountExample accountExample;

    /**
     * Issue the brand new non fungible token into circulation.
     *
     * @param symbol String version of the new NFT symbol.
     * @return Transaction confirmation.
     */
    public TransactionConfirmation create(String symbol) {
        final DCoreApi dcoreApi = connectionExample.connect();
        final Credentials credentials = loginExample.login();
        final String description = "Creating my new token";

        return dcoreApi.getNftApi()
            .create(
                credentials,
                symbol,
                MAX_NUMBER_OF_TOKENS,
                MAX_SUPPLY_IS_FIXED,
                description,
                MyCustomNftToken.class,
                TOKEN_IS_TRANSFERABLE)
            .blockingGet();
    }

    /**
     * Issue the new created NFT to some accountName.
     *
     * @param symbol String version of the NFT symbol.
     * @param accountName Account name receiving the NFT data instance
     * @return Transaction confirmation.
     */
    public TransactionConfirmation issue(String symbol, String accountName) {
        final DCoreApi dcoreApi = connectionExample.connect();
        final Credentials credentials = loginExample.login();
        final Account receiver = accountExample.getAccountByName(accountName);

        return dcoreApi.getNftApi()
            .issue(
                credentials,
                symbol,
                receiver.getId())
            .blockingGet();
    }

    /**
     * Send one non fungible token to some account on DCore block-chain.
     *
     * @param receiverAccountName account name of token receiver.
     * @param tokenSymbol String version of the NFT symbol.
     * @return Transaction confirmation.
     */
    public TransactionConfirmation sendToken(String receiverAccountName, String tokenSymbol) {
        final DCoreApi dcoreApi = connectionExample.connect();
        final Credentials credentials = loginExample.login();
        final Account receiver = accountExample.getAccountByName(receiverAccountName);
        final Nft nft = getNftBySymbol(tokenSymbol);

        return dcoreApi.getNftApi()
            .transfer(
                credentials,
                receiver.getId(),
                nft.getId())
            .blockingGet();
    }

    /**
     * Search for the NFT object bt its symbol.
     *
     * @param symbol String version of the NFT symbol.
     * @return Nft object.
     */
    public Nft getNftBySymbol(String symbol) {
        final DCoreApi dcoreApi = connectionExample.connect();

        return dcoreApi.getNftApi().get(symbol).blockingGet();
    }
}

