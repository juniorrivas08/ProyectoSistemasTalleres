package domain;

import java.time.LocalDate;
import java.util.LinkedList;

public class WorkOrder {
	
	private int numberOrder;
	private LocalDate creationDate;
	private String status;
	private Vehicle vehicle;
	private Client client;
	private Mechanic mechanic;
	private LinkedList<Service> services;
	private String observation;
	private double totalCost;
	
	public WorkOrder(int numberOrder, LocalDate creationDate, String status, Vehicle vehicle, Client client,
			Mechanic mechanic, LinkedList<Service> services, String observation, double totalCost) {
		super();
		this.numberOrder = numberOrder;
		this.creationDate = creationDate;
		this.status = status;
		this.vehicle = vehicle;
		this.client = client;
		this.mechanic = mechanic;
		this.services = services;
		this.observation = observation;
		this.totalCost = totalCost;
	}

	public int getNumberOrder() {
		return numberOrder;
	}

	public void setNumberOrder(int numberOrder) {
		this.numberOrder = numberOrder;
	}

	public LocalDate getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDate creationDate) {
		this.creationDate = creationDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Mechanic getMechanic() {
		return mechanic;
	}

	public void setMechanic(Mechanic mechanic) {
		this.mechanic = mechanic;
	}

	public LinkedList<Service> getServices() {
		return services;
	}

	public void setServices(LinkedList<Service> services) {
		this.services = services;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}
	
	public String toString() {
		return numberOrder+"-"+creationDate+"-"+status+"-"+vehicle.getPlate()+"-"+client.id+"-"+mechanic.getId()+"-"+
				services+"-"+observation+"-"+totalCost;
	}
	
	
}
