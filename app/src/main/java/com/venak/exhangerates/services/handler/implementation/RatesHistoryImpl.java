package com.venak.exhangerates.services.handler.implementation;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.JsonElement;
import com.venak.exhangerates.BuildConfig;
import com.venak.exhangerates.R;
import com.venak.exhangerates.listeners.DataAccessListener;
import com.venak.exhangerates.model.Currency;
import com.venak.exhangerates.model.ExchangeRate;
import com.venak.exhangerates.model.ConvertHistory;
import com.venak.exhangerates.services.handler.BaseServiceHandler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class RatesHistoryImpl extends BaseServiceHandler {
    private static final String TAG = RatesHistoryImpl.class.getSimpleName();
    private static final String END_POINT = "history/{date}.json";
    private static final String DATE_PATH = "date";
    private DataAccessListener dataAccessListener;
    private String date;

    public RatesHistoryImpl(@NonNull DataAccessListener dataAccessListener, String date) {
        this.dataAccessListener = dataAccessListener;
        this.date = date;
    }

    @Override
    public void executeRequestCommand() {
        if (BuildConfig.IS_APP_CERTIFIED) {
            HistoryRequest convertRequest = retrofit.create(HistoryRequest.class);
            Call<Currency> ratesHistory = convertRequest.getRatesHistory(date, BuildConfig.API_APP_ID);
            Log.e(TAG, ratesHistory.request().url().toString());
            try {
                Currency currenciesHistory = ratesHistory.execute().body();
                if (currenciesHistory != null) {
                    Log.e(TAG, currenciesHistory.toString());
                    dataAccessListener.onSuccess(currenciesHistory);
                }
                if (currenciesHistory != null) {
                    Iterator<Map.Entry<String, JsonElement>> it = currenciesHistory.rates.entrySet().iterator();
                    currenciesHistory.exchangeRates = new ArrayList<>();
                    while (it.hasNext()) {
                        currenciesHistory.exchangeRates.add(new ExchangeRate(it.next().getKey(), it.next().getValue().getAsString()));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                return;
            }
        } else {
            //Only apply this Object when u the app does not have certified Certificate
            final List<ConvertHistory> convertHistories = dbConnection.historyDao().getAll();
            if (convertHistories != null && convertHistories.size() > 0) {
                dataAccessListener.onSuccess(convertHistories);
            } else {
                dataAccessListener.onFailed(context.getString(R.string.failed_network_data));
            }
        }

    }

    public interface HistoryRequest {
        @GET(END_POINT)
        Call<Currency> getRatesHistory(@Path(DATE_PATH) String date, @Query("app_id") String appId);
    }
}
