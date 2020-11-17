package com.reali.demo.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ListingGeoDTO extends BaseDTO{


	private String type="FeatureCollection";
	private List<ListingGeoFeaturesDTO> features;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<ListingGeoFeaturesDTO> getFeatures() {
		return features;
	}
	public void setFeatures(List<ListingGeoFeaturesDTO> features) {
		this.features = features;
	}
	

}