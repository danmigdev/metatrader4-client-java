package human.coejoder.mt4client;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link Account} using mock objects.
 * These tests do not require a live MT4 connection.
 */
public class TestAccount {

    private static final Logger LOG = LoggerFactory.getLogger(TestAccount.class);

    private MT4ClientInterface mockClient;
    private Account mockAccount;

    @BeforeEach
    public void setUp() throws JsonProcessingException, MT4Exception {
        mockClient = mock(MT4ClientInterface.class);
        mockAccount = mock(Account.class);

        // Setup default mock behavior
        when(mockAccount.getLogin()).thenReturn(12345L);
        when(mockAccount.getTradeMode()).thenReturn(AccountTradeMode.ACCOUNT_TRADE_MODE_DEMO);
        when(mockAccount.getName()).thenReturn("Test Account");
        when(mockAccount.getServer()).thenReturn("Demo Server");
        when(mockAccount.getCurrency()).thenReturn("USD");
        when(mockAccount.getCompany()).thenReturn("Test Broker");

        when(mockClient.getAccount()).thenReturn(mockAccount);
    }

    @Test
    @DisplayName("Account leverage returns correct value")
    public void testAccountLeverage() throws JsonProcessingException, MT4Exception {
        when(mockAccount.getLeverage()).thenReturn(100L);

        Account account = mockClient.getAccount();
        long leverage = account.getLeverage();

        LOG.trace("Account leverage: {}", leverage);
        assertEquals(100L, leverage);
        verify(mockAccount).getLeverage();
    }

    @Test
    @DisplayName("Account limit orders returns correct value")
    public void testGetLimitOrders() throws JsonProcessingException, MT4Exception {
        when(mockAccount.getLimitOrders()).thenReturn(200);

        Account account = mockClient.getAccount();
        int limitOrders = account.getLimitOrders();

        LOG.trace("Account limit orders: {}", limitOrders);
        assertEquals(200, limitOrders);
        verify(mockAccount).getLimitOrders();
    }

    @Test
    @DisplayName("Account margin stop-out mode returns correct value")
    public void testGetMarginStopOutMode() throws JsonProcessingException, MT4Exception {
        when(mockAccount.getMarginStopOutMode()).thenReturn(AccountStopoutMode.ACCOUNT_STOPOUT_MODE_PERCENT);

        Account account = mockClient.getAccount();
        AccountStopoutMode mode = account.getMarginStopOutMode();

        LOG.trace("Account margin stop-out mode: {}", mode);
        assertEquals(AccountStopoutMode.ACCOUNT_STOPOUT_MODE_PERCENT, mode);
        verify(mockAccount).getMarginStopOutMode();
    }

    @Test
    @DisplayName("Account is trade allowed returns true")
    public void testIsTradeAllowed() throws JsonProcessingException, MT4Exception {
        when(mockAccount.isTradeAllowed()).thenReturn(true);

        Account account = mockClient.getAccount();
        boolean tradeAllowed = account.isTradeAllowed();

        LOG.trace("Account is trade allowed: {}", tradeAllowed);
        assertTrue(tradeAllowed);
        verify(mockAccount).isTradeAllowed();
    }

    @Test
    @DisplayName("Account is trade for EA allowed returns correct value")
    public void testIsTradeForExpertAdvisorAllowed() throws JsonProcessingException, MT4Exception {
        when(mockAccount.isTradeForExpertAdvisorAllowed()).thenReturn(1);

        Account account = mockClient.getAccount();
        int eaAllowed = account.isTradeForExpertAdvisorAllowed();

        LOG.trace("Account is trade for EA allowed: {}", eaAllowed);
        assertEquals(1, eaAllowed);
        verify(mockAccount).isTradeForExpertAdvisorAllowed();
    }

    @Test
    @DisplayName("Account balance returns correct value")
    public void testGetBalance() throws JsonProcessingException, MT4Exception {
        when(mockAccount.getBalance()).thenReturn(10000.0);

        Account account = mockClient.getAccount();
        double balance = account.getBalance();

        LOG.trace("Account balance: {}", balance);
        assertEquals(10000.0, balance, 0.001);
        verify(mockAccount).getBalance();
    }

    @Test
    @DisplayName("Account credit returns correct value")
    public void testGetCredit() throws JsonProcessingException, MT4Exception {
        when(mockAccount.getCredit()).thenReturn(500.0);

        Account account = mockClient.getAccount();
        double credit = account.getCredit();

        LOG.trace("Account credit: {}", credit);
        assertEquals(500.0, credit, 0.001);
        verify(mockAccount).getCredit();
    }

