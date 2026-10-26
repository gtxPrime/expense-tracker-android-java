package com.garvitsharma.expensetracker;

/** Formats paise values as rupee strings, e.g. 123450 -> "₹1,234.50". */
public final class CurrencyUtils {

    private CurrencyUtils() {
    }

    public static String format(long paise) {
        boolean negative = paise < 0;
        long abs = Math.abs(paise);
        long rupees = abs / 100;
        long rest = abs % 100;
        return (negative ? "-" : "") + "\u20B9" + groupIndian(rupees) + String.format("."
                + "%02d", rest);
    }

    /** Indian digit grouping: last three digits, then groups of two. */
    static String groupIndian(long number) {
        String digits = String.valueOf(number);
        if (digits.length() <= 3) {
            return digits;
        }
        String last3 = digits.substring(digits.length() - 3);
        String rest = digits.substring(0, digits.length() - 3);
        StringBuilder sb = new StringBuilder();
        int first = rest.length() % 2;
        if (first > 0) {
            sb.append(rest, 0, first);
        }
        for (int i = first; i < rest.length(); i += 2) {
            if (sb.length() > 0) {
                sb.append(',');
            }
            sb.append(rest, i, i + 2);
        }
        return sb + "," + last3;
    }
}
