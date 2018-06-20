package com.venak.exhangerates.ui.view;

import android.arch.lifecycle.ViewModel;

import com.venak.exhangerates.listeners.DataAccessListener;
import com.venak.exhangerates.model.ExchangeRate;
import com.venak.exhangerates.services.handler.ServiceExecutor;
import com.venak.exhangerates.services.handler.implementation.GetExchangeRatesServiceImpl;

import java.util.List;

import javax.inject.Inject;

public class ExchangeRatesListViewModel extends ViewModel implements DataAccessListener<List<ExchangeRate>> {
    @Inject
    public ServiceExecutor serviceExecutor;
    private List<ExchangeRate> exchangeRates;


    public List<ExchangeRate> getExchangeRates() {
        serviceExecutor = new ServiceExecutor();
        serviceExecutor.executeService(new GetExchangeRatesServiceImpl(this, true));
        return exchangeRates;
    }

    @Override
    public void onSuccess(List<ExchangeRate> results) {
        exchangeRates = results;
    }

    @Override
    public void onFailed(String message) {

    }
}
