package com.machine.print.service;

import com.machine.print.vo.PrintMessageVo;

/**
 * @Description: 电子面打印的service层接口
 *
 * @author machine
 * @date 2017年12月20日 下午1:16:16
*/
public interface ElePrintService {


	/**
	 * @Description: 顺丰电子面单
	 *
	 * @author machine
	 * @date 2017年12月20日 下午1:16:45
	*/
	String processSFMessage(String uuid,PrintMessageVo requestElePrintMessageModel);

}
