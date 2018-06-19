package com.venak.exhangerates.listeners;

import com.venak.exhangerates.model.ExchangeRate;

import java.util.List;

public interface ExchangeRatesLister {
    void success(List<ExchangeRate> exchangeRates);
}
