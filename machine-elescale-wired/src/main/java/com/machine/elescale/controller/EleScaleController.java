package com.machine.elescale.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.machine.elescale.param.ElescaleParam;
import com.machine.elescale.service.EleScaleService;
import com.machine.elescale.vo.Weight;

/**
 * @Description: 电子称
 *
 * @author machine
 * @date 2017年12月20日 上午11:44:21
 */
@Controller
@RequestMapping("/elescale")
public class EleScaleController {

	@Autowired
	private EleScaleService eleScaleService;

	@Autowired
	private Weight weight;

	/**
	 * @Description: Browser[http://localhost:8090/elescale/info]
	 *
	 * @author machine
	 * @date 2017年12月20日 上午11:36:09
	 */
	@RequestMapping(value = "info", method = RequestMethod.GET)
	@ResponseBody
	public Object info() {
		return "是否反转:" + ElescaleParam.breverse + "  保留小数位:" + ElescaleParam.scale + "  缩放比例:" + ElescaleParam.divisor;
	}

	/**
	 * @Description: Browser[http://localhost:8090/elescale/scale?scale=2] 是否反转
	 *
	 * @author machine
	 * @date 2017年12月20日 上午11:36:09
	 */
	@RequestMapping(value = "reverse", method = RequestMethod.GET)
	@ResponseBody
	public Object reverse() {
		if (ElescaleParam.breverse) {
			ElescaleParam.breverse = Boolean.FALSE;
		} else {
			ElescaleParam.breverse = Boolean.TRUE;
		}
		return ElescaleParam.breverse;
	}

	/**
	 * @Description: Browser[http://localhost:8090/elescale/scale?scale=2] 保留小数位
	 *
	 * @author machine
	 * @date 2017年12月20日 上午11:36:09
	 */
	@RequestMapping(value = "scale", method = RequestMethod.GET)
	@ResponseBody
	public Object scale(int scale) {
		ElescaleParam.scale = scale;
		return scale;
	}

	/**
	 * @Description: Browser[http://localhost:8090/elescale/divisor?divisor=10] 缩放比例
	 *
	 * @author machine
	 * @date 2017年12月20日 上午11:36:09
	 */
	@RequestMapping(value = "divisor", method = RequestMethod.GET)
	@ResponseBody
	public Object divisor(int divisor) {
		ElescaleParam.divisor = divisor;
		return divisor;
	}

	/**
	 * @Description: Browser[http://localhost:8090/elescale/commPortNames] 本地所有的端口
	 *
	 * @author machine
	 * @date 2017年12月20日 上午11:36:09
	 */
	@RequestMapping(value = "commPortNames", method = RequestMethod.GET)
	@ResponseBody
	public String getCommPortNames() {
		List<String> portNames = this.eleScaleService.getCommPortNames();
		return JSON.toJSONString(portNames);
	}

	/**
	 * @Description: Browser[http://localhost:8090/elescale/serialPort?name=COM1]
	 *               测试对应的端口是否可用
	 *
	 * @author machine
	 * @date 2017年12月20日 上午11:36:09
	 */
	@RequestMapping(value = "serialPort", method = RequestMethod.GET)
	@ResponseBody
	public String getSerialPortByName(String name) {
		return this.eleScaleService.getSerialPortByName(name);
	}

	/**
	 * @Description: Browser[http://localhost:8090/elescale/weight] 获取重量
	 *
	 * @author machine
	 * @date 2017年12月20日 上午11:36:09
	 */
	@RequestMapping(value = "weight", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Weight getWeight() {
		weight.setWeight(this.eleScaleService.getWeigt());
		return weight;
	}
}
