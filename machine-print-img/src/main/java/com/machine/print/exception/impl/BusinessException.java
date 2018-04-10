package com.machine.print.exception.impl;

import java.io.Serializable;

import com.machine.print.exception.IMachineException;

/**
 * Description:业务异常基类
 */
public class BusinessException extends RuntimeException implements Serializable, IMachineException {
	
	private static final long serialVersionUID = 1937263904748419090L;
	
	/**
	 * 异常code
	 */
	protected String errCode;
	
	/**
	 * 异常信息
	 */
	private String natvieMsg;
	
	/**
	 * 异常 arguments
	 */
	private Object[] arguments;

	public BusinessException() {
		super();
	}
	
	public BusinessException(String msg) {
		super(msg);
	}
	
	public BusinessException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
	public BusinessException(String code, String msg) {
		super(msg);
		this.errCode = code;
	}
	
	public BusinessException(String code, String msg, Throwable cause) {
		super(msg, cause);
		this.errCode = code;
	}
	
	public BusinessException(String code, String msg, String natvieMsg) {
		super(msg);
		this.errCode = code;
		this.natvieMsg = natvieMsg;
	}
	
	public BusinessException(String code, String msg,String natvieMsg, Throwable cause) {
		super(msg, cause);
		this.errCode = code;
		this.natvieMsg = natvieMsg;
	}
	
	public BusinessException(String code,Object... args) {
		super();
		this.errCode = code;
		this.arguments = args;
	}
	
	public BusinessException(String code,String msg, Object... args) {
		super(msg);
		this.errCode = code;
		this.arguments = args;
	}
	
	@Override
	public void setErrorArguments(Object... args) {
		this.arguments = args;
	}
	
	@Override
	public Object[] getErrorArguments() {
		return this.arguments;
	}
	
	@Override
	public String getErrorCode() {
		return this.errCode;
	}
	
	@Override
	public String getNativeMessage() {
		return natvieMsg;
	}
}
