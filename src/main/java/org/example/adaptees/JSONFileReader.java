package org.example.adaptees;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class JSONFileReader {
    public List<Map<String, String>> parseJSON(String filePath, List<String> columns) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        List<Map<String, Object>> rawList = mapper.readValue(new File(filePath), List.class);

        List<Map<String, String>> filtered = new ArrayList<>();
        for (Map<String, Object> raw : rawList) {
            Map<String, String> filteredRow = new LinkedHashMap<>();
            for (String col : columns) {
                filteredRow.put(col, raw.getOrDefault(col, "").toString());
            }
            filtered.add(filteredRow);
        }
        return filtered;
    }
}
