package human.coejoder.mt4client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.InjectableValues;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

/**
 * Unit tests for JSON deserialization of data classes.
 */
class JsonDeserializationTest {

    private ObjectMapper objectMapper;
    private MT4Client mockClient;

    @BeforeEach
    void setUp() {
        mockClient = mock(MT4Client.class);

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new ParameterNamesModule());

        InjectableValues.Std injectableValues = new InjectableValues.Std();
        injectableValues.addValue(MT4Client.class, mockClient);
        objectMapper.setInjectableValues(injectableValues);
    }

    // Account deserialization tests
    @Test
    @DisplayName("Account deserializes from JSON")
    void testAccountDeserialization() throws JsonProcessingException {
        String json = """
            {
                "login": 12345,
                "trade_mode": 0,
                "name": "Test Account",
                "server": "Demo-Server",
                "currency": "USD",
                "company": "Test Broker"
            }
            """;

        Account account = objectMapper.readValue(json, Account.class);

        assertEquals(12345, account.getLogin());
        assertEquals(AccountTradeMode.ACCOUNT_TRADE_MODE_DEMO, account.getTradeMode());
        assertEquals("Test Account", account.getName());
        assertEquals("Demo-Server", account.getServer());
        assertEquals("USD", account.getCurrency());
        assertEquals("Test Broker", account.getCompany());
    }

    @Test
    @DisplayName("Account toString returns correct format")
    void testAccountToString() throws JsonProcessingException {
        String json = """
            {
                "login": 12345,
                "trade_mode": 1,
                "name": "Test",
                "server": "Server",
                "currency": "EUR",
                "company": "Broker"
            }
            """;

        Account account = objectMapper.readValue(json, Account.class);
        String str = account.toString();

        assertTrue(str.contains("12345"));
        assertTrue(str.contains("Test"));
        assertTrue(str.contains("EUR"));
    }

    // Symbol deserialization tests
    @Test
    @DisplayName("Symbol deserializes from JSON")
    void testSymbolDeserialization() throws JsonProcessingException {
        String json = """
            {
                "name": "EURUSD",
                "point": 0.00001,
                "digits": 5,
                "volume_min": 0.01,
                "volume_step": 0.01,
                "volume_max": 100.0,
                "trade_contract_size": 100000.0,
                "trade_tick_value": 1.0,
                "trade_tick_size": 0.00001,
                "trade_stops_level": 0,
                "trade_freeze_level": 0
            }
            """;

        Symbol symbol = objectMapper.readValue(json, Symbol.class);

        assertEquals("EURUSD", symbol.getName());
        assertEquals(0.00001, symbol.getPoint(), 0.0000001);
        assertEquals(5, symbol.getDigits());
        assertEquals(0.01, symbol.getVolumeMin(), 0.001);
        assertEquals(0.01, symbol.getVolumeStep(), 0.001);
        assertEquals(100.0, symbol.getVolumeMax(), 0.1);
        assertEquals(100000.0, symbol.getTradeContractSize(), 0.1);
    }

    @Test
    @DisplayName("Symbol toString returns correct format")
    void testSymbolToString() throws JsonProcessingException {
        String json = """
            {
                "name": "GBPUSD",
                "point": 0.00001,
                "digits": 5,
                "volume_min": 0.01,
                "volume_step": 0.01,
                "volume_max": 50.0,
                "trade_contract_size": 100000.0,
                "trade_tick_value": 1.25,
                "trade_tick_size": 0.00001,
                "trade_stops_level": 10,
                "trade_freeze_level": 5
            }
            """;

        Symbol symbol = objectMapper.readValue(json, Symbol.class);
        String str = symbol.toString();

        assertTrue(str.contains("GBPUSD"));
        assertTrue(str.contains("point="));
        assertTrue(str.contains("digits="));
    }

    // SymbolTick deserialization tests
    @Test
    @DisplayName("SymbolTick deserializes from JSON")
    void testSymbolTickDeserialization() throws JsonProcessingException {
        String json = """
            {
                "time": 1700000000,
                "bid": 1.09876,
                "ask": 1.09886,
                "last": 0.0,
                "volume": 1000
            }
            """;

        SymbolTick tick = objectMapper.readValue(json, SymbolTick.class);

        assertEquals(1700000000, tick.getTime());
        assertEquals(1.09876, tick.getBid(), 0.00001);
        assertEquals(1.09886, tick.getAsk(), 0.00001);
        assertEquals(0.0, tick.getLast(), 0.00001);
        assertEquals(1000, tick.getVolume());
    }

    @Test
    @DisplayName("SymbolTick equals and hashCode work correctly")
    void testSymbolTickEqualsHashCode() {
        SymbolTick tick1 = new SymbolTick(1700000000, 1.1, 1.2, 0.0, 100);
        SymbolTick tick2 = new SymbolTick(1700000000, 1.1, 1.2, 0.0, 100);
        SymbolTick tick3 = new SymbolTick(1700000001, 1.1, 1.2, 0.0, 100);

        assertEquals(tick1, tick2);
        assertEquals(tick1.hashCode(), tick2.hashCode());
        assertNotEquals(tick1, tick3);
    }

    @Test
    @DisplayName("SymbolTick toString returns correct format")
    void testSymbolTickToString() {
        SymbolTick tick = new SymbolTick(1700000000, 1.09876, 1.09886, 0.0, 1000);
        String str = tick.toString();

        assertTrue(str.contains("1700000000"));
        assertTrue(str.contains("1.09876"));
        assertTrue(str.contains("1.09886"));
    }

    // OHLCV deserialization tests
    @Test
    @DisplayName("OHLCV deserializes from JSON")
    void testOHLCVDeserialization() throws JsonProcessingException {
        String json = """
            {
                "time": 1700000000,
                "open": 19000,
                "high": 19100,
                "low": 18900,
                "close": 19050,
                "tick_volume": 1500
            }
            """;

        OHLCV ohlcv = objectMapper.readValue(json, OHLCV.class);

        assertEquals(1700000000, ohlcv.getTime());
        assertEquals(19000, ohlcv.getOpen());
        assertEquals(19100, ohlcv.getHigh());
        assertEquals(18900, ohlcv.getLow());
        assertEquals(19050, ohlcv.getClose());
        assertEquals(1500, ohlcv.getTick_volume());
    }

    @Test
    @DisplayName("OHLCV equals and hashCode work correctly")
    void testOHLCVEqualsHashCode() {
        OHLCV ohlcv1 = new OHLCV(1700000000, 100, 110, 90, 105, 1000);
        OHLCV ohlcv2 = new OHLCV(1700000000, 100, 110, 90, 105, 1000);
        OHLCV ohlcv3 = new OHLCV(1700000001, 100, 110, 90, 105, 1000);

        assertEquals(ohlcv1, ohlcv2);
        assertEquals(ohlcv1.hashCode(), ohlcv2.hashCode());
        assertNotEquals(ohlcv1, ohlcv3);
    }

    @Test
    @DisplayName("OHLCV toString returns correct format")
    void testOHLCVToString() {
        OHLCV ohlcv = new OHLCV(1700000000, 100, 110, 90, 105, 1000);
        String str = ohlcv.toString();

        assertTrue(str.contains("1700000000"));
        assertTrue(str.contains("100"));
        assertTrue(str.contains("110"));
    }

    // Order deserialization tests
    @Test
    @DisplayName("Order deserializes from JSON")
    void testOrderDeserialization() throws JsonProcessingException {
        String json = """
            {
                "ticket": 123456,
                "magic_number": 12345,
                "symbol": "EURUSD",
                "order_type": 0,
                "lots": 0.1,
                "open_price": 1.09876,
                "close_price": 0.0,
                "open_time": "2023.11.15 10:00:00",
                "close_time": null,
                "expiration": null,
                "sl": 1.09500,
                "tp": 1.10200,
                "profit": 50.0,
                "commission": -1.0,
                "swap": -0.5,
                "comment": "Test order"
            }
            """;

        Order order = objectMapper.readValue(json, Order.class);

        assertEquals(123456, order.getTicket());
        assertEquals(12345, order.getMagicNumber());
        assertEquals("EURUSD", order.getSymbol());
        assertEquals(OrderType.OP_BUY, order.getOrderType());
        assertEquals(0.1, order.getLots(), 0.001);
        assertEquals(1.09876, order.getOpenPrice(), 0.00001);
        assertEquals(1.09500, order.getSl(), 0.00001);
        assertEquals(1.10200, order.getTp(), 0.00001);
        assertEquals(50.0, order.getProfit(), 0.01);
        assertEquals("Test order", order.getComment());
    }

    @Test
    @DisplayName("Order toString returns correct format")
    void testOrderToString() {
        Order order = new Order(123, 456, "EURUSD", 0, 0.1, 1.1, 0.0,
                "2023.11.15 10:00:00", null, null, 1.09, 1.12, 50.0, -1.0, -0.5, "test");
        String str = order.toString();

        assertTrue(str.contains("123"));
        assertTrue(str.contains("EURUSD"));
        assertTrue(str.contains("MARKET-BUY"));
    }

    // Signal deserialization tests
    @Test
    @DisplayName("Signal deserializes from JSON")
    void testSignalDeserialization() throws JsonProcessingException {
        String json = """
            {
                "name": "TestSignal",
                "id": 12345,
                "author_login": "trader123",
                "broker": "Test Broker",
                "broker_server": "Demo-Server",
                "balance": 10000.0,
                "equity": 10500.0,
                "gain": 5.0,
                "max_drawdown": 10.5,
                "price": 30.0,
                "roi": 25.0,
                "rating": 4,
                "subscribers": 100,
                "trades": 500,
                "trade_mode": 0,
                "date_published": 1700000000,
                "date_started": 1690000000,
                "leverage": 100,
                "pips": 500,
                "currency": "USD"
            }
            """;

        Signal signal = objectMapper.readValue(json, Signal.class);

        assertEquals("TestSignal", signal.getName());
        assertEquals(12345, signal.getId());
        assertEquals("trader123", signal.getAuthorLogin());
        assertEquals("Test Broker", signal.getBroker());
        assertEquals(10000.0, signal.getBalance(), 0.01);
        assertEquals(10500.0, signal.getEquity(), 0.01);
    }

    @Test
    @DisplayName("Signal toString returns correct format")
    void testSignalToString() throws JsonProcessingException {
        String json = """
            {
                "name": "MySignal",
                "id": 999,
                "author_login": "author",
                "broker": "Broker",
                "broker_server": "Server",
                "balance": 5000.0,
                "equity": 5200.0,
                "gain": 4.0,
                "max_drawdown": 8.0,
                "price": 20.0,
                "roi": 15.0,
                "rating": 3,
                "subscribers": 50,
                "trades": 200,
                "trade_mode": 1,
                "date_published": 1700000000,
                "date_started": 1690000000,
                "leverage": 50,
                "pips": 200,
                "currency": "EUR"
            }
            """;

        Signal signal = objectMapper.readValue(json, Signal.class);
        String str = signal.toString();

        assertTrue(str.contains("MySignal"));
        assertTrue(str.contains("999"));
    }

    // ModifyOrder tests
    @Test
    @DisplayName("ModifyOrder builder creates correct object")
    void testModifyOrderBuilder() {
        Order order = new Order(123, 0, "EURUSD", 0, 0.1, 1.1, 0.0,
                "2023.11.15 10:00:00", null, null, 0.0, 0.0, 0.0, 0.0, 0.0, "");

        ModifyOrder modify = ModifyOrder.Builder.newInstance()
                .setOrder(order)
                .setSl(1.09)
                .setTp(1.12)
                .build();

        assertEquals(123, modify.ticket);
        assertEquals(1.09, modify.sl, 0.001);
        assertEquals(1.12, modify.tp, 0.001);
    }

    @Test
    @DisplayName("ModifyOrder builder with price")
    void testModifyOrderBuilderFull() {
        Order order = new Order(456, 0, "GBPUSD", 4, 0.2, 1.25, 0.0,
                "2023.11.15 11:00:00", null, "2023.12.01 00:00:00", 0.0, 0.0, 0.0, 0.0, 0.0, "");

        ModifyOrder modify = ModifyOrder.Builder.newInstance()
                .setOrder(order)
                .setPrice(1.24)
                .setSl(1.23)
                .setTp(1.27)
                .build();

        assertEquals(456, modify.ticket);
        assertEquals(1.24, modify.price, 0.001);
    }

    // NewOrder tests
    @Test
    @DisplayName("NewOrder builder with symbol name")
    void testNewOrderBuilderWithSymbolName() {
        NewOrder order = NewOrder.Builder.newInstance()
                .setSymbol("EURUSD")
                .setOrderType(OrderType.OP_BUY)
                .setLots(0.1)
                .setSl(1.09)
                .setTp(1.12)
                .setComment("Test")
                .setMagicNumber(12345)
                .build();

        assertEquals("EURUSD", order.symbol);
        assertEquals(OrderType.OP_BUY, order.orderType);
        assertEquals(0.1, order.lots, 0.001);
    }

    @Test
    @DisplayName("NewOrder builder with slippage and price")
    void testNewOrderBuilderWithSlippageAndPrice() {
        NewOrder order = NewOrder.Builder.newInstance()
                .setSymbol("GBPUSD")
                .setOrderType(OrderType.OP_BUYLIMIT)
                .setLots(0.2)
                .setPrice(1.25)
                .setSlippage(3)
                .setSlPoints(100)
                .setTpPoints(200)
                .build();

        assertEquals("GBPUSD", order.symbol);
        assertEquals(OrderType.OP_BUYLIMIT, order.orderType);
        assertEquals(1.25, order.price, 0.001);
        assertEquals(3, (int) order.slippage);
    }
}
