package human.coejoder.mt4client;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link TimeframeFactory}.
 */
public class TestTimeframeFactory {

    static Stream<Object[]> validTimeframes() {
        return Stream.of(
                new Object[] {"15m", StandardTimeframe.PERIOD_M15},
                new Object[] {"1h", StandardTimeframe.PERIOD_H1},
                new Object[] {"1d", StandardTimeframe.PERIOD_D1},
                new Object[] {"1w", StandardTimeframe.PERIOD_W1},
                new Object[] {"1mn", StandardTimeframe.PERIOD_MN1},
                new Object[] {"0", StandardTimeframe.PERIOD_CURRENT},
                new Object[] {"2m", NonStandardTimeframe.PERIOD_M2},
                new Object[] {"2h", NonStandardTimeframe.PERIOD_H2}
        );
    }

    static Stream<String> invalidTimeframes() {
        return Stream.of("10a", "-15m", "M15");
    }

    @ParameterizedTest
    @MethodSource("validTimeframes")
    public void testValidTimeframes(String timeframe, Timeframe expected) {
        TimeframeFactory.INSTANCE.build(timeframe).ifPresentOrElse(
                t -> assertEquals(expected, t),
                () -> fail("Failed to parse valid timeframe.")
        );
    }

    @ParameterizedTest
    @MethodSource("invalidTimeframes")
    public void testInvalidTimeframe(String timeframe) {
        TimeframeFactory.INSTANCE.build(timeframe).ifPresent(
                t -> fail("Non-empty value for invalid timeframe.")
        );
    }
}
