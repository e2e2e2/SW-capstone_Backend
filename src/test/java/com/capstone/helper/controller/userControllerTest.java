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
        user.setName("�׽�Ʈ�� ����");
        user.setId(testUserID);
        user.setAuth(-1);
        user.setAddress("�ּ�1");
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
        user.setName("������Ʈ����");
        user.setAuth(-5);
        user.setAddress("�ּҾ�����Ʈ��");
        user.setPhone_number("010-1234-1523������Ʈ��");
        user.setPassword("password������Ʈ��");
        
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
