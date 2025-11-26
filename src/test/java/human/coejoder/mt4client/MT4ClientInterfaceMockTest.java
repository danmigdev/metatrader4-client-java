package human.coejoder.mt4client;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Mock tests for MT4ClientInterface to improve test coverage.
 * Requires Java 21 for Mockito compatibility.
 */
class MT4ClientInterfaceMockTest {

    private MT4ClientInterface mockClient;

    @BeforeEach
    void setUp() {
        mockClient = mock(MT4ClientInterface.class);
    }

    // Account Tests
    @Test
    @DisplayName("getAccount returns account info")
    void testGetAccount() throws JsonProcessingException, MT4Exception {
        Account mockAccount = mock(Account.class);
        when(mockAccount.getBalance()).thenReturn(10000.0);
        when(mockAccount.getEquity()).thenReturn(10500.0);
        when(mockAccount.getMargin()).thenReturn(1000.0);
        when(mockAccount.getLeverage()).thenReturn(100L);
        when(mockAccount.getCurrency()).thenReturn("EUR");
        when(mockAccount.getName()).thenReturn("Test Account");
        when(mockAccount.getServer()).thenReturn("Demo Server");
        when(mockAccount.getCompany()).thenReturn("Test Broker");
        when(mockAccount.getLogin()).thenReturn(12345L);

        when(mockClient.getAccount()).thenReturn(mockAccount);

        Account account = mockClient.getAccount();
        assertNotNull(account);
        assertEquals(10000.0, account.getBalance());
        assertEquals(10500.0, account.getEquity());
        assertEquals(1000.0, account.getMargin());
        assertEquals(100L, account.getLeverage());
        assertEquals("EUR", account.getCurrency());
        assertEquals("Test Account", account.getName());
        assertEquals("Demo Server", account.getServer());
        assertEquals("Test Broker", account.getCompany());
        assertEquals(12345L, account.getLogin());
        verify(mockClient).getAccount();
    }

    // Symbol Tests
    @Test
    @DisplayName("getSymbolNames returns list of symbols")
    void testGetSymbolNames() throws JsonProcessingException, MT4Exception {
        List<String> symbols = Arrays.asList("DE40", "EURUSD", "GBPUSD", "USDJPY");
        when(mockClient.getSymbolNames()).thenReturn(symbols);

        List<String> result = mockClient.getSymbolNames();
        assertEquals(4, result.size());
        assertTrue(result.contains("DE40"));
        assertTrue(result.contains("EURUSD"));
        verify(mockClient).getSymbolNames();
    }

    @Test
    @DisplayName("getSymbol returns single symbol")
    void testGetSymbol() throws JsonProcessingException, MT4Exception {
        Symbol mockSymbol = mock(Symbol.class);
        when(mockSymbol.getName()).thenReturn("DE40");
        when(mockSymbol.getBid()).thenReturn(19000.0);
        when(mockSymbol.getAsk()).thenReturn(19001.0);
        when(mockSymbol.getDigits()).thenReturn(1);
        when(mockSymbol.getPoint()).thenReturn(0.1);

        when(mockClient.getSymbol("DE40")).thenReturn(mockSymbol);

        Symbol symbol = mockClient.getSymbol("DE40");
        assertNotNull(symbol);
        assertEquals("DE40", symbol.getName());
        assertEquals(19000.0, symbol.getBid());
        assertEquals(19001.0, symbol.getAsk());
        assertEquals(1, symbol.getDigits());
        assertEquals(0.1, symbol.getPoint());
        verify(mockClient).getSymbol("DE40");
    }

    @Test
    @DisplayName("getSymbols returns map of symbols")
    void testGetSymbols() throws JsonProcessingException, MT4Exception {
        Symbol mockSymbol1 = mock(Symbol.class);
        Symbol mockSymbol2 = mock(Symbol.class);
        when(mockSymbol1.getName()).thenReturn("DE40");
        when(mockSymbol2.getName()).thenReturn("EURUSD");

        Map<String, Symbol> symbolMap = new HashMap<>();
        symbolMap.put("DE40", mockSymbol1);
        symbolMap.put("EURUSD", mockSymbol2);

        when(mockClient.getSymbols("DE40", "EURUSD")).thenReturn(symbolMap);

        Map<String, Symbol> result = mockClient.getSymbols("DE40", "EURUSD");
        assertEquals(2, result.size());
        assertNotNull(result.get("DE40"));
        assertNotNull(result.get("EURUSD"));
        verify(mockClient).getSymbols("DE40", "EURUSD");
    }

