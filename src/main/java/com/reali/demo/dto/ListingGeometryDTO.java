package com.reali.demo.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.reali.demo.exception.OutputStatusEnum;

//@XmlAccessorType(XmlAccessType.NONE)
//@XmlType(name = "QuoteDTO")
@XmlRootElement
public class ListingGeometryDTO extends BaseDTO{


	private String type="Point";
	private List<Double> coordinates;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<Double> getCoordinates() {
		return coordinates;
	}
	public void setCoordinates(List<Double> coordinates) {
		this.coordinates = coordinates;
	}

}