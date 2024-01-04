package com.ercandoygun.listenerh2.model;

import com.ercandoygun.listenerh2.entity.RandomDataEntity;
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

    public RandomDataEntity toEntity() {
        RandomDataEntity entity = new RandomDataEntity();
        entity.setRandomNumber(this.randomNumber);
        entity.setTimestamp(this.timestamp);
        entity.setRandomNumberHash(this.randomNumberHash);
        entity.setTimestampHash(this.timestampHash);
        return entity;
    }
}
