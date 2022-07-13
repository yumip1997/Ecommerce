package com.plateer.ec1.common.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
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
        T t = null;
        try {
            t = objectMapper.readValue(currentFile.toFile(), type);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return t;
    }
}
