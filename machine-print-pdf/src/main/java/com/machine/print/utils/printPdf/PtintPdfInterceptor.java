package com.machine.print.utils.printPdf;

import org.slf4j.Logger;
import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedReader;
import org.slf4j.LoggerFactory;
import java.io.InputStreamReader;

/**
 * 调用Runtime.getRuntime().exec()后，如果不及时捕捉进程的输出,会导致JAVA挂住，看似被调用进程没退出!
 * 解决办法:启动进程后，再启动两个JAVA线程及时的把被调用进程的输出截获
 */
public class PtintPdfInterceptor extends Thread {
	InputStream is;
	String type;

	public PtintPdfInterceptor(InputStream is, String type) {
		this.is = is;
		this.type = type;
	}

	public void run() {
		try {
			InputStreamReader isr = new InputStreamReader(is, "utf-8");
			BufferedReader br = new BufferedReader(isr);
			while ((br.readLine()) != null) {
			}
		} catch (IOException ioe) {
			logger.error("PtintPdfInterceptorException: {}", ioe.getMessage());
		}
	}

	private Logger logger = LoggerFactory.getLogger(PtintPdfInterceptor.class);
}