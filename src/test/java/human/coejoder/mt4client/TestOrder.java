package human.coejoder.mt4client;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for order operations using mock objects.
 * These tests do not require a live MT4 connection.
 */
public class TestOrder {

    private static final Logger LOG = LoggerFactory.getLogger(TestOrder.class);
    private static final double MIN_LOTS = 0.01;
    private static final String TEST_SYMBOL = "EURUSD";
    private static final double TEST_BID = 1.10000;
    private static final double TEST_ASK = 1.10010;
    private static final double TEST_POINT = 0.00001;

    private MT4ClientInterface mockClient;
    private Symbol mockSymbol;
    private SymbolTick mockTick;

    @BeforeEach
    public void setUp() throws JsonProcessingException, MT4Exception {
        mockClient = mock(MT4ClientInterface.class);
        mockSymbol = mock(Symbol.class);
        mockTick = new SymbolTick(1700000000, TEST_BID, TEST_ASK, 0.0, 0);

        // Setup symbol mock
        when(mockSymbol.getName()).thenReturn(TEST_SYMBOL);
        when(mockSymbol.getTick()).thenReturn(mockTick);
        when(mockSymbol.getPoint()).thenReturn(TEST_POINT);
        when(mockSymbol.getVolumeMin()).thenReturn(MIN_LOTS);
        when(mockSymbol.getDigits()).thenReturn(5);

        when(mockClient.getSymbol(TEST_SYMBOL)).thenReturn(mockSymbol);
        when(mockClient.getSymbolNames()).thenReturn(Collections.singletonList(TEST_SYMBOL));
    }

    @Test
    @DisplayName("Market buy order creates successfully")
    public void testMarketBuy() throws JsonProcessingException, MT4Exception {
        OrderType orderType = OrderType.OP_BUY;
        int points = 50;
        double sl = TEST_BID - points * TEST_POINT;
        double tp = TEST_BID + points * TEST_POINT;

        Order expectedOrder = new Order(12345, 0, TEST_SYMBOL, orderType.id, MIN_LOTS,
                TEST_ASK, 0.0, "2023.11.15 10:00:00", null, null, sl, tp, 0.0, 0.0, 0.0, "");

        NewOrder newOrder = NewOrder.Builder.newInstance()
                .setSymbol(mockSymbol)
                .setOrderType(orderType)
                .setLots(MIN_LOTS)
                .setSlPoints(points)
                .setTpPoints(points)
                .build();

        when(mockClient.orderSend(any(NewOrder.class))).thenReturn(expectedOrder);

        Order order = mockClient.orderSend(newOrder);

        LOG.trace("New market buy: {}", order);
        assertEquals(orderType, order.getOrderType());
        assertTrue(order.getSl() < TEST_BID);
        assertTrue(order.getTp() > TEST_BID);
        verify(mockClient).orderSend(any(NewOrder.class));
    }

    @Test
    @DisplayName("Market sell order creates successfully")
    public void testMarketSell() throws JsonProcessingException, MT4Exception {
        OrderType orderType = OrderType.OP_SELL;
        int points = 100;
        double sl = TEST_BID + points * TEST_POINT;
        double tp = TEST_BID - points * TEST_POINT;

        Order expectedOrder = new Order(12346, 0, TEST_SYMBOL, orderType.id, MIN_LOTS,
                TEST_BID, 0.0, "2023.11.15 10:00:00", null, null, sl, tp, 0.0, 0.0, 0.0, "");

        NewOrder newOrder = NewOrder.Builder.newInstance()
                .setSymbol(mockSymbol)
                .setOrderType(orderType)
                .setLots(MIN_LOTS)
                .setSl(sl)
                .setTp(tp)
                .build();

        when(mockClient.orderSend(any(NewOrder.class))).thenReturn(expectedOrder);

        Order order = mockClient.orderSend(newOrder);

        LOG.trace("New market sell: {}", order);
        assertEquals(orderType, order.getOrderType());
        assertTrue(order.getSl() > TEST_BID);
        assertTrue(order.getTp() < TEST_BID);
        verify(mockClient).orderSend(any(NewOrder.class));
    }

    @Test
    @DisplayName("Limit buy order creates successfully")
    public void testLimitBuy() throws JsonProcessingException, MT4Exception {
        OrderType orderType = OrderType.OP_BUYLIMIT;
        double optimisticBuyPrice = TEST_ASK / 2;
        int slPoints = 100;
        int tpPoints = 100;

        Order expectedOrder = new Order(12347, 0, TEST_SYMBOL, orderType.id, MIN_LOTS,
                optimisticBuyPrice, 0.0, "2023.11.15 10:00:00", null, null,
                optimisticBuyPrice - slPoints * TEST_POINT, optimisticBuyPrice + tpPoints * TEST_POINT, 0.0, 0.0, 0.0, "");

        NewOrder newOrder = NewOrder.Builder.newInstance()
                .setSymbol(mockSymbol)
                .setOrderType(orderType)
                .setLots(MIN_LOTS)
                .setPrice(optimisticBuyPrice)
                .setSlippage(1)
                .setSlPoints(slPoints)
                .setTpPoints(tpPoints)
                .build();

        when(mockClient.orderSend(any(NewOrder.class))).thenReturn(expectedOrder);

        Order order = mockClient.orderSend(newOrder);

        LOG.trace("New pending buy order: {}", order);
        assertEquals(orderType, order.getOrderType());
        verify(mockClient).orderSend(any(NewOrder.class));
    }

