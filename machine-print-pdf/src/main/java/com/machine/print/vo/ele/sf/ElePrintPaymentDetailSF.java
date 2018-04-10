package com.machine.print.vo.ele.sf;

import java.io.Serializable;

/**
 * 支付信息
 */
@SuppressWarnings("serial")
public class ElePrintPaymentDetailSF implements Serializable {
	/**
	 * 付款方式
	 */
	private String paymentMethod="";

	/**
	 * 计费重量
	 */
	private String billingWeight="";

	/**
	 * 包装费用
	 */
	private String packingCost="";

	/**
	 * 月结账号
	 */
	private String monthlyAccount="";
	/**
	 * 生命价值
	 */
	private String lifeValue="";
	/**
	 * 运费
	 */
	private String freight="";
	/**
	 * 第三方地区
	 */
	private String thirdPartyRegion="";
	/**
	 * 保价费用
	 */
	private String insuredValue="";
	/**
	 * 费用合计
	 */
	private String totalCost="";
	/**
	 * 实际重量
	 */
	private String actualWeight="";
	/**
	 * 定时派送
	 */
	private String timedDelivery="";

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getBillingWeight() {
		return billingWeight;
	}

	public void setBillingWeight(String billingWeight) {
		this.billingWeight = billingWeight;
	}

	public String getPackingCost() {
		return packingCost;
	}

	public void setPackingCost(String packingCost) {
		this.packingCost = packingCost;
	}

	public String getMonthlyAccount() {
		return monthlyAccount;
	}

	public void setMonthlyAccount(String monthlyAccount) {
		this.monthlyAccount = monthlyAccount;
	}

	public String getLifeValue() {
		return lifeValue;
	}

	public void setLifeValue(String lifeValue) {
		this.lifeValue = lifeValue;
	}

	public String getFreight() {
		return freight;
	}

	public void setFreight(String freight) {
		this.freight = freight;
	}

	public String getThirdPartyRegion() {
		return thirdPartyRegion;
	}

	public void setThirdPartyRegion(String thirdPartyRegion) {
		this.thirdPartyRegion = thirdPartyRegion;
	}

	public String getInsuredValue() {
		return insuredValue;
	}

	public void setInsuredValue(String insuredValue) {
		this.insuredValue = insuredValue;
	}

	public String getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(String totalCost) {
		this.totalCost = totalCost;
	}

	public String getActualWeight() {
		return actualWeight;
	}

	public void setActualWeight(String actualWeight) {
		this.actualWeight = actualWeight;
	}

	public String getTimedDelivery() {
		return timedDelivery;
	}

	public void setTimedDelivery(String timedDelivery) {
		this.timedDelivery = timedDelivery;
	}

}
