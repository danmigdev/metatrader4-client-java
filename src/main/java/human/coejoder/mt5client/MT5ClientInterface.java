package human.coejoder.mt5client;

import com.fasterxml.jackson.core.JsonProcessingException;
import human.coejoder.mt4client.*;

import java.util.List;
import java.util.Map;

/**
 * Interface for MT5Client to enable mocking and testing.
 *
 * MT5 uses long (ulong in MQL5) for ticket numbers to support the larger ticket range.
 */
public interface MT5ClientInterface extends AutoCloseable {

    /**
     * Shutdown the client connection.
     */
    void shutdown();

    /**
     * Get the account information.
     */
    Account getAccount() throws JsonProcessingException, MT4Exception;

    /**
     * Get the list of symbol names.
     */
    List<String> getSymbolNames() throws JsonProcessingException, MT4Exception;

    /**
     * Get symbols by names.
     */
    Map<String, Symbol> getSymbols(String... names) throws JsonProcessingException, MT4Exception;

    /**
     * Get a single symbol by name.
     */
    Symbol getSymbol(String name) throws JsonProcessingException, MT4Exception;

    /**
     * Get signal names.
     */
    List<String> getSignalNames() throws JsonProcessingException, MT4Exception;

    /**
     * Get OHLCV data.
     */
    List<OHLCV> getOHLCV(String symbol, Timeframe timeframe, long limit, long timeout) throws JsonProcessingException, MT4Exception;

    /**
     * Get OHLCV data with offset.
     */
    List<OHLCV> getOHLCV(String symbol, Timeframe timeframe, long limit, long timeout, long offset) throws JsonProcessingException, MT4Exception;

    /**
     * Get signals by names.
     */
    Map<String, Signal> getSignals(String... names) throws JsonProcessingException, MT4Exception;

    /**
     * Get a single signal by name.
     */
    Signal getSignal(String name) throws JsonProcessingException, MT4Exception;

    /**
     * Run an indicator.
     */
    double runIndicator(Indicator func) throws JsonProcessingException, MT4Exception;

    /**
     * Run an indicator with timeout.
     */
    double runIndicator(Indicator func, int timeout) throws JsonProcessingException, MT4Exception;

    /**
     * Get open positions and pending orders.
     */
    List<MT5Order> getOrders() throws JsonProcessingException, MT4Exception;

    /**
     * Get historical orders.
     */
    List<MT5Order> getOrdersHistorical() throws JsonProcessingException, MT4Exception;

    /**
     * Get an order/position by ticket (using long for MT5 compatibility).
     */
    MT5Order getOrder(long ticket) throws JsonProcessingException, MT4Exception;

    /**
     * Send a new order.
     */
    MT5Order orderSend(NewOrder newOrder) throws JsonProcessingException, MT4Exception;

    /**
     * Modify an order/position.
     */
    MT5Order orderModify(MT5ModifyOrder modifyOrder) throws JsonProcessingException, MT4Exception;

    /**
     * Close a position by ticket (using long for MT5 compatibility).
     */
    void orderClose(long ticket) throws JsonProcessingException, MT4Exception;

    /**
     * Close a position.
     */
    void orderClose(MT5Order order) throws JsonProcessingException, MT4Exception;

    /**
     * Delete a pending order by ticket (using long for MT5 compatibility).
     */
    void orderDelete(long ticket, boolean closeIfOpened) throws JsonProcessingException, MT4Exception;

    /**
     * Delete a pending order.
     */
    void orderDelete(MT5Order order, boolean closeIfOpened) throws JsonProcessingException, MT4Exception;

    /**
     * Delete a pending order by ticket (closes if opened).
     */
    void orderDelete(long ticket) throws JsonProcessingException, MT4Exception;

    /**
     * Delete a pending order (closes if opened).
     */
    void orderDelete(MT5Order order) throws JsonProcessingException, MT4Exception;
}
