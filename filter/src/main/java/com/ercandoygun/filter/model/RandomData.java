package com.ercandoygun.filter.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RandomData {

    private static final int MESSAGE_QUEUE_THRESHOLD = 90;

    @JsonProperty("randomNumber")
    private int randomNumber;

    @JsonProperty("timestamp")
    private String timestamp;

    @JsonProperty("randomNumberHash")
    private String randomNumberHash;

    @JsonProperty("timestampHash")
    private String timestampHash;

    public boolean isRedirected() {
        return randomNumber > MESSAGE_QUEUE_THRESHOLD;
    }
}
