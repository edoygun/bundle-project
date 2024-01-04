package com.ercandoygun.listenermongo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RandomData {

    @JsonProperty("randomNumber")
    private int randomNumber;

    @JsonProperty("timestamp")
    private String timestamp;

    @JsonProperty("randomNumberHash")
    private String randomNumberHash;

    @JsonProperty("timestampHash")
    private String timestampHash;
}
