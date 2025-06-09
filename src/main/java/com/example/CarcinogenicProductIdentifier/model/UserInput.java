package com.example.CarcinogenicProductIdentifier.model;

import java.util.ArrayList;
import java.util.List;

public class UserInput {
    private List<String> itemsUsed;

    public UserInput() {
        this.itemsUsed = new ArrayList<>();
    }

    public List<String> getItemsUsed() {
        return itemsUsed;
    }

    public void setItemsUsed(List<String> itemsUsed) {
        this.itemsUsed = itemsUsed;
    }

    public void addItem(String item) {
        this.itemsUsed.add(item);
    }
}
