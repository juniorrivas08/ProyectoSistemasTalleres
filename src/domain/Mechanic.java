package domain;

public class Mechanic extends Person{

	private String specialty;

	public Mechanic() {
		
	}

	public Mechanic(String id, String name, String phone, String mail, String specialty) {
		super(id, name, phone, mail);
		this.specialty = specialty;
	}

	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}
	
	public String toString() {
		return super.toString()+specialty;
	}
	
	
}
