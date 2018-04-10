package com.machine.print.vo.ele.sf;

import java.io.Serializable;

/**
 * 顺丰电子面单实体类
 */
@SuppressWarnings("serial")
public class ElePrintVoSF implements Serializable {

	/**
	 * 电子面单号(子单号)
	 */
	private String eleSheetNo = "";

	/**
	 * 电子面单号(母单号)
	 */
	private String eleSheetMotherNo = "";

	/**
	 * 快递产品类型
	 */
	private String expressType = "";

	/**
	 * 代收贷款
	 */
	private ElePrintCollectionLoanSF collectionLoan;

	/**
	 * 目的地
	 */
	private ElePrintDestinationSF destination;

	/**
	 * 收件人
	 */
	private ElePrintRecipienterSF recipienter;

	/**
	 * 寄件人
	 */
	private ElePrintSenderSF sender;

	/**
	 * 支付信息
	 */
	private ElePrintPaymentDetailSF paymentDetail;

	/**
	 * 托寄物
	 */
	private ElePrintEntrustedGoodsSF entrustedGoods;

	/**
	 * 备注
	 */
	private ElePrintRemarksSF remarks;

	/**
	 * 收派件信息
	 */
	private ElePrintRecipientSendInfSF recipientSendInf;

	/**
	 * 自定义区域
	 */
	private ElePrintCustomLocaleSF customLocale;

	/**
	 * 图片
	 */
	private ElePrintImgSF img;

	/**
	 * 广告
	 */
	private String advertisement = "";

	/**
	 * 波次单号
	 */
	private String wareDocCode = "";

	/* 波次顺序 */
	private String countIndex = "";

	public String getEleSheetNo() {
		return eleSheetNo;
	}

	public void setEleSheetNo(String eleSheetNo) {
		this.eleSheetNo = eleSheetNo;
	}

	public String getEleSheetMotherNo() {
		return eleSheetMotherNo;
	}

	public void setEleSheetMotherNo(String eleSheetMotherNo) {
		this.eleSheetMotherNo = eleSheetMotherNo;
	}

	public ElePrintCollectionLoanSF getCollectionLoan() {
		return collectionLoan;
	}

	public void setCollectionLoan(ElePrintCollectionLoanSF collectionLoan) {
		this.collectionLoan = collectionLoan;
	}

	public ElePrintDestinationSF getDestination() {
		return destination;
	}

	public void setDestination(ElePrintDestinationSF destination) {
		this.destination = destination;
	}

	public ElePrintRecipienterSF getRecipienter() {
		return recipienter;
	}

	public void setRecipienter(ElePrintRecipienterSF recipienter) {
		this.recipienter = recipienter;
	}

	public ElePrintSenderSF getSender() {
		return sender;
	}

	public void setSender(ElePrintSenderSF sender) {
		this.sender = sender;
	}

	public ElePrintPaymentDetailSF getPaymentDetail() {
		return paymentDetail;
	}

	public void setPaymentDetail(ElePrintPaymentDetailSF paymentDetail) {
		this.paymentDetail = paymentDetail;
	}

	public ElePrintEntrustedGoodsSF getEntrustedGoods() {
		return entrustedGoods;
	}

	public void setEntrustedGoods(ElePrintEntrustedGoodsSF entrustedGoods) {
		this.entrustedGoods = entrustedGoods;
	}

	public ElePrintRecipientSendInfSF getRecipientSendInf() {
		return recipientSendInf;
	}

	public void setRecipientSendInf(ElePrintRecipientSendInfSF recipientSendInf) {
		this.recipientSendInf = recipientSendInf;
	}

	public ElePrintCustomLocaleSF getCustomLocale() {
		return customLocale;
	}

	public void setCustomLocale(ElePrintCustomLocaleSF customLocale) {
		this.customLocale = customLocale;
	}

	public ElePrintImgSF getImg() {
		return img;
	}

	public void setImg(ElePrintImgSF img) {
		this.img = img;
	}

	public String getExpressType() {
		return expressType;
	}

	public void setExpressType(String expressType) {
		this.expressType = expressType;
	}

	public String getAdvertisement() {
		return advertisement;
	}

	public void setAdvertisement(String advertisement) {
		this.advertisement = advertisement;
	}

	public ElePrintRemarksSF getRemarks() {
		return remarks;
	}

	public void setRemarks(ElePrintRemarksSF remarks) {
		this.remarks = remarks;
	}

	public String getCountIndex() {
		return countIndex;
	}

	public void setCountIndex(String countIndex) {
		this.countIndex = countIndex;
	}

	public String getWareDocCode() {
		return wareDocCode;
	}

	public void setWareDocCode(String wareDocCode) {
		this.wareDocCode = wareDocCode;
	}
}
