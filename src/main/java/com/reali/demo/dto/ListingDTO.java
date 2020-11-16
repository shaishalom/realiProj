package com.reali.demo.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.reali.demo.exception.OutputStatusEnum;

//@XmlAccessorType(XmlAccessType.NONE)
//@XmlType(name = "QuoteDTO")
@XmlRootElement
public class ListingDTO extends BaseDTO{


	private Long id;
	private String street;
	private String status;
	private double price;
	private Integer bedrooms ;
	private Integer bathrooms;
	private Integer squareFootage;
	private Double lat;
	private Double lng;
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
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLng() {
		return lng;
	}
	public void setLng(double lng) {
		this.lng = lng;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ListingDTO [id=");
		builder.append(id);
		builder.append(", street=");
		builder.append(street);
		builder.append(", status=");
		builder.append(status);
		builder.append(", price=");
		builder.append(price);
		builder.append(", bedrooms=");
		builder.append(bedrooms);
		builder.append(", bathrooms=");
		builder.append(bathrooms);
		builder.append(", squareFootage=");
		builder.append(squareFootage);
		builder.append(", lat=");
		builder.append(lat);
		builder.append(", lng=");
		builder.append(lng);
		builder.append("]");
		return builder.toString();
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	

}