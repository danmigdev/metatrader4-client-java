package human.coejoder.mt4client;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link Indicator} class.
 */
class IndicatorTest {

    private static final String TEST_SYMBOL = "EURUSD";
    private static final Timeframe TEST_TIMEFRAME = StandardTimeframe.PERIOD_H1;
    private static final long TEST_SHIFT = 0;
    private static final long TEST_PERIOD = 14;

    @Test
    @DisplayName("iAC creates correct indicator")
    void testIAC() {
        Indicator indicator = Indicator.iAC(TEST_SYMBOL, TEST_TIMEFRAME, TEST_SHIFT);

        assertEquals("iAC", indicator.getName());
        ArrayNode args = indicator.getArguments();
        assertEquals(3, args.size());
        assertEquals(TEST_SYMBOL, args.get(0).asText());
        assertEquals(TEST_TIMEFRAME.getMinutes(), args.get(1).asLong());
        assertEquals(TEST_SHIFT, args.get(2).asLong());
    }

    @Test
    @DisplayName("iAD creates correct indicator")
    void testIAD() {
        Indicator indicator = Indicator.iAD(TEST_SYMBOL, TEST_TIMEFRAME, TEST_SHIFT);

        assertEquals("iAD", indicator.getName());
        ArrayNode args = indicator.getArguments();
        assertEquals(3, args.size());
    }

    @Test
    @DisplayName("iADX creates correct indicator")
    void testIADX() {
        Indicator indicator = Indicator.iADX(TEST_SYMBOL, TEST_TIMEFRAME, TEST_PERIOD,
                AppliedPrice.PRICE_CLOSE, IndicatorLine_iADX.MAIN, TEST_SHIFT);

        assertEquals("iADX", indicator.getName());
        ArrayNode args = indicator.getArguments();
        assertEquals(6, args.size());
        assertEquals(TEST_PERIOD, args.get(2).asLong());
        assertEquals(AppliedPrice.PRICE_CLOSE.id, args.get(3).asInt());
        assertEquals(IndicatorLine_iADX.MAIN.id, args.get(4).asInt());
    }

    @Test
    @DisplayName("iAlligator creates correct indicator")
    void testIAlligator() {
        Indicator indicator = Indicator.iAlligator(TEST_SYMBOL, TEST_TIMEFRAME,
                13, 8, 8, 5, 5, 3,
                SmoothingMethod.SMMA, AppliedPrice.PRICE_MEDIAN,
                IndicatorLine_iAlligator.GATORJAW, TEST_SHIFT);

        assertEquals("iAlligator", indicator.getName());
        ArrayNode args = indicator.getArguments();
        assertEquals(12, args.size());
    }

    @Test
    @DisplayName("iAO creates correct indicator")
    void testIAO() {
        Indicator indicator = Indicator.iAO(TEST_SYMBOL, TEST_TIMEFRAME, TEST_SHIFT);

        assertEquals("iAO", indicator.getName());
        assertEquals(3, indicator.getArguments().size());
    }

    @Test
    @DisplayName("iATR creates correct indicator")
    void testIATR() {
        Indicator indicator = Indicator.iATR(TEST_SYMBOL, TEST_TIMEFRAME, TEST_PERIOD, TEST_SHIFT);

        assertEquals("iATR", indicator.getName());
        assertEquals(4, indicator.getArguments().size());
    }

    @Test
    @DisplayName("iBearsPower creates correct indicator")
    void testIBearsPower() {
        Indicator indicator = Indicator.iBearsPower(TEST_SYMBOL, TEST_TIMEFRAME,
                TEST_PERIOD, AppliedPrice.PRICE_CLOSE, TEST_SHIFT);

        assertEquals("iBearsPower", indicator.getName());
        assertEquals(5, indicator.getArguments().size());
    }

    @Test
    @DisplayName("iBands creates correct indicator")
    void testIBands() {
        Indicator indicator = Indicator.iBands(TEST_SYMBOL, TEST_TIMEFRAME,
                20, 2.0, 0, AppliedPrice.PRICE_CLOSE,
                IndicatorLine_iBands.MAIN, TEST_SHIFT);

        assertEquals("iBands", indicator.getName());
        assertEquals(8, indicator.getArguments().size());
    }

    @Test
    @DisplayName("iBullsPower creates correct indicator")
    void testIBullsPower() {
        Indicator indicator = Indicator.iBullsPower(TEST_SYMBOL, TEST_TIMEFRAME,
                TEST_PERIOD, AppliedPrice.PRICE_CLOSE, TEST_SHIFT);

        assertEquals("iBullsPower", indicator.getName());
        assertEquals(5, indicator.getArguments().size());
    }

