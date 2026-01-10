package data;

import java.util.ArrayList;

import business.LogicService;
import domain.Service;

public class ServiceData {
	
	private static  String  filePath = "service.json";
	
	private static JsonUtils<Service> jsonUt = new JsonUtils<Service>(filePath);
	
	
	public ServiceData() {}
	
	public static boolean saveService(Service service) {
		
		if(LogicService.validateCode(getList(), service.getCode())) {
			return false;
		}
		
		try {
			jsonUt.saveElement(service);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e);
			return false;
		}
		
		return true;
	}
	
	public static ArrayList<Service> getList(){
		
		try {
			return (ArrayList<Service>) jsonUt.getAll(Service.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ArrayList<Service>();
		}
	}
	
	public static boolean deleteService(Service service) {
		int i = 0;
		ArrayList<Service> services = getList();
		try {
			for(Service ser: services) {
				if(ser.getCode() == service.getCode()) {
					jsonUt.deleteElement(service, i);
					return true;
				}
				i++;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	
	public static boolean editService(Service service)  {
		
		ArrayList<Service> services = getList();
		int i = 0;
		for(Service ser: services) {
			if(ser.getCode() == service.getCode()) {
				try {
					jsonUt.editElement(service, i);
					return true;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else {
				i++;
			}
		}
		return false;
		
	}
	
	
}