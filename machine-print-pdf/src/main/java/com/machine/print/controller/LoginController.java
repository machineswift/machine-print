package com.machine.print.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Description: login
 *
 * @author machine
 * @date 2017年12月18日 下午2:37:52
 */
@Controller
public class LoginController {
	/**
	 * @Description: login
	 *
	 * @author machine
	 * @date 2017年12月18日 下午2:39:32
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(String loginEmail, String loginPass, Model model) {
		if ("deppon".equals(loginEmail) && "deppon".equals(loginPass)) {
			model.addAttribute("name", loginEmail);
			return "printer/printers";
		} else {
			model.addAttribute("loginEmail", loginEmail);
			model.addAttribute("loginPass", "deppon");
			return "index";
		}
	}
}
