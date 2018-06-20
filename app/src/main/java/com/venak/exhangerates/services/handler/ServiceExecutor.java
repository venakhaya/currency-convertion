package com.venak.exhangerates.services.handler;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.venak.exhangerates.R;
import com.venak.exhangerates.application.CurrencyApplication;
import com.venak.exhangerates.util.Util;

import javax.inject.Inject;

public class ServiceExecutor {
    @Inject
    Context context;

    public ServiceExecutor(){
        CurrencyApplication.getAppComponent().inject(this);
    }
    public void executeService(final BaseServiceHandler baseServiceHandler) {

        if (Util.isNetworkAvailable(context)) {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    baseServiceHandler.executeRequestCommand();
                    return null;
                }
            }.execute();
        } else {
            Toast.makeText(context, context.getString(R.string.connection_message), Toast.LENGTH_LONG).show();
        }
    }
}
