package com.capstone.helper.controller;

import com.capstone.helper.SwCapstoneBackendApplicationTests;
import com.capstone.helper.model.User;
import com.capstone.helper.vo.UserVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class userControllerTest extends SwCapstoneBackendApplicationTests{
	int testUserID;
	@Autowired
    private UserController controller;
 
    @Test
    public void create(){
        User user = new User();
        user.setName("테스트용 유저");
        user.setId(testUserID);
        user.setAuth(-1);
        user.setAddress("주소1");
        user.setPhone_number("010-1111-2222");
        user.setPassword("password!@#");
 
        User newUser = controller.saveUser(user);
        testUserID = newUser.getId();
        
    }
/*
    @Test
    public void read(){
        User readUser = controller.getOneUser(testUserID);
        
    }

    @Test
    public void update(){
        UserVo user = new UserVo();
        user.setId(testUserID);
        user.setName("업데이트유저");
        user.setAuth(-5);
        user.setAddress("주소업데이트됨");
        user.setPhone_number("010-1234-1523업데이트됨");
        user.setPassword("password업데이트됨");
        
        controller.updateUser(user);

    }
    
    
    @Test
    public void delete(){
    	UserVo user = new UserVo();
        user.setId(testUserID);
        
        controller.deleteUser(user);
    }
    */
}
