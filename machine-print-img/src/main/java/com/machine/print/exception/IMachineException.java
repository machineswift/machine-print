package com.machine.print.exception;

public interface IMachineException {
	/**
	 * Description:[获取error Code]
	 * @return String
	 */
	String getErrorCode();

	/**
	 * Description:[获取异常信息]
	 * @return String
	 */
	String getNativeMessage();

	/**
	 * Description:[设置 error arguments]
	 * @return void
	 */
	void setErrorArguments(Object... objects);

	/**
	 * Description:[获取 error arguments]
	 * @return Object[]
	 */
	Object[] getErrorArguments();

}
