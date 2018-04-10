package com.machine.print.threadpool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.print.PrintService;
import com.machine.print.config.PrintConfig;
import com.machine.print.cache.PrintCacheUtils;
import com.machine.print.utils.io.PrintFileUtils;
import com.machine.print.utils.printPdf.PtintPdf;
import com.machine.print.utils.printer.PrinterUtils;
import com.machine.print.exception.impl.PrintBusinessException;

/**
 * @Description:打印pdf
 *
 * @author machine
 * @date 2017年12月20日 下午12:22:06
 */
public class PrintPdfTask implements Runnable {

	private String uuid;
	private String printName;

	public PrintPdfTask(String uuid, String printName) {
		this.uuid = uuid;
		this.printName = printName;
	}

	/**
	 * 打印要计数的报文
	 */
	public PrintPdfTask(String uuid, String printName, boolean bCount) {
		if (bCount) {
			PrintCacheUtils.uuidCountIndex.put(uuid, PrintCacheUtils.getPrinterCountNextIndex(printName));
		}
		this.uuid = uuid;
		this.printName = printName;
	}

	@Override
	public void run() {
		boolean bPrint = Boolean.TRUE;
		if (!PrintCacheUtils.pdfUUIDMap.containsKey(uuid)) {
			/*判断是否存在对应的pdf发生异常重试次数*/
			int tryTimes = 0;
			while (true) {
				if (tryTimes > 5) {
					logger.error("Exception  tryTimes: {}", tryTimes);
					break;
				}
				try {
					if (PrintCacheUtils.pdfUUIDMap.containsKey(uuid)) {
						break;
					}
					if (PrintCacheUtils.requestPrintMessagesMap.containsKey(uuid)) {
						if (PrintCacheUtils.htmlUUIDMap.containsKey(uuid)) {
							logger.debug("Processing to htmlUUIDMap and next step is generate pdfFile : {}", uuid);
							Thread.sleep(200);
						} else {
							logger.debug("Processing to ruquestPrintMessagesMap and next step is generate htmlFile : {}", uuid);
							Thread.sleep(360);
						}
					} else {
						logger.info("The pdf has been clean : {}", uuid);
						bPrint = Boolean.FALSE;
						break;
					}
				} catch (InterruptedException e) {
					tryTimes++;
					logger.error("InterruptedException : {}", e.getMessage());
				} catch (Exception e) {
					tryTimes++;
					logger.error("Exception : {}", e.getMessage());
				}
			}
		}
		/* 判断是否打印 */
		if (bPrint) {
			while (true) {
				try {
					Thread.sleep(PrintConfig.TIME_INTERVAL_2_PRINTER);
				} catch (InterruptedException e1) {
					logger.error("InterruptedException : {}", e1.getMessage());
				}
				try {
					logger.debug("Begin print,printerName : {} uuid : {} ", printName, uuid);
					/* pdf发送到打印机打印 */
					PtintPdf.getInstance().execute(printName, uuid);
					break;
				} catch (PrintBusinessException e) {
					logger.error("PrintBusinessException  uuid : {} code : {}  message : {}", uuid, e.getErrorCode(),
							e.getMessage());
					if ("PRINT_2304".equals(e.getErrorCode())) {
						break;
					} else {
						PrinterUtils.getInstance().refreshCache();
						PrintService printService = PrinterUtils.getInstance().getPrintService(printName);
						if (null == printService) {
							logger.error("Can not find printer : {}", PrintPdfTask.class, printName);
							try {
								/*打印机问题则睡眠等待打印机恢复*/
								Thread.sleep(5 * 1000);
							} catch (InterruptedException e1) {
								logger.error("InterruptedException : {}", e1.getMessage());
							}
						}
					}
				} catch (Exception e) {
					logger.error("Exception uuid : {}   {}", uuid, e.getMessage());
					break;
				}
			}
		}
		/* 清理数据--start */
		PrintCacheUtils.removeCacheByKey(printName, uuid);
		PrintFileUtils.delTempFileByUUID(uuid);
		logger.debug("Delete Cache and file by uuid : {}", uuid);
		/* 清理数据--end */
	}

	private Logger logger = LoggerFactory.getLogger(PrintPdfTask.class);
}
