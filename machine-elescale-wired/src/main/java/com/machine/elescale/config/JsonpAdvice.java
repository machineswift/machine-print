package com.machine.elescale.config;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice;

/**
 * @Description: MvcConfigurer JsonpAdvice
 * @author machine
 * @date 2017年12月20日 下午1:09:38
 */
@ControllerAdvice(basePackages = "com.machine.elescale.controller")
public class JsonpAdvice extends AbstractJsonpResponseBodyAdvice {
	public JsonpAdvice() {
		super("callback", "jsonp");
	}
}