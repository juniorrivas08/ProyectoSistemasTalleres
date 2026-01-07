package data;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class JsonUtils<T> {
    private String filePath;
    private static final ObjectMapper mapper;
    
    static {
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    public JsonUtils(String path) {
        this.filePath = path;
    }

    public List<T> getAll(Class<T> clazz) throws IOException {
        File file = new File(this.filePath);
        
        if (!file.exists() || file.length() == 0) {
            return new ArrayList<>();
        }

        try {
            return mapper.readValue(file, 
                mapper.getTypeFactory().constructCollectionType(List.class, clazz));
        } catch (Exception e) {
       
            System.err.println("Archivo JSON corrupto. Creando backup y archivo nuevo.");
            File backup = new File(this.filePath + ".backup");
            file.renameTo(backup);
            return new ArrayList<>();
        }
    }

    public void saveElement(T element, Class<T> clazz) throws IOException {
        List<T> list = getAll(clazz);
        list.add(element);
        saveAll(list);
    }
    
    public void saveAll(List<T> list) throws IOException {
        File file = new File(filePath);
        
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }
        
        mapper.writerWithDefaultPrettyPrinter().writeValue(file, list);
    }
}