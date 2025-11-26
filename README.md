# MetaTrader 4/5 Java Client

Java client library for MetaTrader 4 and MetaTrader 5 via ZeroMQ.

## Overview

This library provides a Java interface to communicate with MetaTrader 4/5 terminals through ZeroMQ messaging. It allows you to:

- Query account information (balance, equity, margin, etc.)
- Get market symbol data and real-time prices
- Place, modify, and close orders
- Run technical indicators (RSI, MACD, Bollinger Bands, etc.)
- Access trading signals
- Retrieve OHLCV (candlestick) data

## Requirements

- Java 21 or higher
- MetaTrader 4/5 terminal with ZeroMQ Expert Advisor running
- Maven 3.x (or use included Maven wrapper)

## Installation

### Maven

Add the dependency to your `pom.xml`:

```xml
<dependency>
    <groupId>human.coejoder</groupId>
    <artifactId>metatrader4-client-java</artifactId>
    <version>1.1.0</version>
</dependency>
```

### Build from source

```bash
mvnw clean install
```

## Usage

### MT4 Client

```java
// Connect to MT4 terminal
try (MT4Client client = new MT4Client("tcp://127.0.0.1:28282")) {

    // Get account info
    Account account = client.getAccount();
    System.out.println("Balance: " + account.getBalance());
    System.out.println("Equity: " + account.getEquity());

    // Get symbol info
    Symbol eurusd = client.getSymbol("EURUSD");
    SymbolTick tick = eurusd.getTick();
    System.out.println("EURUSD Bid: " + tick.getBid());
    System.out.println("EURUSD Ask: " + tick.getAsk());

    // Place a market order
    NewOrder order = NewOrder.Builder.newInstance()
        .setSymbol(eurusd)
        .setOrderType(OrderType.OP_BUY)
        .setLots(0.1)
        .setSlPoints(100)
        .setTpPoints(200)
        .build();
    Order result = client.orderSend(order);
    System.out.println("Order ticket: " + result.getTicket());

    // Run RSI indicator
    double rsi = client.runIndicator(
        Indicator.iRSI("EURUSD", StandardTimeframe.PERIOD_H1, 14, AppliedPrice.PRICE_CLOSE, 0)
    );
    System.out.println("RSI: " + rsi);
}
```

### MT5 Client

```java
// Connect to MT5 terminal (supports long ticket numbers)
try (MT5Client client = new MT5Client("tcp://127.0.0.1:28282")) {

    // Get orders (returns MT5Order with long ticket)
    List<MT5Order> orders = client.getOrders();
    for (MT5Order order : orders) {
        System.out.println("Ticket: " + order.getTicket()); // long type
    }

    // Get OHLCV data
    List<OHLCV> candles = client.getOHLCV("EURUSD", StandardTimeframe.PERIOD_H1, 100, 5000);
}
```

## Available Indicators

The library supports all standard MT4/MT5 indicators:

- **Trend**: iMA, iADX, iBands, iEnvelopes, iIchimoku, iSAR
- **Oscillators**: iRSI, iMACD, iStochastic, iCCI, iMomentum, iRVI, iWPR, iDeMarker, iForce
- **Volume**: iOBV, iMFI, iBWMFI
- **Bill Williams**: iAlligator, iGator, iAO, iAC, iFractals

## Project Structure

```
src/main/java/human/coejoder/
    mt4client/          # MT4 client classes
        MT4Client.java          # Main MT4 client
        MT4ClientInterface.java # Client interface
        Account.java            # Account information
        Symbol.java             # Market symbol
        Order.java              # Order representation
        NewOrder.java           # New order builder
        Indicator.java          # Technical indicators
        ...
    mt5client/          # MT5 client classes
        MT5Client.java          # Main MT5 client (long tickets)
        MT5Order.java           # MT5 order (long ticket)
        ...
```

## Test Results

| Metric | Coverage |
|--------|----------|
| Instructions | **83%** |
| Lines  | 83%      |
| Branches | 58%    |
| Methods | 69%     |
| Classes | 98%     |

### Coverage by Package

| Package | Instructions | Branches |
|---------|-------------|----------|
| mt4client | 87% | 59% |
| mt5client | 53% | 50% |

### Test Execution

```
Tests run: 192, Failures: 0, Errors: 0, Skipped: 0
```

Run tests:

```bash
mvnw test
```

Run tests with coverage report:

```bash
mvnw clean test
```

Coverage report generated at: `target/site/jacoco/index.html`

## Dependencies

- **JeroMQ** (0.6.0) - Pure Java ZeroMQ implementation
- **Jackson** (2.20.1) - JSON serialization/deserialization
- **Lombok** (1.18.42) - Boilerplate code reduction
- **SLF4J/Logback** - Logging
- **JUnit 5** (5.10.2) - Testing framework
- **Mockito** (5.14.2) - Mocking framework
- **JaCoCo** (0.8.12) - Code coverage

## License

See LICENSE file for details.
