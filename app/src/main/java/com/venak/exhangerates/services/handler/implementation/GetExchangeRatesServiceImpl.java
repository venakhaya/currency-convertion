package com.venak.exhangerates.services.handler.implementation;

import android.util.Log;

import com.google.gson.JsonElement;
import com.venak.exhangerates.BuildConfig;
import com.venak.exhangerates.R;
import com.venak.exhangerates.listeners.DataAccessListener;
import com.venak.exhangerates.model.Currency;
import com.venak.exhangerates.model.ExchangeRate;
import com.venak.exhangerates.services.ApiClient;
import com.venak.exhangerates.services.handler.BaseServiceHandler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class GetExchangeRatesServiceImpl implements BaseServiceHandler {
    private static final String TAG = GetExchangeRatesServiceImpl.class.getSimpleName();
    private static final String END_POINT = "latest.json";
    private DataAccessListener dataAccessListener;
    private boolean isNetworkCall;

    public GetExchangeRatesServiceImpl(DataAccessListener dataAccessListener, boolean isNetworkCall) {
        this.dataAccessListener = dataAccessListener;
        this.isNetworkCall = isNetworkCall;
    }

    @Override
    public void executeRequestCommand() {
        try {
            Currency currency = null;
            CurrencyRequest currencyRequest = new ApiClient().provideRetrofit().create(CurrencyRequest.class);
            Call<Currency> currencyCall = currencyRequest.getLatest(BuildConfig.API_APP_ID);
            if (isNetworkCall) {
                Log.e(TAG, currencyCall.request().url().toString());
                currency = currencyCall.execute().body();
            }
            if (currency != null) {
                Iterator<Map.Entry<String, JsonElement>> it = currency.rates.entrySet().iterator();
                currency.exchangeRates = new ArrayList<>();
                while (it.hasNext()) {
                    currency.exchangeRates.add(new ExchangeRate(it.next().getKey(), it.next().getValue().getAsString()));
                }
                List<ExchangeRate> exchangeRates = CURRENCY_DB_CONNECTION.exchangeRateDao().getAll();
                if (exchangeRates == null || exchangeRates.size() == 0) {
                    CURRENCY_DB_CONNECTION.exchangeRateDao().insertAll(currency.exchangeRates);
                } else {
                    for (ExchangeRate exchangeRate : currency.exchangeRates) {
                        ExchangeRate temp = CURRENCY_DB_CONNECTION.exchangeRateDao().findByByKey(exchangeRate.key);
                        if (temp == null) {
                            CURRENCY_DB_CONNECTION.exchangeRateDao().insert(exchangeRate);
                        } else {
                            exchangeRate.id = temp.id;
                            CURRENCY_DB_CONNECTION.exchangeRateDao().update(exchangeRate);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            List<ExchangeRate> exchangeRates = CURRENCY_DB_CONNECTION.exchangeRateDao().getAll();
            if (exchangeRates != null) {
                dataAccessListener.onSuccess(exchangeRates);
            } else {
                dataAccessListener.onFailed(CONTEXT.getString(R.string.connection_message));
            }
        }
    }


    public interface CurrencyRequest {
        @GET(END_POINT)
        Call<Currency> getLatest(@Query(APP_ID) String appId);
    }
}
