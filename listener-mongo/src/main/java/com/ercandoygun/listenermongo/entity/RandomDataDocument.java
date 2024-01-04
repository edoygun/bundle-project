package com.ercandoygun.listenermongo.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Document
public class RandomDataDocument {

    @Id
    private String id;
    private int randomNumber;
    private String timestamp;
    private String randomNumberHash;
    private String timestampHash;
    private List<RandomDataDocument> nestedRandomDataList = new ArrayList<>();
}
