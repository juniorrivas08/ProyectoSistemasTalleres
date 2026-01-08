package domain;

public class Client extends Person {
	
	private String address;
	
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
	

	public String toString() {
		return super.toString()+address;
	}
	
}
