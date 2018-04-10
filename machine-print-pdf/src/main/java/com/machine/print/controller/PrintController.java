package com.machine.print.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.machine.print.cache.PrintCacheUtils;
import com.machine.print.common.PrintCommonParam;
import com.machine.print.envm.PrintExceptions;
import com.machine.print.exception.impl.PrintBusinessException;
import com.machine.print.exception.impl.PrintGeneralException;
import com.machine.print.service.ElePrintService;
import com.machine.print.threadpool.PrintPdfTask;
import com.machine.print.threadpool.PrintThreadPool;
import com.machine.print.threadpool.WayBillElePrintTask;
import com.machine.print.utils.io.PrintFileUtils;
import com.machine.print.utils.printer.PrinterUtils;
import com.machine.print.vo.PrintMessageVo;

/**
 * @Description: 打印
 *
 * @author machine
 * @date 2017年12月20日 上午11:44:21
 */
@Controller
@RequestMapping("/print")
public class PrintController {

	@Autowired
	ElePrintService elePrintService;

	/**
	 * @Description: 查询可用的打印机
	 *
	 * @author machine
	 * @date 2017年12月20日 上午11:36:09
	 */
	@RequestMapping(value = "printers", method = RequestMethod.POST)
	@ResponseBody
	public String getPrinters() {
		PrinterUtils.getInstance().refreshCache();
		String[] printerNames = PrinterUtils.getInstance().getPrinterNames();
		return JSON.toJSONString(printerNames);
	}

	/**
	 * @Description: 查询对应打印机正在处理的任务
	 *
	 * @author machine
	 * @date 2017年12月20日 上午11:36:36
	 */
	@RequestMapping(value = "printerQueue", method = RequestMethod.POST)
	@ResponseBody
	public String printerQueue(@RequestBody String printerName) {
		return JSON.toJSONString(PrintCacheUtils.getPrintersQueueMapValues(printerName));
	}

	/**
	 * @Description: 删除对应打印机某个打印任务
	 *
	 * @author machine
	 * @date 2017年12月20日 上午11:34:09
	 */
	@RequestMapping(value = "removeQueueCode", method = RequestMethod.POST)
	@ResponseBody
	public String removeQueueCode(@RequestBody String jsonStr) {
		JSONObject jSONObject = JSONObject.parseObject(jsonStr);
		String uuid = jSONObject.getString("uuid");
		String printerName = jSONObject.getString("printerName");
		/* 清理数据--start */
		PrintCacheUtils.removeCacheByKey(printerName, uuid);
		PrintFileUtils.delTempFileByUUID(uuid);
		logger.info("Delete(browser-remove) Cache and file by printerName: {} uuid : {}", printerName, uuid);
		/* 清理数据--end */
		return PrintCommonParam.SUCCESS;
	}

	/**
	 * @Description: 清除对应打印机的所有任务
	 *
	 * @author machine
	 * @date 2017年12月20日 上午11:35:12
	 */
	@RequestMapping(value = "clearQueue", method = RequestMethod.POST)
	@ResponseBody
	public String clearQueue(@RequestBody String printerName) {
		if (null == printerName || printerName.length() == 0) {
			return PrintCommonParam.SUCCESS;
		}
		List<String> uuids = new ArrayList<String>(300);
		for (String uuid : PrintCacheUtils.getPrintersQueueMapValues(printerName).keySet()) {
			uuids.add(uuid);
		}
		for (String uuid : uuids) {
			/* 清理数据--start */
			PrintCacheUtils.removeCacheByKey(printerName, uuid);
			PrintFileUtils.delTempFileByUUID(uuid);
			logger.info("Delete(browser-clear) Cache and file by uuid : {}", uuid);
			/* 清理数据--end */
		}
		return PrintCommonParam.SUCCESS;
	}
	
	/**
	 * @Description: 电子面单
	 *
	 * @author machine
	 * @date 2017年12月20日 上午11:33:51
	 */
	@RequestMapping(value = "elePrintMessage", method = RequestMethod.POST)
	@ResponseBody
	public String elePrintMessage(@RequestBody String elePrintMessage) {
		logger.debug("EPrintMessage: {} ", elePrintMessage);
		/* 单列线程池处理逻辑[控制处理的速度,解决高并发时机器卡顿问题] */
		PrintThreadPool.getInstance().getPrintTypeExecutor(PrintCommonParam.WAYBILL_ELE_PRINT)
				.execute(new WayBillElePrintTask(elePrintMessage));
		logger.debug("Successful creation of task : {}", WayBillElePrintTask.class);
		return PrintCommonParam.SUCCESS;
	}

