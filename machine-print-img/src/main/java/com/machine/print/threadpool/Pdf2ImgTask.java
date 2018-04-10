package com.machine.print.threadpool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.machine.print.cache.PrintCacheUtils;
import com.machine.print.common.PrintCommonParam;
import com.machine.print.utils.io.PrintFileUtils;
import com.machine.print.utils.pdf2jepg.Pdf2Jepg;

/**
 * @author machine
 *
 */
public class Pdf2ImgTask implements Runnable {

	private String uuid;
	private String printerName;

	public Pdf2ImgTask(String uuid, String printerName) {
		this.uuid = uuid;
		this.printerName = printerName;
	}

	@Override
	public void run() {
		try {
			if (PrintCacheUtils.requestPrintMessagesMap.containsKey(uuid)
					&& PrintCacheUtils.htmlUUIDMap.containsKey(uuid) && PrintCacheUtils.pdfUUIDMap.containsKey(uuid)) {
				String pdfPath = new StringBuilder(PrintCommonParam.PDF_PATH).append(uuid).append(".pdf").toString();
				String imgPath = PrintCommonParam.IMG_PATH;
				PrintCacheUtils.concurrentMap.put(uuid, printerName);
				Pdf2Jepg.execute(pdfPath, imgPath);
				PrintCacheUtils.concurrentMap.remove(uuid);
				logger.debug("Successful generation File : {} uuid : {}", imgPath, uuid);
				PrintCacheUtils.imgUUIDMap.put(uuid, Boolean.TRUE);
				logger.debug("Successful deposit UUID to imgUUIDMap : {}", uuid);
			}
		} catch (Exception e) {
			logger.error("Pdf2ImgTask exception : {}", e.getMessage());
			/* 清理数据--start */
			PrintCacheUtils.concurrentMap.remove(uuid);
			PrintCacheUtils.removeCacheByKey(printerName, uuid);
			PrintFileUtils.delTempFileByUUID(uuid);
			logger.error("Delete Cache and file by uuid : {}", uuid);
			/* 清理数据--end */
		}
	}

	private Logger logger = LoggerFactory.getLogger(Pdf2ImgTask.class);
}
