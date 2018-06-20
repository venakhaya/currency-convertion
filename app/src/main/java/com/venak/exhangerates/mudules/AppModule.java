package com.venak.exhangerates.mudules;

import android.content.Context;

import com.venak.exhangerates.application.CurrencyApplication;
import com.venak.exhangerates.repository.CurrencyDb;
import com.venak.exhangerates.util.ConverterCalculator;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private CurrencyApplication currencyApplication;


    public AppModule(CurrencyApplication currencyApplication) {
        this.currencyApplication = currencyApplication;
    }

    @Provides
    @CurrencyApplicationScope
    public CurrencyApplication getCurrencyApplication() {
        return currencyApplication;
    }

    @Provides
    @CurrencyApplicationScope
    public Context getContext() {
        return currencyApplication;
    }

    @Provides
    @CurrencyApplicationScope
    public CurrencyDb getCurrencyDb() {
        return CurrencyDb.getAppDatabase(currencyApplication);
    }

    @Provides
    @CurrencyApplicationScope
    public ConverterCalculator converterCalculator() {
        return new ConverterCalculator();
    }
}