    @Test
    @DisplayName("iCCI creates correct indicator")
    void testICCI() {
        Indicator indicator = Indicator.iCCI(TEST_SYMBOL, TEST_TIMEFRAME,
                TEST_PERIOD, AppliedPrice.PRICE_TYPICAL, TEST_SHIFT);

        assertEquals("iCCI", indicator.getName());
        assertEquals(5, indicator.getArguments().size());
    }

    @Test
    @DisplayName("iDeMarker creates correct indicator")
    void testIDeMarker() {
        Indicator indicator = Indicator.iDeMarker(TEST_SYMBOL, TEST_TIMEFRAME, TEST_PERIOD, TEST_SHIFT);

        assertEquals("iDeMarker", indicator.getName());
        assertEquals(4, indicator.getArguments().size());
    }

    @Test
    @DisplayName("iEnvelopes creates correct indicator")
    void testIEnvelopes() {
        Indicator indicator = Indicator.iEnvelopes(TEST_SYMBOL, TEST_TIMEFRAME,
                20, SmoothingMethod.SMA, 0, AppliedPrice.PRICE_CLOSE,
                0.1, IndicatorLine_iBands.UPPER, TEST_SHIFT);

        assertEquals("iEnvelopes", indicator.getName());
        assertEquals(9, indicator.getArguments().size());
    }

    @Test
    @DisplayName("iForce creates correct indicator")
    void testIForce() {
        Indicator indicator = Indicator.iForce(TEST_SYMBOL, TEST_TIMEFRAME,
                TEST_PERIOD, SmoothingMethod.SMA, AppliedPrice.PRICE_CLOSE, TEST_SHIFT);

        assertEquals("iForce", indicator.getName());
        assertEquals(6, indicator.getArguments().size());
    }

    @Test
    @DisplayName("iFractals creates correct indicator")
    void testIFractals() {
        Indicator indicator = Indicator.iFractals(TEST_SYMBOL, TEST_TIMEFRAME,
                IndicatorLine_iBands.UPPER, TEST_SHIFT);

        assertEquals("iFractals", indicator.getName());
        assertEquals(4, indicator.getArguments().size());
    }

    @Test
    @DisplayName("iGator creates correct indicator")
    void testIGator() {
        Indicator indicator = Indicator.iGator(TEST_SYMBOL, TEST_TIMEFRAME,
                13, 8, 8, 5, 5, 3,
                SmoothingMethod.SMMA, AppliedPrice.PRICE_MEDIAN,
                IndicatorLine_iBands.UPPER, TEST_SHIFT);

        assertEquals("iGator", indicator.getName());
        assertEquals(12, indicator.getArguments().size());
    }

    @Test
    @DisplayName("iIchimoku creates correct indicator")
    void testIIchimoku() {
        Indicator indicator = Indicator.iIchimoku(TEST_SYMBOL, TEST_TIMEFRAME,
                9, 26, 52, IndicatorLine_iIchimoku.TENKANSEN, TEST_SHIFT);

        assertEquals("iIchimoku", indicator.getName());
        assertEquals(7, indicator.getArguments().size());
    }

    @Test
    @DisplayName("iBWMFI creates correct indicator")
    void testIBWMFI() {
        Indicator indicator = Indicator.iBWMFI(TEST_SYMBOL, TEST_TIMEFRAME, TEST_SHIFT);

        assertEquals("iBWMFI", indicator.getName());
        assertEquals(3, indicator.getArguments().size());
    }

    @Test
    @DisplayName("iMomentum creates correct indicator")
    void testIMomentum() {
        Indicator indicator = Indicator.iMomentum(TEST_SYMBOL, TEST_TIMEFRAME,
                TEST_PERIOD, AppliedPrice.PRICE_CLOSE, TEST_SHIFT);

        assertEquals("iMomentum", indicator.getName());
        assertEquals(5, indicator.getArguments().size());
    }

    @Test
    @DisplayName("iMFI creates correct indicator")
    void testIMFI() {
        Indicator indicator = Indicator.iMFI(TEST_SYMBOL, TEST_TIMEFRAME, TEST_PERIOD, TEST_SHIFT);

        assertEquals("iMFI", indicator.getName());
        assertEquals(4, indicator.getArguments().size());
    }

