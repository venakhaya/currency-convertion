package com.venak.exhangerates.util;

import com.venak.exhangerates.model.Transaction;

public class ConverterCalculator {
    private static final double BASE_MARK_UP = 1.07;
    private static final double ALT_MARK_UP = 1.04;

    public Transaction convert(Transaction transaction) {
        if (transaction.base.equals(transaction.from.key) && transaction.amount < 200) {
            transaction.finalAmount = (transaction.amount * Double.parseDouble(transaction.to.value)) * BASE_MARK_UP;
            return transaction;
        } else if (transaction.base.equals(transaction.from.key) && transaction.amount >= 200) {
            transaction.finalAmount = transaction.amount * Double.parseDouble(transaction.to.value) * ALT_MARK_UP;
            return transaction;
        } else {
            transaction.finalAmount = (transaction.amount * Double.parseDouble(transaction.to.value));
        }
        return transaction;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
