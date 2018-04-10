package com.machine.print.utils.pdf2jepg;

import java.io.File;
import java.io.IOException;

import com.machine.print.config.PrintConfig;

public class Pdf2Jepg {

	private static final String toPdfTool = PrintConfig.PDF2IMG_TOOL;

	public static void execute(String pdfPath, String imgPath) throws IOException, InterruptedException {
		StringBuilder cmd = new StringBuilder();
		if (new File(pdfPath).exists()) {
			cmd.append(toPdfTool).append("  -r:900 -i:").append(pdfPath).append(" -o:").append(imgPath);
			Process proc = Runtime.getRuntime().exec(cmd.toString());
			Pdf2JepgInterceptor errorGobbler = new Pdf2JepgInterceptor(proc.getErrorStream(), "Error");
			Pdf2JepgInterceptor outputGobbler = new Pdf2JepgInterceptor(proc.getInputStream(), "Output");
			errorGobbler.start();
			outputGobbler.start();
			proc.waitFor();
		}
	}
}
