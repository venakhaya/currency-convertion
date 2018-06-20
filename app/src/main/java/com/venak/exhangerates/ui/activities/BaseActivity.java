package com.venak.exhangerates.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.venak.exhangerates.application.CurrencyApplication;
import com.venak.exhangerates.services.handler.ServiceExecutor;
import com.venak.exhangerates.ui.fragments.BaseFragment;

import javax.inject.Inject;

public class BaseActivity extends AppCompatActivity {
    @Inject
    public ServiceExecutor serviceExecutor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CurrencyApplication.getAppComponent().inject(this);
    }

    protected void commitFragmentById(AppCompatActivity fragmentActivity, int container, BaseFragment baseFragment) {
        fragmentActivity.getSupportFragmentManager().beginTransaction()
                .replace(container, baseFragment)
                .commit();
    }
}
