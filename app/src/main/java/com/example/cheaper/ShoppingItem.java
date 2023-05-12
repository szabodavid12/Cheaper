package com.example.cheaper;

import java.io.Serializable;

public class ShoppingItem implements Serializable {

    private String itemName;
    private String itemPrice;
    private String itemImageUrl;
    private String groceryName;

    public ShoppingItem(String itemName, String itemPrice, String itemImageUrl, String groceryName) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemImageUrl = itemImageUrl;
        this.groceryName = groceryName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getItemImageUrl() {
        return itemImageUrl;
    }

    public void setItemImageUrl(String itemImageUrl) {
        this.itemImageUrl = itemImageUrl;
    }

    public String getGroceryName() {
        return groceryName;
    }

    public void setGroceryName(String groceryName) {
        this.groceryName = groceryName;
    }

    @Override
    public String toString() {
        return "ShoppingItem{" +
                "itemName='" + itemName + '\'' +
                ", itemPrice='" + itemPrice + '\'' +
                ", itemImageUrl='" + itemImageUrl + '\'' +
                ", groceryName='" + groceryName + '\'' +
                '}';
    }
}