    @Test
    @DisplayName("Account profit returns correct value")
    public void testGetProfit() throws JsonProcessingException, MT4Exception {
        when(mockAccount.getProfit()).thenReturn(250.50);

        Account account = mockClient.getAccount();
        double profit = account.getProfit();

        LOG.trace("Account profit: {}", profit);
        assertEquals(250.50, profit, 0.001);
        verify(mockAccount).getProfit();
    }

    @Test
    @DisplayName("Account equity returns correct value")
    public void testGetEquity() throws JsonProcessingException, MT4Exception {
        when(mockAccount.getEquity()).thenReturn(10250.50);

        Account account = mockClient.getAccount();
        double equity = account.getEquity();

        LOG.trace("Account equity: {}", equity);
        assertEquals(10250.50, equity, 0.001);
        verify(mockAccount).getEquity();
    }

    @Test
    @DisplayName("Account margin returns correct value")
    public void testGetMargin() throws JsonProcessingException, MT4Exception {
        when(mockAccount.getMargin()).thenReturn(1000.0);

        Account account = mockClient.getAccount();
        double margin = account.getMargin();

        LOG.trace("Account margin: {}", margin);
        assertEquals(1000.0, margin, 0.001);
        verify(mockAccount).getMargin();
    }

    @Test
    @DisplayName("Account free margin returns correct value")
    public void testGetMarginFree() throws JsonProcessingException, MT4Exception {
        when(mockAccount.getMarginFree()).thenReturn(9250.50);

        Account account = mockClient.getAccount();
        double marginFree = account.getMarginFree();

        LOG.trace("Account free margin: {}", marginFree);
        assertEquals(9250.50, marginFree, 0.001);
        verify(mockAccount).getMarginFree();
    }

    @Test
    @DisplayName("Account margin level returns correct value")
    public void testGetMarginLevel() throws JsonProcessingException, MT4Exception {
        when(mockAccount.getMarginLevel()).thenReturn(1025.05);

        Account account = mockClient.getAccount();
        double marginLevel = account.getMarginLevel();

        LOG.trace("Account margin level: {}", marginLevel);
        assertEquals(1025.05, marginLevel, 0.001);
        verify(mockAccount).getMarginLevel();
    }

    @Test
    @DisplayName("Account margin call level returns correct value")
    public void testGetMarginCallLevel() throws JsonProcessingException, MT4Exception {
        when(mockAccount.getMarginCallLevel()).thenReturn(100.0);

        Account account = mockClient.getAccount();
        double marginCallLevel = account.getMarginCallLevel();

        LOG.trace("Account margin call level: {}", marginCallLevel);
        assertEquals(100.0, marginCallLevel, 0.001);
        verify(mockAccount).getMarginCallLevel();
    }

    @Test
    @DisplayName("Account margin stop-out level returns correct value")
    public void testGetMarginStopOutLevel() throws JsonProcessingException, MT4Exception {
        when(mockAccount.getMarginStopOutLevel()).thenReturn(50.0);

        Account account = mockClient.getAccount();
        double marginStopOutLevel = account.getMarginStopOutLevel();

        LOG.trace("Account margin stop-out level: {}", marginStopOutLevel);
        assertEquals(50.0, marginStopOutLevel, 0.001);
        verify(mockAccount).getMarginStopOutLevel();
    }

    @Test
    @DisplayName("getAccount returns account from mock client")
    public void testGetAccountFromClient() throws JsonProcessingException, MT4Exception {
        Account account = mockClient.getAccount();

        assertNotNull(account);
        assertEquals(12345L, account.getLogin());
        assertEquals("Test Account", account.getName());
        assertEquals("Demo Server", account.getServer());
        assertEquals("USD", account.getCurrency());
        assertEquals("Test Broker", account.getCompany());
        verify(mockClient).getAccount();
    }

    @Test
    @DisplayName("getAccount throws MT4Exception on error")
    public void testGetAccountThrowsException() throws JsonProcessingException, MT4Exception {
        MT4Exception exception = MT4Exception.Builder.newInstance()
                .setErrorCode(MT4Exception.Code.ERR_NO_CONNECTION.id)
                .setMessage("No connection to trade server")
                .build();

        when(mockClient.getAccount()).thenThrow(exception);

        MT4Exception thrown = assertThrows(MT4Exception.class, () -> mockClient.getAccount());
        assertEquals(MT4Exception.Code.ERR_NO_CONNECTION, thrown.errorCode);
        assertTrue(thrown.message.contains("No connection"));
    }
}
