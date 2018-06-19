package com.venak.exhangerates.services.handler;


import android.content.Context;

import com.venak.exhangerates.application.CurrencyApplication;
import com.venak.exhangerates.model.Currency;
import com.venak.exhangerates.repository.CurrencyDb;
import com.venak.exhangerates.util.ConverterCalculator;

public interface BaseServiceHandler {
    String APP_ID = "app_id";
    Context CONTEXT = CurrencyApplication.getContext();
    CurrencyDb CURRENCY_DB_CONNECTION = CurrencyApplication.getCurrencyDb();
    ConverterCalculator CONVERTER_CALCULATOR = new ConverterCalculator();

    void executeRequestCommand();
}
