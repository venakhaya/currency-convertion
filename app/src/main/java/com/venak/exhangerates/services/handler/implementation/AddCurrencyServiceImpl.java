package com.venak.exhangerates.services.handler.implementation;

import com.venak.exhangerates.R;
import com.venak.exhangerates.listeners.DataAccessListener;
import com.venak.exhangerates.model.ExchangeRate;
import com.venak.exhangerates.services.handler.BaseServiceHandler;

public class AddCurrencyServiceImpl extends BaseServiceHandler {
    private DataAccessListener dataAccessListener;
    private ExchangeRate exchangeRate;

    public AddCurrencyServiceImpl(DataAccessListener dataAccessListener, ExchangeRate exchangeRate) {
        super();
        this.dataAccessListener = dataAccessListener;
        this.exchangeRate = exchangeRate;
    }

    @Override
    public void executeRequestCommand() {
        try {
            if (exchangeRate == null) {
                dataAccessListener.onFailed(context.getString(R.string.failed_add_currency));
            } else {
                ExchangeRate temp = dbConnection.exchangeRateDao().findByByKey(exchangeRate.key);
                if (temp == null) {
                    dbConnection.exchangeRateDao().insert(exchangeRate);
                } else {
                    exchangeRate.id = temp.id;
                    dbConnection.exchangeRateDao().update(exchangeRate);
                }
                dataAccessListener.onSuccess(context.getString(R.string.successful_add_currency));
            }
        } catch (Exception e) {
            dataAccessListener.onFailed(context.getString(R.string.failed_add_currency));
        }finally {

        }
    }
}
