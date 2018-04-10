package com.machine.print;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import com.machine.print.common.PrintCommonParam;
import com.machine.print.config.PrintConfig;
import com.machine.print.envm.PrintExceptions;
import com.machine.print.exception.impl.PrintBusinessException;
import com.machine.print.utils.io.FileUtils;

@SpringBootApplication
public class StartSpringBootMain {
	public static void main(String[] args) throws Exception {
		ConfigurableApplicationContext context = SpringApplication.run(StartSpringBootMain.class, args);
		ConfigurableEnvironment environment = context.getEnvironment();
		initPrintConfig(environment);
		init(PrintConfig.TEMP_PATH);
	}

	private static void initPrintConfig(ConfigurableEnvironment environment) {

		PrintConfig.TEMP_PATH = environment.getProperty("PRINT.TEMP_PATH");

		PrintConfig.PTINT_PARA = environment.getProperty("PRINT.PTINT_PARA");

		PrintConfig.HTML2PDF_TOOL = environment.getProperty("PRINT.HTML2PDF_TOOL");

		PrintConfig.PDF2IMG_TOOL = environment.getProperty("PRINT.PDF2IMG_TOOL");

		PrintConfig.TIME_INTERVAL_2_PRINTER = Long.parseLong(environment.getProperty("PRINT.TIME_INTERVAL_2_PRINTER"));

		PrintConfig.PRINTER_MAX_COUNT_NUM = Long.parseLong(environment.getProperty("PRINT.PRINTER_MAX_COUNT_NUM"));

		PrintConfig.THREAD_NUM_GEN_HTML = Integer.parseInt(environment.getProperty("PRINT.THREAD_NUM_GEN_HTML"));
		PrintConfig.THREAD_NUM_GEN_PDF = Integer.parseInt(environment.getProperty("PRINT.THREAD_NUM_GEN_PDF"));
		PrintConfig.THREAD_NUM_GEN_IMG = Integer.parseInt(environment.getProperty("PRINT.THREAD_NUM_GEN_IMG"));
	}

	private static void init(String tempPath) {
		/* 清理目录的临时文件 */
		logger.info("TEMP_PATH : {}", tempPath);
		if (null == tempPath || tempPath.length() == 0) {
			logger.error("Code : {} message : {}", "PRINT_1700", PrintExceptions.getValueByKey("PRINT_1700"));
			throw new PrintBusinessException("PRINT_1700", "TEMP_PATH");
		}
		File tempFile = new File(tempPath);
		if (!tempFile.exists()) {
			logger.error("Code : {} message : {}", "PRINT_510", PrintExceptions.getValueByKey("PRINT_510"));
			FileUtils.mkDir(tempFile);
			logger.warn("TEMP_PATH not exit  create new Directory : {}", tempPath);
		}
		if (!tempFile.isDirectory()) {
			tempFile.delete();
			FileUtils.mkDir(tempFile);
			logger.warn("TEMP_PATH is a file then delete and  create new Directory : {}", tempPath);
		}
		File[] subFiles = tempFile.listFiles();
		// 遍历该目录
		for (File subFile : subFiles) {
			// 递归清空其内容后再删除
			FileUtils.delDir(subFile);
		}

		/* 创建必要的文件夹 */
		FileUtils.mkDir(new File(PrintCommonParam.HTML_PATH));
		logger.info("Create new Directory : {}", PrintCommonParam.HTML_PATH);
		FileUtils.mkDir(new File(PrintCommonParam.PDF_PATH));
		logger.info("Create new Directory : {}", PrintCommonParam.PDF_PATH);
		FileUtils.mkDir(new File(PrintCommonParam.IMG_PATH));
		logger.info("Create new Directory : {}", PrintCommonParam.IMG_PATH);
	}

	private static Logger logger = LoggerFactory.getLogger(StartSpringBootMain.class);
}