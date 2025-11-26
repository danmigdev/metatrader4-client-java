package human.coejoder.mt4client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PUBLIC;

/**
 * An OHLCV bar.
 *
 * @see <a href="https://docs.mql4.com/series/copyrates">copyrates</a>
 * @see <a href="https://docs.mql4.com/constants/structures/mqlrates">mqlrates</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@FieldDefaults(makeFinal = true, level = PUBLIC)
public class OHLCV {
    int time;
    int open;
    int high;
    int low;
    int close;
    int tick_volume;
}
