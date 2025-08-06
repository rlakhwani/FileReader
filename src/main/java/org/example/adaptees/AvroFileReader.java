package org.example.adaptees;

import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.FileReader;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericRecord;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AvroFileReader {

    public List<Map<String, String>> parseAvro(String filePath, List<String> columns) throws Exception {
        List<Map<String, String>> records = new ArrayList<>();
        File avroFile = new File(filePath);

        try (FileReader<GenericRecord> dataFileReader = DataFileReader.openReader(avroFile, new GenericDatumReader<>())) {
            while (dataFileReader.hasNext()) {
                GenericRecord record = dataFileReader.next();
                Map<String, String> row = new LinkedHashMap<>();
                for (String col : columns) {
                    Object value = record.get(col);
                    row.put(col, value != null ? value.toString() : "");
                }
                records.add(row);
            }
        }
        return records;
    }
}
