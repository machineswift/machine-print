package com.machine.print.utils.html2pdf;

import java.io.File;
import org.slf4j.Logger;
import java.io.IOException;
import org.slf4j.LoggerFactory;
import com.machine.print.config.PrintConfig;

/**
 * @Description: Html2Pdf
 *
 * @author machine
 * @date 2017年12月20日 下午12:43:44
 */
public class Html2Pdf {

	public void execute(String htmlPath, String pdfPath) throws IOException, InterruptedException {
		if (!new File(htmlPath).exists()) {
			return;
		}
		StringBuilder cmd = new StringBuilder();
		cmd.append(PrintConfig.HTML2PDF_TOOL).append(" ").append(" --margin-top 0mm  ").append(" --margin-bottom 0mm  ")
				.append(" --margin-left 0mm  ").append(" --margin-right 0mm ").append(" --page-height 180  ")
				.append(" --page-width 100  ").append(htmlPath).append(" ").append(pdfPath);
		logger.debug("Html2Pdf command : {}", cmd);
		Process proc = Runtime.getRuntime().exec(cmd.toString());
		Html2PdfInterceptor errorGobbler = new Html2PdfInterceptor(proc.getErrorStream(), "Error");
		Html2PdfInterceptor outputGobbler = new Html2PdfInterceptor(proc.getInputStream(), "Output");
		errorGobbler.start();
		outputGobbler.start();
		proc.waitFor();
	}

	/**
	 * @Description: 单例模式
	 *
	 * @author machine
	 * @date 2017年12月20日 上午9:06:15
	 */
	public static Html2Pdf getInstance() {
		if (html2Pdf == null) {
			synchronized (Html2Pdf.class) {
				if (html2Pdf == null) {
					html2Pdf = new Html2Pdf();
				}
			}
		}
		return html2Pdf;
	}

	private Html2Pdf() {
	}

	private volatile static Html2Pdf html2Pdf;

	private Logger logger = LoggerFactory.getLogger(Html2Pdf.class);
}
