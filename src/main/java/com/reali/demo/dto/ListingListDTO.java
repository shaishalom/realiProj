package com.reali.demo.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.reali.demo.exception.OutputStatusEnum;

//@XmlAccessorType(XmlAccessType.NONE)
//@XmlType(name = "QuoteDTO")
@XmlRootElement
public class ListingListDTO extends BaseOutputDTO{

	
	
	public ListingListDTO() {
		this.setStatus(new StatusDTO(OutputStatusEnum.SUCCESS, "", ""));
	}

	private List<ListingDTO> listingList;
	private String type="FeatureCollection";

	public List<ListingDTO> getListingList() {
		return listingList;
	}

	public void setListingList(List<ListingDTO> listingList) {
		this.listingList = listingList;
	}



}