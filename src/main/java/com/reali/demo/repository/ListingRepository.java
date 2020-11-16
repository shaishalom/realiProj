package com.reali.demo.repository;

import java.util.List;

import com.reali.demo.dto.ListingDTO;
import com.reali.demo.exception.ProjBusinessException;
 

public interface ListingRepository{
 
	
	public List<ListingDTO> fetchData(String csvFile) throws ProjBusinessException ;
	
	
	
}
