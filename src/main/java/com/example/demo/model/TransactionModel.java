package com.example.demo.model;

/**
 * Created by colin on 02.08.17.
 */
public class TransactionModel {
    double amount;
    long timestamp;

    public TransactionModel() {
    }

    public TransactionModel(double amount, long timestamp) {
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }


    @Override
    public String toString() {
        return "TransactionModel{" +
                "amount=" + amount +
                ", timestamp=" + timestamp +
                '}';
    }
}
