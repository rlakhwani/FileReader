package org.example;

import org.example.config.ConfigLoader;
import org.example.factory.DataReaderFactory;
import org.example.interfaces.DataReader;

import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws Exception {
        String configPath = "config/config.json";

        Set<String> fileTypes = ConfigLoader.getSupportedFileTypes(configPath);

        for (String fileType : fileTypes) {
            List<String> columns = ConfigLoader.loadColumns(configPath, fileType);
            DataReader reader = DataReaderFactory.getReader(fileType);

            String directory = "data/" + fileType + "_files/";

            /*List<RowData> data = reader.readData(directory, columns);
            System.out.println("\n" + fileType.toUpperCase() + " Data:");
            data.forEach(System.out::println);*/

            reader.readDataInChunks(directory, columns, 100, chunk -> {
                System.out.println("\n" + fileType.toUpperCase() + " Data:");
                chunk.forEach(System.out::println);
            });
        }
    }
}