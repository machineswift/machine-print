package com.machine.print.vo.ele.sf;

import java.io.Serializable;

/**
 * 托寄物
 */
@SuppressWarnings("serial")
public class ElePrintEntrustedGoodsSF implements Serializable{
	/**
	 * 托寄物
	 */
	private String entrustedGoods="";
	
	/**
	 * 水印
	 */
	private String watermark = "";

	public String getEntrustedGoods() {
		return entrustedGoods;
	}

	public void setEntrustedGoods(String entrustedGoods) {
		this.entrustedGoods = entrustedGoods;
	}

	public String getWatermark() {
		return watermark;
	}

	public void setWatermark(String watermark) {
		this.watermark = watermark;
	}
}
