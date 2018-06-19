package com.venak.exhangerates.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.venak.exhangerates.R;
import com.venak.exhangerates.listeners.DataAccessListener;
import com.venak.exhangerates.model.ExchangeRate;
import com.venak.exhangerates.services.handler.ServiceExecutor;
import com.venak.exhangerates.services.handler.implementation.ConvertCurrencyImpl;
import com.venak.exhangerates.ui.activities.DetailActivity;
import com.venak.exhangerates.ui.activities.RatesListActivity;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link RatesListActivity}
 * in two-pane mode (on tablets) or a {@link DetailActivity}
 * on handsets.
 */
public class ItemDetailFragment extends BaseFragment implements DataAccessListener<String> {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The content this fragment is presenting.
     */
    private ExchangeRate exchangeRate;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemDetailFragment() {
    }

    private CollapsingToolbarLayout appBarLayout;
    private Activity activity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {

            exchangeRate = (ExchangeRate) getArguments().getSerializable(ARG_ITEM_ID);

            activity = this.getActivity();
            appBarLayout = activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                if (exchangeRate != null) {
                    appBarLayout.setTitle(exchangeRate.key);
                }
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.item_detail, container, false);

        if (exchangeRate != null) {
            ((TextView) rootView.findViewById(R.id.item_detail)).setText(exchangeRate.value);
        }

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
//        HistoryFragment fragment = new HistoryFragment();
//
//        getActivity().getSupportFragmentManager().beginTransaction()
//                .replace(R.id.history_container, fragment)
//                .commit();
        commitFragmentById(getActivity(), R.id.history_container, new HistoryFragment());
    }

    @Override
    public void onSuccess(final String results) {
        getActivity().runOnUiThread(
                new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(), results, Toast.LENGTH_LONG).show();
                    }
                }
        );

    }

    @Override
    public void onFailed(final String message) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
            }
        });
    }
}
