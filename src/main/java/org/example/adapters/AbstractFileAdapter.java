package org.example.adapters;

import org.example.interfaces.DataReader;
import org.example.models.RowData;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public abstract class AbstractFileAdapter implements DataReader {
    protected abstract String getFileExtension();
    protected abstract List<Map<String, String>> parseFile(File file, List<String> columns) throws Exception;

    @Override
    public String getSupportedFileType() {
        return getFileExtension().toLowerCase();
    }

    @Override
    public List<RowData> readData(String directoryPath, List<String> columns) {
        List<RowData> allRows = new ArrayList<>();
        readDataInChunks(directoryPath, columns, Integer.MAX_VALUE, allRows::addAll);
        return allRows;
    }

    @Override
    public void readDataInChunks(String directoryPath, List<String> columns, int chunkSize, Consumer<List<RowData>> chunkConsumer) {
        File dir = new File(directoryPath);
        if (!dir.exists() || !dir.isDirectory()) {
            throw new IllegalArgumentException("Invalid directory: " + directoryPath);
        }

        File[] files = dir.listFiles((d, name) -> name.toLowerCase().endsWith("." + getFileExtension()));
        if (files == null || files.length == 0) return;

        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            List<Future<Void>> futures = new ArrayList<>();

            for (File file : files) {
                futures.add(executor.submit(() -> {
                    try {
                        List<Map<String, String>> allRows = parseFile(file, columns);
                        for (int i = 0; i < allRows.size(); i += chunkSize) {
                            int end = Math.min(i + chunkSize, allRows.size());
                            List<RowData> chunk = allRows.subList(i, end).stream()
                                    .map(RowData::new)
                                    .collect(Collectors.toList());
                            chunkConsumer.accept(chunk);
                        }
                    } catch (Exception e) {
                        System.err.println(getFileExtension().toUpperCase() + " Read failed: "
                                + file.getName() + " – " + e.getMessage());
                    }
                    return null;
                }));
            }

            for (Future<Void> future : futures) {
                future.get();
            }
        } catch (Exception e) {
            throw new RuntimeException("Error processing " + getFileExtension().toUpperCase() + " files", e);
        }
    }
}
