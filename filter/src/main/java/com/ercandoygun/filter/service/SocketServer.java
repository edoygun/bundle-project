package com.ercandoygun.filter.service;

import com.ercandoygun.filter.model.RandomData;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

@Slf4j
@Component
public class SocketServer {

    @Value("${socket.server.host}")
    private String socketServerHost;

    @Value("${socket.server.port}")
    private int socketServerPort;

    @Autowired
    private KafkaProducer producer;

    @Autowired
    private FileService fileService;

    @PostConstruct
    public void init() {
        connectAndListen();
    }

    private final ObjectMapper objectMapper = new ObjectMapper();

    private ServerSocket serverSocket;
    private Socket socket;
    private InputStreamReader inputStreamReader;
    private BufferedReader reader;
    private void connectAndListen() {
        try {
            serverSocket = new ServerSocket(socketServerPort);
            socket = serverSocket.accept();
            inputStreamReader = new InputStreamReader(socket.getInputStream());
            reader = new BufferedReader(inputStreamReader);
        }catch (IOException e) {
            e.printStackTrace();
        }

        while(true) {
            try {
                String line;
                while ((line = reader.readLine()) != null) {
                    RandomData randomData = objectMapper.readValue(line, RandomData.class);
                    log.info("Random data has been received: {}", randomData);
                    processOrRedirect(randomData);
                }
            } catch (Exception e) {
                log.error("Something went wrong while receiving random data from the socket.", e);
            }
        }
    }

    private void processOrRedirect(RandomData data) {
        if (data.isRedirected()) {
            producer.sendMessage(data);
        } else {
            fileService.appendDataToFile(data);
        }
    }

    @PreDestroy
    public void cleanup() {
        try {
            fileService.closeWriter();
            socket.close();
            inputStreamReader.close();
            reader.close();
        } catch (IOException e) {
            log.error("Something went wrong while trying to close the socket gracefully.", e);
        }
    }
}