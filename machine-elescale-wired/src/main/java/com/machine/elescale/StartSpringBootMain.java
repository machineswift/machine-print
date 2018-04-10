package com.machine.elescale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import com.machine.elescale.param.ElescaleParam;
import com.machine.elescale.util.ComEventListener;

/**
 * @Description: springboot应用启动类
 *
 * @author machine
 * @date 2017年12月20日 下午12:56:02
 */
@SpringBootApplication
public class StartSpringBootMain {
	public static void main(String[] args) throws Exception {
		ConfigurableApplicationContext context = SpringApplication.run(StartSpringBootMain.class, args);
		ConfigurableEnvironment environment = context.getEnvironment();
		ComEventListener.getInstance(environment.getProperty("ELESCALE.PORT_NAME"));
		if("ture".equalsIgnoreCase(environment.getProperty("ELESCALE.reverse").trim())) {
			ElescaleParam.breverse = Boolean.TRUE;
		}else {
			ElescaleParam.breverse = Boolean.FALSE;
		}
		ElescaleParam.breverse = Boolean.parseBoolean(environment.getProperty("ELESCALE.reverse").trim());
		ElescaleParam.scale = Integer.parseInt(environment.getProperty("ELESCALE.scale").trim());
		ElescaleParam.divisor = Integer.parseInt(environment.getProperty("ELESCALE.divisor").trim());
	}
}