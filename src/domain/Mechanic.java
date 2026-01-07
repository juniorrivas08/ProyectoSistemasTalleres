package domain;

public class Mechanic {

	private String id;
	private String specialty;
	private String nameMechanic;
	private String mail;
	private String phoneMechanic;
	

	public Mechanic() {
		
	}
	
	public Mechanic(String specialty, String nameMechanic, String mail, String phoneMechanic) {
		this.specialty = specialty;
		this.nameMechanic = nameMechanic;
		this.mail = mail;
		this.phoneMechanic = phoneMechanic;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}

	public String getNameMechanic() {
		return nameMechanic;
	}

	public void setNameMechanic(String nameMechanic) {
		this.nameMechanic = nameMechanic;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPhoneMechanic() {
		return phoneMechanic;
	}

	public void setPhoneMechanic(String phoneMechanic) {
		this.phoneMechanic = phoneMechanic;
	}

	@Override
	public String toString() {
		return "Mechanic [id=" + id + ", specialty=" + specialty + ", nameMechanic=" + nameMechanic + ", mail=" + mail
				+ ", phoneMechanic=" + phoneMechanic + "]";
	}
	
	
	
	
}
