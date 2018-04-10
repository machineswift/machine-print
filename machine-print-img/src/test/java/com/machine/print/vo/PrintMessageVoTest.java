package com.machine.print.vo;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.machine.print.vo.ele.sf.ElePrintCollectionLoanSF;
import com.machine.print.vo.ele.sf.ElePrintCustomLocaleSF;
import com.machine.print.vo.ele.sf.ElePrintDestinationSF;
import com.machine.print.vo.ele.sf.ElePrintEntrustedGoodsSF;
import com.machine.print.vo.ele.sf.ElePrintImgSF;
import com.machine.print.vo.ele.sf.ElePrintPaymentDetailSF;
import com.machine.print.vo.ele.sf.ElePrintRecipientSendInfSF;
import com.machine.print.vo.ele.sf.ElePrintRecipienterSF;
import com.machine.print.vo.ele.sf.ElePrintRemarksSF;
import com.machine.print.vo.ele.sf.ElePrintSenderSF;
import com.machine.print.vo.ele.sf.ElePrintVoSF;

public class PrintMessageVoTest {
	public ElePrintVoSF getElePrintVoSF() {
		ElePrintVoSF elePrintVoSF = new ElePrintVoSF();
		/**
		 * 电子面单号(子单号)
		 */
		elePrintVoSF.setEleSheetNo("956789452458");
		/**
		 * 电子面单号(母单号)
		 */
		elePrintVoSF.setEleSheetMotherNo("444004772115");
		/**
		 * 快递产品类型
		 */
		elePrintVoSF.setExpressType("顺丰特惠");
		/**
		 * 广告
		 */
		elePrintVoSF.setAdvertisement("广告");

		/* 波次单号 */
		elePrintVoSF.setWareDocCode("WV171218000016");
		
		/* 波次顺序 */
		elePrintVoSF.setCountIndex("999");

		/**
		 * 代收贷款
		 */
		ElePrintCollectionLoanSF collectionLoan = new ElePrintCollectionLoanSF();
		collectionLoan.setCardNumber("8881688888");
		collectionLoan.setMoney("¥8000.5");
		elePrintVoSF.setCollectionLoan(collectionLoan);
		/**
		 * 目的地
		 */
		ElePrintDestinationSF destination = new ElePrintDestinationSF();
		destination.setDestination("7312");
		elePrintVoSF.setDestination(destination);

		/**
		 * 收件人
		 */
		ElePrintRecipienterSF recipienter = new ElePrintRecipienterSF();
		recipienter.setName("李小龙");
		recipienter.setTelephone("0512-83476584");
		recipienter.setMobilePhone("18223549327");
		recipienter.setProvice("湖南省");
		recipienter.setCity("湘潭市");
		recipienter.setArea("雨湖区");
		recipienter.setDetailAddress("学府路与建设路交汇口666号南山软件产业基地B栋8楼");
		elePrintVoSF.setRecipienter(recipienter);

		/**
		 * 寄件人
		 */
		ElePrintSenderSF sender = new ElePrintSenderSF();
		sender.setName("崔维星");
		sender.setTelephone("0512-83478888");
		sender.setMobilePhone("18223548888");
		sender.setProvice("上海市");
		sender.setCity("上海市");
		sender.setArea("青浦区");
		sender.setDetailAddress("徐泾镇明珠路1018号D2 4楼");
		elePrintVoSF.setSender(sender);

		/**
		 * 支付信息
		 */
		ElePrintPaymentDetailSF paymentDetail = new ElePrintPaymentDetailSF();
		paymentDetail.setPaymentMethod("转第三方");
		paymentDetail.setBillingWeight("1.5KG");
		paymentDetail.setPackingCost("10元");
		paymentDetail.setMonthlyAccount("00000000001");
		paymentDetail.setLifeValue("2000元");
		paymentDetail.setFreight("22元");
		paymentDetail.setThirdPartyRegion("010D");
		paymentDetail.setInsuredValue("10元");
		paymentDetail.setTotalCost("42元");
		paymentDetail.setActualWeight("1.3KG");
		paymentDetail.setTimedDelivery("2016/3/31-1500-1600");
		elePrintVoSF.setPaymentDetail(paymentDetail);
		/**
		 * 托寄物
		 */
		ElePrintEntrustedGoodsSF entrustedGoods = new ElePrintEntrustedGoodsSF();
		entrustedGoods.setEntrustedGoods("托寄物");
		entrustedGoods.setWatermark("德邦水印");
		;
		elePrintVoSF.setEntrustedGoods(entrustedGoods);
		/**
		 * 备注
		 */
		ElePrintRemarksSF remarks = new ElePrintRemarksSF();
		remarks.setRemarks("备注");
		elePrintVoSF.setRemarks(remarks);

		/**
		 * 收派件信息
		 */
		ElePrintRecipientSendInfSF recipientSendInf = new ElePrintRecipientSendInfSF();
		recipientSendInf.setCollector("67345678");
		recipientSendInf.setReceiptDate("5月10日");
		recipientSendInf.setDispatchClerk("55544556");
		elePrintVoSF.setRecipientSendInf(recipientSendInf);
		/**
		 * 自定义区域
		 */
		ElePrintCustomLocaleSF customLocale = new ElePrintCustomLocaleSF();
		customLocale.setCustomLocale("自定义区域");
		elePrintVoSF.setCustomLocale(customLocale);
		/**
		 * 图片
		 */
		ElePrintImgSF img = new ElePrintImgSF();
		elePrintVoSF.setImg(img);
		return elePrintVoSF;
	}

	@Test
	public void elePrintMessageSF() {
		PrintMessageVo printMessageVo = new PrintMessageVo();
		printMessageVo.setExpressName("SF");
		printMessageVo.setPrinterName("\\\\Sha-q09210278\\HP LaserJet Professional P1108");
		printMessageVo.setPrintMessage(getElePrintVoSF());
		System.out.println(JSON.toJSON(printMessageVo));
	}
}
