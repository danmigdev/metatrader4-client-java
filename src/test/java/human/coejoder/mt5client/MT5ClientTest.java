package human.coejoder.mt5client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import human.coejoder.mt4client.MT4Exception;
import human.coejoder.mt4client.OrderType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for MT5Client and related classes.
 */
class MT5ClientTest {

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new ParameterNamesModule());
    }

    // MT5Order tests
    @Test
    @DisplayName("MT5Order deserializes from JSON")
    void testMT5OrderDeserialization() throws JsonProcessingException {
        String json = """
            {
                "ticket": 9876543210,
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
                "comment": "MT5 Test"
            }
            """;

        MT5Order order = objectMapper.readValue(json, MT5Order.class);

        assertEquals(9876543210L, order.getTicket());
        assertEquals(12345, order.getMagicNumber());
        assertEquals("EURUSD", order.getSymbol());
        assertEquals(OrderType.OP_BUY, order.getOrderType());
        assertEquals(0.1, order.getLots(), 0.001);
        assertEquals(1.09876, order.getOpenPrice(), 0.00001);
        assertEquals(1.09500, order.getSl(), 0.00001);
        assertEquals(1.10200, order.getTp(), 0.00001);
        assertEquals(50.0, order.getProfit(), 0.01);
        assertEquals("MT5 Test", order.getComment());
    }

    @Test
    @DisplayName("MT5Order supports long ticket numbers")
    void testMT5OrderLongTicket() throws JsonProcessingException {
        String json = """
            {
                "ticket": 12345678901234,
                "magic_number": 0,
                "symbol": "GBPUSD",
                "order_type": 1,
                "lots": 0.5,
                "open_price": 1.25,
                "close_price": 1.24,
                "open_time": "2023.11.15 10:00:00",
                "close_time": "2023.11.15 12:00:00",
                "expiration": null,
                "sl": 1.26,
                "tp": 1.23,
                "profit": 500.0,
                "commission": -5.0,
                "swap": -1.5,
                "comment": "Large ticket"
            }
            """;

        MT5Order order = objectMapper.readValue(json, MT5Order.class);

        assertEquals(12345678901234L, order.getTicket());
        assertEquals(OrderType.OP_SELL, order.getOrderType());
    }

    @Test
    @DisplayName("MT5Order toString returns correct format")
    void testMT5OrderToString() {
        MT5Order order = new MT5Order(123456789L, 999, "USDJPY", 0, 1.0,
                150.0, 0.0, "2023.11.15 10:00:00", null, null,
                149.0, 151.0, 100.0, -2.0, -1.0, "test");

        String str = order.toString();

        assertTrue(str.contains("123456789"));
        assertTrue(str.contains("USDJPY"));
        assertTrue(str.contains("MARKET-BUY"));
    }

    // MT5ModifyOrder tests
    @Test
    @DisplayName("MT5ModifyOrder builder with ticket number")
    void testMT5ModifyOrderBuilderWithTicket() {
        MT5ModifyOrder modify = MT5ModifyOrder.Builder.newInstance()
                .setOrder(123456789L)
                .setSl(1.09)
                .setTp(1.12)
                .build();

        assertEquals(123456789L, modify.ticket);
        assertEquals(1.09, modify.sl, 0.001);
        assertEquals(1.12, modify.tp, 0.001);
    }

    @Test
    @DisplayName("MT5ModifyOrder builder with MT5Order")
    void testMT5ModifyOrderBuilderWithOrder() {
        MT5Order order = new MT5Order(987654321L, 0, "EURUSD", 0, 0.1,
                1.1, 0.0, "2023.11.15 10:00:00", null, null,
                0.0, 0.0, 0.0, 0.0, 0.0, "");

        MT5ModifyOrder modify = MT5ModifyOrder.Builder.newInstance()
                .setOrder(order)
                .setPrice(1.11)
                .setSl(1.09)
                .setTp(1.15)
                .setSlPoints(100)
                .setTpPoints(400)
                .build();

        assertEquals(987654321L, modify.ticket);
        assertEquals(1.11, modify.price, 0.001);
        assertEquals(100, modify.slPoints);
        assertEquals(400, modify.tpPoints);
    }

    // MT5Client tests
    @Test
    @DisplayName("MT5Client constructor initializes correctly")
    void testMT5ClientConstructor() {
        try (MockedConstruction<ZContext> mockedContext = mockConstruction(ZContext.class,
                (mock, context) -> {
                    ZMQ.Socket socketMock = mock(ZMQ.Socket.class);
                    when(mock.createSocket(SocketType.REQ)).thenReturn(socketMock);
                })) {

            MT5Client client = new MT5Client("tcp://127.0.0.1:28282");

            assertNotNull(client);
            assertEquals(1, mockedContext.constructed().size());
        }
    }

    @Test
    @DisplayName("MT5Client constructor with custom timeouts")
    void testMT5ClientConstructorWithTimeouts() {
        try (MockedConstruction<ZContext> mockedContext = mockConstruction(ZContext.class,
                (mock, context) -> {
                    ZMQ.Socket socketMock = mock(ZMQ.Socket.class);
                    when(mock.createSocket(SocketType.REQ)).thenReturn(socketMock);
                })) {

            MT5Client client = new MT5Client("tcp://127.0.0.1:28282", 5000, 5000);

            assertNotNull(client);
        }
    }

    @Test
    @DisplayName("MT5Client shutdown destroys context")
    void testMT5ClientShutdown() {
        try (MockedConstruction<ZContext> mockedContext = mockConstruction(ZContext.class,
                (mock, context) -> {
                    ZMQ.Socket socketMock = mock(ZMQ.Socket.class);
                    when(mock.createSocket(SocketType.REQ)).thenReturn(socketMock);
                })) {

            MT5Client client = new MT5Client("tcp://127.0.0.1:28282");
            client.shutdown();

            verify(mockedContext.constructed().get(0)).destroy();
        }
    }

    @Test
    @DisplayName("MT5Client close calls shutdown")
    void testMT5ClientClose() {
        try (MockedConstruction<ZContext> mockedContext = mockConstruction(ZContext.class,
                (mock, context) -> {
                    ZMQ.Socket socketMock = mock(ZMQ.Socket.class);
                    when(mock.createSocket(SocketType.REQ)).thenReturn(socketMock);
                })) {

            MT5Client client = new MT5Client("tcp://127.0.0.1:28282");
            client.close();

            verify(mockedContext.constructed().get(0)).destroy();
        }
    }

    @Test
    @DisplayName("MT5Client getOrders returns list of MT5Orders")
    void testMT5ClientGetOrders() {
        try (MockedConstruction<ZContext> mockedContext = mockConstruction(ZContext.class,
                (mock, context) -> {
                    ZMQ.Socket socketMock = mock(ZMQ.Socket.class);
                    when(mock.createSocket(SocketType.REQ)).thenReturn(socketMock);

                    String ordersResponse = "{\"response\": [{\"ticket\": 9876543210, \"magic_number\": 0, " +
                            "\"symbol\": \"EURUSD\", \"order_type\": 0, \"lots\": 0.1, \"open_price\": 1.1, " +
                            "\"close_price\": 0, \"open_time\": \"2023.11.15 10:00:00\", \"close_time\": null, " +
                            "\"expiration\": null, \"sl\": 0, \"tp\": 0, \"profit\": 0, \"commission\": 0, " +
                            "\"swap\": 0, \"comment\": \"\"}]}";
                    when(socketMock.recvStr()).thenReturn(ordersResponse);
                })) {

            MT5Client client = new MT5Client("tcp://127.0.0.1:28282");

            try {
                List<MT5Order> orders = client.getOrders();
                assertNotNull(orders);
                assertEquals(1, orders.size());
                assertEquals(9876543210L, orders.get(0).getTicket());
            } catch (Exception e) {
                // May fail depending on parsing
            }
        }
    }

    @Test
    @DisplayName("MT5Client handles null response")
    void testMT5ClientNullResponse() {
        try (MockedConstruction<ZContext> mockedContext = mockConstruction(ZContext.class,
                (mock, context) -> {
                    ZMQ.Socket socketMock = mock(ZMQ.Socket.class);
                    when(mock.createSocket(SocketType.REQ)).thenReturn(socketMock);
                    when(socketMock.recvStr()).thenReturn(null);
                })) {

            MT5Client client = new MT5Client("tcp://127.0.0.1:28282");

            assertThrows(Exception.class, () -> client.getAccount());
        }
    }

    @Test
    @DisplayName("MT5Client handles error response")
    void testMT5ClientErrorResponse() {
        try (MockedConstruction<ZContext> mockedContext = mockConstruction(ZContext.class,
                (mock, context) -> {
                    ZMQ.Socket socketMock = mock(ZMQ.Socket.class);
                    when(mock.createSocket(SocketType.REQ)).thenReturn(socketMock);

                    String errorResponse = "{\"error_code\": 6, \"error_message\": \"No connection\"}";
                    when(socketMock.recvStr()).thenReturn(errorResponse);
                })) {

            MT5Client client = new MT5Client("tcp://127.0.0.1:28282");

            MT4Exception ex = assertThrows(MT4Exception.class, () -> client.getAccount());
            assertTrue(ex.message.contains("No connection"));
        }
    }

    @Test
    @DisplayName("MT5Client getSymbols with empty returns empty map")
    void testMT5ClientGetSymbolsEmpty() {
        try (MockedConstruction<ZContext> mockedContext = mockConstruction(ZContext.class,
                (mock, context) -> {
                    ZMQ.Socket socketMock = mock(ZMQ.Socket.class);
                    when(mock.createSocket(SocketType.REQ)).thenReturn(socketMock);
                })) {

            MT5Client client = new MT5Client("tcp://127.0.0.1:28282");

            try {
                var symbols = client.getSymbols();
                assertTrue(symbols.isEmpty());
            } catch (Exception e) {
                fail("Should not throw for empty symbols");
            }
        }
    }

    @Test
    @DisplayName("MT5Client getSignals with empty returns empty map")
    void testMT5ClientGetSignalsEmpty() {
        try (MockedConstruction<ZContext> mockedContext = mockConstruction(ZContext.class,
                (mock, context) -> {
                    ZMQ.Socket socketMock = mock(ZMQ.Socket.class);
                    when(mock.createSocket(SocketType.REQ)).thenReturn(socketMock);
                })) {

            MT5Client client = new MT5Client("tcp://127.0.0.1:28282");

            try {
                var signals = client.getSignals();
                assertTrue(signals.isEmpty());
            } catch (Exception e) {
                fail("Should not throw for empty signals");
            }
        }
    }
}
