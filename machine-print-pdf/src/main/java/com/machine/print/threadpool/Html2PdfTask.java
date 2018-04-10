package com.machine.print.threadpool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.machine.print.cache.PrintCacheUtils;
import com.machine.print.config.PrintConfig;
import com.machine.print.utils.html2pdf.Html2Pdf;
import com.machine.print.utils.io.PrintFileUtils;

/**
 * @Description: html to Pdf Task
 *
 * @author machine
 * @date 2017年12月20日 下午12:34:28
*/
public class Html2PdfTask implements Runnable {

	private String uuid;
	private String printerName;

	public Html2PdfTask(String uuid, String printerName) {
		this.uuid = uuid;
		this.printerName = printerName;
	}

	@Override
	public void run() {
		try {
			if (PrintCacheUtils.requestPrintMessagesMap.containsKey(uuid)
					&& PrintCacheUtils.htmlUUIDMap.containsKey(uuid)) {
				String htmlPath = new StringBuilder(PrintConfig.HTML_PATH).append(uuid).append(".html").toString();
				String pdfPath = new StringBuilder(PrintConfig.PDF_PATH).append(uuid).append(".pdf").toString();
				/*生成pdf*/
				Html2Pdf.getInstance().execute(htmlPath, pdfPath);
				logger.debug("Successful generation pdfFile : {}", pdfPath);
				/* 缓存已经生成pdf的UUID */
				PrintCacheUtils.pdfUUIDMap.put(uuid, Boolean.TRUE);
				logger.debug("Successful deposit UUID to pdfUUIDMap : {}", uuid);
			}
		} catch (Exception e) {
			logger.error("Html2PdfTask exception : {}", e.getMessage());
			/* 清理数据--start */
			PrintCacheUtils.removeCacheByKey(printerName, uuid);
			PrintFileUtils.delTempFileByUUID(uuid);
			logger.error("Delete Cache and file by uuid : {}", uuid);
			/* 清理数据--end */
		}
	}
	private Logger logger = LoggerFactory.getLogger(Html2PdfTask.class);
}
