package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BeaconResp {
    private long frequency;
    private long timestamp;
    private String seedValue;
    private String previousOutputValue;
    private String signatureValue;
    private String outputValue;
    private int statusCode;
}
