package com.commit.demo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
//import org.testifyproject.guava.common.net.MediaType;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.commit.demo.controller.AuthController;
import com.commit.demo.controller.QuoteController;
import com.commit.demo.dto.JwtResponse;
import com.commit.demo.dto.LoginRequest;
import com.commit.demo.dto.QuoteDTO;
import com.commit.demo.dto.SignupRequest;
import com.commit.demo.model.ERole;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class QuoteTestWithAuthentication {

	
	
	@Mock
	private MockMvc mockMvc;
	
//	@MockBean
//	private UserRepository userRepository;
	
	
	@Autowired
	QuoteController quoteController;

	
	@Autowired
	AuthController authController;

	
	byte[] token = null;
	
	  @Before
	    public void setup() throws Exception {

	        // this must be called for the @Mock annotations above to be processed
	        // and for the mock service to be injected into the controller under
	        // test.
	        MockitoAnnotations.initMocks(this);

	        this.mockMvc = MockMvcBuilders.standaloneSetup(quoteController).build();
	        System.out.println(this.mockMvc);
	        Long userId = login("carmel","111111");
	        System.out.println("for user id:"+userId + ",token:"+new String(token));	
	    }
	
	    public void signupAndLogin() throws Exception {
			
			Set rolesSet = new HashSet<>(Arrays.asList(ERole.ROLE_USER.toString(),ERole.ROLE_ADMIN.toString()));
			SignupRequest signupRequest = new SignupRequest("shai","shai@111.com",rolesSet,"111111");

			
			authController.registerUser(signupRequest);
			


			LoginRequest loginRequest = new LoginRequest("shai","111111");
			ResponseEntity resp= authController.authenticateUser(loginRequest);
			if (resp.getBody() != null) {
				token = ((JwtResponse) resp.getBody()).getAccessToken().getBytes();
				Long userId= ((JwtResponse) resp.getBody()).getId();
				
			}
			
	    }

	
    public Long login(String user,String password) throws Exception {
		

		LoginRequest loginRequest = new LoginRequest(user,password);
		ResponseEntity resp= authController.authenticateUser(loginRequest);
		Long userId = null;
		if (resp.getBody() != null) {
			token = ((JwtResponse) resp.getBody()).getAccessToken().getBytes();
			System.out.println(token);	
			userId= ((JwtResponse) resp.getBody()).getId();

		}
		return userId;
		
    }
	
	
	 public void sendGetWithExpectedData(String url, byte[] token, String postData, String[][] keys) throws Exception {

		 String s = new String(token);
		 ResultActions actions = null;

		 if (postData!=null) {
			 
			 actions =  mockMvc.perform(get(url).
					 contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
					 .header(HttpHeaders.AUTHORIZATION, "Bearer " + s)
					 .accept(MediaType.APPLICATION_JSON))
			 	.andExpect(status().isOk());
			 
			 	
		 }else {
			  actions = mockMvc.perform(get(url).
					 contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
					 .header(HttpHeaders.AUTHORIZATION, "Bearer " + s)
					 .accept(MediaType.APPLICATION_JSON))
			 	.andExpect(status().isOk());
		 }
		 if (keys!=null) {
			 for (String key[] : keys) {
				 actions.andExpect(jsonPath(key[0]).value(key[1]));
			 }
		 }
		 actions.andDo(MockMvcResultHandlers.print());

	    }
	
	
	 public void sendPostWithExpectedData(String url, byte[] token, String postData, String[][] keys) throws Exception {

		 String s = new String(token);
		 ResultActions actions = null;

		 if (postData!=null) {
			 
			 actions =  mockMvc.perform(post(url).
					 contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
					 .header(HttpHeaders.AUTHORIZATION, "Bearer " + s)
					 .content(postData)
					 .accept(MediaType.APPLICATION_JSON))
			 	.andExpect(status().isOk());
			 
			 	
		 }else {
			  actions = mockMvc.perform(post(url).
					 contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
					 .header(HttpHeaders.AUTHORIZATION, "Bearer " + s)
					 .accept(MediaType.APPLICATION_JSON))
			 	.andExpect(status().isOk());
		 }
		 if (keys!=null) {
			 for (String key[] : keys) {
				 actions.andExpect(jsonPath(key[0]).value(key[1]));
			 }
		 }
		 actions.andDo(MockMvcResultHandlers.print());
 	    }
	

	

	
	

	@Test
	public void TestFoundIdGet() {
		ResponseEntity<QuoteDTO> quoteResponse = null;
		
		
		try {
			

			String[][] keys= { {"name" , "bezeq"} , {"id" ,"1"} };	
			sendGetWithExpectedData("http://localhost:8080/quote/getQuoteById/1",token, null,keys);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	@Test
	public void TestFoundIdPost() {
		ResponseEntity<QuoteDTO> quoteResponse = null;
		
		
		try {
			

			String[][] keys= { {"name" , "bezeq"} , {"id" ,"1"} };	
			sendPostWithExpectedData("http://localhost:8080/quote/getQuoteById/1",token, null,keys);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
	public int getRandomNumber() {
		Random rand = new Random(); 
	    int rand_num = rand.nextInt(1000);
	    return rand_num;
	}
	
	

}
