package com.reali.demo;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.reali.demo.DemoApplication;
import com.reali.demo.controller.ListingController;
import com.reali.demo.dto.ListingCriteriaDTO;
import com.reali.demo.dto.ListingListDTO;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class ListingTest {

	
	@Autowired
	ListingController listingController;

	
	
	@Test
	public void TestFoundId() {
		ResponseEntity<ListingListDTO> listingListResponse = null;
		
		
		try {
			ListingCriteriaDTO listingCriteriaDTO = new ListingCriteriaDTO();
			listingCriteriaDTO.setMinPrice(100000L);
			listingListResponse = listingController.listings(listingCriteriaDTO, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ListingListDTO listingListDTO = listingListResponse.getBody();
		assertNotNull(listingListDTO);
		assertNotNull(listingListDTO.getListingList().size());

	}
	

	@Test
	public void TestValidationError() {
		ResponseEntity<ListingListDTO> listingListResponse = null;
		
		
		try {
			ListingCriteriaDTO listingCriteriaDTO = new ListingCriteriaDTO();
			listingCriteriaDTO.setMinPrice(100000L);
			listingCriteriaDTO.setMaxPrice(90000L);
			listingListResponse = listingController.listings(listingCriteriaDTO, null);
		} catch (Exception e) {
			//e.printStackTrace();
		}
		assertNull(listingListResponse);

	}

	
	@Test
	public void TestNotFoundData() {
		ResponseEntity<ListingListDTO> listingListResponse = null;
		
		
		try {
			ListingCriteriaDTO listingCriteriaDTO = new ListingCriteriaDTO();
			listingCriteriaDTO.setMinBath(5);
			listingListResponse = listingController.listings(listingCriteriaDTO, null);
		} catch (Exception e) {
			e.printStackTrace();
		}

		ListingListDTO listingListDTO = listingListResponse.getBody();
		assertNotNull(listingListDTO);
		assertTrue(listingListDTO.getListingList().size()==0);
		
		
	}


}
