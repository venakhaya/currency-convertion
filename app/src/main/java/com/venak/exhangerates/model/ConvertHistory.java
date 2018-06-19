package com.venak.exhangerates.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class ConvertHistory {
    @PrimaryKey(autoGenerate = true)
    public long id;
    public String from;
    public String to;
    public double amount;
    public double marketValue;
    public String date;

    public ConvertHistory(String from, String to, double amount, double marketValue, String date) {
        this.from = from;
        this.to = to;
        this.amount = amount;
        this.marketValue = marketValue;
        this.date = date;
    }

    @Override
    public String toString() {
        return
                "To '" + to + '\'' +
                        "\nAmount:" + amount +
                        "\nValue:" + marketValue +
                        "\nDate:" + date;
    }

}
