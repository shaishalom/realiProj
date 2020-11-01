package com.commit.demo;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import javax.swing.text.View;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.commit.demo.controller.AuthController;
import com.commit.demo.controller.QuoteController;
import com.commit.demo.dto.ItemDTO;
import com.commit.demo.dto.JwtResponse;
import com.commit.demo.dto.LoginRequest;
import com.commit.demo.dto.QuoteDTO;
import com.commit.demo.dto.SignupRequest;
import com.commit.demo.exception.OutputStatusEnum;
import com.commit.demo.model.ERole;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class QuoteTest {

	
	
	@InjectMocks
	QuoteController controller = new QuoteController();
//	@Mock
//	CodeService service = new CodeService();
	@Mock
	View mockView;
	MockMvc mockMvc;
//	Code code = new Code();
//	@Before
//	public void setUp() throws Exception {
//	MockitoAnnotations.initMocks(this);
	//mockMvc = MockMvcBuilders.standaloneSetup(controller).setSingleView(mockView).build();
	
	
//	@Mock
	@Autowired
	QuoteController quoteController;

	
	@Autowired
	AuthController authController;

	
//	mockMvc.perform(post("/quote/id")).andExpect(status().isOk())
	
	String token = null;
//	@Test
//	public void contextLoads() {
//	}
	
	
//	@Mock
//	private HttpServletRequest httpServletRequest;
//	@Mock
//	private MerchantCredentialsChangeService mockCredentionChangeService;
	
	@Before
	    public void signupAndLogin() {
			
			Set rolesSet = new HashSet<>(Arrays.asList(ERole.ROLE_USER.toString(),ERole.ROLE_ADMIN.toString()));
			SignupRequest signupRequest = new SignupRequest("shai","shai@111.com",rolesSet,"111111");

			
			authController.registerUser(signupRequest);
			

//			Set rolesSet = new HashSet<>(Arrays.asList(ERole.ROLE_USER.toString(),ERole.ROLE_ADMIN.toString()));
///			SignupRequest signupRequest = new SignupRequest("shai","shai@111.com",rolesSet,"111111");

			LoginRequest loginRequest = new LoginRequest("shai","111111");
			ResponseEntity resp= authController.authenticateUser(loginRequest);
			if (resp.getBody() != null) {
				token = ((JwtResponse) resp.getBody()).getAccessToken();
			}
			
	    }
	
	
	public static String sendPost(String url, String token, String postData) {
	    PrintWriter out = null;
	    BufferedReader in = null;
	    String result = "";
	    try {
	        URL realUrl = new URL(url);
	        URLConnection conn = realUrl.openConnection();
	        conn.setDoOutput(true);
	        conn.setRequestProperty("Authorization","Bearer "+token );
	        conn.setRequestProperty("Accept", "application/json");
	        conn.setRequestProperty("contentType","application/json");
	        ((HttpURLConnection) conn).setRequestMethod("POST");


	        // build connection
	        OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());

	        writer.write(postData);
	        writer.flush();
	        String line;
	        BufferedReader reader = new BufferedReader(new 
	                                         InputStreamReader(conn.getInputStream()));
	        while ((line = reader.readLine()) != null) {
	          System.out.println(line);
	        }
	        writer.close();
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    finally {
	        try {
	            if (out != null) {
	                out.close();
	            }
	            if (in != null) {
	                in.close();
	            }
	        } catch (IOException ex) {
	            ex.printStackTrace();
	        }
	    }
	    return result;
	}
	
	
	
	@Test
	public void TestFoundId() {
		ResponseEntity<QuoteDTO> quoteResponse = null;
		
		
		try {
			
//			mockMvc.perform(post("/getQuoteById")
//			   Mockito.when(mockCredentionChangeService.getUserByToken(Matchers.eq(token), Matchers.any(HttpServletRequest.class)))
//	            .thenReturn(expectedUsername);

			sendPost("http://localhost:8080/quote/getQuoteById/1", token,null);
//			quoteResponse = quoteController.getQuoteById(1L,);
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
