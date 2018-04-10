package com.machine.print.utils.printer;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.print.DocFlavor;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description: 打印机工具类(单例)
 *
 * @author machine
 * @date 2017年12月20日 下午12:50:27
*/
public class PrinterUtils {

	public static PrinterUtils getInstance() {
		if (printerUtils == null) {
			synchronized (PrinterUtils.class) {
				if (printerUtils == null) {
					printerUtils = new PrinterUtils();
				}
			}
		}
		return printerUtils;
	}

	/**
	 * @Description: 判断打印机是否可用(缓存时间:cacheTime)
	 *
	 * @author machine
	 * @date 2017年12月20日 下午12:51:07
	*/
	public boolean containPrint(String printName) {
		synchronized (lock) {
			if (System.currentTimeMillis() - previousTimeMillis > cacheTime) {
				if (printerMap.containsKey(printName)) {
					return Boolean.TRUE;
				}
				if (printerMap.size() == 0) {
					previousTimeMillis = System.currentTimeMillis();
					printerMap = new ConcurrentHashMap<String, PrintService>();
					for (PrintService printService : getPrintServices()) {
						printerMap.put(printService.getName(), printService);
					}
				}
			} else {
				previousTimeMillis = System.currentTimeMillis();
				logger.debug("PrinterCache Start loading PrintServices!");
				printerMap = new ConcurrentHashMap<String, PrintService>();
				for (PrintService printService : getPrintServices()) {
					printerMap.put(printService.getName(), printService);
					logger.debug("PrinterCache loading PrintServiceName : {}", printService.getName());
				}
				logger.debug("PrinterCache End loading PrintServices!");
			}
			return printerMap.containsKey(printName);
		}
	}

	/**
	 * @Description:  得到可用的打印机(缓存时间:cacheTime)
	 *
	 * @author machine
	 * @date 2017年12月20日 下午12:52:06
	*/
	public PrintService getPrintService(String printName) {
		synchronized (lock) {
			if (System.currentTimeMillis() - previousTimeMillis < cacheTime) {
				if (printerMap.containsKey(printName)) {
					return printerMap.get(printName);
				}
				if (printerMap.size() == 0) {
					previousTimeMillis = System.currentTimeMillis();
					printerMap = new ConcurrentHashMap<String, PrintService>();
					for (PrintService printService : getPrintServices()) {
						printerMap.put(printService.getName(), printService);
					}
				}
			} else {
				previousTimeMillis = System.currentTimeMillis();
				logger.debug("PrinterCache Start loading PrintServices!");
				printerMap = new ConcurrentHashMap<String, PrintService>();
				for (PrintService printService : getPrintServices()) {
					printerMap.put(printService.getName(), printService);
					logger.debug("PrinterCache loading PrintServiceName : {}", printService.getName());
				}
				logger.debug("PrinterCache End loading PrintServices!");
			}
			return printerMap.get(printName);
		}
	}

	/**
	 * @Description: 返回可用的打印机服务
	 *
	 * @author machine
	 * @date 2017年12月20日 下午12:52:26
	*/
	public PrintService[] getPrintServices() {
		PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
		DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
		return PrintServiceLookup.lookupPrintServices(flavor, pras);
	}

	/**
	 * @Description: 可用的打印机名字列表
	 *
	 * @author machine
	 * @date 2017年12月20日 下午12:52:47
	*/
	public String[] getPrinterNames() {
		PrintService[] printServices = getPrintServices();
		String[] PrinterNames = new String[printServices.length];
		int index = 0;
		for (PrintService rintService : getPrintServices()) {
			PrinterNames[index] = rintService.getName();
			index++;
		}
		return PrinterNames;
	}

	/**
	 * @Description: 刷新缓存(printerMap)
	 *
	 * @author machine
	 * @date 2017年12月20日 下午12:52:59
	*/
	public void refreshCache() {
		synchronized (lock) {
			previousTimeMillis = 0L;
		}
	}

	private PrinterUtils() {
	}

	private static Object lock = new Object();
	/* 打印机列表缓存时间 */
	private static long cacheTime = 1000 * 60L;
	private volatile static PrinterUtils printerUtils;
	private Logger logger = LoggerFactory.getLogger(PrinterUtils.class);
	private static volatile long previousTimeMillis = System.currentTimeMillis();
	private Map<String, PrintService> printerMap = new HashMap<String, PrintService>();
}