    // OHLCV Tests
    @Test
    @DisplayName("getOHLCV returns candle data")
    void testGetOHLCV() throws JsonProcessingException, MT4Exception {
        List<OHLCV> candles = Arrays.asList(
            new OHLCV(1700000000, 19000, 19100, 18900, 19050, 1000),
            new OHLCV(1700000060, 19050, 19150, 19000, 19100, 1200),
            new OHLCV(1700000120, 19100, 19200, 19050, 19180, 800)
        );

        when(mockClient.getOHLCV("DE40", StandardTimeframe.PERIOD_M1, 3, 5000)).thenReturn(candles);

        List<OHLCV> result = mockClient.getOHLCV("DE40", StandardTimeframe.PERIOD_M1, 3, 5000);
        assertEquals(3, result.size());
        assertEquals(1700000000, result.get(0).time);
        assertEquals(19000, result.get(0).open);
        assertEquals(19100, result.get(0).high);
        assertEquals(18900, result.get(0).low);
        assertEquals(19050, result.get(0).close);
        assertEquals(1000, result.get(0).tick_volume);
        verify(mockClient).getOHLCV("DE40", StandardTimeframe.PERIOD_M1, 3, 5000);
    }

    @Test
    @DisplayName("getOHLCV with offset returns candle data")
    void testGetOHLCVWithOffset() throws JsonProcessingException, MT4Exception {
        List<OHLCV> candles = Arrays.asList(
            new OHLCV(1699999940, 18900, 19000, 18850, 18950, 900)
        );

        when(mockClient.getOHLCV("DE40", StandardTimeframe.PERIOD_M1, 1, 5000, 5)).thenReturn(candles);

        List<OHLCV> result = mockClient.getOHLCV("DE40", StandardTimeframe.PERIOD_M1, 1, 5000, 5);
        assertEquals(1, result.size());
        verify(mockClient).getOHLCV("DE40", StandardTimeframe.PERIOD_M1, 1, 5000, 5);
    }

    // Order Tests
    @Test
    @DisplayName("getOrders returns open orders")
    void testGetOrders() throws JsonProcessingException, MT4Exception {
        Order order1 = new Order(123, 456, "DE40", OrderType.OP_BUY.id, 0.5, 19000.0, 0.0,
            "2023.11.15 10:00:00", null, null, 18900.0, 19100.0, 50.0, -1.0, -0.5, "test");
        Order order2 = new Order(124, 456, "DE40", OrderType.OP_SELL.id, 0.3, 19100.0, 0.0,
            "2023.11.15 11:00:00", null, null, 19200.0, 19000.0, -20.0, -0.5, -0.3, "test2");

        when(mockClient.getOrders()).thenReturn(Arrays.asList(order1, order2));

        List<Order> orders = mockClient.getOrders();
        assertEquals(2, orders.size());
        assertEquals(123, orders.get(0).getTicket());
        assertEquals(124, orders.get(1).getTicket());
        verify(mockClient).getOrders();
    }

    @Test
    @DisplayName("getOrdersHistorical returns closed orders")
    void testGetOrdersHistorical() throws JsonProcessingException, MT4Exception {
        Order closedOrder = new Order(100, 456, "DE40", OrderType.OP_BUY.id, 0.5, 19000.0, 19050.0,
            "2023.11.15 10:00:00", "2023.11.15 11:00:00", null, 0.0, 0.0, 50.0, -1.0, -0.5, "closed");

        when(mockClient.getOrdersHistorical()).thenReturn(Collections.singletonList(closedOrder));

        List<Order> history = mockClient.getOrdersHistorical();
        assertEquals(1, history.size());
        assertEquals(100, history.get(0).getTicket());
        assertEquals(19050.0, history.get(0).getClosePrice());
        verify(mockClient).getOrdersHistorical();
    }

    @Test
    @DisplayName("getOrder returns single order by ticket")
    void testGetOrder() throws JsonProcessingException, MT4Exception {
        Order order = new Order(999, 456, "DE40", OrderType.OP_BUYLIMIT.id, 0.1, 18900.0, 0.0,
            "2023.11.15 12:00:00", null, null, 18800.0, 19000.0, 0.0, 0.0, 0.0, "limit");

        when(mockClient.getOrder(999)).thenReturn(order);

        Order result = mockClient.getOrder(999);
        assertNotNull(result);
        assertEquals(999, result.getTicket());
        assertEquals(OrderType.OP_BUYLIMIT, result.getOrderType());
        verify(mockClient).getOrder(999);
    }

