package com.capstone.helper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import com.capstone.helper.vo.UserVo;
import com.fasterxml.jackson.databind.ObjectMapper;


@SpringBootTest
@AutoConfigureMockMvc
class LoginTest {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	
	protected MockHttpSession session;
	
	
	@BeforeEach
	public void setUp() throws Exception{
		String content = objectMapper.writeValueAsString(new UserVo("userID", "Hong", "password1234",0, "010-1111-2222", "address"));
		System.out.println(content);
		mockMvc.perform(post("/register/local").contentType(MediaType.APPLICATION_JSON).content(content));
	}
	
	@Test
	public void loginAndLogoutTest() throws Exception{
		
		String content = objectMapper.writeValueAsString(new UserVo("userID","","password1234",0,"",""));
		
		System.out.println(content);
		
		session = (MockHttpSession) mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON).content(content))
						.andExpect(status().isOk())
						.andReturn().getRequest().getSession();
		
		mockMvc.perform(post("/logout").session(session))
		.andExpect(status().isOk());
		
	}
	
	@Test
	public void accessWithoutLoginTest() throws Exception{
		mockMvc.perform(delete("/user"))
				.andExpect(status().isUnauthorized());
	}
	
	@AfterEach
	public void deleteUser() throws Exception{
		
		String content = objectMapper.writeValueAsString(new UserVo("userID","","password1234",0,"",""));
		
		session = (MockHttpSession) mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON).content(content))
				.andReturn().getRequest().getSession();
		mockMvc.perform(delete("/user").session(session));
		
	}

	
}
