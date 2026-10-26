package com.garvitsharma.expensetracker;

import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Collections;
import java.util.List;

/** Home screen: totals at the top, the list of transactions below. */
public class MainActivity extends AppCompatActivity {

    private TransactionDbHelper db;
    private TransactionAdapter adapter;
    private TextView tvIncome;
    private TextView tvExpense;
    private TextView tvBalance;
    private TextView tvEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new TransactionDbHelper(this);
        tvIncome = findViewById(R.id.tvTotalIncome);
        tvExpense = findViewById(R.id.tvTotalExpense);
        tvBalance = findViewById(R.id.tvBalance);
        tvEmpty = findViewById(R.id.tvEmpty);

        RecyclerView recycler = findViewById(R.id.recyclerTransactions);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TransactionAdapter(this::confirmDelete);
        recycler.setAdapter(adapter);

        FloatingActionButton fab = findViewById(R.id.fabAdd);
        fab.setOnClickListener(v ->
                startActivity(new Intent(this, AddTransactionActivity.class)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadTransactions(); // refresh after returning from the add screen
    }

    private void loadTransactions() {
        List<Transaction> transactions;
        try {
            transactions = db.getAll();
        } catch (SQLiteException e) {
            Toast.makeText(this, R.string.error_load, Toast.LENGTH_LONG).show();
            transactions = Collections.emptyList();
        }
        adapter.setItems(transactions);
        tvEmpty.setVisibility(transactions.isEmpty() ? View.VISIBLE : View.GONE);

        Summary summary = Summary.of(transactions);
        tvIncome.setText(CurrencyUtils.format(summary.getTotalIncomePaise()));
        tvExpense.setText(CurrencyUtils.format(summary.getTotalExpensePaise()));
        tvBalance.setText(CurrencyUtils.format(summary.getBalancePaise()));
    }

    private void confirmDelete(final Transaction transaction) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.delete_title)
                .setMessage(getString(R.string.delete_message, transaction.getDescription()))
                .setPositiveButton(R.string.delete, (dialog, which) -> {
                    if (db.delete(transaction.getId())) {
                        Toast.makeText(this, R.string.deleted, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, R.string.error_storage, Toast.LENGTH_SHORT).show();
                    }
                    loadTransactions();
                })
                .setNegativeButton(R.string.cancel, null)
                .show();
    }

    @Override
    protected void onDestroy() {
        db.close();
        super.onDestroy();
    }
}
