package com.commit.demo;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.commit.demo.controller.QuoteController;
import com.commit.demo.dto.ItemDTO;
import com.commit.demo.dto.QuoteDTO;
import com.commit.demo.exception.OutputStatusEnum;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class QuoteTest {

	@Autowired
	QuoteController quoteController;
	
//	@Test
//	public void contextLoads() {
//	}
	
	@Test
	public void TestFoundId() {
		ResponseEntity<QuoteDTO> quoteResponse = null;
		try {
			quoteResponse = quoteController.getQuoteById(1L);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		QuoteDTO quoteDTO = quoteResponse.getBody();
		assertNotNull(quoteDTO);
		assertNotNull(quoteDTO.getId());
		assertTrue(quoteDTO.getStatus().getOutputStatusEnum().equals(OutputStatusEnum.SUCCESS));

	}
	

	@Test
	public void TestNotFoundId() {
		ResponseEntity<QuoteDTO> quoteResponse = null;
		try {
			quoteResponse = quoteController.getQuoteById(1000L);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		QuoteDTO quoteDTO = quoteResponse.getBody();
		assertNull(quoteDTO.getId());
		assertTrue(quoteDTO.getStatus().getOutputStatusEnum().equals(OutputStatusEnum.NO_RECORDS_FOUND));
		
	}
	
	public int getRandomNumber() {
		Random rand = new Random(); 
	    int rand_num = rand.nextInt(1000);
	    return rand_num;
	}
	
	
	@Test
	public void TestCreateNotPassValidationNegativeNumber() {
		QuoteDTO quoteInpDto = new QuoteDTO();
		quoteInpDto.setName("quote-" + getRandomNumber());
		quoteInpDto.setPrice(-20);
		quoteInpDto.addToItemList(new ItemDTO("item-"+getRandomNumber()));
		ResponseEntity<QuoteDTO> quoteResponse = null;
		try {
			quoteResponse = quoteController.createOrUpdateQuote(quoteInpDto, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		QuoteDTO quoteDTO = quoteResponse.getBody();
		assertNull(quoteDTO.getId());
		assertTrue(quoteDTO.getStatus().getOutputStatusEnum().equals(OutputStatusEnum.INVALID_INPUT));
	}	
	
	@Test
	public void TestCreateNotPassValidationNameBlank() {
		QuoteDTO quoteInpDto = new QuoteDTO();
		quoteInpDto.setName("");
		quoteInpDto.setPrice(3d);
		quoteInpDto.addToItemList(new ItemDTO("item-"+getRandomNumber()));
		ResponseEntity<QuoteDTO> quoteResponse = null;
		try {
			quoteResponse = quoteController.createOrUpdateQuote(quoteInpDto, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		QuoteDTO quoteDTO = quoteResponse.getBody();
		assertNull(quoteDTO.getId());
		assertTrue(quoteDTO.getStatus().getOutputStatusEnum().equals(OutputStatusEnum.INVALID_INPUT));
	}	
	
	@Test
	public void TestCreateUpdateAndDeleteNewQuote() {
		QuoteDTO quoteInpDto = new QuoteDTO();
		quoteInpDto.setName("quote-" + getRandomNumber());
		quoteInpDto.setPrice(getRandomNumber());
		quoteInpDto.addToItemList(new ItemDTO("item-"+getRandomNumber()));
		ResponseEntity<QuoteDTO> quoteResponse = null;
		try {
			quoteResponse = quoteController.createOrUpdateQuote(quoteInpDto, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		QuoteDTO quoteDTO = quoteResponse.getBody();
		assertNotNull(quoteDTO.getId());
		assertTrue(quoteDTO.getStatus().getOutputStatusEnum().equals(OutputStatusEnum.SUCCESS));
		Long createdQuoteId= quoteDTO.getId();
		
		quoteDTO.setName("shaiQuote");
		try {
			quoteResponse = quoteController.createOrUpdateQuote(quoteDTO, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		QuoteDTO  quoteUpdateDTO = quoteResponse.getBody();
		assertNotNull(quoteUpdateDTO.getId());
		assertTrue(quoteUpdateDTO.getStatus().getOutputStatusEnum().equals(OutputStatusEnum.SUCCESS));
		assertTrue(quoteUpdateDTO.getName().equals("shaiQuote"));
		assertTrue(quoteUpdateDTO.getId().equals(createdQuoteId)); //make sure id was not changed
		
		try {
			quoteResponse = quoteController.deleteQuoteById(quoteUpdateDTO.getId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		QuoteDTO quoteDeleteDTO   = quoteResponse.getBody();
		assertNotNull(quoteDeleteDTO.getId());
		
		assertTrue(quoteUpdateDTO.getStatus().getOutputStatusEnum().equals(OutputStatusEnum.SUCCESS));
		assertNull(quoteDeleteDTO.getName());  //verify name not exists(it's deleted)
		
		try {
			quoteResponse = quoteController.getQuoteById(quoteDeleteDTO.getId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		QuoteDTO quoteNotFoundDTO = quoteResponse.getBody();
		assertNull(quoteNotFoundDTO.getId());
		assertTrue(quoteNotFoundDTO.getStatus().getOutputStatusEnum().equals(OutputStatusEnum.NO_RECORDS_FOUND)); //expected not exist after delete

		
	}
	
	@Test
	public void TestCreateTwiceSameQuoteName() {
		QuoteDTO quoteInpDto = new QuoteDTO();
		quoteInpDto.setName("quote-" + getRandomNumber());
		quoteInpDto.setPrice(getRandomNumber());
		quoteInpDto.addToItemList(new ItemDTO("item-"+getRandomNumber()));
		ResponseEntity<QuoteDTO> quoteResponse = null;
		try {
			quoteResponse = quoteController.createOrUpdateQuote(quoteInpDto, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		QuoteDTO quoteDTO = quoteResponse.getBody();
		assertNotNull(quoteDTO.getId());
		assertTrue(quoteDTO.getStatus().getOutputStatusEnum().equals(OutputStatusEnum.SUCCESS));
		Long createdQuoteId= quoteDTO.getId();
		
		quoteDTO.setId(null);
		try {
			quoteResponse = quoteController.createOrUpdateQuote(quoteDTO, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		QuoteDTO  quoteCreateDTO = quoteResponse.getBody();
		assertNull(quoteCreateDTO.getId());
		assertTrue(quoteCreateDTO.getStatus().getOutputStatusEnum().equals(OutputStatusEnum.QUATE_NAME_ALREADY_EXIST));
		

		
	}

}
