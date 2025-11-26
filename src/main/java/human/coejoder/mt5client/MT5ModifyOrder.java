package human.coejoder.mt5client;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * An order/position modification request for MetaTrader 5.
 *
 * MT5 uses long (ulong in MQL5) for ticket numbers.
 *
 * @see <a href="https://www.mql5.com/en/docs/trading/ordermodify">https://www.mql5.com/en/docs/trading/ordermodify</a>
 * @see <a href="https://www.mql5.com/en/docs/trading/positionmodify">https://www.mql5.com/en/docs/trading/positionmodify</a>
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MT5ModifyOrder {

    public static class Builder implements OrderStep {
        private long ticket;
        private Double price;
        private Double sl;
        private Double tp;
        private Integer slPoints;
        private Integer tpPoints;

        private Builder() {}

        /**
         * Construct a modify-order builder.
         *
         * @return The modify-order builder instance.
         */
        public static OrderStep newInstance() {
            return new Builder();
        }

        /**
         * Build the modify-order instance.
         *
         * @return The modify-order request.
         */
        public MT5ModifyOrder build() {
            return new MT5ModifyOrder(this);
        }

        /**
         * @param ticket The order/position ticket number.
         * @return This Builder.
         */
        @Override
        public Builder setOrder(long ticket) {
            this.ticket = ticket;
            return this;
        }

        /**
         * @param order The order/position to modify.
         * @return This Builder.
         */
        @Override
        public Builder setOrder(MT5Order order) {
            this.ticket = order.getTicket();
            return this;
        }

        /**
         * @param price The desired open price; applies to pending orders only.  Optional.
         * @return This Builder.
         */
        public Builder setPrice(Double price) {
            this.price = price;
            return this;
        }

        /**
         * @param sl The absolute stop-loss to use.  Optional.
         * @return This Builder.
         */
        public Builder setSl(Double sl) {
            this.sl = sl;
            return this;
        }

        /**
         * @param tp The absolute take-profit to use.  Optional.
         * @return This Builder.
         */
        public Builder setTp(Double tp) {
            this.tp = tp;
            return this;
        }

        /**
         * @param slPoints The relative stop-loss to use, in points.  Optional.
         * @return This Builder.
         */
        public Builder setSlPoints(Integer slPoints) {
            this.slPoints = slPoints;
            return this;
        }

        /**
         * @param tpPoints The relative take-profit to use, in points.  Optional.
         * @return This Builder.
         */
        public Builder setTpPoints(Integer tpPoints) {
            this.tpPoints = tpPoints;
            return this;
        }
    }

    public interface OrderStep {
        Builder setOrder(long ticket);
        Builder setOrder(MT5Order order);
    }

    public final long ticket;
    public final Double price;
    public final Double sl;
    public final Double tp;
    @JsonProperty("sl_points")
    public final Integer slPoints;
    @JsonProperty("tp_points")
    public final Integer tpPoints;

    private MT5ModifyOrder(Builder builder) {
        this.ticket = builder.ticket;
        this.price = builder.price;
        this.sl = builder.sl;
        this.tp = builder.tp;
        this.slPoints = builder.slPoints;
        this.tpPoints = builder.tpPoints;
    }
}
