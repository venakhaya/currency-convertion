package com.venak.exhangerates.application;

import android.app.Application;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.migration.Migration;

import com.venak.exhangerates.mudules.AppComponent;
import com.venak.exhangerates.mudules.AppModule;
import com.venak.exhangerates.mudules.DaggerAppComponent;
import com.venak.exhangerates.mudules.NetModule;

public class CurrencyApplication extends Application {
    private static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).netModule(
                new NetModule()).build();
        appComponent.inject(this);
    }

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // Since we didn't alter the table, there's nothing else to do here.
        }
    };

    public static AppComponent getAppComponent() {
        return appComponent;
    }
}
