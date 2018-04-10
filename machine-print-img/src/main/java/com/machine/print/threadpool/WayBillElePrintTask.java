package com.machine.print.threadpool;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.machine.print.cache.PrintCacheUtils;
import com.machine.print.utils.io.PrintFileUtils;
import com.machine.print.utils.printer.PrinterUtils;
import com.machine.print.vo.PrintMessageVo;

/**
 * @Description: 电子面单打印task
 *
 * @author machine
 * @date 2017年12月20日 下午2:37:53
 */
public class WayBillElePrintTask implements Runnable {
	private String elePrintMessage;

	public WayBillElePrintTask(String elePrintMessage) {
		this.elePrintMessage = elePrintMessage;
	}

	@Override
	public void run() {
		String printerName = null;
		String expressName = null;
		PrintMessageVo printMessageVo = null;
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		try {
			printMessageVo = JSONObject.parseObject(elePrintMessage, PrintMessageVo.class);
			printerName = printMessageVo.getPrinterName();
			expressName = printMessageVo.getExpressName();
			logger.info("uuid: {}   printerName : {}   expressName : {} ", uuid, printerName, expressName);
			if (null == printerName || printerName.length() == 0) {
				/* 记录日志 */
				logger.error("PriterName is null ! Message: {} ", elePrintMessage);
			} else if (null == expressName || expressName.length() == 0) {
				/* 记录日志 */
				logger.error("ExpressName is null ! Message: {} ", elePrintMessage);
			} else {
				logger.debug("Successful message parsing RequestElePrintMessageVo!");
				/* 找不到报文对应的打印机 */
				if (!PrinterUtils.getInstance().containPrint(printerName)) {
					logger.error("Can not find Printer[找不到打印机] : {}", printerName);
				} else {
					logger.debug("Successfully found the corresponding printer: {}", printerName);
					/* 缓存接收的报文信息 */
					PrintCacheUtils.requestPrintMessagesMap.put(uuid, printMessageVo);
					/* 通过单例线程池保证打印顺序 */
					PrintThreadPool.getInstance().getPtintExecutor(printerName)
							.execute(new PrintImgTask(uuid, printerName));
					logger.info("CountInfo--------------uuid : {}  countIndex : {}", uuid,
							PrintCacheUtils.uuidCountIndex.get(uuid));
					logger.debug("Successful creation of task : {}", PrintImgTask.class);
					/* 线程池处理对应的逻辑[生成html] */
					PrintThreadPool.getInstance().generateHtmlExecutor
							.execute(new GenerateHtmlTask(uuid, printerName, expressName, printMessageVo));
					logger.debug("Successful creation of task : {}", GenerateHtmlTask.class);
				}
			}
		} catch (Exception e) {
			logger.error("EXCPTION:{} ElePrintMessage:{}", e.getMessage(), elePrintMessage);
			/* 清理数据--start */
			PrintCacheUtils.removeCacheByKey(printerName, uuid);
			PrintFileUtils.delTempFileByUUID(uuid);
			logger.error("Delete Cache and file by uuid : {}", uuid);
			/* 清理数据--end */
		}
	}

	private Logger logger = LoggerFactory.getLogger(WayBillElePrintTask.class);
}