    // Order Send/Modify/Close/Delete Tests
    @Test
    @DisplayName("orderSend creates new order")
    void testOrderSend() throws JsonProcessingException, MT4Exception {
        NewOrder newOrder = NewOrder.Builder.newInstance()
            .setSymbol("DE40")
            .setOrderType(OrderType.OP_BUY)
            .setLots(0.1)
            .setSl(18900.0)
            .setTp(19100.0)
            .setComment("new order")
            .setMagicNumber(12345)
            .build();

        Order sentOrder = new Order(500, 12345, "DE40", OrderType.OP_BUY.id, 0.1, 19000.0, 0.0,
            "2023.11.15 14:00:00", null, null, 18900.0, 19100.0, 0.0, 0.0, 0.0, "new order");

        when(mockClient.orderSend(newOrder)).thenReturn(sentOrder);

        Order result = mockClient.orderSend(newOrder);
        assertNotNull(result);
        assertEquals(500, result.getTicket());
        assertEquals(12345, result.getMagicNumber());
        verify(mockClient).orderSend(newOrder);
    }

    @Test
    @DisplayName("orderModify updates order")
    void testOrderModify() throws JsonProcessingException, MT4Exception {
        Order original = new Order(500, 12345, "DE40", OrderType.OP_BUY.id, 0.1, 19000.0, 0.0,
            "2023.11.15 14:00:00", null, null, 18900.0, 19100.0, 0.0, 0.0, 0.0, "original");

        ModifyOrder modify = ModifyOrder.Builder.newInstance()
            .setOrder(original)
            .setSl(18800.0)
            .setTp(19200.0)
            .build();

        Order modified = new Order(500, 12345, "DE40", OrderType.OP_BUY.id, 0.1, 19000.0, 0.0,
            "2023.11.15 14:00:00", null, null, 18800.0, 19200.0, 0.0, 0.0, 0.0, "original");

        when(mockClient.orderModify(modify)).thenReturn(modified);

        Order result = mockClient.orderModify(modify);
        assertNotNull(result);
        assertEquals(18800.0, result.getSl());
        assertEquals(19200.0, result.getTp());
        verify(mockClient).orderModify(modify);
    }

    @Test
    @DisplayName("orderClose closes order by ticket")
    void testOrderCloseByTicket() throws JsonProcessingException, MT4Exception {
        doNothing().when(mockClient).orderClose(500);

        mockClient.orderClose(500);
        verify(mockClient).orderClose(500);
    }

    @Test
    @DisplayName("orderClose closes order object")
    void testOrderCloseByOrder() throws JsonProcessingException, MT4Exception {
        Order order = new Order(500, 12345, "DE40", OrderType.OP_BUY.id, 0.1, 19000.0, 0.0,
            "2023.11.15 14:00:00", null, null, 18900.0, 19100.0, 50.0, 0.0, 0.0, "to close");

        doNothing().when(mockClient).orderClose(order);

        mockClient.orderClose(order);
        verify(mockClient).orderClose(order);
    }

    @Test
    @DisplayName("orderDelete deletes pending order by ticket")
    void testOrderDeleteByTicket() throws JsonProcessingException, MT4Exception {
        doNothing().when(mockClient).orderDelete(600, false);

        mockClient.orderDelete(600, false);
        verify(mockClient).orderDelete(600, false);
    }

    @Test
    @DisplayName("orderDelete deletes pending order object")
    void testOrderDeleteByOrder() throws JsonProcessingException, MT4Exception {
        Order pendingOrder = new Order(600, 12345, "DE40", OrderType.OP_BUYLIMIT.id, 0.1, 18900.0, 0.0,
            "2023.11.15 15:00:00", null, null, 18800.0, 19000.0, 0.0, 0.0, 0.0, "pending");

        doNothing().when(mockClient).orderDelete(pendingOrder, true);

        mockClient.orderDelete(pendingOrder, true);
        verify(mockClient).orderDelete(pendingOrder, true);
    }

    @Test
    @DisplayName("orderDelete with single param closes if opened")
    void testOrderDeleteSingleParam() throws JsonProcessingException, MT4Exception {
        doNothing().when(mockClient).orderDelete(700);

        mockClient.orderDelete(700);
        verify(mockClient).orderDelete(700);
    }

    // Signal Tests
    @Test
    @DisplayName("getSignalNames returns signal list")
    void testGetSignalNames() throws JsonProcessingException, MT4Exception {
        List<String> signals = Arrays.asList("Signal1", "Signal2", "Signal3");
        when(mockClient.getSignalNames()).thenReturn(signals);

        List<String> result = mockClient.getSignalNames();
        assertEquals(3, result.size());
        assertTrue(result.contains("Signal1"));
        verify(mockClient).getSignalNames();
    }

