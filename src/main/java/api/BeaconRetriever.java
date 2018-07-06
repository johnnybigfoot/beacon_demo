package api;

import model.BeaconResp;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static io.restassured.RestAssured.given;

public class BeaconRetriever {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(BeaconRetriever.class);

    public Optional<BeaconResp> getLastValue() {
        try {
            String endpoint = "https://beacon.nist.gov/rest/record/last";
            BeaconResp resp = given().expect().statusCode(200).when().get(endpoint).xmlPath().getObject("", BeaconResp.class);
            return Optional.of(resp);
        } catch (Exception e) {
            LOG.error("Unable to get a response from Randomness Beacon");
        }
        return Optional.empty();
    }

    public Map<String, Integer> analyzeOutputValue(Optional<BeaconResp> beaconResp) {
        if (beaconResp.isPresent()) {
            String value = beaconResp.get().getOutputValue();
            LOG.info("Printing analyzed string, only unique characters:");
            Map<String, Integer> result = new HashMap<String, Integer>();
            for (int i = 0; i < value.length(); i++) {
                String symbol = value.substring(i, i + 1);
                result.put(symbol, StringUtils.countMatches(value, symbol));
            }
            return result;
        } else {
            LOG.info("No output value received");
            return null;
        }
    }
}
