package com.thirdi_pms;

import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.thirdi.pms.login.api.LoginService;
import com.thirdi.pms.login.impl.LoginServiceImpl;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:src/main/resources/services-config.xml")
public class AppTest {
    @Autowired
    LoginService loginService;

    @Test
    public void testClass()
    {
        //assertTrue( true );
    	String user="preeti.shah";
    	String pass="password";
    	System.out.println(loginService);
    	int apprempid=loginService.checkLogin(user, pass);
    	System.out.println(apprempid);
    }
}
