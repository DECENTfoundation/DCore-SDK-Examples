package ch.decent.dcore.java.example.examples;

import ch.decent.sdk.model.NftModel;

import java.util.List;
import java.util.Map;

/**
 * This is a custom class you can create to hold NFT data and implement your custom logic on it in your application.
 */
public final class MyCustomNftToken implements NftModel {

    public int size;
    public boolean consumed;

    public MyCustomNftToken(int size, boolean consumed) {
        this.size = size;
        this.consumed = consumed;
    }

    public int getSize() {
        return size;
    }

    public boolean isConsumed() {
        return consumed;
    }

    public Map<String, Object> createUpdate() {
        return DefaultImpls.createUpdate(this);
    }

    public List<Object> values() {
        return DefaultImpls.values(this);
    }
}