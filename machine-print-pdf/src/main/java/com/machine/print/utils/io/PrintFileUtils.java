package com.machine.print.utils.io;

import java.io.File;
import com.machine.print.config.PrintConfig;

/**
 * @Description: 打印文件工具类
 *
 * @author machine
 * @date 2017年12月20日 下午12:45:59
*/
public class PrintFileUtils {

	/**
	 * @Description: 删除临时文件
	 *
	 * @author machine
	 * @date 2017年12月20日 下午12:46:25
	*/
	public static void delTempFileByUUID(String uuid) {
		String htmlPath = new StringBuilder(PrintConfig.HTML_PATH).append(uuid).append(".html").toString();
		String pdfPath = new StringBuilder(PrintConfig.PDF_PATH).append(uuid).append(".pdf").toString();
		FileUtils.delDir(new File(htmlPath));
		FileUtils.delDir(new File(pdfPath));
	}
}
