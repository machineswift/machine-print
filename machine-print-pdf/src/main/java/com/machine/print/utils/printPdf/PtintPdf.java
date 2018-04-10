package com.machine.print.utils.printPdf;


import org.slf4j.Logger;
import java.io.IOException;
import org.slf4j.LoggerFactory;
import com.machine.print.config.PrintConfig;

/**
 * @Description: pdf发送到打印机
 *
 * @author machine
 * @date 2017年12月20日 上午10:38:59
 */
public class PtintPdf {

	public void execute(String printerName, String uuid) throws IOException, InterruptedException {
		String pdfPath = new StringBuilder(PrintConfig.PDF_PATH).append(uuid).append(".pdf").toString();
		StringBuilder cmd = new StringBuilder();
		cmd.append(PrintConfig.PRINT_PDF_TOOL).append(" -silent -print-to \"").append(printerName).append("\"  \"")
				.append(pdfPath).append("\"");
		logger.info("PtintPdf command : {}", cmd);
		Process proc = Runtime.getRuntime().exec(cmd.toString());
		PtintPdfInterceptor errorGobbler = new PtintPdfInterceptor(proc.getErrorStream(), "Error");
		PtintPdfInterceptor outputGobbler = new PtintPdfInterceptor(proc.getInputStream(), "Output");
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
	public static PtintPdf getInstance() {
		if (ptintPdf == null) {
			synchronized (PtintPdf.class) {
				if (ptintPdf == null) {
					ptintPdf = new PtintPdf();
				}
			}
		}
		return ptintPdf;
	}

	private PtintPdf() {
	}

	private volatile static PtintPdf ptintPdf;

	private Logger logger = LoggerFactory.getLogger(PtintPdf.class);
}
