package com.reali.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

//@XmlRootElement
public class ListingPropertiesDTO extends BaseDTO{


	private Long id;
	private double price;
	private String street;
	private Integer bedrooms ;

	private Integer bathrooms;
	
	@JsonProperty("sq_ft")
	private Integer squareFootage;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getBedrooms() {
		return bedrooms;
	}
	public void setBedrooms(int bedrooms) {
		this.bedrooms = bedrooms;
	}
	public int getBathrooms() {
		return bathrooms;
	}
	public void setBathrooms(int bathrooms) {
		this.bathrooms = bathrooms;
	}
	public int getSquareFootage() {
		return squareFootage;
	}
	public void setSquareFootage(int squareFootage) {
		this.squareFootage = squareFootage;
	}

	
	

}