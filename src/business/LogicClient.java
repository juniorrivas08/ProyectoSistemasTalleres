package business;

import java.util.ArrayList;

import domain.Client;

public class LogicClient {

	public static boolean validateId(String data, ArrayList<Client> list) {
		
		for(Client c: list) {
			if(c.getId().equals(data)) {
				return true;
			}
		}
		
		return false;
		
	}
	
}
