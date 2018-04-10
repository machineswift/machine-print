package com.machine.print.vo.ele.sf;

import java.io.Serializable;

/**
 * @Description: 图片
 *
 * @author machine
 * @date 2017年12月20日 下午4:54:37
*/
@SuppressWarnings("serial")
public class ElePrintImgSF implements Serializable {
	private String logoImg = "";
	private String telImg = "";
	private String barcodeImg = "";

	public String getLogoImg() {
		return logoImg;
	}

	public void setLogoImg(String logoImg) {
		this.logoImg = logoImg;
	}

	public String getTelImg() {
		return telImg;
	}

	public void setTelImg(String telImg) {
		this.telImg = telImg;
	}

	public String getBarcodeImg() {
		return barcodeImg;
	}

	public void setBarcodeImg(String barcodeImg) {
		this.barcodeImg = barcodeImg;
	}

}
