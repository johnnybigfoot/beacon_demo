package runner;

import api.BeaconRetriever;
import model.BeaconResp;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Optional;

public class Runner {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(Runner.class);

    public static void main(String[] args) {
        BeaconRetriever retriever = new BeaconRetriever();
        Optional<BeaconResp> lastValue = retriever.getLastValue();
        LOG.info("Response received");
        Map<String, Integer> analyzedValue = retriever.analyzeOutputValue(lastValue);
        for (String key : analyzedValue.keySet()) {
            LOG.info(key + "," + analyzedValue.get(key));
        }
    }
}
