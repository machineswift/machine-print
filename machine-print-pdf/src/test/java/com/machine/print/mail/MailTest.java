//package com.machine.print.mail;
//
//import javax.annotation.Resource;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//import com.machine.print.StartSpringBootMain;
//
//@SpringBootTest(classes = StartSpringBootMain.class)
//@RunWith(SpringJUnit4ClassRunner.class)
//@WebAppConfiguration
//public class MailTest {
//	@Resource
//	private JavaMailSender javaMailSender;
//	
//	@Test
//	public void  testSendMail() {
////		SimpleMailMessage message = new SimpleMailMessage();
////		/*自己的邮箱地址*/
////		message.setFrom("machineswift@163.com");
////		/*接受者的邮箱*/
////		message.setTo("1254010874@qq.com");
////		message.setSubject("打印机BUG");
////		message.setText("不吃鸡,认认真真修改BUG!");
////		this.javaMailSender.send(message);
//	}
//
//}
