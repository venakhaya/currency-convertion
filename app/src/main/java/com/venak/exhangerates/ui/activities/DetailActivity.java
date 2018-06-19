package com.venak.exhangerates.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.venak.exhangerates.R;
import com.venak.exhangerates.ui.fragments.AddFragment;
import com.venak.exhangerates.ui.fragments.BaseFragment;
import com.venak.exhangerates.ui.fragments.ConvertFragment;
import com.venak.exhangerates.ui.fragments.ItemDetailFragment;

/**
 * An activity representing a single Item detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link RatesListActivity}.
 */
public class DetailActivity extends BaseActivity implements View.OnClickListener {
    private FloatingActionButton fab;
    private FloatingActionButton add_fab;
    private Toolbar toolbar;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        toolbar = findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);
        fab = findViewById(R.id.fab);
        add_fab = findViewById(R.id.add_fab);
        fab.setOnClickListener(this);
        add_fab.setOnClickListener(this);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        Bundle arguments = new Bundle();
        arguments.putSerializable(ItemDetailFragment.ARG_ITEM_ID,
                getIntent().getSerializableExtra(ItemDetailFragment.ARG_ITEM_ID));
        ItemDetailFragment fragment = new ItemDetailFragment();
        fragment.setArguments(arguments);
        commitFragmentById(this, R.id.item_detail_container, fragment);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {

            NavUtils.navigateUpTo(this, new Intent(this, RatesListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        Bundle arguments;
        BaseFragment fragment;
        switch (v.getId()) {
            case R.id.add_fab:
                arguments = new Bundle();
                fragment = new AddFragment();
                fragment.setArguments(arguments);
                commitFragmentById(this, R.id.item_detail_container, fragment);
                break;
            case R.id.fab:
                arguments = new Bundle();
                fragment = new ConvertFragment();
                fragment.setArguments(arguments);
                commitFragmentById(this, R.id.item_detail_container, fragment);
                break;
            default://Do nothing
        }
    }
}
