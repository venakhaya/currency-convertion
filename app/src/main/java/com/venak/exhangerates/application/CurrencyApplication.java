package com.venak.exhangerates.application;

import android.app.Application;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;

import com.venak.exhangerates.repository.CurrencyDb;

public class CurrencyApplication extends Application {
    private static CurrencyDb currencyDb;
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        currencyDb = Room.databaseBuilder(getApplicationContext(), CurrencyDb.class, "CurrencyDa").build();
        context = this;
    }

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // Since we didn't alter the table, there's nothing else to do here.
        }
    };

    public static Context getContext() {
        return context;
    }

    public static CurrencyDb getCurrencyDb() {
        return currencyDb;
    }
}
