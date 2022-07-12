package com.plateer.ec1.common.utils;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

@Slf4j
public class JsonReaderUtil {

    private final String FILE_PATH;
    private final Gson gson = new Gson();

    public JsonReaderUtil(String filetPath){
        this.FILE_PATH = filetPath;
    }

    public <T> T getObject(String fileName, Class<T> type) {
        Reader reader = null;
        try {
            reader = new FileReader(FILE_PATH + fileName);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return gson.fromJson(reader, type);
    }
}
