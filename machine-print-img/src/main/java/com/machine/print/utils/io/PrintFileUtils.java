package com.machine.print.utils.io;

import java.io.File;

import com.machine.print.common.PrintCommonParam;

public class PrintFileUtils {

	public static void delTempFileByUUID(String uuid) {
		/* 删除临时文件 */
		String htmlPath = new StringBuilder(PrintCommonParam.HTML_PATH).append(uuid).append(".html").toString();
		String pdfPath = new StringBuilder(PrintCommonParam.PDF_PATH).append(uuid).append(".pdf").toString();
		String tempLogPath = new StringBuilder(PrintCommonParam.PDF_PATH).append(uuid).append(".log").toString();
		String imgPath = new StringBuilder(PrintCommonParam.IMG_PATH).append(uuid).append(".pdf-JPG").toString();
		//String imgPath = new StringBuilder(PrintCommonParam.IMG_PATH).append(uuid).append(".jpg").toString();

		FileUtils.delDir(new File(htmlPath));
		FileUtils.delDir(new File(pdfPath));
		FileUtils.delDir(new File(tempLogPath));
		FileUtils.delDir(new File(imgPath));
	}
}
