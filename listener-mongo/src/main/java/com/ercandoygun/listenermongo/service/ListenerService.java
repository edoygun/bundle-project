package com.ercandoygun.listenermongo.service;

import com.ercandoygun.listenermongo.dao.RandomDataRepository;
import com.ercandoygun.listenermongo.entity.RandomDataDocument;
import com.ercandoygun.listenermongo.model.RandomData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Slf4j
@Service
public class ListenerService {

    @Autowired
    private RandomDataRepository randomDataRepository;

    @KafkaListener(topics = "randomDataTopic")
    public void handleRandomData(RandomData data) {
        log.info("Message received from randomDataTopic: {}", data);

        int randomNumber = data.getRandomNumber();
        String timestamp = data.getTimestamp();
        String randomNumberHash = data.getRandomNumberHash();
        String timestampHash = data.getTimestampHash();

        RandomDataDocument messageDocument = new RandomDataDocument();
        messageDocument.setRandomNumber(randomNumber);
        messageDocument.setTimestamp(timestamp);
        messageDocument.setRandomNumberHash(randomNumberHash);
        messageDocument.setTimestampHash(timestampHash);

        if (parseHex(randomNumberHash) > 90 && parseHex(timestampHash) > 99) {
            RandomDataDocument lastDoc = randomDataRepository.findFirstByOrderByIdDesc();

            if (lastDoc != null) {
                lastDoc.getNestedRandomDataList().add(messageDocument);
                randomDataRepository.save(lastDoc);
                log.info("The random data message was saved as part of a nested list.");
                return;
            }
        }

        randomDataRepository.save(messageDocument);
        log.info("The random data message was saved as a seperate document.");
    }

    private static int parseHex(String hexString) {
        return new BigInteger(hexString, 16).intValue();
    }
}
