package org.example.interfaces;

import org.example.models.RowData;

import java.util.List;
import java.util.function.Consumer;

public interface DataReader {
    String getSupportedFileType();

    List<RowData> readData(String directoryPath, List<String> columns);

    void readDataInChunks(String directoryPath, List<String> columns, int chunkSize, Consumer<List<RowData>> chunkConsumer);
}
