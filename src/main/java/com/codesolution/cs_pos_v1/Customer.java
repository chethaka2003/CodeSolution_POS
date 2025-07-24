package com.codesolution.cs_pos_v1;

//This class is for handle the customer
public class Customer {

    String customer_id;
    String customer_name;
    String customer_email;
    String customer_phone;
    String customer_address;

    public Customer(String customer_id, String customer_name, String customer_email, String customer_phone, String customer_address) {
        this.customer_id = customer_id;
        this.customer_name = customer_name;
        this.customer_email = customer_email;
        this.customer_phone = customer_phone;
        this.customer_address = customer_address;
    }

}
