package com.reali.demo.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.reali.demo.dto.ListingGeoDTO;
import com.reali.demo.dto.ListingGeoFeaturesDTO;
import com.reali.demo.dto.ListingGeometryDTO;
import com.reali.demo.dto.ListingListDTO;
import com.reali.demo.dto.ListingPropertiesDTO;

/**
 * oonversion for the geo site 
 *
 */
@Component("listingListToListingGeoConverter")
public class ListingListToListingGeoConverter implements Function<ListingListDTO, ListingGeoDTO> {

	@Autowired
	protected ModelMapper modelMapper;

	@Override
	public ListingGeoDTO apply(ListingListDTO listingListDTO) {
		
		ListingGeoDTO listingGeoDTO = new ListingGeoDTO();
		List<ListingGeoFeaturesDTO> featureList = listingListDTO.getListingList().stream().map(item -> {

			ListingGeoFeaturesDTO listingGeoFeaturesDTO = new ListingGeoFeaturesDTO();
			
			ListingGeometryDTO listingGeometryDTO = new ListingGeometryDTO();
			List<Double> coordinates = new ArrayList<Double>();
			coordinates.add(item.getLng());
			coordinates.add(item.getLat());
			listingGeometryDTO.setCoordinates(coordinates);

	
			ListingPropertiesDTO listingPropertiesDTO = modelMapper.map(item, ListingPropertiesDTO.class);

			listingGeoFeaturesDTO.setListingGeometryDTO(listingGeometryDTO);
			listingGeoFeaturesDTO.setProperties(listingPropertiesDTO);

			return listingGeoFeaturesDTO;
		}).collect(Collectors.toList());
		
		listingGeoDTO.setFeatures(featureList);

		return listingGeoDTO;
	}



}

