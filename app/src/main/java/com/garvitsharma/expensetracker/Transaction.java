package com.garvitsharma.expensetracker;

/**
 * One income or expense entry. The amount is stored in paise (1/100 rupee) as a long
 * so totals never suffer from floating point rounding errors.
 */
public class Transaction {

    private final long id;
    private final TransactionType type;
    private final long amountPaise;
    private final String description;
    private final String category;
    private final long timestamp;

    public Transaction(long id, TransactionType type, long amountPaise,
                       String description, String category, long timestamp) {
        this.id = id;
        this.type = type;
        this.amountPaise = amountPaise;
        this.description = description;
        this.category = category;
        this.timestamp = timestamp;
    }

    public long getId() {
        return id;
    }

    public TransactionType getType() {
        return type;
    }

    public long getAmountPaise() {
        return amountPaise;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
