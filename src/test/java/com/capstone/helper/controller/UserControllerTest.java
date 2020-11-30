package com.capstone.helper.controller;


import com.capstone.helper.model.User;
import com.capstone.helper.security.AuthInterceptor;
import com.capstone.helper.service.ReceiverEnvironmentService;
import com.capstone.helper.service.TokenService;
import com.capstone.helper.service.UserService;
import com.capstone.helper.vo.UserVo;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import javax.servlet.http.HttpSession;

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
}
