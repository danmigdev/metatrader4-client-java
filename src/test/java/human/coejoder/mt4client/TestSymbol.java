package human.coejoder.mt4client;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link Symbol} using mock objects.
 * These tests do not require a live MT4 connection.
 */
public class TestSymbol {

    private static final Logger LOG = LoggerFactory.getLogger(TestSymbol.class);
    private static final String TEST_SYMBOL_NAME = "EURUSD";
    private static final double TEST_POINT = 0.00001;
    private static final int TEST_DIGITS = 5;
    private static final double TEST_BID = 1.10000;
    private static final double TEST_ASK = 1.10010;
    private static final double TEST_VOLUME_MIN = 0.01;
    private static final double TEST_VOLUME_MAX = 100.0;
    private static final double TEST_VOLUME_STEP = 0.01;

    private MT4ClientInterface mockClient;
    private Symbol mockSymbol;

    @BeforeEach
    public void setUp() throws JsonProcessingException, MT4Exception {
        mockClient = mock(MT4ClientInterface.class);
        mockSymbol = mock(Symbol.class);

        // Setup default mock behavior for symbol
        when(mockSymbol.getName()).thenReturn(TEST_SYMBOL_NAME);
        when(mockSymbol.getPoint()).thenReturn(TEST_POINT);
        when(mockSymbol.getDigits()).thenReturn(TEST_DIGITS);
        when(mockSymbol.getVolumeMin()).thenReturn(TEST_VOLUME_MIN);
        when(mockSymbol.getVolumeMax()).thenReturn(TEST_VOLUME_MAX);
        when(mockSymbol.getVolumeStep()).thenReturn(TEST_VOLUME_STEP);
        when(mockSymbol.getBid()).thenReturn(TEST_BID);
        when(mockSymbol.getAsk()).thenReturn(TEST_ASK);

        when(mockClient.getSymbol(TEST_SYMBOL_NAME)).thenReturn(mockSymbol);
    }

    @Test
    @DisplayName("getSymbolNames returns list of symbol names")
    public void testGetSymbolNames() throws JsonProcessingException, MT4Exception {
        List<String> symbolNames = Arrays.asList("EURUSD", "GBPUSD", "USDJPY", "AUDUSD", "USDCAD");
        when(mockClient.getSymbolNames()).thenReturn(symbolNames);

        List<String> result = mockClient.getSymbolNames();

        LOG.trace("Symbol names: {}", String.join(", ", result));
        assertEquals(5, result.size());
        assertTrue(result.contains("EURUSD"));
        assertTrue(result.contains("GBPUSD"));
        verify(mockClient).getSymbolNames();
    }

    @Test
    @DisplayName("getSymbol returns symbol with correct properties")
    public void testGetSymbol() throws JsonProcessingException, MT4Exception {
        Symbol symbol = mockClient.getSymbol(TEST_SYMBOL_NAME);

        LOG.trace("Symbol: {}", symbol.getName());
        assertNotNull(symbol);
        assertEquals(TEST_SYMBOL_NAME, symbol.getName());
        assertEquals(TEST_POINT, symbol.getPoint());
        assertEquals(TEST_DIGITS, symbol.getDigits());
        verify(mockClient).getSymbol(TEST_SYMBOL_NAME);
    }

    @Test
    @DisplayName("getSymbolTick returns current prices")
    public void testGetSymbolTick() throws JsonProcessingException, MT4Exception {
        SymbolTick tick = new SymbolTick(1700000000, TEST_BID, TEST_ASK, 0.0, 1000);
        when(mockSymbol.getTick()).thenReturn(tick);

        Symbol symbol = mockClient.getSymbol(TEST_SYMBOL_NAME);
        SymbolTick result = symbol.getTick();

        LOG.trace("Symbol tick: {}", result);
        assertNotNull(result);
        assertEquals(TEST_BID, result.getBid());
        assertEquals(TEST_ASK, result.getAsk());
        verify(mockSymbol).getTick();
    }

