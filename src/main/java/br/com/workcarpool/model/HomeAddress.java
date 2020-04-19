package br.com.workcarpool.model;

import java.util.List;

public class HomeAddress {
	
	private String address;
	private List<Double> coordinates;
	private String type;
	
	public HomeAddress() {
		this.type = "Point";
	}

	public HomeAddress(String address, List<Double> coordinates) {
		this.address = address;
		this.coordinates = coordinates;
		this.type = "Point";
	}

	public String getAddress() {
		return address;
	}

	public List<Double> getCoordinates() {
		return coordinates;
	}
	
	public void setCoordinates(List<Double> coordinates) {
		this.coordinates = coordinates;
	}

	public String getType() {
		return type;
	}
	
}
