package com.machine.print.utils.barcode;


import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

import javax.imageio.ImageIO;

import org.jbarcode.JBarcode;
import org.jbarcode.encode.Code128Encoder;
import org.jbarcode.encode.EAN13Encoder;
import org.jbarcode.encode.InvalidAtributeException;
import org.jbarcode.paint.EAN13TextPainter;
import org.jbarcode.paint.WidthCodedPainter;

import com.machine.print.exception.impl.PrintBusinessException;

public class JbarcodeUtils {

	/**
	 * 128条形码
	 * 
	 * @param strBarCode
	 *            条形码：0-100位
	 * @param dimension
	 *            商品条形码：尺寸
	 * @param barheight
	 *            商品条形码：高度
	 * @return 图片(Base64编码)
	 * @throws InvalidAtributeException
	 * @throws NumberFormatException
	 * @throws IOException
	 */
	public static String generateBarCode128(String strBarCode, String dimension, String barheight)
			throws NumberFormatException, InvalidAtributeException, IOException {
		if(null ==strBarCode || strBarCode.length() ==0) {
			throw new PrintBusinessException("PRINT_8010", "BarCode is null !");
		}
		ByteArrayOutputStream outputStream = null;
		BufferedImage bi = null;
		JBarcode productBarcode = new JBarcode(Code128Encoder.getInstance(), WidthCodedPainter.getInstance(),
				EAN13TextPainter.getInstance());

		// 尺寸，面积，大小 密集程度
		productBarcode.setXDimension(Double.valueOf(dimension).doubleValue());
		// 高度 10.0 = 1cm 默认1.5cm
	//	productBarcode.setBarHeight(Double.valueOf(barheight).doubleValue());
		// 宽度
	//	productBarcode.setWideRatio(Double.valueOf(50).doubleValue());
		// 是否显示字体
		productBarcode.setShowText(false);
		// 显示字体样式
		//productBarcode.setTextPainter(BaseLineTextPainter.getInstance());

		// 生成二维码
		bi = productBarcode.createBarcode(strBarCode);

		outputStream = new ByteArrayOutputStream();
		ImageIO.write(bi, "jpg", outputStream);
		return Base64.getEncoder().encodeToString(outputStream.toByteArray());
	}

	/**
	 * 商品条形码
	 * 
	 * @param strBarCode
	 *            商品条形码：13位
	 * @param dimension
	 *            商品条形码：尺寸
	 * @param barheight
	 *            商品条形码：高度
	 * @return 图片(Base64编码)
	 * @throws InvalidAtributeException
	 * @throws IOException
	 */
	public static String generateBarCode(String strBarCode, String dimension, String barheight)
			throws InvalidAtributeException, IOException {
		// isNumeric 是否是数值

		ByteArrayOutputStream outputStream = null;
		BufferedImage bi = null;
		int len = strBarCode.length();
		JBarcode productBarcode = new JBarcode(EAN13Encoder.getInstance(), WidthCodedPainter.getInstance(),
				EAN13TextPainter.getInstance());

		String barCode = strBarCode.substring(0, len - 1);
		String code = strBarCode.substring(len - 1, len);

		// 校验13位
		String checkCode = productBarcode.calcCheckSum(barCode);
		if (!code.equals(checkCode)) {
			return "checkCodeError";
		}

		// 尺寸，面积，大小
		productBarcode.setXDimension(Double.valueOf(dimension).doubleValue());
		// 高度 10.0 = 1cm 默认1.5cm
		productBarcode.setBarHeight(Double.valueOf(barheight).doubleValue());
		// 宽度
		productBarcode.setWideRatio(Double.valueOf(25).doubleValue());

		// 是否校验13位，默认false
		productBarcode.setShowCheckDigit(true);

		// 显示字符串内容中是否显示检查码内容
		productBarcode.setShowCheckDigit(true);

		// 生成二维码
		bi = productBarcode.createBarcode(barCode);

		outputStream = new ByteArrayOutputStream();
		ImageIO.write(bi, "jpg", outputStream);
		return Base64.getEncoder().encodeToString(outputStream.toByteArray());
	}
}
