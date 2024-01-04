package com.ercandoygun.randomgenerator.service;

import com.ercandoygun.randomgenerator.model.RandomData;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class SocketClient {

    @Value("${socket.server.host}")
    private String socketServerHost;

    @Value("${socket.server.port}")
    private int socketServerPort;

    private final Random random = new Random();

    private final ObjectMapper objectMapper = new ObjectMapper();

    private Socket clientSocket;
    private OutputStreamWriter outputStreamWriter;
    private BufferedWriter writer;
    private ScheduledExecutorService executorService;

    private boolean stopExecution = false;

    @PostConstruct
    public void init() {
        try {
            clientSocket = new Socket(socketServerHost, socketServerPort);
            outputStreamWriter = new OutputStreamWriter(clientSocket.getOutputStream());
            writer = new BufferedWriter(outputStreamWriter);
            executorService = Executors.newScheduledThreadPool(1);
            executorService.scheduleAtFixedRate(this::generateAndSendRandomData, 0, 1000, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            log.error("Exception occured while initializing the socket client.", e);
        }

    }

    public void generateAndSendRandomData() {
        if (stopExecution) {
            log.warn("Execution has been stopped due to a previous exception.");
            return;
        }

        List<RandomData> randomNumbers = new ArrayList<>();

        try {
            for (int i = 0; i < 5; i++) {
                int randomNumber = random.nextInt(100);
                RandomData each = new RandomData(randomNumber);
                randomNumbers.add(each);
            }
            sendToSocket(randomNumbers);
        } catch (Exception e) {
            log.error("Something went wrong sending data to the socket:", e);
            stopExecution = true;
        }
    }

    private void sendToSocket(List<RandomData> randomDataList) {
        randomDataList.forEach(data ->
                {
                    try {
                        String jsonStr = objectMapper.writeValueAsString(data);
                        writer.write(jsonStr);
                        writer.newLine();
                        writer.flush();
                        log.info("Random data has been sent to the socket: {}", jsonStr);
                    } catch (IOException e) {
                        log.error("Exception while sending data to the socket!", e);
                    }
                });
    }

    @PreDestroy
    public void cleanup() {
        try {
            clientSocket.close();
            outputStreamWriter.close();
            writer.close();
        } catch (IOException e) {
            log.error("Something went wrong while trying to close the socket gracefully.", e);
        }
    }
}