package com.machine.print.threadpool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.machine.print.vo.PrintMessageVo;
import com.machine.print.envm.PrintExceptions;
import com.machine.print.cache.PrintCacheUtils;
import com.machine.print.common.PrintCommonParam;
import com.machine.print.utils.io.PrintFileUtils;
import com.machine.print.utils.genhtml.GenerateHtml4Ele;
import com.machine.print.exception.impl.PrintBusinessException;
import com.machine.print.exception.impl.PrintGeneralException;

/**
 * @Description: 生成html
 *
 * @author machine
 * @date 2017年12月20日 下午2:57:29
 */
public class GenerateHtmlTask implements Runnable {

	private String uuid;
	private String printerName;
	private String expressName;
	private PrintMessageVo printMessageVo;

	public GenerateHtmlTask(String uuid, String printerName, String expressName, PrintMessageVo printMessageVo) {
		this.uuid = uuid;
		this.expressName = expressName;
		this.printMessageVo = printMessageVo;
	}

	@Override
	public void run() {
		try {
			/* 处理顺丰的电子面单 */
			if (PrintCommonParam.SF_EXPRESS.equals(expressName)) {
				logger.debug("Start processing the SF list : {}", uuid);
				/* 电子面单生成html工具类 */
				GenerateHtml4Ele.getInstance().processSFMessage(uuid, printMessageVo);
				logger.debug("End processing the SF list : {}", uuid);
			} else {
				logger.error("Nonsupport this Express : {}  ElePrintMessage : {}", expressName,
						PrintCacheUtils.requestPrintMessagesMap.get(this.uuid));
				PrintCacheUtils.requestPrintMessagesMap.remove(uuid);
				logger.error("Removed uuid : {} from requestPrintMessagesMap", uuid);
			}
		} catch (PrintBusinessException e) {
			logger.error("PrintBusinessException CODE:{} :{}  ExceptionMessage:{}  ElePrintMessage:{}", e.getErrorCode(),
					PrintExceptions.getValueByKey(e.getErrorCode()), e.getMessage(),
					PrintCacheUtils.requestPrintMessagesMap.get(this.uuid));
			/* 清理数据--start */
			PrintCacheUtils.removeCacheByKey(printerName, uuid);
			PrintFileUtils.delTempFileByUUID(uuid);
			logger.error("Delete Cache and file by uuid : {}", uuid);
			/* 清理数据--end */
		} catch (PrintGeneralException e) {
			logger.error("PrintGeneralException:{} ElePrintMessage:{}", e.getMessage(),
					PrintCacheUtils.requestPrintMessagesMap.get(this.uuid));
			/* 清理数据--start */
			PrintCacheUtils.removeCacheByKey(printerName, uuid);
			PrintFileUtils.delTempFileByUUID(uuid);
			logger.error("Delete Cache and file by uuid : {}", uuid);
			/* 清理数据--end */
		} catch (Exception e) {
			logger.error("EXCPTION:{} ElePrintMessage:{}", e.getMessage(),
					PrintCacheUtils.requestPrintMessagesMap.get(this.uuid));
			/* 清理数据--start */
			PrintCacheUtils.removeCacheByKey(printerName, uuid);
			PrintFileUtils.delTempFileByUUID(uuid);
			logger.error("Delete Cache and file by uuid : {}", uuid);
			/* 清理数据--end */
		}
	}

	private Logger logger = LoggerFactory.getLogger(GenerateHtmlTask.class);
}
