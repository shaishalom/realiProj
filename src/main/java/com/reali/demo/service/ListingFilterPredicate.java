package com.reali.demo.service;

import java.util.function.Predicate;

import com.reali.demo.dto.ListingCriteriaDTO;
import com.reali.demo.dto.ListingDTO;


public class ListingFilterPredicate implements Predicate<ListingDTO> {
	
	ListingCriteriaDTO	listingCriteriaDTO;

	public ListingFilterPredicate(ListingCriteriaDTO listingCriteriaDTO) {
		super();
		this.listingCriteriaDTO = listingCriteriaDTO;
	}

	@Override
	public boolean test(ListingDTO listingDTO) {

		if (listingCriteriaDTO.getMinPrice()!=null) {
			if (listingDTO.getPrice()<(listingCriteriaDTO.getMinPrice())) {
				return false;
			}
		}
		if (listingCriteriaDTO.getMaxPrice()!=null) {
			if (listingDTO.getPrice()>(listingCriteriaDTO.getMaxPrice())) {
				return false;
			}
		}
		
		if (listingCriteriaDTO.getMinBedRooms()!=null) {
			if (listingDTO.getBedrooms()<(listingCriteriaDTO.getMinBedRooms())) {
				return false;
			}
		}
		if (listingCriteriaDTO.getMaxBedRooms()!=null) {
			if (listingDTO.getBedrooms()>(listingCriteriaDTO.getMaxBedRooms())) {
				return false;
			}
		}
		
		if (listingCriteriaDTO.getMinBath()!=null) {
			if (listingDTO.getBathrooms()<(listingCriteriaDTO.getMinBath())) {
				return false;
			}
		}
		if (listingCriteriaDTO.getMaxBath()!=null) {
			if (listingDTO.getBathrooms()>(listingCriteriaDTO.getMaxBath())) {
				return false;
			}
		}

		return true;
	}

	public ListingFilterPredicate() {
		super();
	}

}

