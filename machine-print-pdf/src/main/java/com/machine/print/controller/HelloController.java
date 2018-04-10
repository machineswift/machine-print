package com.machine.print.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	@RequestMapping("/html")
	public String home() {
		return "{\"barCode\":\"956789452458\"}";
	}

	@RequestMapping("/hello")
	public void hello(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		PrintWriter out = resp.getWriter();
		out.println("<html>" + "<head><title>page2</title></head>" + "<body>" + new Date() + "</body>" + "</html>");
	}

	@RequestMapping("/echo")
	public String echo(String msg) {
		return "[ECHO]" + msg;
	}

	@RequestMapping(value = "/message/{message}", method = RequestMethod.GET)
	public String message(String msg) {
		return "[ECHO]" + msg;
	}

	@RequestMapping("/mul")
	public Object mul(int num) {
		return num * 6;
	}

	@RequestMapping("/object")
	public String object(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("====客户IP地址: " + request.getRemoteAddr());
		System.out.println("====客户端响应编码: " + response.getCharacterEncoding());
		System.out.println("====取得SessionID: " + request.getSession().getId());
		System.out.println("====取得真实路径: " + request.getServletContext().getRealPath("/upload"));
		return "com.machine";
	}
}
