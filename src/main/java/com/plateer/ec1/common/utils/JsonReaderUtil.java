package com.plateer.ec1.common.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
public class JsonReaderUtil {

    private final String FILE_PATH;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public JsonReaderUtil(String filetPath){
        this.FILE_PATH = filetPath;
    }

    public <T> T getObject(String fileName, Class<T> type) {
        Path currentFile = Paths.get(FILE_PATH + fileName);
        try {
            return objectMapper.readValue(currentFile.toFile(), type);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
