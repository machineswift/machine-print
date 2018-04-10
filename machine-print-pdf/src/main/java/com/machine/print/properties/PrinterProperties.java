package com.machine.print.properties;

import java.io.File;
import java.util.Set;
import org.slf4j.Logger;
import java.util.Arrays;
import java.util.HashSet;
import java.io.IOException;
import java.util.Properties;
import java.util.Enumeration;
import java.io.FileInputStream;
import org.slf4j.LoggerFactory;
import java.io.FileNotFoundException;
import com.machine.print.config.PrintConfig;
import com.machine.print.envm.PrintExceptions;
import com.machine.print.common.PrintCommonParam;
import com.machine.print.exception.impl.PrintGeneralException;

/**
 * @Description:  properties工具类
 *
 * @author machine
 * @date 2017年12月20日 下午1:14:01
*/
public class PrinterProperties {

	public static PrinterProperties getInstance() {
		if (printerProperties == null) {
			synchronized (PrinterProperties.class) {
				if (printerProperties == null) {
					printerProperties = new PrinterProperties();
				}
			}
		}
		return printerProperties;
	}

	/**
	 * @Description: 快递电子面单模板映射信息
	 *
	 * @author machine
	 * @date 2017年12月20日 下午1:15:04
	*/
	public String getExpressElesheet(String code) {
		return EXPRESS_ELESHEET.getProperty(code);
	}

	private void expressEleSheet(String prefixPath) {
		try {
			logger.info("loading properties : {}{}", prefixPath, "express-elesheet.properties");
			EXPRESS_ELESHEET.load(new FileInputStream(prefixPath + "express-elesheet.properties"));
			logger.info("End loading properties : {}{}", prefixPath, "express-elesheet.properties");
		} catch (FileNotFoundException e) {
			throw new PrintGeneralException(PrintExceptions.getValueByKey("PRINT_1100") + e.getMessage());
		} catch (IOException e) {
			throw new PrintGeneralException(PrintExceptions.getValueByKey("PRINT_1200") + e.getMessage());
		}
		try {
			/* 验证配置的模板是否存在 */
			logger.info("Start Verify that the template exists !");
			String templatePath = PrintConfig.PTINT_PARA + PrintCommonParam.FILE_SEPRATER + "template";
			File templateFile = new File(templatePath);
			if (templateFile.isDirectory()) {
				String[] fileNames = templateFile.list();
				Set<String> fileNamesSet = new HashSet<>(Arrays.asList(fileNames));
				Enumeration<?> enuValue = EXPRESS_ELESHEET.elements();
				while (enuValue.hasMoreElements()) {
					String value = (String) enuValue.nextElement();
					if (!fileNamesSet.contains(value)) {
						throw new PrintGeneralException(PrintExceptions.getValueByKey("PRINT_3100") + value);
					}
				}
			}
			logger.info("End Verify that the template exists !");
		} catch (PrintGeneralException e) {
			throw e;
		} catch (Exception e) {
			throw new PrintGeneralException(PrintExceptions.getValueByKey("PRINT_3010") + e.getMessage());
		}
	}

	/**
	 * @Description: 初始化方法[在类实例化的时候加载一次]
	 *
	 * @author machine
	 * @date 2017年12月20日 下午1:15:40
	*/
	private void init() {
		String prefixPath = PrintConfig.PTINT_PARA + PrintCommonParam.FILE_SEPRATER + "properties"
				+ PrintCommonParam.FILE_SEPRATER;
		logger.info("PrinterProperties prefixPath : {}", prefixPath);
		expressEleSheet(prefixPath);

	}

	private PrinterProperties() {
		init();
	}

	private Properties EXPRESS_ELESHEET = new Properties();
	private volatile static PrinterProperties printerProperties;
	private Logger logger = LoggerFactory.getLogger(PrinterProperties.class);
}
