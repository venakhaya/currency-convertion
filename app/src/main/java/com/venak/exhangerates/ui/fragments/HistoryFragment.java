package com.venak.exhangerates.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.venak.exhangerates.R;
import com.venak.exhangerates.listeners.DataAccessListener;
import com.venak.exhangerates.model.ConvertHistory;
import com.venak.exhangerates.services.handler.ServiceExecutor;
import com.venak.exhangerates.services.handler.implementation.RatesHistoryImpl;
import com.venak.exhangerates.util.Util;

import java.util.Date;
import java.util.List;

public class HistoryFragment extends BaseFragment implements DataAccessListener<List<ConvertHistory>> {
    private RecyclerView recyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.history_layout, container, false);
        recyclerView = rootView.findViewById(R.id.history_recycle_view);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        new ServiceExecutor().executeService(new RatesHistoryImpl(this, Util.date(new Date())));
    }

    @Override
    public void onSuccess(List<ConvertHistory> results) {
        List<ConvertHistory> histories = results;

        setupRecyclerView(histories);
    }

    @Override
    public void onFailed(String message) {

    }


    private void setupRecyclerView(final List<ConvertHistory> convertHistories) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                recyclerView.setAdapter(new RatesRecyclerViewAdapter(convertHistories));
            }
        });
    }

    public class RatesRecyclerViewAdapter
            extends RecyclerView.Adapter<RatesRecyclerViewAdapter.ViewHolder> {

        private List<ConvertHistory> convertHistories;

        public RatesRecyclerViewAdapter(List<ConvertHistory> convertHistories) {
            this.convertHistories = convertHistories;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list_content, parent, false);
            return new RatesRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final RatesRecyclerViewAdapter.ViewHolder holder, int position) {
            holder.currencyKeyTextView.setText(convertHistories.get(position).from);
            holder.currencyValueTextView.setText(convertHistories.get(position).toString());
            holder.itemView.setTag(convertHistories.get(position));
        }

        @Override
        public int getItemCount() {
            return convertHistories.size();
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
