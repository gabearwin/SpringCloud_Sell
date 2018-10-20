package com.imooc.order.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

/**
 * Created by 廖师兄
 * 2018-02-21 10:40
 */
public class JsonUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 转换为json字符串
     *
     * @param object
     * @return
     */
    public static String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * json转对象
     *
     * @param json
     * @param classType
     * @return
     */
    public static <T> T toBean(String json, Class<T> classType) {
        try {
            return objectMapper.readValue(json, classType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> List<T> toList(String json, TypeReference typeReference) {
        try {
            return objectMapper.readValue(json, typeReference);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
