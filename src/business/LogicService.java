package business;

import java.util.ArrayList;

import domain.Service;

public class LogicService {

	public static boolean validateCode(ArrayList<Service> list, int code) {
		
		for(Service s: list) {
			if(s.getCode() == code) {
				return true;
			}
		}
		
		return false;
		
	}
	
	public static Service searchService(ArrayList<Service> list, int code) {
		
		for(Service s: list) {
			if(s.getCode() == code) {
				return s;
			}
		}
		
		return null;
	}
	
}
