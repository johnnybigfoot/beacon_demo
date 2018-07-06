package integration;

import api.BeaconRetriever;
import model.BeaconResp;
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
    public void validateAnalyzer() {
        Optional<BeaconResp> lastValue = Optional.empty();
        Map<String, Integer> analyzedOutput = retriever.analyzeOutputValue(lastValue);
        assertNull("Method should process empty value correctly", analyzedOutput);
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
