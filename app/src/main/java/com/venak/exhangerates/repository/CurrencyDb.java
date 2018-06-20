package com.venak.exhangerates.repository;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.venak.exhangerates.BuildConfig;
import com.venak.exhangerates.model.ConvertHistory;
import com.venak.exhangerates.model.ExchangeRate;
import com.venak.exhangerates.repository.dao.ConvertHistoryDao;
import com.venak.exhangerates.repository.dao.ExchangeRateDao;

@Database(entities = {ConvertHistory.class, ExchangeRate.class}, exportSchema = false, version = 3)
public abstract class CurrencyDb extends RoomDatabase {

    public abstract ConvertHistoryDao historyDao();

    public abstract ExchangeRateDao exchangeRateDao();

    public static CurrencyDb getAppDatabase(Context context) {

        return Room.databaseBuilder(context.getApplicationContext(), CurrencyDb.class, BuildConfig.DB_NAME).build();

    }
}
