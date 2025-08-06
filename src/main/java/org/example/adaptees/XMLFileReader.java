package org.example.adaptees;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class XMLFileReader {
    public List<Map<String, String>> parseXML(String filePath, List<String> columns) throws Exception {
        XmlMapper xmlMapper = new XmlMapper();

        // Assuming XML structure: <records><record>...</record></records>
        Map<String, List<Map<String, Object>>> data = xmlMapper.readValue(new File(filePath), Map.class);

        List<Map<String, Object>> records = (List<Map<String, Object>>) data.getOrDefault("record", List.of());
        List<Map<String, String>> filtered = new ArrayList<>();

        for (Map<String, Object> record : records) {
            Map<String, String> filteredRow = new LinkedHashMap<>();
            for (String col : columns) {
                filteredRow.put(col, record.getOrDefault(col, "").toString());
            }
            filtered.add(filteredRow);
        }

        return filtered;
    }
}
