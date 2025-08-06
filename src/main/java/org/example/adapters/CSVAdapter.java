package org.example.adapters;

import org.example.adaptees.CSVFileReader;

import java.io.File;
import java.util.List;
import java.util.Map;

public class CSVAdapter extends AbstractFileAdapter {
    private CSVFileReader csvReader = new CSVFileReader();

    @Override
    protected String getFileExtension() {
        return "csv";
    }

    @Override
    protected List<Map<String, String>> parseFile(File file, List<String> columns) throws Exception {
        return csvReader.parseCSV(file.getAbsolutePath(), columns);
    }
}
