package com.venak.exhangerates.listeners;

import android.support.annotation.UiThread;

public interface DataAccessListener<T> {
    @UiThread
    void onSuccess(T results);

    @UiThread
    void onFailed(String message);
}
