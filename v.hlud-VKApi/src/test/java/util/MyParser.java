package util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;

@Log4j
public class MyParser {
    private static final String CONFIG_SRC = "/ConfigData.json";
    private final static String CRED_SRC = "/Credentials.json";
    private final static String TEST_SOURCE = "/TestData.json";
    private static JSONParser parser;
    private static ObjectMapper objectMapper;

    static {
        parser = new JSONParser();
        objectMapper = new ObjectMapper();
    }

    public static <T> T getTestValue(String key) {
        JSONObject data = null;
        try {
            data = (JSONObject) parser.parse(new FileReader(FileProvider.getPath(TEST_SOURCE)));
        } catch (Exception e) {
            log.error("parsing error", e);
        }
        return (T) data.get(key);
    }

    public static <T> T getConfigValue(String key) {
        JSONObject data = null;
        try {
            data = (JSONObject) parser.parse(new FileReader(FileProvider.getPath(CONFIG_SRC)));
        } catch (Exception e) {
            log.error("parsing error", e);
        }
        return (T) data.get(key);
    }

    public static <T> T getCredentialsValue(String key) {
        JSONObject data = null;
        try {
            data = (JSONObject) parser.parse(new FileReader(FileProvider.getPath(CRED_SRC)));
        } catch (Exception e) {
            log.error("parsing error", e);
        }
        return (T) data.get(key);
    }

    public static <T> T getObjectFromString(String jsonObject, Class<T> tClass) {
        T obj = null;
        try {
            obj = objectMapper.readValue(jsonObject, tClass);
        } catch (JsonProcessingException e) {
            log.error("parser error");
        }
        return obj;
    }
}