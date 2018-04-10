package com.machine.print.vo.ele.sf;

import java.io.Serializable;

/**
 * 自定义区域
 *
 */
@SuppressWarnings("serial")
public class ElePrintCustomLocaleSF implements Serializable {
	/**
	 * 自定义区域
	 */
	private String customLocale = "";

	public String getCustomLocale() {
		return customLocale;
	}

	public void setCustomLocale(String customLocale) {
		this.customLocale = customLocale;
	}
}
