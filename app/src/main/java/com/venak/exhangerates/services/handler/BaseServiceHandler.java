package com.venak.exhangerates.services.handler;


import android.content.Context;

import com.venak.exhangerates.application.CurrencyApplication;
import com.venak.exhangerates.listeners.DataAccessListener;
import com.venak.exhangerates.repository.CurrencyDb;
import com.venak.exhangerates.util.ConverterCalculator;

import javax.inject.Inject;

import retrofit2.Retrofit;

public abstract class BaseServiceHandler {
    @Inject
    public CurrencyDb dbConnection;
    @Inject
    public Context context;
    @Inject
    public Retrofit retrofit;
    @Inject
    public ConverterCalculator converterCalculator;

    protected DataAccessListener dataAccessListener;

    public BaseServiceHandler() {
        CurrencyApplication.getAppComponent().inject(this);
    }

    public abstract void executeRequestCommand();
}
