package integration;

import api.BeaconRetriever;
import model.BeaconResp;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.Map;
import java.util.Optional;

import static junit.framework.TestCase.*;

public class BeaconTest {
    private final BeaconRetriever retriever = new BeaconRetriever();

    @Test
    public void validateResponseSuccess() {
        Optional<BeaconResp> lastValue = retriever.getLastValue();
        assertTrue("Unable to get response", lastValue.isPresent());
    }

    @Test
    public void validateAnalyzerSanity() {
        Optional<BeaconResp> lastValue = Optional.empty();
        Map<String, Integer> analyzedOutput = retriever.analyzeOutputValue(lastValue);
        assertNull("Method should process empty value correctly", analyzedOutput);
    }

    @Test
    public void validateAnalyzerCalculation() {
        Optional<BeaconResp> lastValue = retriever.getLastValue();
        Map<String, Integer> analyzedValue = retriever.analyzeOutputValue(lastValue);
        for (String key : analyzedValue.keySet()) {
            int calculatedAmount = analyzedValue.get(key);
            int recalculatedAmount = StringUtils.countMatches(lastValue.get().getOutputValue(), key);
            assertEquals("Wrong calculation of number of occurrences", recalculatedAmount, calculatedAmount);
        }
    }

    @Test
    public void validateAnalyzerEntries() {
        Optional<BeaconResp> lastValue = retriever.getLastValue();
        Map<String, Integer> analyzedValue = retriever.analyzeOutputValue(lastValue);
        String outputValue = lastValue.get().getOutputValue();
        for (int i = 0; i < outputValue.length(); i++) {
            String symbol = outputValue.substring(i, i + 1);
            assertTrue("Symbol is absent from result set: " + symbol, analyzedValue.keySet().contains(symbol));
        }
    }

    @Test
    public void symbolsCountTest() {
        Optional<BeaconResp> lastValue = retriever.getLastValue();
        Map<String, Integer> analyzedValue = retriever.analyzeOutputValue(lastValue);
        int calculatedCommonLength = 0;
        for (String key : analyzedValue.keySet()) {
            calculatedCommonLength += analyzedValue.get(key);
        }
        assertEquals("Wrong amount of symbol occurrences", lastValue.get().getOutputValue().length(), calculatedCommonLength);
    }
}
