package com.machine.print.vo;

import java.io.Serializable;

/**
 * 电子面单公共实体类
 */
@SuppressWarnings("serial")
public class PrintMessageVo implements Serializable {
	/* 打印机名称 */
	private String printerName = "";
	/* 快递名称 */
	private String expressName = "";
	/* 请求打印的报文实体 */
	private Object printMessage;

	public String getExpressName() {
		return expressName;
	}

	public void setExpressName(String expressName) {
		this.expressName = expressName;
	}

	public String getPrinterName() {
		return printerName;
	}

	public void setPrinterName(String printerName) {
		this.printerName = printerName;
	}

	public Object getPrintMessage() {
		return printMessage;
	}

	public void setPrintMessage(Object printMessage) {
		this.printMessage = printMessage;
	}
}
