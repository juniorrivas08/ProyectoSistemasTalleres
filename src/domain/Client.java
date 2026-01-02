package domain;

import java.util.LinkedList;

public class Client extends Person {
	
	private String address;
	private LinkedList<Vehicle> vehicles;
	
	public Client() {
		
	}
	
	public Client(String id, String name, String phone, String mail, String address) {
		super(id, name, phone, mail);
		this.address = address;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
		
	public LinkedList<Vehicle> getVehicles() {
		return vehicles;
	}

	public void setVehicles(LinkedList<Vehicle> vehicles) {
		this.vehicles = vehicles;
	}

	public String toString() {
		return super.toString()+address;
	}
	
}