    @Test
    @DisplayName("iMA creates correct indicator")
    void testIMA() {
        Indicator indicator = Indicator.iMA(TEST_SYMBOL, TEST_TIMEFRAME,
                TEST_PERIOD, 0, SmoothingMethod.SMA, AppliedPrice.PRICE_CLOSE, TEST_SHIFT);

        assertEquals("iMA", indicator.getName());
        assertEquals(7, indicator.getArguments().size());
    }

    @Test
    @DisplayName("iOsMA creates correct indicator")
    void testIOsMA() {
        Indicator indicator = Indicator.iOsMA(TEST_SYMBOL, TEST_TIMEFRAME,
                12, 26, 9, AppliedPrice.PRICE_CLOSE, TEST_SHIFT);

        assertEquals("iOsMA", indicator.getName());
        assertEquals(7, indicator.getArguments().size());
    }

    @Test
    @DisplayName("iMACD creates correct indicator")
    void testIMACD() {
        Indicator indicator = Indicator.iMACD(TEST_SYMBOL, TEST_TIMEFRAME,
                12, 26, 9, AppliedPrice.PRICE_CLOSE,
                IndicatorLine_iMACD.MAIN, TEST_SHIFT);

        assertEquals("iMACD", indicator.getName());
        assertEquals(8, indicator.getArguments().size());
    }

    @Test
    @DisplayName("iOBV creates correct indicator")
    void testIOBV() {
        Indicator indicator = Indicator.iOBV(TEST_SYMBOL, TEST_TIMEFRAME,
                AppliedPrice.PRICE_CLOSE, TEST_SHIFT);

        assertEquals("iOBV", indicator.getName());
        assertEquals(4, indicator.getArguments().size());
    }

    @Test
    @DisplayName("iSAR creates correct indicator")
    void testISAR() {
        Indicator indicator = Indicator.iSAR(TEST_SYMBOL, TEST_TIMEFRAME, 0.02, 0.2, TEST_SHIFT);

        assertEquals("iSAR", indicator.getName());
        assertEquals(5, indicator.getArguments().size());
    }

    @Test
    @DisplayName("iRSI creates correct indicator")
    void testIRSI() {
        Indicator indicator = Indicator.iRSI(TEST_SYMBOL, TEST_TIMEFRAME,
                TEST_PERIOD, AppliedPrice.PRICE_CLOSE, TEST_SHIFT);

        assertEquals("iRSI", indicator.getName());
        assertEquals(5, indicator.getArguments().size());
    }

    @Test
    @DisplayName("iRVI creates correct indicator")
    void testIRVI() {
        Indicator indicator = Indicator.iRVI(TEST_SYMBOL, TEST_TIMEFRAME,
                TEST_PERIOD, IndicatorLine_iMACD.MAIN, TEST_SHIFT);

        assertEquals("iRVI", indicator.getName());
        assertEquals(5, indicator.getArguments().size());
    }

    @Test
    @DisplayName("iStdDev creates correct indicator")
    void testIStdDev() {
        Indicator indicator = Indicator.iStdDev(TEST_SYMBOL, TEST_TIMEFRAME,
                TEST_PERIOD, 0, SmoothingMethod.SMA, AppliedPrice.PRICE_CLOSE, TEST_SHIFT);

        assertEquals("iStdDev", indicator.getName());
        assertEquals(7, indicator.getArguments().size());
    }

    @Test
    @DisplayName("iStochastic creates correct indicator")
    void testIStochastic() {
        Indicator indicator = Indicator.iStochastic(TEST_SYMBOL, TEST_TIMEFRAME,
                5, 3, 3, SmoothingMethod.SMA, PriceField.LOW_HIGH,
                IndicatorLine_iMACD.MAIN, TEST_SHIFT);

        assertEquals("iStochastic", indicator.getName());
        assertEquals(9, indicator.getArguments().size());
    }

    @Test
    @DisplayName("iWPR creates correct indicator")
    void testIWPR() {
        Indicator indicator = Indicator.iWPR(TEST_SYMBOL, TEST_TIMEFRAME, TEST_PERIOD, TEST_SHIFT);

        assertEquals("iWPR", indicator.getName());
        assertEquals(4, indicator.getArguments().size());
    }

    @Test
    @DisplayName("Indicator constructor works correctly")
    void testIndicatorConstructor() {
        com.fasterxml.jackson.databind.node.JsonNodeFactory factory =
            com.fasterxml.jackson.databind.node.JsonNodeFactory.instance;
        ArrayNode args = factory.arrayNode().add("test").add(1);

        Indicator indicator = new Indicator("testIndicator", args);

        assertEquals("testIndicator", indicator.getName());
        assertEquals(args, indicator.getArguments());
    }
}
