package com.machine.print.utils.printer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.OrientationRequested;
import javax.print.attribute.standard.PrintQuality;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.machine.print.common.PrintCommonParam;
import com.machine.print.exception.impl.PrintBusinessException;

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
	 * 判断打印机是否可用(缓存时间:cacheTime)
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
	 * 得到可用的打印机(缓存时间:cacheTime)
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

	public PrintService[] getPrintServices() {
		PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
		DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
		return PrintServiceLookup.lookupPrintServices(flavor, pras);
	}

	/**
	 * 可用的打印机名字列表
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

	public void printImg(String printerName, String uuid) throws PrintException, IOException {
		String imgPath = new StringBuilder(PrintCommonParam.IMG_PATH).append(uuid).append(".pdf-JPG")
				.append(PrintCommonParam.FILE_SEPRATER).append("P0001.jpg").toString();
		//String imgPath = new StringBuilder(PrintCommonParam.IMG_PATH).append(uuid).append(".jpg").toString();
		if(!new File(imgPath).exists()) {
			return;
		}
		try {
			DocFlavor dof = null;
			if (imgPath.endsWith(".gif")) {
				dof = DocFlavor.INPUT_STREAM.GIF;
			} else if (imgPath.endsWith(".jpg")) {
				dof = DocFlavor.INPUT_STREAM.JPEG;
			} else if (imgPath.endsWith(".png")) {
				dof = DocFlavor.INPUT_STREAM.PNG;
			} else {
				throw new PrintBusinessException("PRINT_2204", imgPath);
			}

			PrintService ps = getPrintService(printerName);

			PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
			pras.add(OrientationRequested.PORTRAIT);

			pras.add(PrintQuality.HIGH);
			DocAttributeSet das = new HashDocAttributeSet();
			// 设置打印纸张的大小（以毫米为单位）
			das.add(new MediaPrintableArea(0F, 0F, 100F, 180F, MediaPrintableArea.MM));
			FileInputStream fin = new FileInputStream(imgPath);
			Doc doc = new SimpleDoc(fin, dof, das);
			DocPrintJob job = ps.createPrintJob();
			job.print(doc, pras);
			fin.close();
		} catch (IOException ie) {
			throw new PrintBusinessException("PRINT_2304", ie.getMessage());
		} catch (PrintException pe) {
			throw new PrintBusinessException("PRINT_2306", pe.getMessage());
		}
	}

	/**
	 * 刷新缓存
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
