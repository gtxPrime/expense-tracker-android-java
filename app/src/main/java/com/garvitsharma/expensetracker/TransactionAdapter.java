package com.garvitsharma.expensetracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.color.MaterialColors;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/** Shows each transaction as a row in the RecyclerView. */
public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {

    /** Called when the user taps the delete button on a row. */
    public interface OnDeleteListener {
        void onDelete(Transaction transaction);
    }

    private final List<Transaction> items = new ArrayList<>();
    private final OnDeleteListener deleteListener;
    private final SimpleDateFormat dateFormat =
            new SimpleDateFormat("dd MMM", Locale.getDefault());

    public TransactionAdapter(OnDeleteListener deleteListener) {
        this.deleteListener = deleteListener;
    }

    public void setItems(List<Transaction> newItems) {
        items.clear();
        items.addAll(newItems);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_transaction, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Transaction t = items.get(position);
        boolean income = t.getType() == TransactionType.INCOME;
        holder.tvDescription.setText(t.getDescription());
        holder.tvMeta.setText(t.getCategory() + " \u2022 " + dateFormat.format(new Date(t.getTimestamp())));
        holder.tvAmount.setText((income ? "+" : "-") + CurrencyUtils.format(t.getAmountPaise()));
        holder.tvAmount.setTextColor(income
                ? holder.itemView.getContext().getColor(R.color.income)
                : MaterialColors.getColor(holder.itemView,
                        com.google.android.material.R.attr.colorError));
        holder.ivIcon.setImageResource(iconFor(t));
        holder.btnDelete.setOnClickListener(v -> deleteListener.onDelete(t));
    }

    /** Material vector icon shown in the circle at the start of each row. */
    static int iconFor(Transaction t) {
        if (t.getType() == TransactionType.INCOME) {
            return R.drawable.ic_cat_income;
        }
        switch (t.getCategory()) {
            case "Food":
                return R.drawable.ic_cat_food;
            case "Travel":
                return R.drawable.ic_cat_travel;
            case "Shopping":
                return R.drawable.ic_cat_shopping;
            case "Bills":
                return R.drawable.ic_cat_bills;
            default:
                return R.drawable.ic_cat_other;
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView tvDescription;
        final ImageView ivIcon;
        final TextView tvMeta;
        final TextView tvAmount;
        final ImageButton btnDelete;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            ivIcon = itemView.findViewById(R.id.ivIcon);
            tvMeta = itemView.findViewById(R.id.tvMeta);
            tvAmount = itemView.findViewById(R.id.tvAmount);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
