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

    public List<T> getAll(Class<T> temp) throws Exception {
		File file = new File(this.filePath);
		if(!file.exists()) {
			return new ArrayList<T>();
		}
		return mapper.readValue(file, mapper.getTypeFactory().
				constructCollectionType(List.class, temp));
	}
	
	public void saveElement (T t) throws Exception{
		List<T> temp = getAll((Class<T>) t.getClass());
		temp.add(t);
		mapper.writeValue(new File(filePath), temp);
	}
    
	public void deleteElement(T t, int index) throws Exception {
		List<T> temp = getAll((Class<T>) t.getClass());
		temp.remove(index);
		mapper.writeValue(new File(filePath), temp);
	}
	
	public void editElement(T t, int index) throws Exception {
		List<T> list = getAll((Class<T>) t.getClass());
		list.set(index, t);
		mapper.writeValue(new File(filePath), list);
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