package data;

import java.util.ArrayList;

import business.LogicClient;
import domain.Client;
import presentation.GUINotify;

public class ClientData {
	
	private static  String  filePath = "client.json";
	
	private static JsonUtils<Client> jsonUt = new JsonUtils<Client>(filePath);
	
	
	public ClientData() {}
	
	public static boolean saveClient(Client client) {
		
		if(LogicClient.validateId(client.getId(), getList())) {
			GUINotify.errorData("Cliente con ID ya registrado");
			return false;
		}
		
		try {
			jsonUt.saveElement(client);
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
		
		return true;
	}
	
	public static ArrayList<Client> getList(){
		
		try {
			return (ArrayList<Client>) jsonUt.getAll(Client.class);
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<Client>();
		}
	}
	
	public static boolean deleteClient(Client client) {
		int i = 0;
		ArrayList<Client> clients = getList();
		try {
			for(Client cli: clients) {
				if(cli.getId().equals(cli.getId())) {
					jsonUt.deleteElement(client, i);
					return true;
				}else {
					i++;
				}
			}
		} catch (Exception e) {
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