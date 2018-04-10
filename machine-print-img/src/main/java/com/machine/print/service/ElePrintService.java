package com.machine.print.service;

import com.machine.print.vo.PrintMessageVo;

public interface ElePrintService {


	String processSFMessage(String uuid,PrintMessageVo requestElePrintMessageModel);

}
