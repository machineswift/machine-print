package com.machine.print.vo.ele.sf;

import java.io.Serializable;

/**
 * 收派件信息
 *
 */
@SuppressWarnings("serial")
public class ElePrintRecipientSendInfSF implements Serializable{
	/**
	 * 收件员
	 */
	private String collector=""; 
	
	/**
	 * 收件日期
	 */
	private String receiptDate="";
	
	/**
	 * 派件员
	 */
	private String dispatchClerk="";

	public String getCollector() {
		return collector;
	}

	public void setCollector(String collector) {
		this.collector = collector;
	}

	public String getReceiptDate() {
		return receiptDate;
	}

	public void setReceiptDate(String receiptDate) {
		this.receiptDate = receiptDate;
	}

	public String getDispatchClerk() {
		return dispatchClerk;
	}

	public void setDispatchClerk(String dispatchClerk) {
		this.dispatchClerk = dispatchClerk;
	}
}
