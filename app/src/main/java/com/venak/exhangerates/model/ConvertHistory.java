package com.venak.exhangerates.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.venak.exhangerates.repository.DateTypeConverter;

import java.util.Date;

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

    public long getId() {
        return id;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public double getAmount() {
        return amount;
    }

    public double getMarketValue() {
        return marketValue;
    }

    public String getDate() {
        return date;
    }
}