    @Test
    @DisplayName("getSymbols returns map of symbols")
    public void testGetSymbols() throws JsonProcessingException, MT4Exception {
        Symbol mockSymbol2 = mock(Symbol.class);
        when(mockSymbol2.getName()).thenReturn("GBPUSD");

        Map<String, Symbol> symbolMap = new HashMap<>();
        symbolMap.put("EURUSD", mockSymbol);
        symbolMap.put("GBPUSD", mockSymbol2);

        when(mockClient.getSymbols("EURUSD", "GBPUSD")).thenReturn(symbolMap);

        Map<String, Symbol> result = mockClient.getSymbols("EURUSD", "GBPUSD");

        assertEquals(2, result.size());
        assertNotNull(result.get("EURUSD"));
        assertNotNull(result.get("GBPUSD"));
        verify(mockClient).getSymbols("EURUSD", "GBPUSD");
    }

    @Test
    @DisplayName("Symbol isSelected returns correct value")
    public void testSymbolIsSelected() throws JsonProcessingException, MT4Exception {
        when(mockSymbol.isSelected()).thenReturn(true);

        Symbol symbol = mockClient.getSymbol(TEST_SYMBOL_NAME);
        boolean isSelected = symbol.isSelected();

        assertTrue(isSelected);
        verify(mockSymbol).isSelected();
    }

    @Test
    @DisplayName("Symbol isVisible returns correct value")
    public void testSymbolIsVisible() throws JsonProcessingException, MT4Exception {
        when(mockSymbol.isVisible()).thenReturn(true);

        Symbol symbol = mockClient.getSymbol(TEST_SYMBOL_NAME);
        boolean isVisible = symbol.isVisible();

        assertTrue(isVisible);
        verify(mockSymbol).isVisible();
    }

    @Test
    @DisplayName("Symbol spread returns correct value")
    public void testSymbolSpread() throws JsonProcessingException, MT4Exception {
        when(mockSymbol.spread()).thenReturn(10);

        Symbol symbol = mockClient.getSymbol(TEST_SYMBOL_NAME);
        int spread = symbol.spread();

        assertEquals(10, spread);
        verify(mockSymbol).spread();
    }

    @Test
    @DisplayName("Symbol isSpreadFloat returns correct value")
    public void testSymbolIsSpreadFloat() throws JsonProcessingException, MT4Exception {
        when(mockSymbol.isSpreadFloat()).thenReturn(true);

        Symbol symbol = mockClient.getSymbol(TEST_SYMBOL_NAME);
        boolean isSpreadFloat = symbol.isSpreadFloat();

        assertTrue(isSpreadFloat);
        verify(mockSymbol).isSpreadFloat();
    }

    @Test
    @DisplayName("Symbol trade mode returns correct value")
    public void testSymbolTradeMode() throws JsonProcessingException, MT4Exception {
        when(mockSymbol.getTradeMode()).thenReturn(SymbolTradeMode.SYMBOL_TRADE_MODE_FULL);

        Symbol symbol = mockClient.getSymbol(TEST_SYMBOL_NAME);
        SymbolTradeMode tradeMode = symbol.getTradeMode();

        assertEquals(SymbolTradeMode.SYMBOL_TRADE_MODE_FULL, tradeMode);
        verify(mockSymbol).getTradeMode();
    }

    @Test
    @DisplayName("Symbol trade execution mode returns correct value")
    public void testSymbolTradeExecutionMode() throws JsonProcessingException, MT4Exception {
        when(mockSymbol.getTradeExecutionMode()).thenReturn(SymbolTradeExecution.SYMBOL_TRADE_EXECUTION_INSTANT);

        Symbol symbol = mockClient.getSymbol(TEST_SYMBOL_NAME);
        SymbolTradeExecution execMode = symbol.getTradeExecutionMode();

        assertEquals(SymbolTradeExecution.SYMBOL_TRADE_EXECUTION_INSTANT, execMode);
        verify(mockSymbol).getTradeExecutionMode();
    }

