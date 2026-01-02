package domain;

public class Vehicle {
	
	private String plate;
	private String brand;
	private String model;
	private int year;
	private String fuelType;
	private Client client;
	
	public Vehicle() {
		
	}
	
	public Vehicle(String plate, String brand, String model, int year, String fuelType, Client client) {
		super();
		this.plate = plate;
		this.brand = brand;
		this.model = model;
		this.year = year;
		this.fuelType = fuelType;
		this.client = client;
	}

	public String getPlate() {
		return plate;
	}
	
	public void setPlate(String plate) {
		this.plate = plate;
	}
	
	public String getBrand() {
		return brand;
	}
	
	public void setBrand(String brand) {
		this.brand = brand;
	}
	
	public String getModel() {
		return model;
	}
	
	public void setModel(String model) {
		this.model = model;
	}
	
	public int getYear() {
		return year;
	}
	
	public void setYear(int year) {
		this.year = year;
	}
	
	public String getFuelType() {
		return fuelType;
	}
	
	public void setFuelType(String fuelType) {
		this.fuelType = fuelType;
	}
	
	public Client getClient() {
		return client;
	}
	
	public void setClient(Client client) {
		this.client = client;
	}
	
	public String toString() {
		return plate+"-"+brand+"-"+model+"-"+year+"-"+fuelType+"-"+client.id;
	}
	
	
}
