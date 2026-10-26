package com.garvitsharma.expensetracker;

/** Whether a transaction adds to or subtracts from the balance. */
public enum TransactionType {
    INCOME,
    EXPENSE;

    public static TransactionType fromString(String value) {
        for (TransactionType type : values()) {
            if (type.name().equalsIgnoreCase(value)) {
                return type;
            }
        }
        return EXPENSE;
    }
}
