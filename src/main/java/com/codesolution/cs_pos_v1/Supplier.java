package com.codesolution.cs_pos_v1;

public class Supplier {

    String supplierID;
    String supplierName;
    String [] supItemCodes ;
    String supContactNumber;
    String supAddress;
    String supEmail;
    String supNote;

    public Supplier(String supplierID, String supplierName, String[] supItemCodes, String supContactNumber, String supAddress, String supEmail, String supNote) {
        this.supplierID = supplierID;
        this.supplierName = supplierName;
        this.supItemCodes = supItemCodes;
        this.supContactNumber = supContactNumber;
        this.supAddress = supAddress;
        this.supEmail = supEmail;
        this.supNote = supNote;
    }
}
