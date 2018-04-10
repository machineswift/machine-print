package com.machine.print.vo.ele.sf;

import java.io.Serializable;

/**
 * 目的地
 */
@SuppressWarnings("serial")
public class ElePrintDestinationSF implements Serializable{
	
	private String destination="";

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

}
