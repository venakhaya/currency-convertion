package com.venak.exhangerates.services.handler.implementation;

import android.util.Log;

import com.google.gson.JsonObject;
import com.venak.exhangerates.BuildConfig;
import com.venak.exhangerates.R;
import com.venak.exhangerates.listeners.DataAccessListener;
import com.venak.exhangerates.model.Transaction;
import com.venak.exhangerates.model.ConvertHistory;
import com.venak.exhangerates.services.ApiClient;
import com.venak.exhangerates.services.handler.BaseServiceHandler;
import com.venak.exhangerates.util.Util;

import java.util.Date;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class ConvertCurrencyImpl implements BaseServiceHandler {
    private static final String TAG = ConvertCurrencyImpl.class.getSimpleName();
    private static final String END_POINT = "convert/{amount}/{from}/{to}";
    private DataAccessListener dataAccessListener;
    private Transaction transaction;

    public ConvertCurrencyImpl(DataAccessListener dataAccessListener, Transaction transaction) {
        this.dataAccessListener = dataAccessListener;
        this.transaction = transaction;
    }

    @Override
    public void executeRequestCommand() {

        if (BuildConfig.IS_APP_CERTIFIED) {
            ConvertRequest convertRequest = new ApiClient().provideRetrofit().create(ConvertRequest.class);
            Call<JsonObject> jsonObject = convertRequest.convertCurrency(String.valueOf(transaction.amount),
                    transaction.from.key, transaction.to.key, BuildConfig.API_APP_ID);
            Log.e(TAG, jsonObject.request().url().toString());
            try {
                JsonObject jsonObject1 = jsonObject.execute().body();
                if (jsonObject1 != null) {
                    Log.e(TAG, jsonObject1.toString());
                    transaction.finalAmount = Double.parseDouble(jsonObject1.get("response").getAsString());
                }
            } catch (Exception e) {
                dataAccessListener.onFailed(CONTEXT.getString(R.string.failed_network_data));
                e.printStackTrace();
            }
        } else {
            transaction = CONVERTER_CALCULATOR.convert(transaction);
        }
        ConvertHistory convertHistory = new ConvertHistory(transaction.from.key,
                transaction.to.key, transaction.amount, transaction.finalAmount, Util.date(new Date()));
        CURRENCY_DB_CONNECTION.historyDao().insert(convertHistory);
        boolean success = CURRENCY_DB_CONNECTION.exchangeRateDao().findByByKey(transaction.from.key) != null;
        if (success) {
            dataAccessListener.onSuccess(transaction);
        } else {
            dataAccessListener.onFailed(CONTEXT.getString(R.string.failed_network_data));
        }
    }

    public interface ConvertRequest {
        @GET(END_POINT)
        Call<JsonObject> convertCurrency(@Path("amount") String amount, @Path("from") String from
                , @Path("to") String to, @Query(APP_ID) String appId);
    }
}
