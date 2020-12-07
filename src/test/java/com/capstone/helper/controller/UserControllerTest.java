package com.capstone.helper.controller;


import com.capstone.helper.model.ReceiverEnvironment;
import com.capstone.helper.model.Token;
import com.capstone.helper.model.User;
import com.capstone.helper.security.AuthInterceptor;
import com.capstone.helper.service.ReceiverEnvironmentService;
import com.capstone.helper.service.TokenService;
import com.capstone.helper.service.UserService;
import com.capstone.helper.vo.UserVo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(UserController.class)
public class UserControllerTest{
	@Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @MockBean
    private ReceiverEnvironmentService receiverEnvironmentService;  
    @MockBean
    private TokenService tokenService;
    @Autowired
	private ObjectMapper objectMapper;
    @MockBean
    private HttpSession session;
    @MockBean
    AuthInterceptor authInterceptor;
    
    @BeforeEach
    public void setUp() throws Exception {
    	Mockito.doReturn(true).when(authInterceptor).preHandle(Mockito.any(), Mockito.any(), Mockito.any());
    }
	
    @Test
    public void getOneUserTest() throws Exception {
    	
    	User user = new User("userID", "Hong", "password1234",0, "010-1111-2222", "address");
    	user.setId(1);

    	given(userService.findIdByuserID("userID")).willReturn(1);
    	given(userService.findOne(1)).willReturn(user);
    	
	    mockMvc.perform(get("/user"))
	    		.andExpect(status().isOk());
    }
    
    @Test
    public void saveUserTest() throws Exception {
    	
    	User newUserInfo = new User("userID111","John","password5678",0,"010-2222-3333","address");
    	newUserInfo.setId(2);
    	String requestBody = objectMapper.writeValueAsString(newUserInfo);
    	
    	given(userService.save(newUserInfo)).willReturn(newUserInfo);
    	System.out.println(requestBody);
    	
    	mockMvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON).content(requestBody))
    			.andExpect(status().isOk());
    	
    }
    
    @Test
    public void deleteUserTest() throws Exception {
    	
    	User user = new User("userID", "Hong", "password1234",0, "010-1111-2222", "address");
    	user.setId(1);
    	
    	given(userService.findIdByuserID(user.getUserID())).willReturn(user.getId());
    	given(userService.delete(user.getId())).willReturn(user.getId());
    	
    	mockMvc.perform(delete("/user"))
    			.andExpect(status().isOk());
    	
    }
    
    @Test
    public void updateUserTest() throws Exception {
    	
    	User user = new User("userID222","Hong","password5678",0,"010-2222-3333","address");
    	user.setId(1);
    	UserVo userVo = new UserVo("userID222","Hong","password5678",0,"010-2222-3333","address");
    	String requestBody = objectMapper.writeValueAsString(userVo);
    	
    	given(userService.findIdByuserID(user.getUserID())).willReturn(user.getId());
    	given(userService.update(userVo,user.getId())).willReturn(user);
    	
    	mockMvc.perform(patch("/user").contentType(MediaType.APPLICATION_JSON).content(requestBody))
    			.andExpect(status().isOk());
    	
    }
    
    @Test
    public void getAllmembersTest() throws Exception {
    	
    	User user1 = new User("userID111","Hong1","password5678",0,"010-2222-1111","address");
    	user1.setId(1);
    	User user2 = new User("userID222","Hong2","password5678",0,"010-2222-2222","address");
    	user2.setId(2);
    	User user3 = new User("userID333","Hong3","password5678",0,"010-2222-3333","address");
    	user3.setId(3);
    	UserVo userVo = new UserVo("userID222","Hong","password5678",0,"010-2222-3333","address");
    	String requestBody = objectMapper.writeValueAsString(userVo);
    	
    	List<User> userList = new ArrayList<>();
    	userList.add(user1);
    	userList.add(user2);
    	userList.add(user3);
    	
    	
    	given(userService.findAll()).willReturn(userList);
    	
    	mockMvc.perform(get("/user-list"))
    			.andExpect(status().isOk());
    	
    }
    
    @Test
    public void registerAppWithTokenTest() throws Exception {
    	
    	User user1 = new User("userID111","Hong1","password5678",0,"010-2222-1111","address");
    	user1.setId(1);
    	String userID = "userID111";
    	Integer numID = 1;
    	
    	ReceiverEnvironment receiverEnvironment = new ReceiverEnvironment(user1.getId(), false, false);
    	
    	String tokenValue = "cklBYJZ4TnalsXkdFYbV1O:APA91bHjPnBWVBIRtez283VIKBVLzPbDmeTBSqd7nU_97nkPYf8XYHvEzwNkCmPmuzqmJfMOWUIiwIvuSf1QjYX1rz32CJftvMZzm6Y0cwSBImZTL5tcVPJscSalRZ33n2WvM-pdhQKP";
    	Token token = new Token(numID,tokenValue);
    	
    	JsonObject jsonObject = new JsonObject();
    	jsonObject.addProperty("token", tokenValue);
    	jsonObject.addProperty("id", numID);
    	String requestBody = jsonObject.toString();
    	
    	
    	given(session.getAttribute("userID")).willReturn(user1.getUserID());
    	given(userService.findIdByuserID(user1.getUserID())).willReturn(user1.getId());
    	given(receiverEnvironmentService.findByUserId(numID)).willReturn(receiverEnvironment);
    	given(receiverEnvironmentService.save(receiverEnvironment)).willReturn(receiverEnvironment);
    	given(tokenService.save(token)).willReturn(token);
    	
    	mockMvc.perform(post("/user/has-app/true").contentType(MediaType.APPLICATION_JSON).content(requestBody))
				.andExpect(status().isOk());
    }
    
    @Test
    public void registerWebTest() throws Exception {
    	
    	User user1 = new User("userID111","Hong1","password5678",0,"010-2222-1111","address");
    	user1.setId(1);
    	String userID = "userID111";
    	Integer numID = 1;
    	
    	ReceiverEnvironment receiverEnvironment = new ReceiverEnvironment(user1.getId(), false, false);
    	
    	given(session.getAttribute("userID")).willReturn(user1.getUserID());
    	given(userService.findIdByuserID(user1.getUserID())).willReturn(user1.getId());
    	given(receiverEnvironmentService.findByUserId(numID)).willReturn(receiverEnvironment);
    	given(receiverEnvironmentService.save(receiverEnvironment)).willReturn(receiverEnvironment);
    	
    	mockMvc.perform(post("/user/has-web/true"))
				.andExpect(status().isOk());
    }
    
}
