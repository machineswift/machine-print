package com.machine.elescale.vo;

import java.io.Serializable;
import org.springframework.stereotype.Component;

@Component
@SuppressWarnings("serial")
public class Weight implements Serializable {
	String weight;

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

}
