package com.machine.print.controller;

public class PrintControllerTest {
	static String elePrintMessage1 = "{\"expressName\":\"SF\",\"printMessage\":{\"eleSheetMotherNo\":\"444004772115\",\"img\":{\"logoImg\":\"\",\"barcodeImg\":\"\",\"telImg\":\"\"},\"expressType\":\"顺丰特惠\",\"destination\":{\"destination\":\"7312\"},\"eleSheetNo\":\"956789452458\",\"collectionLoan\":{\"money\":\"¥8000.5\",\"cardNumber\":\"8881688888\"},\"recipienter\":{\"area\":\"雨湖区\",\"mobilePhone\":\"18223549327\",\"city\":\"湘潭市\",\"provice\":\"湖南省\",\"name\":\"李小龙\",\"detailAddress\":\"学府路与建设路交汇口666号南山软件产业基地B栋8楼\",\"telephone\":\"0512-83476584\"},\"recipientSendInf\":{\"dispatchClerk\":\"55544556\",\"receiptDate\":\"5月10日\",\"collector\":\"67345678\"},\"paymentDetail\":{\"actualWeight\":\"1.3KG\",\"monthlyAccount\":\"00000000001\",\"packingCost\":\"10元\",\"billingWeight\":\"1.5KG\",\"freight\":\"22元\",\"insuredValue\":\"10元\",\"paymentMethod\":\"转第三方\",\"timedDelivery\":\"2016/3/31-1500-1600\",\"lifeValue\":\"2000元\",\"thirdPartyRegion\":\"010D\",\"totalCost\":\"42元\"},\"sender\":{\"area\":\"青浦区\",\"mobilePhone\":\"18223548888\",\"city\":\"上海市\",\"provice\":\"上海市\",\"name\":\"崔维星\",\"detailAddress\":\"徐泾镇明珠路1018号D2 4楼\",\"telephone\":\"0512-83478888\"},\"customLocale\":{\"customLocale\":\"自定义区域\"},\"countIndex\":1,\"advertisement\":\"广告\",\"entrustedGoods\":{\"entrustedGoods\":\"托寄物\"}},\"printerName\":\"EPSON LQ-80KFII ESC/P2\"}" ;

	public static void main(String[] args) {
		DoSomething ds1 = new DoSomething(elePrintMessage1);
		Thread t1 = new Thread(ds1);
		t1.start();
	}
}