package com.garvitsharma.expensetracker;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class AmountParserTest {

    @Test
    public void parsesWholeRupees() {
        AmountParser.Result r = AmountParser.parse("250");
        assertTrue(r.isOk());
        assertEquals(25000L, r.getPaise());
    }

    @Test
    public void parsesDecimals() {
        assertEquals(12050L, AmountParser.parse("120.5").getPaise());
        assertEquals(12005L, AmountParser.parse("120.05").getPaise());
        assertEquals(50L, AmountParser.parse(".5").getPaise());
    }

    @Test
    public void ignoresCommasAndSpaces() {
        assertEquals(123456700L, AmountParser.parse(" 12,34,567 ").getPaise());
    }

    @Test
    public void rejectsEmpty() {
        assertEquals(AmountParser.Status.EMPTY, AmountParser.parse("").getStatus());
        assertEquals(AmountParser.Status.EMPTY, AmountParser.parse("   ").getStatus());
        assertEquals(AmountParser.Status.EMPTY, AmountParser.parse(null).getStatus());
    }

    @Test
    public void rejectsZeroAndNegative() {
        assertFalse(AmountParser.parse("0").isOk());
        assertFalse(AmountParser.parse("0.00").isOk());
        assertFalse(AmountParser.parse("-5").isOk());
    }

    @Test
    public void rejectsGarbageAndTooManyDecimals() {
        assertEquals(AmountParser.Status.INVALID, AmountParser.parse("abc").getStatus());
        assertEquals(AmountParser.Status.INVALID, AmountParser.parse("1.234").getStatus());
        assertEquals(AmountParser.Status.INVALID, AmountParser.parse("1.2.3").getStatus());
        assertEquals(AmountParser.Status.INVALID, AmountParser.parse(".").getStatus());
    }

    @Test
    public void rejectsTooLarge() {
        assertEquals(AmountParser.Status.TOO_LARGE,
                AmountParser.parse("99999999999").getStatus());
        assertTrue(AmountParser.parse("9999999999.99").isOk());
    }
}
