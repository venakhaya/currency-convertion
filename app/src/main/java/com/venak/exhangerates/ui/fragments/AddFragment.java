package com.venak.exhangerates.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.venak.exhangerates.R;
import com.venak.exhangerates.listeners.DataAccessListener;
import com.venak.exhangerates.model.ExchangeRate;
import com.venak.exhangerates.services.handler.implementation.AddCurrencyServiceImpl;

public class AddFragment extends BaseFragment implements DataAccessListener<String>, View.OnClickListener {
    private EditText currencyKeyEditText;
    private EditText currencyValueEditText;
    private TextView statustextView;
    private Button addButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_currency, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        currencyKeyEditText = view.findViewById(R.id.add_currency_key);
        currencyValueEditText = view.findViewById(R.id.add_amount_edit_text);
        addButton = view.findViewById(R.id.add_button);
        statustextView = view.findViewById(R.id.status_text_view);
        addButton.setOnClickListener(this);
    }

    @Override
    public void onSuccess(String results) {
        setStatus(results);
    }

    @Override
    public void onFailed(String message) {
        setStatus(message);
    }

    private void setStatus(final String status) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                statustextView.setText(status);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (currencyKeyEditText.getText().toString().length() == 3 && currencyValueEditText.getText().toString().length() > 0) {
            ExchangeRate exchangeRate = new ExchangeRate(currencyKeyEditText.getText().toString(),
                    currencyValueEditText.getText().toString());

            serviceExecutor.executeService(new AddCurrencyServiceImpl(this, exchangeRate));
        }
    }
}
