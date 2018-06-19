package com.venak.exhangerates.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class ExchangeRate implements Serializable {

    public ExchangeRate(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @PrimaryKey(autoGenerate = true)
    public long id;
    public String key;
    public String value;

    @Override
    public String toString() {
        return key;
    }
}
