package com.venak.exhangerates.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.venak.exhangerates.R;
import com.venak.exhangerates.application.CurrencyApplication;
import com.venak.exhangerates.repository.CurrencyDb;
import com.venak.exhangerates.services.handler.ServiceExecutor;

public class BaseFragment extends Fragment {
    public ServiceExecutor serviceExecutor;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        serviceExecutor = new ServiceExecutor();
    }


    public void commitFragmentById(FragmentActivity fragmentActivity, int container, BaseFragment baseFragment) {
        fragmentActivity.getSupportFragmentManager().beginTransaction()
                .replace(container, baseFragment)
                .commit();
    }
}
