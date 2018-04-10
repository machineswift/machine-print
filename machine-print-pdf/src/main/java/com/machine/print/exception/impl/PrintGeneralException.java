package com.machine.print.exception.impl;

import com.machine.print.exception.GeneralException;

public class PrintGeneralException extends GeneralException {
	private static final long serialVersionUID = -8101181379247830957L;

	public PrintGeneralException(String msg) {
        super(msg);
    }
	
	public PrintGeneralException(Throwable e) {
	    super(e);
	}
}
