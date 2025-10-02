package com.codesolution.cs_pos_v1;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

public class Transaction {
     String transaction_id;
     String customer_id;
     Date transaction_date;
     Time transaction_time;
     String payment_type;
     double total_amount;
     double discount_amount;
     double net_amount;
     int lastDigits;

    public Transaction(String transaction_id, Date transaction_date, Time transaction_time, String payment_type, double total_amount, double discount_amount, double net_amount, int lastDigits,String customer_id) {
        this.transaction_id = transaction_id;
        this.transaction_date = transaction_date;
        this.transaction_time = transaction_time;
        this.payment_type = payment_type;
        this.total_amount = total_amount;
        this.discount_amount = discount_amount;
        this.net_amount = net_amount;
        this.lastDigits = lastDigits;
        this.customer_id = customer_id;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public Date getTransaction_date() {
        return transaction_date;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public Time getTransaction_time() {
        return transaction_time;
    }

    public double getDiscount_amount() {
        return discount_amount;
    }

    public double getTotal_amount() {
        return total_amount;
    }

    public double getNet_amount() {
        return net_amount;
    }

    public int getLastDigits() {
        return lastDigits;
    }

    public String getCustomer_id() {
        return customer_id;
    }
}
