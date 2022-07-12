package com.plateer.ec1.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JsonFileReader {

    private static final String TEST_RESOURCE_PATH = "./src/test/java/com/plateer/ec1/resource/";

    public static <T> T getObject(String fileName, Class<T> valueType) {
        Path currentFile = Paths.get(TEST_RESOURCE_PATH + fileName);
        ObjectMapper mapper = new ObjectMapper();

        T t=null;
        try {
            t = mapper.readValue(currentFile.toFile(), valueType);
        } catch (IOException e) {
            throw new RuntimeException("파일을 읽을 수 없습니다.", e);
        }

        return t;
    }


}
