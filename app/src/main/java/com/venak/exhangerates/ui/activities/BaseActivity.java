package com.venak.exhangerates.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;

import com.venak.exhangerates.ui.fragments.BaseFragment;

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void commitFragmentById(FragmentActivity fragmentActivity, int container, BaseFragment baseFragment) {
        fragmentActivity.getSupportFragmentManager().beginTransaction()
                .replace(container, baseFragment)
                .commit();
    }
}
