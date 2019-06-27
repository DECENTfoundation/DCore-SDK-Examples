package ch.decent.dcore.java.example.examples;

import ch.decent.sdk.crypto.Address;
import ch.decent.sdk.crypto.ECKeyPair;
import ch.decent.sdk.crypto.ECKeyPairKt;
import org.springframework.stereotype.Component;

@Component
public class GenerateKeysExample {

    /**
     * Example of key pair (private/public) generation.
     *
     * IMPORTANT: This method of key generation should not be used on production system.
     * Private key should be always generated on client side and never exposed.
     * If you ever come to use case where you need to generate and store the private key
     * in database to act on behalf of the user, please consider encrypting them by users
     * password and salt.
     *
     * @return Generated public key
     */
    public Address generateKeys() {
        final ECKeyPair generatedKeyPair = ECKeyPairKt.generatePrivateFromStringPhrase("testPassphrase");

        //NOTE: Be careful with generated private key and consider generation on client side.
//        final String privateKey = ECKeyPairKt.base58(generatedKeyPair);

        return ECKeyPairKt.address(ECKeyPairKt.generatePrivateFromStringPhrase("test"));
    }
}
