# File Reader System (Adapter Design Pattern)
This project is a Java-based file reader application designed using the Adapter Design Pattern.
It supports reading data from multiple file formats (CSV, JSON, XML, Avro) and allows column configurations dynamically through a config file.
The application also supports virtual threads for high-performance parallel file reading and is easily extensible for adding new file formats.


## Features  
* Multiple file format support: CSV, JSON, XML, Avro  
* Pluggable adapters (auto-discovery) using ServiceLoader  
* Configurable column selection via config.json  
* Virtual threads for efficient parallel file processing  
* Chunk-based reading to handle large files  
* Extensible design – add new adapters without modifying the factory  
* Lightweight and testable architecture

## Architecture Overview

### Design Pattern
* Adapter Pattern – Each file type has an adapter that converts its specific format into a unified DataReader interface.  
* Service Loader (Auto Discovery) – Adapters are automatically registered and discovered at runtime.

### Main Components

| Component |                             Description                             |
|:----------|:-------------------------------------------------------------------:|
| DataReader     |                    Interface for reading files.                     |
| AbstractFileAdapter    | Base adapter implementing chunked file reading and virtual threads. |
| CSVAdapter, JSONAdapter, XMLAdapter, AvroAdapter    |            Concrete adapters for specific file formats.             |
| DataReaderFactory |           Uses ServiceLoader to auto-discover adapters.             |
| ConfigLoader | Reads config.json for column mapping and other configurations. |


## Project Structure
src/main/java

├── org/example  
│&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;├── Main.java  
│&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;├── adapters  
│&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;│&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;├── AbstractFileAdapter.java  
│&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;│&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;├── CSVAdapter.java  
│&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;│&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;├── JSONAdapter.java  
│&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;│&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;├── XMLAdapter.java  
│&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;│&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└── AvroAdapter.java  
│&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;├── adaptees  
│&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;│&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;├── CSVFileReader.java  
│&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;│&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;├── JSONFileReader.java  
│&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;│&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;├── XMLFileReader.java  
│&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;│&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└── AvroFileReader.java  
│&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;├── config  
│&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;│&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└── ConfigLoader.java  
│&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;├── factory  
│&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;│&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└── DataReaderFactory.java  
│&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;├── interfaces  
│&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;│&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└── DataReader.java  
│&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└── models  
│&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└── RowData.java  


## Configuration
config/config.json

json  
```
{
"csv": ["id", "name", "email"],
"json": ["id", "username", "contact"],
"xml": ["id", "name", "email"],
"avro": ["id", "name", "email"]
}
```

This config:  
* Defines the columns to read for each file type.  
* You can add or remove columns without changing any code.


## Running the Application
### 1️⃣ Prerequisites
Java 21+  
Maven 3.8+  
Apache Avro dependency (already in pom.xml)

### 2️⃣ Build
bash  
`mvn clean install`

### 3️⃣ Run
bash  
`mvn exec:java -Dexec.mainClass="org.example.Main"`


## Adding a New File Type
To add a new file format (e.g. Excel):  
1. Create a new adapter class (e.g. ExcelAdapter) extending AbstractFileAdapter.
2. Implement parseFile() for your format.
3. Add it to META-INF/services/org.example.interfaces.DataReader.  

No factory changes are required!
