package ch.decent.dcore.java.example.examples;

import ch.decent.sdk.model.NftModel;

import java.util.List;
import java.util.Map;

public final class MyNewNft implements NftModel {

    private int size;
//    private String color;
    private boolean eaten;

    public MyNewNft(int size, boolean eaten) {
        this.size = size;
//        this.color = color;
        this.eaten = eaten;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

//    public String getColor() {
//        return color;
//    }
//
//    public void setColor(String color) {
//        this.color = color;
//    }

    public boolean isEaten() {
        return eaten;
    }

    public void setEaten(boolean eaten) {
        this.eaten = eaten;
    }

    public Map<String, Object> createUpdate() {
        return DefaultImpls.createUpdate(this);
    }

    public List<Object> values() {
        return DefaultImpls.values(this);
    }
}