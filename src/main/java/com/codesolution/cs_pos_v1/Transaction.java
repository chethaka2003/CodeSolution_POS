package com.codesolution.cs_pos_v1;

import java.util.ArrayList;

public class Transaction {
     int transaction_id;
     String transaction_date;
     String transaction_time;
     String transaction_type;
     String transaction_status;
     String transaction_description;
     String transaction_amount;
     ArrayList<Item> items;

    public Transaction(int transaction_id, String transaction_date, String transaction_time, String transaction_type, String transaction_status) {
        this.transaction_id = transaction_id;
        this.transaction_date = transaction_date;
        this.transaction_time = transaction_time;
        this.transaction_type = transaction_type;
        this.transaction_status = transaction_status;

    }

}
