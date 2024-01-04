package com.ercandoygun.listenerh2.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RandomDataEntity {

    @Id
    @GeneratedValue
    private int id;
    private int randomNumber;
    private String timestamp;
    private String randomNumberHash;
    private String timestampHash;
}
