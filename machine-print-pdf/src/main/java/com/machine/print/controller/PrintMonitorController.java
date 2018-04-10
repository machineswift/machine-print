package com.machine.print.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author machine
 *
 */
@Controller
@RequestMapping("/print")
public class PrintMonitorController {

	@RequestMapping("monitor")
	@ResponseBody
	public String printerQueue() {
		StringBuilder sbResult = new StringBuilder();

		// /int count1 =
		// PrintThreadPool.getInstance().html2pdfExecutor.getActiveCount();

		// sbResult.append("active:" + count1);
		return sbResult.toString();
	}
}
