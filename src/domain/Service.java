package domain;

public class Service {
	
	private int code;
	private String nameService;
	private String description;
	private double baseCost;
	private int hours;
	
	public Service() {
		
	}
	
	public Service(int code, String nameService, String description, double baseCost, int hours) {
		super();
		this.code = code;
		this.nameService = nameService;
		this.description = description;
		this.baseCost = baseCost;
		this.hours = hours;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getNameService() {
		return nameService;
	}

	public void setNameService(String nameService) {
		this.nameService = nameService;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getBaseCost() {
		return baseCost;
	}

	public void setBaseCost(double baseCost) {
		this.baseCost = baseCost;
	}

	public int getHours() {
		return hours;
	}

	public void setHours(int hours) {
		this.hours = hours;
	}
	
	
	public String toString() {
		return code+"-"+nameService+"-"+description+"-"+baseCost+"-"+hours;
	}
	
}
