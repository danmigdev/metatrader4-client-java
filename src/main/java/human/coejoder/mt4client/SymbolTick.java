package human.coejoder.mt4client;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PUBLIC;

/**
 * The latest prices of a symbol in MetaTrader 4.
 *
 * @see <a href="https://docs.mql4.com/constants/structures/mqltick">mqltick</a>
 */
@AllArgsConstructor(onConstructor_ = @JsonCreator)
@Getter
@ToString
@EqualsAndHashCode
@FieldDefaults(makeFinal = true, level = PUBLIC)
public class SymbolTick {
    int time;
    double bid;
    double ask;
    double last;
    int volume;
}