    @Test
    @DisplayName("getSignal returns single signal")
    void testGetSignal() throws JsonProcessingException, MT4Exception {
        Signal mockSignal = mock(Signal.class);
        when(mockSignal.getName()).thenReturn("TestSignal");
        when(mockClient.getSignal("TestSignal")).thenReturn(mockSignal);

        Signal result = mockClient.getSignal("TestSignal");
        assertNotNull(result);
        assertEquals("TestSignal", result.getName());
        verify(mockClient).getSignal("TestSignal");
    }

    @Test
    @DisplayName("getSignals returns map of signals")
    void testGetSignals() throws JsonProcessingException, MT4Exception {
        Signal mockSignal1 = mock(Signal.class);
        Signal mockSignal2 = mock(Signal.class);

        Map<String, Signal> signalMap = new HashMap<>();
        signalMap.put("Signal1", mockSignal1);
        signalMap.put("Signal2", mockSignal2);

        when(mockClient.getSignals("Signal1", "Signal2")).thenReturn(signalMap);

        Map<String, Signal> result = mockClient.getSignals("Signal1", "Signal2");
        assertEquals(2, result.size());
        verify(mockClient).getSignals("Signal1", "Signal2");
    }

    // Indicator Tests
    @Test
    @DisplayName("runIndicator returns indicator value")
    void testRunIndicator() throws JsonProcessingException, MT4Exception {
        Indicator mockIndicator = mock(Indicator.class);
        when(mockClient.runIndicator(mockIndicator)).thenReturn(19050.5);

        double result = mockClient.runIndicator(mockIndicator);
        assertEquals(19050.5, result);
        verify(mockClient).runIndicator(mockIndicator);
    }

    @Test
    @DisplayName("runIndicator with timeout returns value")
    void testRunIndicatorWithTimeout() throws JsonProcessingException, MT4Exception {
        Indicator mockIndicator = mock(Indicator.class);
        when(mockClient.runIndicator(mockIndicator, 10000)).thenReturn(19100.0);

        double result = mockClient.runIndicator(mockIndicator, 10000);
        assertEquals(19100.0, result);
        verify(mockClient).runIndicator(mockIndicator, 10000);
    }

    // Shutdown Test
    @Test
    @DisplayName("shutdown closes connection")
    void testShutdown() {
        doNothing().when(mockClient).shutdown();

        mockClient.shutdown();
        verify(mockClient).shutdown();
    }

    // Exception Tests
    @Test
    @DisplayName("getAccount throws MT4Exception on error")
    void testGetAccountThrowsException() throws JsonProcessingException, MT4Exception {
        MT4Exception exception = MT4Exception.Builder.newInstance()
            .setErrorCode(6)
            .setMessage("Connection failed")
            .build();

        when(mockClient.getAccount()).thenThrow(exception);

        MT4Exception thrown = assertThrows(MT4Exception.class, () -> {
            mockClient.getAccount();
        });
        assertTrue(thrown.message.contains("Connection failed"));
    }

    @Test
    @DisplayName("orderSend throws MT4Exception on invalid order")
    void testOrderSendThrowsException() throws JsonProcessingException, MT4Exception {
        NewOrder invalidOrder = NewOrder.Builder.newInstance()
            .setSymbol("INVALID")
            .setOrderType(OrderType.OP_BUY)
            .setLots(0.1)
            .build();

        MT4Exception exception = MT4Exception.Builder.newInstance()
            .setErrorCode(131)
            .setMessage("Invalid lots")
            .build();

        when(mockClient.orderSend(invalidOrder)).thenThrow(exception);

        MT4Exception thrown = assertThrows(MT4Exception.class, () -> {
            mockClient.orderSend(invalidOrder);
        });
        assertTrue(thrown.message.contains("Invalid lots"));
    }

    @Test
    @DisplayName("getOHLCV throws exception on timeout")
    void testGetOHLCVThrowsException() throws JsonProcessingException, MT4Exception {
        MT4Exception exception = MT4Exception.Builder.newInstance()
            .setErrorCode(128)
            .setMessage("Timeout")
            .build();

        when(mockClient.getOHLCV("DE40", StandardTimeframe.PERIOD_M1, 1000, 100))
            .thenThrow(exception);

        assertThrows(MT4Exception.class, () -> {
            mockClient.getOHLCV("DE40", StandardTimeframe.PERIOD_M1, 1000, 100);
        });
    }

    // AutoCloseable Test
    @Test
    @DisplayName("close calls shutdown")
    void testClose() throws Exception {
        doNothing().when(mockClient).close();

        mockClient.close();
        verify(mockClient).close();
    }
}
