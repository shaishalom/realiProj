package com.reali.demo.repository;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

import com.reali.demo.dto.ListingDTO;
import com.reali.demo.dto.StatusDTO;
import com.reali.demo.exception.OutputStatusEnum;
import com.reali.demo.exception.ProjBusinessException;

@Component("listingRepository")
public class ListingRepositoryImpl implements ListingRepository {

	Map<String, List<ListingDTO>> listingKeyToListMap = new HashMap<String,List<ListingDTO>>();
	
	/**
	 * go to cache first. If not exists , fetch it from CSV
	 * @throws ProjBusinessException 
	 */
	public List<ListingDTO> fetchData(String csvFile) throws ProjBusinessException {
		List<ListingDTO> list = listingKeyToListMap.get(csvFile);
		if (list==null) {
			list = fetchFromCSV(csvFile);	
			listingKeyToListMap.put(csvFile, list);
		}
		return list;
	}
	
	/**
	 * fetch from CSV File
	 * @param csvFile
	 * @return
	 * @throws ProjBusinessException 
	 */
	public List<ListingDTO> fetchFromCSV(String csvFile) throws ProjBusinessException {
		List<ListingDTO> listingList = null;
		
		InputStream is = getClass().getClassLoader().getResourceAsStream(csvFile);
	    String[] headers = null;
	    try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
		    headers =  br.readLine().split(",");
		 } catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	    String[] headers1 = headers;
	    
	    
		try (Stream<String> stream = Files.lines(Paths.get(getClass().getClassLoader().getResource(csvFile).toURI()))) {
			listingList = stream
		        .skip(1) // skip headers
		        .map(line -> line.split(","))
		        .map(data -> {
		        	ListingDTO listingDTO = new ListingDTO();
		            for (int i = 0; i < data.length; i++) {
		            	switch (headers1[i]) {
		            		case "id": listingDTO.setId(new Long(data[i])); break;
		            		case "street": listingDTO.setStreet(data[i]); break;
		            		case "status": listingDTO.setStatus(data[i]); break;
		            		case "price": listingDTO.setPrice(new Long(data[i])); break;
		            		case "bedrooms": listingDTO.setBedrooms(new Integer(data[i])); break;
		            		case "bathrooms": listingDTO.setBathrooms(new Integer(data[i])); break;
		            		case "sq_ft": listingDTO.setSquareFootage(new Integer(data[i])); break;
		            		case "lat": listingDTO.setLat(new Double(data[i])); break;
		            		case "lng": listingDTO.setLng(new Double(data[i])); break;
		            		
		            	}
		            }
		            return listingDTO;
		        }).collect(Collectors.toList());
		} catch (IOException e) {
			throw new ProjBusinessException(new StatusDTO(OutputStatusEnum.UNEXPECTED,"File not Found:"+csvFile));
		} catch (URISyntaxException e1) {
			throw new ProjBusinessException(new StatusDTO(OutputStatusEnum.UNEXPECTED,"File not Found:"+csvFile));
		}
		return listingList;
	}

}
