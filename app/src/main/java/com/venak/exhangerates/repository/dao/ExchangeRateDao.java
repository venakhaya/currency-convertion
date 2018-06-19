package com.venak.exhangerates.repository.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.venak.exhangerates.model.ExchangeRate;

import java.util.List;

@Dao
public interface ExchangeRateDao {
    @Query("SELECT * FROM ExchangeRate")
    List<ExchangeRate> getAll();

    @Query("SELECT * FROM ExchangeRate WHERE ExchangeRate.`key` LIKE :key")
    ExchangeRate findByByKey(String key);

    @Insert
    void insertAll(List<ExchangeRate> exchangeRates);

    @Insert
    void insert(ExchangeRate exchangeRate);

    @Update
    void update(ExchangeRate exchangeRate);

    @Delete
    void delete(ExchangeRate exchangeRate);
}
