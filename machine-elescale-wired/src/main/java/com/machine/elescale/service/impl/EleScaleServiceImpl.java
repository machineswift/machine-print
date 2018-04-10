package com.machine.elescale.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.springframework.stereotype.Service;

import com.machine.elescale.param.ElescaleParam;
import com.machine.elescale.service.EleScaleService;

import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;

/**
 * @Description: 电子面单对应的service实现类
 *
 * @author machine
 * @date 2017年12月20日 下午1:17:23
 */
@Service
public class EleScaleServiceImpl implements EleScaleService {

	/**
	 * @Description: 顺丰电子面单
	 *
	 * @author machine
	 * @date 2017年12月20日 下午1:17:41
	 */
	@Override
	public String processSFMessage(String uuid) {
		return "";
	}

	/**
	 * @Description: 获取本地所有的端口并输出其名称
	 *
	 * @author machine
	 * @date 2017年12月20日 下午1:16:45
	 */
	@Override
	public List<String> getCommPortNames() {
		List<String> names = new ArrayList<String>();
		/* 获取本地所有的端口并输出其名称 */
		CommPortIdentifier portIdentifier = null;
		/* 记录所有端口的变量 */
		Enumeration<?> allPorts = CommPortIdentifier.getPortIdentifiers();

		/* 遍历输出每一个端口 */
		while (allPorts.hasMoreElements()) {
			portIdentifier = (CommPortIdentifier) allPorts.nextElement();
			names.add(portIdentifier.getName());
		}
		return names;
	}

	/**
	 * @Description: 测试对应的端口是否可用
	 *
	 * @author machine
	 * @date 2017年12月20日 下午1:16:45
	 */
	@Override
	public String getSerialPortByName(String name) {
		/* 获取端口 */
		CommPortIdentifier com = null;
		try {
			com = CommPortIdentifier.getPortIdentifier(name);
		} catch (NoSuchPortException e) {
			return "端口:" + name + "没找到   " + e.getMessage();
		}

		/* 打开端口 */
		SerialPort serialCom = null;
		try {
			/* open方法的第1个参数表示串口被打开后的所有者名称， */
			/* 第2个参数表示如果串口被占用的时候本程序的最长等待时间，以毫秒为单位。 */
			serialCom = (SerialPort) com.open(name, 1000);
		} catch (PortInUseException e) {
			return "端口:" + name + "被占用   " + e.getMessage();
		}

		/* 设置两个端口的参数 */
		try {
			serialCom.setSerialPortParams(9600, // 波特率
					SerialPort.DATABITS_8, // 数据位数
					SerialPort.STOPBITS_1, // 停止位
					SerialPort.PARITY_NONE// 奇偶位
			);
		} catch (UnsupportedCommOperationException e) {
			return "不支持-->>端口:" + name + "波特率:9600-据位数:8-停止位:1-奇偶位:无-message:" + e.getMessage();
		}
		/* 关闭端口 */
		serialCom.close();
		return "支持-->>端口:" + name + "-	波特率:9600-据位数:8-停止位:1-奇偶位:无";
	}

	/**
	 * @Description: 获取重量
	 *
	 * @author machine
	 * @date 2017年12月20日 下午1:16:45
	 */
	@Override
	public String getWeigt() {
		return new BigDecimal(ElescaleParam.weight).divide(new BigDecimal(ElescaleParam.divisor), ElescaleParam.scale, BigDecimal.ROUND_HALF_UP).toString();
	}

}
