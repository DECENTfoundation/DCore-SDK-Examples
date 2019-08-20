package ch.decent.dcore.java.example.examples;

import ch.decent.sdk.api.rx.DCoreApi;
import ch.decent.sdk.crypto.Credentials;
import ch.decent.sdk.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NFTExample {

    private static final Long AMOUNT_OF_DCT_REQUIRED_FOR_NFT_ISSUE = 500000L;
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
     * @return Transaction confirmation.
     */
    public TransactionConfirmation issue(String symbol) {
        final DCoreApi dcoreApi = connectionExample.connect();
        final Credentials credentials = loginExample.login();
        final AssetAmount dctAssetAmount = new AssetAmount(AMOUNT_OF_DCT_REQUIRED_FOR_NFT_ISSUE);
        final Fee initialFee = new Fee(dctAssetAmount.getAssetId(), AMOUNT_OF_DCT_REQUIRED_FOR_NFT_ISSUE);

        return dcoreApi.getNftApi()
            .issue(
                credentials,
                symbol,
                credentials.getAccount(),
                new MyCustomNftToken(5, false),
                null,
                initialFee)
            .blockingGet();
    }

    /**
     * Send one non fungible token to some account on DCore block-chain.
     *
     * @param receiverAccountName account name of token receiver.
     * @return Transaction confirmation.
     */
    public TransactionConfirmation sendToken(String receiverAccountName) {
        final DCoreApi dcoreApi = connectionExample.connect();
        final Credentials credentials = loginExample.login();
        final Account receiver = accountExample.getAccountByName(receiverAccountName);
        final List<NftData<NftModel>> result = getNftByAccount(credentials.getAccount());

        return dcoreApi.getNftApi()
            .transfer(
                credentials,
                receiver.getId(),
                result.get(0).getId())
            .blockingGet();
    }

    /**
     * Search for the NFT object by the owner.
     *
     * @param account ChainObject account chain object.
     * @return Nft object.
     */
    public List<NftData<NftModel>> getNftByAccount(AccountObjectId account) {
        final DCoreApi dcoreApi = connectionExample.connect();

        return dcoreApi.getNftApi().getNftBalances(account).blockingGet();
    }
}

