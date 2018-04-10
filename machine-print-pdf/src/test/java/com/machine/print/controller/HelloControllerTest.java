package com.machine.print.controller;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.machine.print.StartSpringBootMain;

import junit.framework.TestCase;

@SpringBootTest(classes = StartSpringBootMain.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class HelloControllerTest {
	@Resource
	private HelloController helloController;

	@Test
	public void testHome() {
		TestCase.assertEquals(this.helloController.home(), "{\"barCode\":\"956789452458\"}");
	}
}