    @Test
    @DisplayName("Limit sell order creates successfully")
    public void testLimitSell() throws JsonProcessingException, MT4Exception {
        OrderType orderType = OrderType.OP_SELLLIMIT;
        double optimisticSellPrice = TEST_BID * 2;

        Order expectedOrder = new Order(12348, 0, TEST_SYMBOL, orderType.id, MIN_LOTS,
                optimisticSellPrice, 0.0, "2023.11.15 10:00:00", null, null, 0.0, 0.0, 0.0, 0.0, 0.0, "");

        NewOrder newOrder = NewOrder.Builder.newInstance()
                .setSymbol(mockSymbol)
                .setOrderType(orderType)
                .setLots(MIN_LOTS)
                .setPrice(optimisticSellPrice)
                .setSlippage(1)
                .build();

        when(mockClient.orderSend(any(NewOrder.class))).thenReturn(expectedOrder);

        Order order = mockClient.orderSend(newOrder);

        LOG.trace("New pending sell order: {}", order);
        assertEquals(orderType, order.getOrderType());
        verify(mockClient).orderSend(any(NewOrder.class));
    }

    @Test
    @DisplayName("Modify open order updates SL/TP")
    public void testModifyOpenOrder() throws JsonProcessingException, MT4Exception {
        OrderType orderType = OrderType.OP_BUY;
        int points = 200;
        double newSl = TEST_BID - points * TEST_POINT;
        double newTp = TEST_BID + points * TEST_POINT;

        Order originalOrder = new Order(12349, 0, TEST_SYMBOL, orderType.id, MIN_LOTS,
                TEST_ASK, 0.0, "2023.11.15 10:00:00", null, null, 0.0, 0.0, 0.0, 0.0, 0.0, "");

        Order modifiedOrder = new Order(12349, 0, TEST_SYMBOL, orderType.id, MIN_LOTS,
                TEST_ASK, 0.0, "2023.11.15 10:00:00", null, null, newSl, newTp, 0.0, 0.0, 0.0, "");

        ModifyOrder modifyOrder = ModifyOrder.Builder.newInstance()
                .setOrder(originalOrder)
                .setSl(newSl)
                .setTp(newTp)
                .build();

        when(mockClient.orderSend(any(NewOrder.class))).thenReturn(originalOrder);
        when(mockClient.orderModify(any(ModifyOrder.class))).thenReturn(modifiedOrder);

        Order order = mockClient.orderModify(modifyOrder);

        assertTrue(order.getSl() < TEST_BID);
        assertTrue(order.getTp() > TEST_BID);
        verify(mockClient).orderModify(any(ModifyOrder.class));
    }

    @Test
    @DisplayName("Close open order succeeds")
    public void testCloseOpenOrder() throws JsonProcessingException, MT4Exception {
        Order order = new Order(12350, 0, TEST_SYMBOL, OrderType.OP_BUY.id, MIN_LOTS,
                TEST_ASK, 0.0, "2023.11.15 10:00:00", null, null, 0.0, 0.0, 50.0, 0.0, 0.0, "");

        doNothing().when(mockClient).orderClose(order);

        mockClient.orderClose(order);

        verify(mockClient).orderClose(order);
    }

    @Test
    @DisplayName("Delete pending order succeeds")
    public void testDeletePendingOrder() throws JsonProcessingException, MT4Exception {
        Order pendingOrder = new Order(12351, 0, TEST_SYMBOL, OrderType.OP_BUYLIMIT.id, MIN_LOTS,
                TEST_ASK / 2, 0.0, "2023.11.15 10:00:00", null, null, 0.0, 0.0, 0.0, 0.0, 0.0, "");

        doNothing().when(mockClient).orderDelete(pendingOrder);
        when(mockClient.getOrder(12351)).thenReturn(null);

        mockClient.orderDelete(pendingOrder);
        Order deletedOrder = mockClient.getOrder(12351);

        assertNull(deletedOrder);
        verify(mockClient).orderDelete(pendingOrder);
    }

