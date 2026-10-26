package com.garvitsharma.expensetracker;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class SummaryAndCurrencyTest {

    private static Transaction tx(TransactionType type, long paise) {
        return new Transaction(1, type, paise, "test", "Other", 0L);
    }

    @Test
    public void emptyListGivesZeros() {
        Summary s = Summary.of(new ArrayList<>());
        assertEquals(0, s.getTotalIncomePaise());
        assertEquals(0, s.getTotalExpensePaise());
        assertEquals(0, s.getBalancePaise());
    }

    @Test
    public void totalsAndBalance() {
        List<Transaction> list = new ArrayList<>();
        list.add(tx(TransactionType.INCOME, 500000));
        list.add(tx(TransactionType.EXPENSE, 120050));
        list.add(tx(TransactionType.EXPENSE, 30000));
        list.add(tx(TransactionType.INCOME, 10));
        Summary s = Summary.of(list);
        assertEquals(500010, s.getTotalIncomePaise());
        assertEquals(150050, s.getTotalExpensePaise());
        assertEquals(349960, s.getBalancePaise());
    }

    @Test
    public void balanceCanBeNegative() {
        List<Transaction> list = new ArrayList<>();
        list.add(tx(TransactionType.EXPENSE, 1000));
        assertEquals(-1000, Summary.of(list).getBalancePaise());
    }

    @Test
    public void currencyUsesIndianGrouping() {
        assertEquals("\u20B90.00", CurrencyUtils.format(0));
        assertEquals("\u20B999.99", CurrencyUtils.format(9999));
        assertEquals("\u20B91,234.50", CurrencyUtils.format(123450));
        assertEquals("\u20B912,34,567.00", CurrencyUtils.format(123456700));
        assertEquals("-\u20B91,000.00", CurrencyUtils.format(-100000));
    }

    @Test
    public void typeParsingFallsBackToExpense() {
        assertEquals(TransactionType.INCOME, TransactionType.fromString("income"));
        assertEquals(TransactionType.EXPENSE, TransactionType.fromString("nonsense"));
    }
}
