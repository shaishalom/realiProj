package com.reali.demo.dto;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;


@XmlRootElement
public class ListingGeoFeaturesDTO extends BaseDTO{


	private String type="Feature";

	@JsonProperty("geometry")
	private ListingGeometryDTO listingGeometryDTO;
	
	@JsonProperty("properties")
	private ListingPropertiesDTO properties;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public ListingGeometryDTO getListingGeometryDTO() {
		return listingGeometryDTO;
	}
	public void setListingGeometryDTO(ListingGeometryDTO listingGeometryDTO) {
		this.listingGeometryDTO = listingGeometryDTO;
	}
	public ListingPropertiesDTO getProperties() {
		return properties;
	}
	public void setProperties(ListingPropertiesDTO properties) {
		this.properties = properties;
	}
	

}