    @Test
    @DisplayName("Symbol swap mode returns correct value")
    public void testSymbolSwapMode() throws JsonProcessingException, MT4Exception {
        when(mockSymbol.getSwapMode()).thenReturn(SymbolSwapMode.SYMBOL_SWAP_MODE_POINTS);

        Symbol symbol = mockClient.getSymbol(TEST_SYMBOL_NAME);
        SymbolSwapMode swapMode = symbol.getSwapMode();

        assertEquals(SymbolSwapMode.SYMBOL_SWAP_MODE_POINTS, swapMode);
        verify(mockSymbol).getSwapMode();
    }

    @Test
    @DisplayName("Symbol swap long returns correct value")
    public void testSymbolSwapLong() throws JsonProcessingException, MT4Exception {
        when(mockSymbol.getSwapLong()).thenReturn(-1.5);

        Symbol symbol = mockClient.getSymbol(TEST_SYMBOL_NAME);
        double swapLong = symbol.getSwapLong();

        assertEquals(-1.5, swapLong, 0.001);
        verify(mockSymbol).getSwapLong();
    }

    @Test
    @DisplayName("Symbol swap short returns correct value")
    public void testSymbolSwapShort() throws JsonProcessingException, MT4Exception {
        when(mockSymbol.getSwapShort()).thenReturn(0.5);

        Symbol symbol = mockClient.getSymbol(TEST_SYMBOL_NAME);
        double swapShort = symbol.getSwapShort();

        assertEquals(0.5, swapShort, 0.001);
        verify(mockSymbol).getSwapShort();
    }

    @Test
    @DisplayName("Symbol margin initial returns correct value")
    public void testSymbolMarginInitial() throws JsonProcessingException, MT4Exception {
        when(mockSymbol.getMarginInitial()).thenReturn(0.0);

        Symbol symbol = mockClient.getSymbol(TEST_SYMBOL_NAME);
        double marginInitial = symbol.getMarginInitial();

        assertEquals(0.0, marginInitial, 0.001);
        verify(mockSymbol).getMarginInitial();
    }

    @Test
    @DisplayName("Symbol margin maintenance returns correct value")
    public void testSymbolMarginMaintenance() throws JsonProcessingException, MT4Exception {
        when(mockSymbol.getMarginMaintenance()).thenReturn(0.0);

        Symbol symbol = mockClient.getSymbol(TEST_SYMBOL_NAME);
        double marginMaintenance = symbol.getMarginMaintenance();

        assertEquals(0.0, marginMaintenance, 0.001);
        verify(mockSymbol).getMarginMaintenance();
    }

    @Test
    @DisplayName("getSymbol throws MT4Exception on unknown symbol")
    public void testGetSymbolThrowsException() throws JsonProcessingException, MT4Exception {
        MT4Exception exception = MT4Exception.Builder.newInstance()
                .setErrorCode(MT4Exception.Code.ERR_UNKNOWN_SYMBOL.id)
                .setMessage("Unknown symbol")
                .build();

        when(mockClient.getSymbol("INVALID")).thenThrow(exception);

        MT4Exception thrown = assertThrows(MT4Exception.class, () -> mockClient.getSymbol("INVALID"));
        assertEquals(MT4Exception.Code.ERR_UNKNOWN_SYMBOL, thrown.errorCode);
    }

    @Test
    @DisplayName("Symbol volume properties return correct values")
    public void testSymbolVolumeProperties() throws JsonProcessingException, MT4Exception {
        Symbol symbol = mockClient.getSymbol(TEST_SYMBOL_NAME);

        assertEquals(TEST_VOLUME_MIN, symbol.getVolumeMin());
        assertEquals(TEST_VOLUME_MAX, symbol.getVolumeMax());
        assertEquals(TEST_VOLUME_STEP, symbol.getVolumeStep());
    }
}
