package com.reali.demo.service;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reali.demo.dto.BaseInputDTO;
import com.reali.demo.dto.BaseOutputDTO;
import com.reali.demo.dto.ListingCriteriaDTO;
import com.reali.demo.dto.ListingDTO;
import com.reali.demo.dto.ListingListDTO;
import com.reali.demo.dto.StatusDTO;
import com.reali.demo.exception.OutputStatusEnum;
import com.reali.demo.exception.ProjBusinessException;
import com.reali.demo.repository.ListingRepository;

@Service
public class ListingServiceFlow extends BaseServiceImpl {

	
	@Autowired
	ListingRepository ListingRepository;


	@Autowired
	protected ModelMapper modelMapper;
	

	
	@Autowired
	Logger logger;




	/**
	 * make the filter
	 * @param listingCriteriaDTO
	 * @return
	 * @throws ProjBusinessException
	 */
	private ListingListDTO filterListings(ListingCriteriaDTO listingCriteriaDTO) throws ProjBusinessException {
		
		
		ListingListDTO listingListDTO = new ListingListDTO();
		List<ListingDTO>  allList = ListingRepository.fetchData("listing-details.csv");
		Predicate<ListingDTO> listingPredicate	= new ListingFilterPredicate(listingCriteriaDTO);
		
		List<ListingDTO> filterList = allList.stream().filter(listingPredicate).collect(Collectors.toList());  
		listingListDTO.setListingList(filterList);
		return listingListDTO;
		
	}
	
	/**
	 * implement the framework
	 */
	public BaseOutputDTO executeService(BaseInputDTO listingCriteriaDTO, BaseOutputDTO listingListDTO ) throws ProjBusinessException {
	     listingListDTO = filterListings((ListingCriteriaDTO) listingCriteriaDTO);
		return listingListDTO;
	}
	
	
	/**
	 * make the validation
	 */
	@Override
	public Boolean validateInput(BaseInputDTO baseInputDTO, BaseOutputDTO baseSRQRES) throws ProjBusinessException {
		ListingCriteriaDTO listingCriteriaDTO = (ListingCriteriaDTO) baseInputDTO;

		if (listingCriteriaDTO.getMinPrice()!=null && listingCriteriaDTO.getMaxPrice()!=null) {
			if (listingCriteriaDTO.getMaxPrice()>listingCriteriaDTO.getMinPrice()) {
				
				StatusDTO statusDTO = new StatusDTO(OutputStatusEnum.INVALID_INPUT, "max Price:" + listingCriteriaDTO.getMaxPrice() + " is higher then minPrice:" +listingCriteriaDTO.getMinPrice()  );
				logger.error(statusDTO.toString());
				throw new ProjBusinessException(statusDTO);
			}
		}
		
		
		if (listingCriteriaDTO.getMinBedRooms()!=null && listingCriteriaDTO.getMaxBedRooms()!=null) {
			if (listingCriteriaDTO.getMaxBedRooms()>listingCriteriaDTO.getMinBedRooms()) {
				
				StatusDTO statusDTO = new StatusDTO(OutputStatusEnum.INVALID_INPUT, "maxbedRoom:" + listingCriteriaDTO.getMaxBath() + " is higher then minBedRoom:" +listingCriteriaDTO.getMinBath()  );
				logger.error(statusDTO.toString());
				throw new ProjBusinessException(statusDTO);
			}
		}

		if (listingCriteriaDTO.getMinBath()!=null && listingCriteriaDTO.getMaxBath()!=null) {
			if (listingCriteriaDTO.getMaxBath()>listingCriteriaDTO.getMinBath()) {
				
				StatusDTO statusDTO = new StatusDTO(OutputStatusEnum.INVALID_INPUT, "maxbath:" + listingCriteriaDTO.getMaxBath() + " is higher then minBath:" +listingCriteriaDTO.getMinBath()  );
				logger.error(statusDTO.toString());
				throw new ProjBusinessException(statusDTO);
			}
		}
		
		return true;
	}


	@Override
	public BaseOutputDTO initResponse() {
		// TODO Auto-generated method stub
		return new ListingListDTO();
	}


}