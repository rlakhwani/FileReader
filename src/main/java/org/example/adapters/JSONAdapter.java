package org.example.adapters;

import org.example.adaptees.JSONFileReader;

import java.io.File;
import java.util.List;
import java.util.Map;

public class JSONAdapter extends AbstractFileAdapter {
    private final JSONFileReader jsonReader = new JSONFileReader();

    @Override
    protected String getFileExtension() {
        return "json";
    }

    @Override
    protected List<Map<String, String>> parseFile(File file, List<String> columns) throws Exception {
        return jsonReader.parseJSON(file.getAbsolutePath(), columns);
    }
}
