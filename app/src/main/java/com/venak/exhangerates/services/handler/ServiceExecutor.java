package com.venak.exhangerates.services.handler;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.venak.exhangerates.R;
import com.venak.exhangerates.application.CurrencyApplication;
import com.venak.exhangerates.util.Util;

public class ServiceExecutor {
    Context context;

    public void executeService(final BaseServiceHandler baseServiceHandler) {
        context = CurrencyApplication.getContext();
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
