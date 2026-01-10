package data;

import java.util.ArrayList;

import business.LogicClient;
import domain.Client;

public class ClientData {
	
	private static  String  filePath = "client.json";
	
	private static JsonUtils<Client> jsonUt = new JsonUtils<Client>(filePath);
	
	
	public ClientData() {}
	
	public static boolean saveClient(Client client) {
		
		if(LogicClient.validateId(client.getId(), getList())) {
			return false;
		}
		
		try {
			jsonUt.saveElement(client);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e);
			return false;
		}
		
		return true;
	}
	
	public static ArrayList<Client> getList(){
		
		try {
			return (ArrayList<Client>) jsonUt.getAll(Client.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ArrayList<Client>();
		}
	}
	
	public static boolean deleteClient(Client client) {
		int i = 0;
		ArrayList<Client> clients = getList();
		try {
			for(Client cli: clients) {
				if(cli.getId().equals(client.getId())) {
					jsonUt.deleteElement(client, i);
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
	
	
	public static boolean editClient(Client client)  {
		
		ArrayList<Client> clients = getList();
		int i = 0;
		for(Client cli: clients) {
			if(cli.getId().equals(client.getId())) {
				try {
					jsonUt.editElement(client, i);
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