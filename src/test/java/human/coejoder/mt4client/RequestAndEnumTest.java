package human.coejoder.mt4client;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link Request} enum and other enums with low coverage.
 */
class RequestAndEnumTest {

    // Request tests
    @Test
    @DisplayName("Request.build creates correct JSON object")
    void testRequestBuild() {
        for (Request request : Request.values()) {
            ObjectNode node = request.build();

            assertNotNull(node);
            assertTrue(node.has("action"));
            assertEquals(request.toString(), node.get("action").asText());
        }
    }

    @Test
    @DisplayName("All Request enum values exist")
    void testAllRequestValues() {
        Request[] values = Request.values();

        assertTrue(values.length > 0);
        assertNotNull(Request.valueOf("GET_ACCOUNT_INFO"));
        assertNotNull(Request.valueOf("GET_SYMBOLS"));
        assertNotNull(Request.valueOf("GET_OHLCV"));
        assertNotNull(Request.valueOf("DO_ORDER_SEND"));
        assertNotNull(Request.valueOf("RUN_INDICATOR"));
    }

    // IndicatorLine_iADX tests
    @Test
    @DisplayName("IndicatorLine_iADX values")
    void testIndicatorLineIADX() {
        assertEquals(0, IndicatorLine_iADX.MAIN.id);
        assertEquals(1, IndicatorLine_iADX.PLUSDI.id);
        assertEquals(2, IndicatorLine_iADX.MINUSDI.id);

        IndicatorLine_iADX[] values = IndicatorLine_iADX.values();
        assertEquals(3, values.length);
        assertNotNull(IndicatorLine_iADX.valueOf("MAIN"));
    }

    // IndicatorLine_iAlligator tests
    @Test
    @DisplayName("IndicatorLine_iAlligator values")
    void testIndicatorLineIAlligator() {
        assertEquals(1, IndicatorLine_iAlligator.GATORJAW.id);
        assertEquals(2, IndicatorLine_iAlligator.GATORTEETH.id);
        assertEquals(3, IndicatorLine_iAlligator.GATORLIPS.id);

        assertEquals(3, IndicatorLine_iAlligator.values().length);
    }

    // IndicatorLine_iBands tests
    @Test
    @DisplayName("IndicatorLine_iBands values")
    void testIndicatorLineIBands() {
        assertEquals(0, IndicatorLine_iBands.MAIN.id);
        assertEquals(1, IndicatorLine_iBands.UPPER.id);
        assertEquals(2, IndicatorLine_iBands.LOWER.id);

        assertEquals(3, IndicatorLine_iBands.values().length);
    }

    // IndicatorLine_iIchimoku tests
    @Test
    @DisplayName("IndicatorLine_iIchimoku values")
    void testIndicatorLineIIchimoku() {
        assertEquals(1, IndicatorLine_iIchimoku.TENKANSEN.id);
        assertEquals(2, IndicatorLine_iIchimoku.KIJUNSEN.id);
        assertEquals(3, IndicatorLine_iIchimoku.SENKOUSPANA.id);
        assertEquals(4, IndicatorLine_iIchimoku.SENKOUSPANB.id);
        assertEquals(5, IndicatorLine_iIchimoku.CHIKOUSPAN.id);

        assertEquals(5, IndicatorLine_iIchimoku.values().length);
    }

    // IndicatorLine_iMACD tests
    @Test
    @DisplayName("IndicatorLine_iMACD values")
    void testIndicatorLineIMACD() {
        assertEquals(0, IndicatorLine_iMACD.MAIN.id);
        assertEquals(1, IndicatorLine_iMACD.SIGNAL.id);

        assertEquals(2, IndicatorLine_iMACD.values().length);
    }

    // PriceField tests
    @Test
    @DisplayName("PriceField values")
    void testPriceField() {
        assertEquals(0, PriceField.LOW_HIGH.id);
        assertEquals(1, PriceField.CLOSE_CLOSE.id);

        assertEquals(2, PriceField.values().length);
        assertNotNull(PriceField.valueOf("LOW_HIGH"));
    }

    // SymbolCalcMode tests
    @Test
    @DisplayName("SymbolCalcMode values and fromId")
    void testSymbolCalcMode() {
        assertEquals(0, SymbolCalcMode.SYMBOL_CALC_MODE_FOREX.id);
        assertEquals(1, SymbolCalcMode.SYMBOL_CALC_MODE_CFD.id);
        assertEquals(2, SymbolCalcMode.SYMBOL_CALC_MODE_FUTURES.id);
        assertEquals(3, SymbolCalcMode.SYMBOL_CALC_MODE_CFDINDEX.id);

        assertTrue(SymbolCalcMode.fromId(0).isPresent());
        assertEquals(SymbolCalcMode.SYMBOL_CALC_MODE_FOREX, SymbolCalcMode.fromId(0).get());
        assertTrue(SymbolCalcMode.fromId(999).isEmpty());
    }

    // AccountInfoDouble tests
    @Test
    @DisplayName("AccountInfoDouble values")
    void testAccountInfoDouble() {
        AccountInfoDouble[] values = AccountInfoDouble.values();
        assertTrue(values.length > 0);

        assertNotNull(AccountInfoDouble.valueOf("ACCOUNT_BALANCE"));
        assertNotNull(AccountInfoDouble.valueOf("ACCOUNT_EQUITY"));
        assertNotNull(AccountInfoDouble.valueOf("ACCOUNT_MARGIN"));
    }

    // AccountInfoInteger tests
    @Test
    @DisplayName("AccountInfoInteger values")
    void testAccountInfoInteger() {
        AccountInfoInteger[] values = AccountInfoInteger.values();
        assertTrue(values.length > 0);

        assertNotNull(AccountInfoInteger.valueOf("ACCOUNT_LEVERAGE"));
        assertNotNull(AccountInfoInteger.valueOf("ACCOUNT_LIMIT_ORDERS"));
    }

    // SymbolInfoDouble tests
    @Test
    @DisplayName("SymbolInfoDouble values")
    void testSymbolInfoDouble() {
        SymbolInfoDouble[] values = SymbolInfoDouble.values();
        assertTrue(values.length > 0);

        assertNotNull(SymbolInfoDouble.valueOf("SYMBOL_BID"));
        assertNotNull(SymbolInfoDouble.valueOf("SYMBOL_ASK"));
    }

    // SymbolInfoInteger tests
    @Test
    @DisplayName("SymbolInfoInteger values")
    void testSymbolInfoInteger() {
        SymbolInfoInteger[] values = SymbolInfoInteger.values();
        assertTrue(values.length > 0);

        assertNotNull(SymbolInfoInteger.valueOf("SYMBOL_SELECT"));
        assertNotNull(SymbolInfoInteger.valueOf("SYMBOL_SPREAD"));
    }

    // Timeframe interface test
    @Test
    @DisplayName("Timeframe interface via StandardTimeframe")
    void testTimeframeInterface() {
        Timeframe tf = StandardTimeframe.PERIOD_M1;
        assertEquals(1, tf.getMinutes());

        Timeframe tf2 = StandardTimeframe.PERIOD_H1;
        assertEquals(60, tf2.getMinutes());
    }
}
