package com.plateer.ec1.common.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Map;

public class ObjectMapperUtil {

    private final static ObjectMapper mapper = new ObjectMapper();
    private final static Gson gson = new Gson();

    public static <T> MultiValueMap<String, String> convertMultiValueMap(T t) {
        Map<String, String> map = mapper.convertValue(t, new TypeReference<Map<String, String>>() {});
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.setAll(map);
        return multiValueMap;
    }

    public static <T> T convertJsonIntoObject(String str, Class<T> type) {
        return gson.fromJson(str, type);
    }

    public static <T> String toJson(T obj){
        return gson.toJson(obj);
    }

}
