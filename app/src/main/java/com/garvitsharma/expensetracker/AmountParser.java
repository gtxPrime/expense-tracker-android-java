package com.garvitsharma.expensetracker;

import java.math.BigDecimal;

/** Validates the amount text typed by the user and converts it to paise. */
public final class AmountParser {

    /** Largest accepted amount: 99,99,99,999.99 rupees. */
    public static final long MAX_PAISE = 999_999_999_999L;

    /** Result of parsing: either a value or a reason it was rejected. */
    public enum Status {
        OK,
        EMPTY,
        INVALID,
        TOO_LARGE
    }

    public static final class Result {
        private final Status status;
        private final long paise;

        Result(Status status, long paise) {
            this.status = status;
            this.paise = paise;
        }

        public Status getStatus() {
            return status;
        }

        public long getPaise() {
            return paise;
        }

        public boolean isOk() {
            return status == Status.OK;
        }
    }

    private AmountParser() {
    }

    public static Result parse(String text) {
        if (text == null || text.trim().isEmpty()) {
            return new Result(Status.EMPTY, 0);
        }
        String clean = text.trim().replace(",", "");
        if (!clean.matches("\\d+(\\.\\d{1,2})?|\\.\\d{1,2}")) {
            return new Result(Status.INVALID, 0);
        }
        BigDecimal rupees = new BigDecimal(clean);
        if (rupees.signum() <= 0) {
            return new Result(Status.INVALID, 0);
        }
        BigDecimal paise = rupees.movePointRight(2);
        if (paise.compareTo(BigDecimal.valueOf(MAX_PAISE)) > 0) {
            return new Result(Status.TOO_LARGE, 0);
        }
        return new Result(Status.OK, paise.longValueExact());
    }
}
