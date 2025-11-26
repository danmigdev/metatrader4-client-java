package human.coejoder.mt4client;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * A trading signal in MetaTrader 4.
 *
 * @see <a href="https://docs.mql4.com/signals">signals</a>
 * @see <a href="https://docs.mql4.com/constants/tradingconstants/signalproperties">signalproperties</a>
 */
@Getter
@AllArgsConstructor(onConstructor_ = @JsonCreator)
@ToString
public class Signal {

    @JsonProperty("author_login")
    private final String authorLogin;

    private final String broker;

    @JsonProperty("broker_server")
    private final String brokerServer;

    private final String name;
    private final String currency;

    @JsonProperty("date_published")
    private final int datePublished;

    @JsonProperty("date_started")
    private final int dateStarted;

    private final int id;
    private final int leverage;
    private final int pips;
    private final int rating;
    private final int subscribers;
    private final int trades;

    @JsonProperty("trade_mode")
    private final int tradeMode;

    private final double balance;
    private final double equity;
    private final double gain;

    @JsonProperty("max_drawdown")
    private final double maxDrawdown;

    private final double price;
    private final double roi;
}
