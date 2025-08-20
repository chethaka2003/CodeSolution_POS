package com.codesolution.cs_pos_v1;

import DataBase.CustomerTableDB;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

//This class is for handle the customer
public class Customer {

    String customer_id;
    String customer_name;
    String customer_email;
    int customer_phone;
    String customer_address;
    Date create_Date;
    int LastDigits;
    int totalTransaction;




    public Customer(String customer_id, String customer_name, String customer_email, int customer_phone, String customer_address,Date create_Date, int totalTransaction,int lastDigits) {
        this.customer_id = customer_id;
        this.customer_name = customer_name;
        this.customer_email = customer_email;
        this.customer_phone = customer_phone;
        this.customer_address = customer_address;
        this.create_Date = create_Date;
        this.totalTransaction = totalTransaction;
        this.LastDigits = lastDigits;
    }

    //Getters

    public String getCustomer_id() {
        return customer_id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public String getCustomer_email() {
        return customer_email;
    }

    public int getCustomer_phone() {
        return customer_phone;
    }

    public String getCustomer_address() {
        return customer_address;
    }

    public int getLastDigits() {
        return LastDigits;
    }

    public Date getCreate_Date() {
        return create_Date;
    }

    public int getTotalTransaction() {
        return totalTransaction;
    }



}
