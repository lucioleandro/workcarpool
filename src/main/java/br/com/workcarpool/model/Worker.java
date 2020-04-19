package br.com.workcarpool.model;

import org.bson.types.ObjectId;

public class Worker {

	private ObjectId id;
	private String firstName;
	private String lastName;
	private String register;
	private HomeAddress homeAddress;
	
	public Worker() {
	}
	public Worker(ObjectId id, String firstName, String lastName, String register, 
			HomeAddress homeAdress) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.register = register;
		this.homeAddress = homeAdress;
	}

	public ObjectId getId() {
		return id;
	}
	
	public void setId(ObjectId id) {
		this.id = id;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getRegister() {
		return register;
	}
	
	public void setRegister(String register) {
		this.register = register;
	}
	
	public HomeAddress getHomeAddress() {
		return homeAddress;
	}
	
	public void setHomeAddress(HomeAddress homeAddress) {
		this.homeAddress = homeAddress;
	}
	
	public Worker generateId() {
		setId(new ObjectId());
		return this;
	}
	
}
