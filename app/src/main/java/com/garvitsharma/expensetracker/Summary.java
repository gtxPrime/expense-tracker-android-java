package com.garvitsharma.expensetracker;

import java.util.List;

/** Total income, total expenses and remaining balance for a list of transactions. */
public class Summary {

    private final long totalIncomePaise;
    private final long totalExpensePaise;

    private Summary(long income, long expense) {
        this.totalIncomePaise = income;
        this.totalExpensePaise = expense;
    }

    public static Summary of(List<Transaction> transactions) {
        long income = 0;
        long expense = 0;
        for (Transaction t : transactions) {
            if (t.getType() == TransactionType.INCOME) {
                income += t.getAmountPaise();
            } else {
                expense += t.getAmountPaise();
            }
        }
        return new Summary(income, expense);
    }

    public long getTotalIncomePaise() {
        return totalIncomePaise;
    }

    public long getTotalExpensePaise() {
        return totalExpensePaise;
    }

    public long getBalancePaise() {
        return totalIncomePaise - totalExpensePaise;
    }
}
