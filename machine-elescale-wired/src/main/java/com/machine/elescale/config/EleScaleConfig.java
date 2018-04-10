package com.machine.elescale.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author machine
 * @date 2017年12月20日 下午1:11:29
*/
@Configuration
@ConfigurationProperties(prefix = "ELESCALE")
@PropertySource("classpath:elescale-config.properties")
public class EleScaleConfig {
	//@Value("${PORT_NAME}")
	public static String PORT_NAME;
}
