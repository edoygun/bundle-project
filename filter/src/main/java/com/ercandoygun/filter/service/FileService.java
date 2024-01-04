package com.ercandoygun.filter.service;

import com.ercandoygun.filter.model.RandomData;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Slf4j
@Service
public class FileService {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final String FILE_PATH = "output.json";

    private FileWriter fileWriter;

    public FileService() {
        initFileWriter();
    }

    private void initFileWriter() {
        try {
            File file = new File(FILE_PATH);
            fileWriter = new FileWriter(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void appendDataToFile(RandomData data) {
        try {
            String jsonDto = objectMapper.writeValueAsString(data);
            fileWriter.write(jsonDto);
            fileWriter.write(System.lineSeparator());
            System.out.println("Data appended to the file: " + FILE_PATH);
        } catch (IOException e) {
            System.err.println("Error appending data to the file: " + e.getMessage());
        }
    }

    public void closeWriter() {
        try {
            fileWriter.close();
        } catch (IOException e) {
            log.error("Something went wrong while closing the file", e);
        }
    }
}
