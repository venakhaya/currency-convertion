package com.venak.exhangerates.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.venak.exhangerates.R;
import com.venak.exhangerates.listeners.DataAccessListener;
import com.venak.exhangerates.model.ExchangeRate;
import com.venak.exhangerates.model.Transaction;
import com.venak.exhangerates.services.handler.ServiceExecutor;
import com.venak.exhangerates.services.handler.implementation.ConvertCurrencyImpl;
import com.venak.exhangerates.services.handler.implementation.GetExchangeRatesServiceImpl;

import java.util.List;

public class ConvertFragment extends BaseFragment {
    public static String ARG_ITEM = "item";
    private ExchangeRate exchangeRate;
    private EditText amountEditText;
    private Spinner fromSpinner;
    private Spinner toSpinner;
    private TextView convertedAmount;
    private Transaction transaction;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        transaction = new Transaction();
        exchangeRate = (ExchangeRate) getArguments().getSerializable(ARG_ITEM);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.convert_layout, container, false);
        fromSpinner = rootView.findViewById(R.id.from_spinner);
        toSpinner = rootView.findViewById(R.id.to_spinner);
        convertedAmount = rootView.findViewById(R.id.converted_amount_text_view);
        amountEditText = rootView.findViewById(R.id.convert_amount_edit_text);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        serviceExecutor.executeService(new GetExchangeRatesServiceImpl(getLocalExchangeRates, false));
    }

    private DataAccessListener getLocalExchangeRates = new DataAccessListener<List<ExchangeRate>>() {
        @Override
        public void onSuccess(List<ExchangeRate> exchangeRates) {
            final ArrayAdapter<ExchangeRate> adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, exchangeRates);
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    fromSpinner.setAdapter(adapter);
                    toSpinner.setAdapter(adapter);
                }
            });

            toSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    transaction.to = adapter.getItem(position);
                    if (validInput()) {
                        convert();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            fromSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
                    transaction.from = adapter.getItem(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }

        @Override
        public void onFailed(String message) {

        }
    };

    boolean validInput() {
        return transaction.from != null && transaction.to != null && amountEditText.getText().length() > 0;
    }

    private void convert() {
        transaction.amount = Double.parseDouble(amountEditText.getText().toString());
        new ServiceExecutor().executeService(new ConvertCurrencyImpl(new DataAccessListener<Transaction>() {
            @Override
            public void onSuccess(final Transaction transaction) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        convertedAmount.setText(transaction.toString());
                    }
                });
            }

            @Override
            public void onFailed(String message) {
                convertedAmount.setText(message);
            }
        }, transaction));
    }
}
