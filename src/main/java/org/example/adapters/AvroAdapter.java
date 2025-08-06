package org.example.adapters;

import org.example.adaptees.AvroFileReader;

import java.io.File;
import java.util.List;
import java.util.Map;

public class AvroAdapter extends AbstractFileAdapter {
    private final AvroFileReader avroReader = new AvroFileReader();

    @Override
    protected String getFileExtension() {
        return "avro";
    }

    @Override
    protected List<Map<String, String>> parseFile(File file, List<String> columns) throws Exception {
        return avroReader.parseAvro(file.getAbsolutePath(), columns);
    }
}
