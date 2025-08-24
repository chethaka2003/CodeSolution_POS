package com.codesolution.cs_pos_v1;

public class Supplier {

    String supplierID;
    String supplierName;
    String supCompanayName;
    int supContactNumber;
    String supAddress;
    String supEmail;
    String supNote;
    int lastDigits;

    public Supplier(String supplierID, String supplierName,String supCompanayName, int supContactNumber, String supAddress, String supEmail, String supNote,int lastDigits) {
        this.supplierID = supplierID;
        this.supplierName = supplierName;
        this.supCompanayName = supCompanayName;
        this.supContactNumber = supContactNumber;
        this.supAddress = supAddress;
        this.supEmail = supEmail;
        this.supNote = supNote;
        this.lastDigits = lastDigits;
    }

    //Creating getters

    public String getSupplierID() {
        return supplierID;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public String getSupCompanayName() {
        return supCompanayName;
    }

    public int getSupContactNumber() {
        return supContactNumber;
    }

    public String getSupAddress() {
        return supAddress;
    }

    public String getSupNote() {
        return supNote;
    }

    public String getSupEmail() {
        return supEmail;
    }

    public int getLastDigits() {
        return lastDigits;
    }
}
