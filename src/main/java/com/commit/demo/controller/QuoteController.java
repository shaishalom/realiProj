package com.commit.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.commit.demo.dto.QuoteDTO;
import com.commit.demo.exception.ProjBusinessException;
import com.commit.demo.service.QuoteService;
import com.commit.demo.utils.StringUtils;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/quote")
@Api()
public class QuoteController extends BaseController {
	@Autowired
	QuoteService service;

	@Autowired
	Logger logger;

	@GetMapping
	public ResponseEntity<List<QuoteDTO>> getAllQuotes() {
		List<QuoteDTO> dtoList = service.getAllQuotes();

		
		String output = StringUtils.toJson(dtoList);
		logger.info("getAllQuotes RESPONSE->" + dtoList);
		
		return new ResponseEntity<List<QuoteDTO>>(dtoList, new HttpHeaders(), HttpStatus.OK);
	}

	//@GetMapping("/{id}")
	public ResponseEntity<QuoteDTO> getQuoteById(@PathVariable("id") Long id) throws Exception {

		logger.info("getQuoteById REQUEST->" + id);

		
		QuoteDTO quoteOutputDTO = null;
		try {
			quoteOutputDTO = service.getQuoteById(id);
		} catch (ProjBusinessException e) {

			quoteOutputDTO = new QuoteDTO();
			quoteOutputDTO.setStatus(handleBusinessException(e));

			return new ResponseEntity<QuoteDTO>(quoteOutputDTO, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		String output = StringUtils.toJson(quoteOutputDTO);
		logger.info("createOrUpdateQuote RESPONSE->" + output);

		return new ResponseEntity<QuoteDTO>(quoteOutputDTO, new HttpHeaders(), HttpStatus.OK);
	}

	@RequestMapping(value = "/getQuoteById/{id}", method = {RequestMethod.GET, RequestMethod.PUT, RequestMethod.POST} , produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<QuoteDTO> getQuoteById(@PathVariable("id") Long id, HttpServletRequest request) throws Exception {

		logger.info("getQuoteById REQUEST->" + id);

		
		QuoteDTO quoteOutputDTO = null;
		try {
			quoteOutputDTO = service.getQuoteById(id);
		} catch (ProjBusinessException e) {

			quoteOutputDTO = new QuoteDTO();
			quoteOutputDTO.setStatus(handleBusinessException(e));

			return new ResponseEntity<QuoteDTO>(quoteOutputDTO, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		String output = StringUtils.toJson(quoteOutputDTO);
		logger.info("getQuoteById RESPONSE->" + output);

		return new ResponseEntity<QuoteDTO>(quoteOutputDTO, new HttpHeaders(), HttpStatus.OK);
	}
	
	
	@PostMapping(value = "/createOrUpdateQuote", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<QuoteDTO> createOrUpdateQuote(@RequestBody QuoteDTO quoteInput, HttpServletRequest request)
			throws Exception {

		
		String quoteInputStr = StringUtils.toJson(quoteInput);
		logger.info("createOrUpdateQuote REQUEST->" + quoteInputStr);
		
		QuoteDTO quoteOutputDTO = null;
		try {
			quoteOutputDTO = service.createOrUpdateQuote(quoteInput);
		} catch (ProjBusinessException e) {
			quoteOutputDTO = new QuoteDTO();
			quoteOutputDTO.setId(quoteInput.getId());
			quoteOutputDTO.setName(quoteInput.getName());
			
			quoteOutputDTO.setStatus(handleBusinessException(e));

			return new ResponseEntity<QuoteDTO>(quoteOutputDTO, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		String output = StringUtils.toJson(quoteOutputDTO);
		logger.info("createOrUpdateQuote RESPONSE->" + output);
		return new ResponseEntity<QuoteDTO>(quoteOutputDTO, new HttpHeaders(), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<QuoteDTO> deleteQuoteById(@PathVariable("id") Long id) throws Exception {

		logger.info("deleteQuoteById REQUEST->" + id);

		QuoteDTO quoteOutputDTO = null;
		try {
			quoteOutputDTO = service.deleteQuoteById(id);
		} catch (ProjBusinessException e) {

			quoteOutputDTO = new QuoteDTO();
			quoteOutputDTO.setStatus(handleBusinessException(e));

			return new ResponseEntity<QuoteDTO>(quoteOutputDTO, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		String output = StringUtils.toJson(quoteOutputDTO);
		logger.info("deleteQuoteById RESPONSE->" + output);

		return new ResponseEntity<QuoteDTO>(quoteOutputDTO, new HttpHeaders(), HttpStatus.OK);
	}

}