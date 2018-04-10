package com.machine.print.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConfigurationProperties(prefix = "PRINT")
@PropertySource("classpath:print-config.properties")
public class PrintConfig {
	// @Value("${TEMP_PATH}")
	public static String TEMP_PATH;
	// @Value("${RESOURCE_PATH}")
	public static String PTINT_PARA;
	// @Value("${HTML2PDF_TOOL}")
	public static String HTML2PDF_TOOL;
	// @Value("${PDF2IMG_TOOL}")
	public static String PDF2IMG_TOOL;
	// @Value("${TIME_INTERVAL_2_PRINTER}")
	public static long TIME_INTERVAL_2_PRINTER;

	// @Value("${PRINTER_MAX_COUNT_NUM}")
	public static long PRINTER_MAX_COUNT_NUM;

	// #生成html的线程池最小数量(最大数量3)
	public static int THREAD_NUM_GEN_HTML;

	// #html转pdf的线程池最小数量(最大数量8)
	public static int THREAD_NUM_GEN_PDF;

	// #pdf转img的线程池最小数量(最大数量5)
	public static int THREAD_NUM_GEN_IMG;
}