    @Test
    @DisplayName("getOrders returns list of open orders")
    public void testOrders() throws JsonProcessingException, MT4Exception {
        Order order1 = new Order(12352, 0, TEST_SYMBOL, OrderType.OP_BUY.id, MIN_LOTS,
                TEST_ASK, 0.0, "2023.11.15 10:00:00", null, null, 0.0, 0.0, 50.0, 0.0, 0.0, "");
        Order order2 = new Order(12353, 0, TEST_SYMBOL, OrderType.OP_SELL.id, MIN_LOTS,
                TEST_BID, 0.0, "2023.11.15 11:00:00", null, null, 0.0, 0.0, -20.0, 0.0, 0.0, "");

        when(mockClient.getOrders()).thenReturn(Arrays.asList(order1, order2));

        List<Order> orders = mockClient.getOrders();

        assertEquals(2, orders.size());
        verify(mockClient).getOrders();
    }

    @Test
    @DisplayName("getOrdersHistorical returns list of closed orders")
    public void testOrdersHistorical() throws JsonProcessingException, MT4Exception {
        Order closedOrder = new Order(12354, 0, TEST_SYMBOL, OrderType.OP_BUY.id, MIN_LOTS,
                TEST_ASK, TEST_ASK + 0.001, "2023.11.15 10:00:00", "2023.11.15 12:00:00", null, 0.0, 0.0, 100.0, -1.0, -0.5, "");

        when(mockClient.getOrdersHistorical()).thenReturn(Collections.singletonList(closedOrder));

        List<Order> history = mockClient.getOrdersHistorical();

        assertEquals(1, history.size());
        assertNotNull(history.get(0).getCloseTime());
        verify(mockClient).getOrdersHistorical();
    }

    @Test
    @DisplayName("Close all orders succeeds")
    public void testCloseAllOrders() throws JsonProcessingException, MT4Exception {
        Order order1 = new Order(12355, 0, TEST_SYMBOL, OrderType.OP_BUY.id, MIN_LOTS,
                TEST_ASK, 0.0, "2023.11.15 10:00:00", null, null, 0.0, 0.0, 50.0, 0.0, 0.0, "");
        Order order2 = new Order(12356, 0, TEST_SYMBOL, OrderType.OP_BUYLIMIT.id, MIN_LOTS,
                TEST_ASK / 2, 0.0, "2023.11.15 11:00:00", null, null, 0.0, 0.0, 0.0, 0.0, 0.0, "");

        when(mockClient.getOrders())
                .thenReturn(Arrays.asList(order1, order2))
                .thenReturn(Collections.emptyList());
        doNothing().when(mockClient).orderDelete(any(Order.class), eq(true));

        List<Order> orders = mockClient.getOrders();
        for (Order order : orders) {
            mockClient.orderDelete(order, true);
        }

        assertEquals(0, mockClient.getOrders().size());
        verify(mockClient, times(2)).orderDelete(any(Order.class), eq(true));
    }

    @Test
    @DisplayName("orderSend throws exception on invalid order")
    public void testOrderSendThrowsException() throws JsonProcessingException, MT4Exception {
        NewOrder invalidOrder = NewOrder.Builder.newInstance()
                .setSymbol("INVALID")
                .setOrderType(OrderType.OP_BUY)
                .setLots(MIN_LOTS)
                .build();

        MT4Exception exception = MT4Exception.Builder.newInstance()
                .setErrorCode(MT4Exception.Code.ERR_INVALID_TRADE_VOLUME.id)
                .setMessage("Invalid trade volume")
                .build();

        when(mockClient.orderSend(invalidOrder)).thenThrow(exception);

        MT4Exception thrown = assertThrows(MT4Exception.class, () -> mockClient.orderSend(invalidOrder));
        assertEquals(MT4Exception.Code.ERR_INVALID_TRADE_VOLUME, thrown.errorCode);
    }

    @Test
    @DisplayName("Order with magic number creates successfully")
    public void testOrderWithMagicNumber() throws JsonProcessingException, MT4Exception {
        int magicNumber = 12345;
        Order expectedOrder = new Order(12357, magicNumber, TEST_SYMBOL, OrderType.OP_BUY.id, MIN_LOTS,
                TEST_ASK, 0.0, "2023.11.15 10:00:00", null, null, 0.0, 0.0, 0.0, 0.0, 0.0, "magic order");

        NewOrder newOrder = NewOrder.Builder.newInstance()
                .setSymbol(mockSymbol)
                .setOrderType(OrderType.OP_BUY)
                .setLots(MIN_LOTS)
                .setMagicNumber(magicNumber)
                .setComment("magic order")
                .build();

        when(mockClient.orderSend(any(NewOrder.class))).thenReturn(expectedOrder);

        Order order = mockClient.orderSend(newOrder);

        assertEquals(magicNumber, order.getMagicNumber());
        assertEquals("magic order", order.getComment());
        verify(mockClient).orderSend(any(NewOrder.class));
    }
}
