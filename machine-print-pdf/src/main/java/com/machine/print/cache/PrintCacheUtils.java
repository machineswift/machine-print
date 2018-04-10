package com.machine.print.cache;

import java.io.File;
import java.util.Base64;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.concurrent.ConcurrentMap;
import com.machine.print.config.PrintConfig;
import java.util.concurrent.ConcurrentHashMap;
import com.machine.print.common.PrintCommonParam;
import com.machine.print.utils.cas.CasAtomicCounter;
import com.machine.print.exception.impl.PrintBusinessException;

/**
 * @Description: 缓存工具类
 *
 * @author machine
 * @date 2017年12月20日 下午12:59:30
 */
public class PrintCacheUtils {

	/**
	 * key:UUID value:接收的报文实体类
	 */
	public final static ConcurrentMap<String, Object> requestPrintMessagesMap = new ConcurrentHashMap<String, Object>();

	/**
	 * key:UUID value:接收报文对应的打印机队列的计数
	 */
	public final static ConcurrentMap<String, Integer> uuidCountIndex = new ConcurrentHashMap<String, Integer>();

	/**
	 * 已经生成html的UUID
	 */
	public final  static ConcurrentMap<String, Boolean> htmlUUIDMap = new ConcurrentHashMap<String, Boolean>();

	/**
	 * 已经生成pdf的UUID
	 */
	public final static ConcurrentMap<String, Boolean> pdfUUIDMap = new ConcurrentHashMap<String, Boolean>();

	/**
	 * @Description:获取打印机当前打印的次数
	 *
	 * @author machine
	 * @date 2017年12月20日 下午1:00:17
	 */
	public static int getPrinterCountNextIndex(String printName) {
		CasAtomicCounter counter = printerIndexMap.get(printName);
		if (null == counter) {
			synchronized (printerIndexMapLock) {
				counter = printerIndexMap.get(printName);
				if (null == counter) {
					counter = new CasAtomicCounter();
					printerIndexMap.put(printName, counter);
				}
			}
		} else if (counter.getCounter() > PrintConfig.PRINTER_MAX_COUNT_NUM) {
			synchronized (printerIndexMapLock) {
				if (counter.getCounter() > PrintConfig.PRINTER_MAX_COUNT_NUM) {
					counter = new CasAtomicCounter();
					printerIndexMap.put(printName, counter);
				} else {
					counter = printerIndexMap.get(printName);
				}
			}
		}
		return counter.getNextIndex();
	}

	/**
	 * @Description: 清理对应打印机名称和uuid的缓存
	 *
	 * @author machine
	 * @date 2017年12月20日 下午1:01:02
	 */
	public static void removeCacheByKey(String printerName, String uuid) {
		if (null == uuid || uuid.length() == 0) {
			throw new PrintBusinessException("PRINT_810");
		}
		if (null != printerName && printerName.length() > 0) {
			ConcurrentMap<String, String> printQueue = printersQueueMap.get(printerName);
			if (null != printQueue) {
				printQueue.remove(uuid);
			}
		}
		pdfUUIDMap.remove(uuid);
		htmlUUIDMap.remove(uuid);
		uuidCountIndex.remove(uuid);
		requestPrintMessagesMap.remove(uuid);
	}

	/**
	 * @Description: 信息按打印机名称存入对应的缓存[页面查询]
	 *
	 * @author machine
	 * @date 2017年12月20日 下午1:01:54
	 */
	public static void putPrintersQueueMap(String printerName, String uuid, String code) {
		ConcurrentMap<String, String> printQueue = printersQueueMap.get(printerName);
		if (null == printQueue) {
			/* 初始化对应打印机的队列 */
			synchronized (printersQueueMapLock) {
				if (null == printersQueueMap.get(printerName)) {
					printQueue = new ConcurrentHashMap<String, String>();
					printersQueueMap.put(printerName, printQueue);
				}
			}
		}
		printQueue.put(uuid, code);
	}

	/**
	 * @Description: 获取对应打印机的队列缓存
	 *
	 * @author machine
	 * @date 2017年12月20日 下午1:04:18
	*/
	public static ConcurrentMap<String, String> getPrintersQueueMapValues(String printerName) {
		ConcurrentMap<String, String> printQueue = printersQueueMap.get(printerName);
		if (null == printQueue) {
			printQueue = new ConcurrentHashMap<String, String>();
		}
		return printQueue;
	}

	/**
	 * @Description:  获取静态图片的Base64[面单类型,快递名称,图片名称]
	 *
	 * @author machine
	 * @date 2017年12月20日 下午1:03:06
	*/
	public static String getImgStr(String type, String expressName, String imgName) {
		if (null == type || type.length() == 0) {
			throw new PrintBusinessException("PRINT_4110", "路径type为空!");
		}
		if (null == expressName || expressName.length() == 0) {
			throw new PrintBusinessException("PRINT_4110", "路径expressName为空!");
		}
		if (null == imgName || imgName.length() == 0) {
			throw new PrintBusinessException("PRINT_4110", "路径imgName为空!");
		}
		String imgType = null;
		if (imgName.endsWith(".gif")) {
			imgType = "gif";
		} else if (imgName.endsWith(".jpg")) {
			imgType = "jpg";
		} else if (imgName.endsWith(".jpeg")) {
			imgType = "jpeg";
		} else if (imgName.endsWith(".png")) {
			imgType = "png";
		} else {
			throw new PrintBusinessException("PRINT_4010", "imgName:" + imgName);
		}

		String key = type + "-" + expressName + "-" + imgName;
		String imgStr = printImgMap.get(key);
		if (null == imgStr) {
			synchronized (printImgMapLock) {
				if (null == printImgMap.get(key)) {
					String pathStr = new StringBuilder(PrintConfig.PTINT_PARA).append(PrintCommonParam.FILE_SEPRATER)
							.append("img").append(PrintCommonParam.FILE_SEPRATER).append(type)
							.append(PrintCommonParam.FILE_SEPRATER).append(expressName).append(PrintCommonParam.FILE_SEPRATER)
							.append(imgName).toString();
					File fileImg = new File(pathStr);
					if (fileImg.exists() && fileImg.isFile()) {
						try {
							BufferedImage bi = ImageIO.read(fileImg);
							ByteArrayOutputStream baos = new ByteArrayOutputStream();
							ImageIO.write(bi, imgType, baos);
							byte[] bytes = baos.toByteArray();
							String encoded = Base64.getEncoder().encodeToString(bytes);
							imgStr = new StringBuilder("data:image/").append(imgType).append(";base64,").append(encoded)
									.toString();
						} catch (IOException e) {
							throw new PrintBusinessException("PRINT_4004", e.getMessage());
						}
					} else {
						throw new PrintBusinessException("PRINT_4110", "imgPath:" + pathStr);
					}
				}
			}
		}
		return imgStr;
	}

	private final static Object printImgMapLock = new Object();
	private final static Object printersQueueMapLock = new Object();
	private final static Object printerIndexMapLock = new Object();
	private final static ConcurrentMap<String, String> printImgMap = new ConcurrentHashMap<String, String>();

	/* 保存接收的报文 [key:打印机名字 value:<报文的UUID>] */
	private static ConcurrentMap<String, ConcurrentMap<String, String>> printersQueueMap = new ConcurrentHashMap<String, ConcurrentMap<String, String>>();
	/* 打印报文计数 器[每个打印机对应一个计数器]*/
	private static ConcurrentMap<String, CasAtomicCounter> printerIndexMap = new ConcurrentHashMap<String, CasAtomicCounter>();
}
