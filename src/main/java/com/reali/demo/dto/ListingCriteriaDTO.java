package com.reali.demo.dto;

import javax.xml.bind.annotation.XmlRootElement;

import com.reali.demo.exception.OutputStatusEnum;

//@XmlAccessorType(XmlAccessType.NONE)
//@XmlType(name = "QuoteDTO")
@XmlRootElement
public class ListingCriteriaDTO extends BaseInputDTO{


	private Long minPrice;
	private Long maxPrice;
	private Integer minBedRooms ;
	private Integer maxBedRooms ;
	private Integer minBath ;
	private Integer maxBath ;
	public Long getMinPrice() {
		return minPrice;
	}
	public void setMinPrice(Long minPrice) {
		this.minPrice = minPrice;
	}
	public Long getMaxPrice() {
		return maxPrice;
	}
	public void setMaxPrice(Long maxPrice) {
		this.maxPrice = maxPrice;
	}
	public Integer getMinBedRooms() {
		return minBedRooms;
	}
	public void setMinBedRooms(Integer minBedRooms) {
		this.minBedRooms = minBedRooms;
	}
	public Integer getMaxBedRooms() {
		return maxBedRooms;
	}
	public void setMaxBedRooms(Integer maxBedRooms) {
		this.maxBedRooms = maxBedRooms;
	}
	public Integer getMinBath() {
		return minBath;
	}
	public void setMinBath(Integer minBath) {
		this.minBath = minBath;
	}
	public Integer getMaxBath() {
		return maxBath;
	}
	public void setMaxBath(Integer maxBath) {
		this.maxBath = maxBath;
	}
	

	

}