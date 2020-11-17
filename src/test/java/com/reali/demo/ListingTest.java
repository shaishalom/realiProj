package com.reali.demo;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.reali.demo.controller.ListingController;
import com.reali.demo.dto.ListingCriteriaDTO;
import com.reali.demo.dto.ListingGeoDTO;
import com.reali.demo.dto.ListingListDTO;
import com.reali.demo.exception.OutputStatusEnum;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class ListingTest {

	
	@Autowired
	ListingController listingController;

	
	
	@Test
	public void TestFoundData() {
		ResponseEntity<ListingGeoDTO> listingGeoResponse = null;
		
		
		try {
			ListingCriteriaDTO listingCriteriaDTO = new ListingCriteriaDTO();
			listingCriteriaDTO.setMinPrice(100000L);
			listingGeoResponse = listingController.listings(listingCriteriaDTO, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ListingGeoDTO listingListDTO = listingGeoResponse.getBody();
		assertNotNull(listingListDTO);
		assertTrue(listingListDTO.getFeatures().size()>0);

	}
	

	@Test
	public void TestValidationError() {
		ResponseEntity<ListingGeoDTO> listingGeoResponse = null;
		
		
		ListingCriteriaDTO listingCriteriaDTO = new ListingCriteriaDTO();
		listingCriteriaDTO.setMinPrice(100000L);
		listingCriteriaDTO.setMaxPrice(90000L);
		try {
			listingGeoResponse = listingController.listings(listingCriteriaDTO, null);
		} catch (Exception e) {
		}
		ListingGeoDTO listingListDTO = listingGeoResponse.getBody();
		assertNotNull(listingListDTO);
//		assertTrue(listingGeoResponse.getBody().getResultStatus().getOutputStatusEnum().equals(OutputStatusEnum.INVALID_INPUT));

	}

	
	@Test
	public void TestNotFoundData() {
		ResponseEntity<ListingGeoDTO> listingGeoResponse = null;
		
		
		try {
			ListingCriteriaDTO listingCriteriaDTO = new ListingCriteriaDTO();
			listingCriteriaDTO.setMinBath(5);
			listingGeoResponse = listingController.listings(listingCriteriaDTO, null);
		} catch (Exception e) {
			e.printStackTrace();
		}

		ListingGeoDTO listingListDTO = listingGeoResponse.getBody();
		assertNotNull(listingListDTO);
		assertTrue(listingListDTO.getFeatures().size()==0);
		
		
	}


}
