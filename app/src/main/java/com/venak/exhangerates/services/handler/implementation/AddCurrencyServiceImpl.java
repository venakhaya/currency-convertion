package com.venak.exhangerates.services.handler.implementation;

import com.venak.exhangerates.R;
import com.venak.exhangerates.listeners.DataAccessListener;
import com.venak.exhangerates.model.ExchangeRate;
import com.venak.exhangerates.services.handler.BaseServiceHandler;

public class AddCurrencyServiceImpl implements BaseServiceHandler {
    private DataAccessListener dataAccessListener;
    private ExchangeRate exchangeRate;

    public AddCurrencyServiceImpl(DataAccessListener dataAccessListener, ExchangeRate exchangeRate) {
        this.dataAccessListener = dataAccessListener;
        this.exchangeRate = exchangeRate;
    }

    @Override
    public void executeRequestCommand() {
        try {

            if (exchangeRate == null) {
                dataAccessListener.onFailed(CONTEXT.getString(R.string.failed_add_currency));
            } else {
                ExchangeRate temp = CURRENCY_DB_CONNECTION.exchangeRateDao().findByByKey(exchangeRate.key);
                if (temp == null) {
                    CURRENCY_DB_CONNECTION.exchangeRateDao().insert(exchangeRate);
                } else {
                    exchangeRate.id = temp.id;
                    CURRENCY_DB_CONNECTION.exchangeRateDao().update(exchangeRate);
                }
                dataAccessListener.onSuccess(CONTEXT.getString(R.string.successful_add_currency));
            }
        } catch (Exception e) {
            dataAccessListener.onFailed(CONTEXT.getString(R.string.failed_add_currency));
        }

    }
}
