package com.venak.exhangerates.model;

import java.text.DecimalFormat;

public class Transaction {
    public String base = "USD";
    public double amount;
    public ExchangeRate from;
    public ExchangeRate to;
    public double finalAmount;

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("####0.00");
        return to.key + " " + df.format(finalAmount);
    }
}
