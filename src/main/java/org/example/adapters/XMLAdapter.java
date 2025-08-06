package org.example.adapters;

import org.example.adaptees.XMLFileReader;

import java.io.File;
import java.util.List;
import java.util.Map;

public class XMLAdapter extends AbstractFileAdapter {
    private final XMLFileReader xmlReader = new XMLFileReader();

    @Override
    protected String getFileExtension() {
        return "xml";
    }

    @Override
    protected List<Map<String, String>> parseFile(File file, List<String> columns) throws Exception {
        return xmlReader.parseXML(file.getAbsolutePath(), columns);
    }
}
