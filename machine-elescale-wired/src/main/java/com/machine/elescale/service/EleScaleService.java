package com.machine.elescale.service;

import java.util.List;

/**
 * @Description: 电子秤的service层接口
 *
 * @author machine
 * @date 2017年12月20日 下午1:16:16
*/
public interface EleScaleService {


	/**
	 * @Description: 顺丰电子面单
	 *
	 * @author machine
	 * @date 2017年12月20日 下午1:16:45
	*/
	String processSFMessage(String uuid);
	/**
	 * @Description: 获取本地所有的端口并输出其名称
	 *
	 * @author machine
	 * @date 2017年12月20日 下午1:16:45
	*/
	List<String> getCommPortNames();
	
	/**
	 * @Description: 测试对应的端口是否可用
	 *
	 * @author machine
	 * @date 2017年12月20日 下午1:16:45
	*/
	String getSerialPortByName(String name);
	
	/**
	 * @Description: 获取重量
	 *
	 * @author machine
	 * @date 2017年12月20日 下午1:16:45
	*/
	String getWeigt();
}
