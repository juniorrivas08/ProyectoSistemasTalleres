package data;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class JsonUtils <T>{
	//Ruta
	private String filePath;
	
	//Manejo
	private static final ObjectMapper mapper = new ObjectMapper().
			registerModule(new JavaTimeModule());
	
	public JsonUtils(String path) {
		this.filePath = path;
	}
	
	public List<T> getAll(Class<T> temp) throws Exception {
		File file = new File(this.filePath);
		if(!file.exists()) {
			return new ArrayList<T>();
		}
		return this.mapper.readValue(file, mapper.getTypeFactory().
				constructCollectionType(List.class, temp));
	}
	
	public void saveElement (T t) throws Exception{
		List<T> temp = getAll((Class<T>) t.getClass());
		temp.add(t);
		
		this.mapper.writeValue(new File(filePath), temp);
	}
}
