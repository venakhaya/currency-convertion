package com.venak.exhangerates.repository;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.venak.exhangerates.model.ConvertHistory;
import com.venak.exhangerates.model.ExchangeRate;
import com.venak.exhangerates.repository.dao.ConvertHistoryDao;
import com.venak.exhangerates.repository.dao.ExchangeRateDao;

@Database(entities = {ConvertHistory.class, ExchangeRate.class}, exportSchema = false, version = 2)
@TypeConverters({DateTypeConverter.class})
public abstract class CurrencyDb extends RoomDatabase {

    public abstract ConvertHistoryDao historyDao();

    public abstract ExchangeRateDao exchangeRateDao();
}
