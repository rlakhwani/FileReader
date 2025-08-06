package org.example.adaptees;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CSVFileReader {
    public List<Map<String, String>> parseCSV(String filePath, List<String> configuredColumns) throws Exception {
        List<Map<String, String>> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String headerLine = br.readLine();
            if (headerLine == null) return records;

            String[] headers = headerLine.split(",");
            Map<String, Integer> columnIndexMap = new LinkedHashMap<>();

            // Map header names to their positions
            for (int i = 0; i < headers.length; i++) {
                columnIndexMap.put(headers[i].trim(), i);
            }

            // Read each row
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                Map<String, String> filteredRow = new LinkedHashMap<>();

                // Only add configured columns
                for (String col : configuredColumns) {
                    Integer index = columnIndexMap.get(col);
                    filteredRow.put(col, (index != null && index < values.length) ? values[index] : "");
                }
                records.add(filteredRow);
            }
        }
        return records;
    }
}
