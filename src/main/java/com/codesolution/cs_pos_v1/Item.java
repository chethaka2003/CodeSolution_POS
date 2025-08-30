package com.codesolution.cs_pos_v1;

public class Item {
    String itemCode;
    String itemName;
    String supplierItemCode;
    String category;
    String subCategory;
    int warrentyMonths ;
    int costPrice;
    int sellingPrice;
    int wholeSalePrice;
    int stockCount;
    String barcode;
    String supplierID;
    String brand;
    String size;
    String color;
    String imagePath;

    public Item(String itemCode, String itemName, String supplierItemCode, String category, String subCategory, int warrentyMonths, int costPrice, int sellingPrice, int wholeSalePrice, int stockCount, String supplierID, String barcode, String brand, String size, String color, String imagePath) {
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.supplierItemCode = supplierItemCode;
        this.category = category;
        this.subCategory = subCategory;
        this.warrentyMonths = warrentyMonths;
        this.costPrice = costPrice;
        this.sellingPrice = sellingPrice;
        this.wholeSalePrice = wholeSalePrice;
        this.stockCount = stockCount;
        this.supplierID = supplierID;
        this.barcode = barcode;
        this.brand = brand;
        this.size = size;
        this.color = color;
        this.imagePath = imagePath;
    }

    public String getItemCode() {
        return itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public String getSupplierItemCode() {
        return supplierItemCode;
    }

    public String getCategory() {
        return category;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public int getWarrentyMonths() {
        return warrentyMonths;
    }

    public int getCostPrice() {
        return costPrice;
    }

    public int getWholeSalePrice() {
        return wholeSalePrice;
    }

    public int getSellingPrice() {
        return sellingPrice;
    }

    public int getStockCount() {
        return stockCount;
    }

    public String getBarcode() {
        return barcode;
    }

    public String getSupplierID() {
        return supplierID;
    }

    public String getBrand() {
        return brand;
    }

    public String getSize() {
        return size;
    }

    public String getColor() {
        return color;
    }

    public String getImagePath() {
        return imagePath;
    }
}
