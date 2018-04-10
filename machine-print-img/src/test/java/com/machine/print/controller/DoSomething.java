package com.machine.print.controller;

public class DoSomething implements Runnable {
	private String elePrintMessage;

	public DoSomething(String elePrintMessage) {
		this.elePrintMessage = elePrintMessage;
	}

	public void run() {
		for (int i = 0; i < 100; i++) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			HttpUtils.sendPost("http://localhost:8080/print/elePrintMessage", elePrintMessage);
		}
	}
}
