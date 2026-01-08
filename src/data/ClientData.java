package data;

import java.util.ArrayList;

import business.LogicClient;
import domain.Client;
import presentation.GUINotify;

public class ClientData {
	
	private static  String  filePath = "client.json";
	
	private static JsonUtils<Client> clients = new JsonUtils<Client>(filePath);
	
	
	public ClientData() {}
	
	public static boolean saveClient(Client client) {
		
		if(LogicClient.validateId(client.getId(), getList())) {
			GUINotify.errorData("Cliente con ID ya registrado");
			return false;
		}
		
		try {
			clients.saveElement(client);
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
		
		return true;
	}
	
	public static ArrayList<Client> getList(){
		
		try {
			return (ArrayList<Client>) clients.getAll(Client.class);
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<Client>();
		}
	}
	
	
}
