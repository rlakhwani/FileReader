package org.example.factory;

import org.example.interfaces.DataReader;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

public class DataReaderFactory {
    private static final Map<String, DataReader> adapterRegistry = new HashMap<>();

    static {
        ServiceLoader<DataReader> loader = ServiceLoader.load(DataReader.class);
        for (DataReader adapter : loader) {
            adapterRegistry.put(adapter.getSupportedFileType(), adapter);
        }
    }

    public static DataReader getReader(String fileType) {
        DataReader adapter = adapterRegistry.get(fileType.toLowerCase());
        if (adapter == null) {
            throw new IllegalArgumentException("Unsupported file type: " + fileType);
        }
        return adapter;

        /*return switch (fileType.toLowerCase()) {
            case "csv" -> new CSVAdapter();
            case "json" -> new JSONAdapter();
            case "xml" -> new XMLAdapter();
            default -> throw new IllegalArgumentException("Unsupported type: " + fileType);
        };*/
    }
}
