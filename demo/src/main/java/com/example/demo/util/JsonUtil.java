package com.example.demo.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.*;

/**
 * @Author: hzwangwenting-01
 * @Description: Json序列化工具类
 * @Date: 2017/6/29 下午10:32
 */

public class JsonUtil {

    /**
     * json mapper(这里的 ObjectMapper 是线程安全的)
     */
    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

    static {
        // 未匹配的属性不解析
        JSON_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // 使用系统默认时区
        JSON_MAPPER.setTimeZone(TimeZone.getDefault());
    }

    /**
     * private constructor
     */
    private JsonUtil() {
    }

    /**
     * 对象装json字符串 <p>
     *
     * @return json string
     */
    public static String toJsonString(Object object) {
        try {
            return JSON_MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Json serialization exception.", e);
        }
    }

    /**
     * string 转化为 map
     *
     * @param json
     * @param classT
     * @param classV
     * @param <T>
     * @param <V>
     * @return
     */
    public static <T, V> Map<T, V> stringToMap(String json, Class<T> classT, Class<V> classV) {
        try {
            return JSON_MAPPER.readValue(json, new TypeReference<Map<T, V>>(){});
        } catch (Exception e) {
            throw new RuntimeException("Json反序列化异常", e);
        }
    }

    /**
     * 反序列化一个 json 字符串
     *
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T parseObject(String json, Class<T> clazz) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }

        try {
            return JSON_MAPPER.readValue(json, clazz);
        } catch (IOException e) {
            System.out.println(e);
            throw new RuntimeException("Json deserialization exception.", e);
        }
    }

    /**
     * json 转对象
     *
     * @param json
     * @return
     */
    public static Map<String, String> parseObjectMap(String json) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        try {
            return JSON_MAPPER.readValue(json, new TypeReference<HashMap<String, String>>() {
            });
        } catch (IOException e) {
            System.out.println(e);
            throw new RuntimeException("Json deserialization exception.", e);
        }
    }

    /**
     * 反序列化一个 json 字符串
     *
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> parseObjectList(String json, Class<T> clazz) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        try {
            JavaType javaType = JSON_MAPPER.getTypeFactory().constructParametricType(List.class, clazz);
            return JSON_MAPPER.readValue(json, javaType);
        } catch (IOException e) {
            throw new RuntimeException("Json deserialization exception.", e);
        }
    }

    /**
     * 反序列化一个 json 字符串
     * @param json
     * @param clazz
     * @param <T>
     * @return List - all always not null
     */
    public static <T> List<T> parseObjectNonNullList(String json, Class<T> clazz) {
        List<T> list = parseObjectList(json, clazz);
        if (list == null ){
            return new ArrayList<>(0);
        }
        return list ;
    }


    /**
     * 获取 Json 树
     *
     * @param json
     * @return
     */
    public static JsonNode readTree(String json) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }

        try {
            return JSON_MAPPER.readTree(json);
        } catch (IOException e) {
            throw new RuntimeException("Json parse exception.", e);
        }
    }

    /**
     * 判断是否是一个 json object
     *
     * @param json
     * @return
     */
    public static boolean isJsonNode(String json) {
        if (json == null) {
            return true;
        }

        return readTree(json) != null;
    }

    /**
     * 获取字段的值（支持深度搜索）<br/>
     * 多个相同字段的情况下，获取节点顺序的第一个 <p>
     *
     * @param jsonNode json tree
     * @param field    字段名
     * @return {@link JsonNode}
     */
    public static String findNodeByField(JsonNode jsonNode, String field) {
        JsonNode node = jsonNode.findValue(field);

        if (node == null) {
            return null;
        }

        return node.toString();
    }
}
