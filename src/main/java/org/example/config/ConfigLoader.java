package org.example.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ConfigLoader {
    public static List<String> loadColumns(String configPath, String fileType) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, List<String>> config = mapper.readValue(new File(configPath), Map.class);
        return config.get(fileType);
    }

    public static Set<String> getSupportedFileTypes(String configPath) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, List<String>> config = mapper.readValue(new File(configPath), Map.class);
        return config.keySet();
    }
}
