package com.machine.print.service.impl;

import java.io.IOException;

import org.jbarcode.encode.InvalidAtributeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.machine.print.cache.PrintCacheUtils;
import com.machine.print.common.PrintCommonParam;
import com.machine.print.config.PrintConfig;
import com.machine.print.exception.impl.PrintBusinessException;
import com.machine.print.properties.PrinterProperties;
import com.machine.print.service.ElePrintService;
import com.machine.print.template.BeetlTemplateUtils;
import com.machine.print.threadpool.Html2PdfTask;
import com.machine.print.threadpool.PrintThreadPool;
import com.machine.print.utils.barcode.JbarcodeUtils;
import com.machine.print.utils.io.FileUtils;
import com.machine.print.vo.PrintMessageVo;
import com.machine.print.vo.ele.sf.ElePrintVoSF;

/**
 * @Description: 电子面单对应的service实现类
 *
 * @author machine
 * @date 2017年12月20日 下午1:17:23
*/
@Service
public class ElePrintServiceImpl implements ElePrintService {

	/**
	 * @Description: 顺丰电子面单 
	 *
	 * @author machine
	 * @date 2017年12月20日 下午1:17:41
	*/
	@Override
	public String processSFMessage(String uuid, PrintMessageVo printMessageVo) {
		try {
			String printerName = printMessageVo.getPrinterName();
			String expressName = printMessageVo.getExpressName();
			ElePrintVoSF elePrintMessageSFVo = JSONObject
					.parseObject(printMessageVo.getPrintMessage().toString(), ElePrintVoSF.class);
			logger.debug("Successful message parsing RequestElePrintMessageVo!");
			logger.info("EleSheetNo : {}",
					elePrintMessageSFVo.getEleSheetNo());
			/*code保存到对应的队列(页面查询)*/
			PrintCacheUtils.putPrintersQueueMap(printerName, uuid, elePrintMessageSFVo.getEleSheetNo());
			elePrintMessageSFVo.getImg().setBarcodeImg("data:image/jpg;base64,"
					+ JbarcodeUtils.generateBarCode128(elePrintMessageSFVo.getEleSheetNo(), "2", "50"));
			logger.debug("Successful generation EleSheetNo : {}", elePrintMessageSFVo.getEleSheetNo());
			elePrintMessageSFVo.getImg().setLogoImg(PrintCacheUtils.getImgStr(PrintCommonParam.ELE_SHEET_TYPE,
					PrintCommonParam.SF_EXPRESS, "logo.jpg"));
			logger.debug("Successful conversion picture : {}", "logo.jpg");
			elePrintMessageSFVo.getImg().setTelImg(
					PrintCacheUtils.getImgStr(PrintCommonParam.ELE_SHEET_TYPE, PrintCommonParam.SF_EXPRESS, "tel.png"));
			logger.debug("Successful conversion picture : {}", "tel.png");
			/* 通过模板引擎生成html */
			String htmlStr = BeetlTemplateUtils.getBindedResult(PrinterProperties.getInstance().getExpressElesheet(expressName), "elePrintVoSF",
					elePrintMessageSFVo);
			logger.debug("Successful generation htmlStr!");
			/* html落地生成文件 */
			String htmlPath = new StringBuilder(PrintConfig.HTML_PATH).append(uuid).append(".html").toString();
			FileUtils.string2File(htmlPath, htmlStr);
			logger.debug("Successful generation htmlFile : {}", htmlPath);
			/* 缓存已经生成html的UUID */
			PrintCacheUtils.htmlUUIDMap.put(uuid, Boolean.TRUE);
			logger.debug("Successful deposit UUID to htmlUUIDMap : {}", uuid);
			/* 通过线程池生成pdf */
			PrintThreadPool.getInstance().html2pdfExecutor.execute(new Html2PdfTask(uuid, printerName));
			logger.debug("Successful creation of task : {}", Html2PdfTask.class);
		} catch (NumberFormatException e) {
			throw new PrintBusinessException("PRINT_5202", e.getMessage());
		} catch (InvalidAtributeException e) {
			throw new PrintBusinessException("PRINT_5203", e.getMessage());
		} catch (IOException e) {
			throw new PrintBusinessException("PRINT_5204", e.getMessage());
		}
		return PrintCommonParam.SUCCESS;
	}

	private Logger logger = LoggerFactory.getLogger(ElePrintServiceImpl.class);
}
