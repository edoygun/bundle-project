package com.ercandoygun.randomgenerator.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.commons.codec.digest.DigestUtils;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class RandomData {

    @JsonProperty("randomNumber")
    private int randomNumber;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("timestamp")
    private String timestamp;

    @JsonProperty("randomNumberHash")
    private String randomNumberHash;

    @JsonProperty("timestampHash")
    private String timestampHash;

    public RandomData(int randomNumber) {
        this.randomNumber = randomNumber;
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.timestampHash = generateHashAndRetrieveLastTwo(String.valueOf(this.timestamp));
        this.randomNumberHash = generateHashAndRetrieveLastTwo(String.valueOf(this.randomNumber));
    }

    private String generateHashAndRetrieveLastTwo(String input) {
        String hashedVer = DigestUtils.md5Hex(input);
        return hashedVer.substring(hashedVer.length() - 2);
    }
}
