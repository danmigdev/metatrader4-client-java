package human.coejoder.mt4client;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for MT4Client data classes
 */
class MT4DataClassesTest {

    // Order Tests
    @Test
    @DisplayName("Order - all getters return correct values")
    void testOrderGetters() {
        Order order = new Order(
            123,             // ticket
            456,             // magicNumber
            "DE40",          // symbol
            OrderType.OP_BUY.id, // orderType
            0.5,             // lots
            19000.0,         // openPrice
            19050.0,         // closePrice
            "2023.11.15 10:00:00", // openTime
            "2023.11.15 11:00:00", // closeTime
            null,            // expiration
            18900.0,         // sl
            19100.0,         // tp
            50.0,            // profit
            -1.0,            // commission
            -0.5,            // swap
            "test order"     // comment
        );

        assertEquals(123, order.getTicket());
        assertEquals(456, order.getMagicNumber());
        assertEquals("DE40", order.getSymbol());
        assertEquals(OrderType.OP_BUY, order.getOrderType());
        assertEquals(0.5, order.getLots(), 0.001);
        assertEquals(19000.0, order.getOpenPrice(), 0.001);
        assertEquals(19050.0, order.getClosePrice(), 0.001);
        assertEquals("2023.11.15 10:00:00", order.getOpenTime());
        assertEquals("2023.11.15 11:00:00", order.getCloseTime());
        assertNull(order.getExpiration());
        assertEquals(18900.0, order.getSl(), 0.001);
        assertEquals(19100.0, order.getTp(), 0.001);
        assertEquals(50.0, order.getProfit(), 0.001);
        assertEquals(-1.0, order.getCommission(), 0.001);
        assertEquals(-0.5, order.getSwap(), 0.001);
        assertEquals("test order", order.getComment());
    }

    @Test
    @DisplayName("Order - toString contains all fields")
    void testOrderToString() {
        Order order = new Order(123, 456, "DE40", OrderType.OP_BUY.id, 0.5, 19000.0, 0.0,
            "2023.11.15 10:00:00", null, null, 0.0, 0.0, 0.0, 0.0, 0.0, "");

        String str = order.toString();
        assertTrue(str.contains("ticket=123"));
        assertTrue(str.contains("magicNumber=456"));
        assertTrue(str.contains("symbol='DE40'"));
        assertTrue(str.contains("orderType=MARKET-BUY"));
    }

    @Test
    @DisplayName("Order - pending order types")
    void testOrderPendingTypes() {
        Order buyLimit = new Order(1, 0, "DE40", OrderType.OP_BUYLIMIT.id, 0.1, 19000.0, 0.0,
            "2023.11.15 10:00:00", null, null, 0.0, 0.0, 0.0, 0.0, 0.0, "");
        Order sellLimit = new Order(2, 0, "DE40", OrderType.OP_SELLLIMIT.id, 0.1, 19000.0, 0.0,
            "2023.11.15 10:00:00", null, null, 0.0, 0.0, 0.0, 0.0, 0.0, "");
        Order buyStop = new Order(3, 0, "DE40", OrderType.OP_BUYSTOP.id, 0.1, 19000.0, 0.0,
            "2023.11.15 10:00:00", null, null, 0.0, 0.0, 0.0, 0.0, 0.0, "");
        Order sellStop = new Order(4, 0, "DE40", OrderType.OP_SELLSTOP.id, 0.1, 19000.0, 0.0,
            "2023.11.15 10:00:00", null, null, 0.0, 0.0, 0.0, 0.0, 0.0, "");

        assertEquals(OrderType.OP_BUYLIMIT, buyLimit.getOrderType());
        assertEquals(OrderType.OP_SELLLIMIT, sellLimit.getOrderType());
        assertEquals(OrderType.OP_BUYSTOP, buyStop.getOrderType());
        assertEquals(OrderType.OP_SELLSTOP, sellStop.getOrderType());
    }

    // OrderType Tests
    @Test
    @DisplayName("OrderType - fromId returns correct values")
    void testOrderTypeFromId() {
        assertEquals(OrderType.OP_BUY, OrderType.fromId(0).orElseThrow());
        assertEquals(OrderType.OP_SELL, OrderType.fromId(1).orElseThrow());
        assertEquals(OrderType.OP_BUYLIMIT, OrderType.fromId(2).orElseThrow());
        assertEquals(OrderType.OP_BUYSTOP, OrderType.fromId(3).orElseThrow());
        assertEquals(OrderType.OP_SELLLIMIT, OrderType.fromId(4).orElseThrow());
        assertEquals(OrderType.OP_SELLSTOP, OrderType.fromId(5).orElseThrow());
    }

    @Test
    @DisplayName("OrderType - fromId returns empty for invalid id")
    void testOrderTypeFromIdInvalid() {
        assertTrue(OrderType.fromId(99).isEmpty());
        assertTrue(OrderType.fromId(-1).isEmpty());
    }

    @Test
    @DisplayName("OrderType - enum values have correct ids")
    void testOrderTypeEnumIds() {
        assertEquals(0, OrderType.OP_BUY.id);
        assertEquals(1, OrderType.OP_SELL.id);
        assertEquals(2, OrderType.OP_BUYLIMIT.id);
        assertEquals(3, OrderType.OP_BUYSTOP.id);
        assertEquals(4, OrderType.OP_SELLLIMIT.id);
        assertEquals(5, OrderType.OP_SELLSTOP.id);
    }

