package com.machine.print.utils.html2pdf;

import java.io.File;
import java.io.IOException;

import com.machine.print.config.PrintConfig;

public class Html2Pdf {
	private static final String toPdfTool = PrintConfig.HTML2PDF_TOOL;

	public static void execute(String htmlPath, String pdfPath) throws IOException, InterruptedException {
		if (!new File(htmlPath).exists()) {
			return;
		}
		StringBuilder cmd = new StringBuilder();
		// if (System.getProperty("os.name").indexOf("Windows") == -1) {
		// 非windows 系统
		// toPdfTool =
		// FileUtil.convertSystemFilePath("/home/ubuntu/wkhtmltox/bin/wkhtmltopdf");
		// }
		cmd.append(toPdfTool).append(" ").append(" --margin-top 0mm  ").append(" --margin-bottom 0mm  ")
				.append(" --margin-left 0mm  ").append(" --margin-right 0mm ").append(" --page-height 180  ")
				.append(" --page-width 100  ").append(htmlPath).append(" ").append(pdfPath);
		Process proc = Runtime.getRuntime().exec(cmd.toString());
		Html2PdfInterceptor errorGobbler = new Html2PdfInterceptor(proc.getErrorStream(), "Error");
		Html2PdfInterceptor outputGobbler = new Html2PdfInterceptor(proc.getInputStream(), "Output");
		errorGobbler.start();
		outputGobbler.start();
		proc.waitFor();
	}
}
