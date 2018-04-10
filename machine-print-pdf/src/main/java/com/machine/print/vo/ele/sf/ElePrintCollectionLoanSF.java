package com.machine.print.vo.ele.sf;

import java.io.Serializable;

/**
 * 代收贷款
 */
@SuppressWarnings("serial")
public class ElePrintCollectionLoanSF implements Serializable {

	/**
	 * 卡号
	 */
	private String cardNumber="";

	/**
	 * 钱
	 */
	private String money="";

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

}
