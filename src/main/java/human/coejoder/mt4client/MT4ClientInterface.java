package human.coejoder.mt4client;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;
import java.util.Map;

/**
 * Interface for MT4Client to enable mocking and testing.
 */
public interface MT4ClientInterface extends AutoCloseable {

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
     * Get open and pending orders.
     */
    List<Order> getOrders() throws JsonProcessingException, MT4Exception;

    /**
     * Get historical orders.
     */
    List<Order> getOrdersHistorical() throws JsonProcessingException, MT4Exception;

    /**
     * Get an order by ticket.
     */
    Order getOrder(int ticket) throws JsonProcessingException, MT4Exception;

    /**
     * Send a new order.
     */
    Order orderSend(NewOrder newOrder) throws JsonProcessingException, MT4Exception;

    /**
     * Modify an order.
     */
    Order orderModify(ModifyOrder modifyOrder) throws JsonProcessingException, MT4Exception;

    /**
     * Close an order by ticket.
     */
    void orderClose(int ticket) throws JsonProcessingException, MT4Exception;

    /**
     * Close an order.
     */
    void orderClose(Order order) throws JsonProcessingException, MT4Exception;

    /**
     * Delete a pending order by ticket.
     */
    void orderDelete(int ticket, boolean closeIfOpened) throws JsonProcessingException, MT4Exception;

    /**
     * Delete a pending order.
     */
    void orderDelete(Order order, boolean closeIfOpened) throws JsonProcessingException, MT4Exception;

    /**
     * Delete a pending order by ticket (closes if opened).
     */
    void orderDelete(int ticket) throws JsonProcessingException, MT4Exception;

    /**
     * Delete a pending order (closes if opened).
     */
    void orderDelete(Order order) throws JsonProcessingException, MT4Exception;
}
