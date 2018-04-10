package com.machine.print.exception.impl;

public class PrintBusinessException extends BusinessException{
	private static final long serialVersionUID = 2253229058144583029L;
	/**
	 * 自定义异常
	 * @param code
	 * @param msg
	 */
	public PrintBusinessException(String code, String msg, Throwable cause) {
    	super(code, msg, cause);
    }
	/**
	 * 自定义异常
	 * @param code
	 * @param msg
	 */
    public PrintBusinessException(String code, String msg) {
    	super(code, msg);
    }
	/**
	 * 自定义异常
	 * @param code
	 * @param msg
	 */
    public PrintBusinessException(String msg) {
    	super(msg);
    }
}
