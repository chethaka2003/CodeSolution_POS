package com.codesolution.cs_pos_v1;

//This class is for temperory hold the items which purchased by the customer
public class CartRow {

    String itemName;
    String quantity;
    String price;

    public CartRow(String itemName, String quantity, String price) {
        this.itemName = itemName;
        this.quantity = quantity;
        this.price = price;
    }

    public String getItemName() {
        return itemName;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getPrice() {
        return price;
    }
}
