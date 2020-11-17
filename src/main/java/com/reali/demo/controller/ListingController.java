package com.reali.demo.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.reali.demo.dto.ListingCriteriaDTO;
import com.reali.demo.dto.ListingGeoDTO;
import com.reali.demo.dto.ListingListDTO;
import com.reali.demo.exception.ProjBusinessException;
import com.reali.demo.service.ListingService;
import com.reali.demo.utils.StringUtils;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("")
@Api()
public class ListingController extends BaseController {
	@Autowired
	ListingService listingService;

	@Autowired
	Logger logger;

	/**
	 * get listing in Get
	 * @param listingCriteriaDTO
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "/listings", produces = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<ListingGeoDTO> listings(@RequestParam(value="min_price",required=false) Long minPrice,
												   @RequestParam(value="max_price",required=false)  Long maxPrice,
	   											   @RequestParam(value="min_bed",required=false) Integer minBed,
	   											   @RequestParam(value="max_bed",required=false) Integer maxBed,
	   											   @RequestParam(value="min_bath",required=false) Integer minBath,
	   											   @RequestParam(value="max_bath",required=false) Integer maxBath,
												   HttpServletRequest request)
												   throws Exception {

		ListingCriteriaDTO listingCriteriaDTO = new ListingCriteriaDTO();
		listingCriteriaDTO.setMinPrice(minPrice);
		listingCriteriaDTO.setMaxPrice(minPrice);
		listingCriteriaDTO.setMinBedRooms(minBed);
		listingCriteriaDTO.setMinBedRooms(maxBed);
		listingCriteriaDTO.setMinBath(minBath);
		listingCriteriaDTO.setMaxBath(maxBath);
				
		
		String criteriaInputStr = StringUtils.toJson(listingCriteriaDTO);
		logger.info("listings REQUEST->" + criteriaInputStr);
		
		ListingGeoDTO listingGeoDTO = null;
		try {
			listingGeoDTO = listingService.filterListings(listingCriteriaDTO);
		} catch (ProjBusinessException e) {
			listingGeoDTO = new ListingGeoDTO();
			
			//listingGeoDTO.setStatus(handleBusinessException(e));

			return new ResponseEntity<ListingGeoDTO>(listingGeoDTO, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		String output = StringUtils.toJson(listingGeoDTO);
		logger.info("listings RESPONSE->" + output);
		return new ResponseEntity<ListingGeoDTO>(listingGeoDTO, new HttpHeaders(), HttpStatus.OK);
	}
	
	/**
	 * get listing in Post
	 * @param listingCriteriaDTO
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value = "/listings", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ListingGeoDTO> listings(@RequestBody ListingCriteriaDTO listingCriteriaDTO, HttpServletRequest request)
			throws Exception {

		
		String criteriaInputStr = StringUtils.toJson(listingCriteriaDTO);
		logger.info("listings REQUEST->" + criteriaInputStr);
		
		ListingGeoDTO listingGeoDTO = null;
		try {
			listingGeoDTO = listingService.filterListings(listingCriteriaDTO);
		} catch (ProjBusinessException e) {
			listingGeoDTO = new ListingGeoDTO();
			
			//listingGeoDTO.setStatus(handleBusinessException(e));

			return new ResponseEntity<ListingGeoDTO>(listingGeoDTO, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		String output = StringUtils.toJson(listingGeoDTO);
		logger.info("listings RESPONSE->" + output);
		return new ResponseEntity<ListingGeoDTO>(listingGeoDTO, new HttpHeaders(), HttpStatus.OK);
	}

	


}