    // OHLCV Tests
    @Test
    @DisplayName("OHLCV - constructor sets all fields")
    void testOHLCVConstructor() {
        OHLCV ohlcv = new OHLCV(1700000000, 19000, 19100, 18900, 19050, 1000);

        assertEquals(1700000000, ohlcv.time);
        assertEquals(19000, ohlcv.open);
        assertEquals(19100, ohlcv.high);
        assertEquals(18900, ohlcv.low);
        assertEquals(19050, ohlcv.close);
        assertEquals(1000, ohlcv.tick_volume);
    }

    @Test
    @DisplayName("OHLCV - multiple instances with different values")
    void testOHLCVMultipleInstances() {
        OHLCV ohlcv1 = new OHLCV(1700000000, 19000, 19100, 18900, 19050, 1000);
        OHLCV ohlcv2 = new OHLCV(1700000060, 19050, 19200, 19000, 19150, 2000);

        assertNotEquals(ohlcv1.time, ohlcv2.time);
        assertNotEquals(ohlcv1.open, ohlcv2.open);
        assertNotEquals(ohlcv1.tick_volume, ohlcv2.tick_volume);
    }

    // NewOrder Tests
    @Test
    @DisplayName("NewOrder.Builder - build with minimal required fields")
    void testNewOrderBuilderMinimal() {
        NewOrder order = NewOrder.Builder.newInstance()
            .setSymbol("DE40")
            .setOrderType(OrderType.OP_BUY)
            .setLots(0.1)
            .build();

        assertNotNull(order);
    }

    @Test
    @DisplayName("NewOrder.Builder - build with all fields")
    void testNewOrderBuilderAllFields() {
        NewOrder order = NewOrder.Builder.newInstance()
            .setSymbol("DE40")
            .setOrderType(OrderType.OP_BUYLIMIT)
            .setLots(0.5)
            .setPrice(19000.0)
            .setSlippage(3)
            .setSl(18900.0)
            .setTp(19100.0)
            .setComment("test order")
            .setMagicNumber(12345)
            .build();

        assertNotNull(order);
    }

    // ModifyOrder Tests
    @Test
    @DisplayName("ModifyOrder.Builder - modify stop loss and take profit")
    void testModifyOrderBuilder() {
        Order original = new Order(999, 0, "DE40", OrderType.OP_BUY.id, 0.1, 19000.0, 0.0,
            "2023.11.15 12:00:00", null, null, 0.0, 0.0, 0.0, 0.0, 0.0, "");

        ModifyOrder modify = ModifyOrder.Builder.newInstance()
            .setOrder(original)
            .setSl(18800.0)
            .setTp(19200.0)
            .build();

        assertNotNull(modify);
    }

    // StandardTimeframe Tests
    @Test
    @DisplayName("StandardTimeframe - values exist")
    void testStandardTimeframeValues() {
        assertNotNull(StandardTimeframe.PERIOD_M1);
        assertNotNull(StandardTimeframe.PERIOD_M5);
        assertNotNull(StandardTimeframe.PERIOD_M15);
        assertNotNull(StandardTimeframe.PERIOD_M30);
        assertNotNull(StandardTimeframe.PERIOD_H1);
        assertNotNull(StandardTimeframe.PERIOD_H4);
        assertNotNull(StandardTimeframe.PERIOD_D1);
        assertNotNull(StandardTimeframe.PERIOD_W1);
        assertNotNull(StandardTimeframe.PERIOD_MN1);
    }

    // AppliedPrice Tests
    @Test
    @DisplayName("AppliedPrice - enum values exist")
    void testAppliedPriceValues() {
        assertNotNull(AppliedPrice.PRICE_CLOSE);
        assertNotNull(AppliedPrice.PRICE_OPEN);
        assertNotNull(AppliedPrice.PRICE_HIGH);
        assertNotNull(AppliedPrice.PRICE_LOW);
        assertNotNull(AppliedPrice.PRICE_MEDIAN);
        assertNotNull(AppliedPrice.PRICE_TYPICAL);
        assertNotNull(AppliedPrice.PRICE_WEIGHTED);
    }

    // SmoothingMethod Tests
    @Test
    @DisplayName("SmoothingMethod - enum values exist")
    void testSmoothingMethodValues() {
        assertNotNull(SmoothingMethod.SMA);
        assertNotNull(SmoothingMethod.EMA);
        assertNotNull(SmoothingMethod.SMMA);
        assertNotNull(SmoothingMethod.LWMA);
    }

    // DayOfWeek Tests
    @Test
    @DisplayName("DayOfWeek - enum values exist")
    void testDayOfWeekValues() {
        assertNotNull(DayOfWeek.SUNDAY);
        assertNotNull(DayOfWeek.MONDAY);
        assertNotNull(DayOfWeek.TUESDAY);
        assertNotNull(DayOfWeek.WEDNESDAY);
        assertNotNull(DayOfWeek.THURSDAY);
        assertNotNull(DayOfWeek.FRIDAY);
        assertNotNull(DayOfWeek.SATURDAY);
    }

    // AccountTradeMode Tests
    @Test
    @DisplayName("AccountTradeMode - enum values exist")
    void testAccountTradeModeValues() {
        assertNotNull(AccountTradeMode.ACCOUNT_TRADE_MODE_DEMO);
        assertNotNull(AccountTradeMode.ACCOUNT_TRADE_MODE_CONTEST);
        assertNotNull(AccountTradeMode.ACCOUNT_TRADE_MODE_REAL);
    }
}
