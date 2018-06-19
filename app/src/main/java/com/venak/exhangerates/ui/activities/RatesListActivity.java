package com.venak.exhangerates.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.venak.exhangerates.R;
import com.venak.exhangerates.listeners.DataAccessListener;
import com.venak.exhangerates.model.ExchangeRate;
import com.venak.exhangerates.services.handler.ServiceExecutor;
import com.venak.exhangerates.services.handler.implementation.GetExchangeRatesServiceImpl;
import com.venak.exhangerates.ui.fragments.AddFragment;
import com.venak.exhangerates.ui.fragments.BaseFragment;
import com.venak.exhangerates.ui.fragments.ConvertFragment;
import com.venak.exhangerates.ui.fragments.ItemDetailFragment;

import java.util.List;

public class RatesListActivity extends BaseActivity implements DataAccessListener<List<ExchangeRate>> {
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean isTwoPane;
    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private FloatingActionButton addFab;
    private Toolbar toolbar;
    private ExchangeRate exchangeRate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);
        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.item_list);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());
        fab = findViewById(R.id.fab);
        addFab = findViewById(R.id.add_fab);
        if (findViewById(R.id.item_detail_container) != null) {
            isTwoPane = true;
        }
        addFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commitFragmentById(RatesListActivity.this, R.id.item_detail_container, new AddFragment());
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putSerializable(ConvertFragment.ARG_ITEM, exchangeRate);
                    ConvertFragment fragment = new ConvertFragment();
                    fragment.setArguments(arguments);
                    commitFragmentById(RatesListActivity.this, R.id.item_detail_container, fragment);
                }
            }
        });


    }

    private void setupRecyclerView(final List<ExchangeRate> exchangeRates) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                recyclerView.setAdapter(new RatesRecyclerViewAdapter(RatesListActivity.this, exchangeRates, isTwoPane));
            }
        });
    }

    @Override
    public void onSuccess(List<ExchangeRate> results) {
        setupRecyclerView(results);
    }

    @Override
    public void onFailed(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(RatesListActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        new ServiceExecutor().executeService(new GetExchangeRatesServiceImpl(this, true));
    }

    public class RatesRecyclerViewAdapter
            extends RecyclerView.Adapter<RatesRecyclerViewAdapter.ViewHolder> {

        private RatesListActivity ratesListActivity;
        private List<ExchangeRate> exchangeRates;
        private boolean isTwoPane;
        private final View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExchangeRate item = (ExchangeRate) view.getTag();
                if (isTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putSerializable(ItemDetailFragment.ARG_ITEM_ID, item);
                    exchangeRate = item;
                    ItemDetailFragment fragment = new ItemDetailFragment();
                    fragment.setArguments(arguments);
                    commitFragmentById(ratesListActivity, R.id.item_detail_container, fragment);
                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent(view.getContext(), DetailActivity.class);
                    intent.putExtra(ItemDetailFragment.ARG_ITEM_ID, item);
                    context.startActivity(intent);
                }
            }
        };

        public RatesRecyclerViewAdapter(RatesListActivity parent,
                                        List<ExchangeRate> exchangeRates,
                                        boolean twoPane) {
            this.exchangeRates = exchangeRates;
            ratesListActivity = parent;
            isTwoPane = twoPane;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.currencyKeyTextView.setText(exchangeRates.get(position).key);
            holder.currencyValueTextView.setText(exchangeRates.get(position).value);
            holder.itemView.setTag(exchangeRates.get(position));
            holder.itemView.setOnClickListener(onClickListener);
        }

        @Override
        public int getItemCount() {
            return exchangeRates.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView currencyKeyTextView;
            final TextView currencyValueTextView;

            ViewHolder(View view) {
                super(view);
                currencyKeyTextView = view.findViewById(R.id.currency_key_textview);
                currencyValueTextView = view.findViewById(R.id.currency_value_textview);
            }
        }
    }
}