	/**
	 * @Description: 电子面单
	 *
	 * @author machine
	 * @date 2017年12月20日 上午11:33:51
	 */
	@RequestMapping(value = "elePrintMessage_BK", method = RequestMethod.POST)
	@ResponseBody
	public String elePrintMessage_BK(@RequestBody String elePrintMessage) {
		logger.debug(
				"<<----------------------------------PrintController elePrintMessage start---------------------------------------->>");
		String printerName = null;
		String expressName = null;
		PrintMessageVo printMessageVo = null;
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");

		try {
			logger.debug("uuid: {}   elePrintMessage : {}", uuid, elePrintMessage);
			printMessageVo = JSONObject.parseObject(elePrintMessage, PrintMessageVo.class);
			printerName = printMessageVo.getPrinterName();
			expressName = printMessageVo.getExpressName();
			logger.info("uuid: {}   printerName : {}   expressName : {} ", uuid, printerName, expressName);
			if (null == printerName || printerName.length() == 0) {
				/* 记录日志 */
				logger.error("PriterName is null ! Message: {} ", elePrintMessage);
				return PrintCommonParam.ERROR;
			}
			if (null == expressName || expressName.length() == 0) {
				/* 记录日志 */
				logger.error("ExpressName is null ! Message: {} ", elePrintMessage);
				return PrintCommonParam.ERROR;
			}
		} catch (Exception e) {
			logger.error("Message parsing exception !  Message: {} EXCPTION:{}", elePrintMessage, e.getMessage());
			return PrintCommonParam.ERROR;
		}
		try {
			logger.debug("Successful message parsing RequestElePrintMessageVo!");
			/* 找不到报文对应的打印机 */
			if (!PrinterUtils.getInstance().containPrint(printerName)) {
				throw new PrintBusinessException("PRINT_2100", printerName);
			}
			logger.debug("Successfully found the corresponding printer: {}", printerName);
			/* 缓存接收的报文信息 */
			PrintCacheUtils.requestPrintMessagesMap.put(uuid, printMessageVo);
			/* 通过单例线程池保证打印顺序 */
			PrintThreadPool.getInstance().getPtintExecutor(printerName)
					.execute(new PrintPdfTask(uuid, printerName));
			logger.debug("Successful creation of task : {}", PrintPdfTask.class);
			/* 处理顺丰的电子面单 */
			if (PrintCommonParam.SF_EXPRESS.equals(expressName)) {
				logger.debug("Start processing the SF list : {}", uuid);
				elePrintService.processSFMessage(uuid, printMessageVo);
				logger.debug("End processing the SF list : {}", uuid);
			}
		} catch (PrintBusinessException e) {
			logger.error("PrintBusinessException CODE:{} :{}  ExceptionMessage:{}  ElePrintMessage:{}", e.getErrorCode(),
					PrintExceptions.getValueByKey(e.getErrorCode()), e.getMessage(), elePrintMessage);
			/* 清理数据--start */
			PrintCacheUtils.removeCacheByKey(printerName, uuid);
			PrintFileUtils.delTempFileByUUID(uuid);
			logger.error("Delete Cache and file by uuid : {}", uuid);
			/* 清理数据--end */
			return PrintCommonParam.ERROR;
		} catch (PrintGeneralException e) {
			logger.error("PrintGeneralException:{} ElePrintMessage:{}", e.getMessage(), elePrintMessage);
			/* 清理数据--start */
			PrintCacheUtils.removeCacheByKey(printerName, uuid);
			PrintFileUtils.delTempFileByUUID(uuid);
			logger.error("Delete Cache and file by uuid : {}", uuid);
			/* 清理数据--end */
			return PrintCommonParam.ERROR;
		} catch (Exception e) {
			logger.error("EXCPTION:{} ElePrintMessage:{}", e.getMessage(), elePrintMessage);
			/* 清理数据--start */
			PrintCacheUtils.removeCacheByKey(printerName, uuid);
			PrintFileUtils.delTempFileByUUID(uuid);
			logger.error("Delete Cache and file by uuid : {}", uuid);
			/* 清理数据--end */
			return PrintCommonParam.ERROR;
		}
		logger.debug(
				"<<----------------------------------PrintController elePrintMessage end---------------------------------------->>");
		return PrintCommonParam.SUCCESS;
	}

	private Logger logger = LoggerFactory.getLogger(PrintController.class);
}
