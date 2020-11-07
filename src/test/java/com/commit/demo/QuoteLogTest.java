package com.commit.demo;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.commit.demo.dto.ItemDTO;
import com.commit.demo.dto.QuoteDTO;
import com.commit.demo.dto.QuoteLogDTO;
import com.commit.demo.exception.OutputStatusEnum;
import com.commit.demo.service.QuoteLogService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class QuoteLogTest {

	
	@Autowired
	QuoteLogService quoteLogService;
	
	@Test
	public void contextLoads() {
	}
	
	@Test
	public void testGetQuoteLogLines() {

		List<QuoteLogDTO> quoteLoglist=null;
		try {
			quoteLoglist = quoteLogService.getAllQuotesLog();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(quoteLoglist.size()>0);

	}
}
