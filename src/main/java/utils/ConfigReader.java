package utils;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;

public class ConfigReader {
    private static JSONObject config;

    static {
        try {
            JSONParser parser = new JSONParser();
            FileReader reader = new FileReader("src/main/resources/configurations/App-config.json");
            config = (JSONObject) parser.parse(reader);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load configuration: " + e.getMessage());
        }
    }

    public static String get(String key) {
        return config.get(key).toString();
    }

    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(config.get(key).toString());
    }

    public static int getInt(String key) {
        return Integer.parseInt(config.get(key).toString());
    }
}
