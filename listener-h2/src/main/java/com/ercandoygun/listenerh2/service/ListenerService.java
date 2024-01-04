package com.ercandoygun.listenerh2.service;

import com.ercandoygun.listenerh2.dao.RandomDataRepository;
import com.ercandoygun.listenerh2.entity.RandomDataEntity;
import com.ercandoygun.listenerh2.model.RandomData;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ListenerService {

    @Autowired
    private RandomDataRepository randomDataRepository;
    private static final int BATCH_SIZE = 60;

    private final List<RandomDataEntity> dataList = new ArrayList<>();

    @KafkaListener(topics = "randomDataTopic")
    public void handleRandomData(RandomData data) {
        log.info("Message receiver from randomDataTopic: {}", data);

        dataList.add(data.toEntity());

        if (dataList.size() == BATCH_SIZE) {
            saveDataList();
        }
    }

    @PreDestroy
    public void flushDataListOnShutdown() {
        if (!dataList.isEmpty()) {
            saveDataList();
        }
    }

    private void saveDataList() {
        randomDataRepository.saveAll(dataList);
        dataList.clear();
    }
}
