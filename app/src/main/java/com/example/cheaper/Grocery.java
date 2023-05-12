package com.example.cheaper;

public class Grocery {

    private String groceryName;
    private String sumPrice;
    private int groceryLogo;

    public Grocery(String groceryName, String sumPrice, int groceryLogo) {
        this.groceryName = groceryName;
        this.sumPrice = sumPrice;
        this.groceryLogo = groceryLogo;
    }

    public String getGroceryName() {
        return groceryName;
    }

    public void setGroceryName(String groceryName) {
        this.groceryName = groceryName;
    }

    public String getSumPrice() {
        return sumPrice;
    }

    public void setSumPrice(String sumPrice) {
        this.sumPrice = sumPrice;
    }

    public int getGroceryLogo() {
        return groceryLogo;
    }

    @Override
    public String toString() {
        return "Grocery{" +
                "groceryName='" + groceryName + '\'' +
                ", sumPrice='" + sumPrice + '\'' +
                ", groceryLogo=" + groceryLogo +
                '}';
    }

    public void setGroceryLogo(int groceryLogo) {
        this.groceryLogo = groceryLogo;
    }
}
