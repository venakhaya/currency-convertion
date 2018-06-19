package com.venak.exhangerates.model;

import com.google.gson.JsonObject;

import java.util.List;

public class Currency {

    public String disclaimer;
    public String license;
    public String timestamp;
    public String base;
    public JsonObject rates;
    public List<ExchangeRate> exchangeRates;
}
