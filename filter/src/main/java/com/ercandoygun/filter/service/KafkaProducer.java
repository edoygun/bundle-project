package com.ercandoygun.filter.service;

import com.ercandoygun.filter.model.RandomData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaProducer {

    private static final String TOPIC_NAME = "randomDataTopic";

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(RandomData data) {
        try {
            kafkaTemplate.send(TOPIC_NAME, objectMapper.writeValueAsString(data));
            log.info("Random data message sent successfuly.");
        } catch (JsonProcessingException e) {
            log.error("Something went wrong while serializing the random data.", e);
        }
    }

}
