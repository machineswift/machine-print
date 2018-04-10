package com.machine.elescale.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.TooManyListenersException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.machine.elescale.param.ElescaleParam;

import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;

/**
 * @Description: "事件监听模式"监听串口COM1
 *
 * @author machine
 * @date 2017年12月20日 下午1:17:23
 */
public class ComEventListener implements SerialPortEventListener {

	/* 未打开的端口 */
	CommPortIdentifier com = null;
	/* 打开的端口 */
	SerialPort serialCom = null;
	/* 输入流 */
	InputStream inputStream = null;

	// 重写继承的监听器方法
	@Override
	public void serialEvent(SerialPortEvent event) {
		try {
			Thread.sleep(20);
		} catch (InterruptedException e) {
			logger.error("读取数据时睡眠异常-message:{}", e.getMessage());
		}
		/* 定义用于缓存读入数据的数组 */
		byte[] cache = new byte[1024];
		/* 记录已经到达串口COM且未被读取的数据的字节数 */
		int availableBytes = 0;
		/* 如果是数据可用的时间发送,则进行数据的读写 */
		StringBuilder sbResult = new StringBuilder();
		if (event.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
			try {
				availableBytes = inputStream.available();
				while (availableBytes > 0) {
					inputStream.read(cache);

					for (int i = 1; i < cache.length && i < availableBytes; i++) {
						// 解码并输出数据
						char c = (char) cache[i];
						if (61 == c || 46 == c) {
							continue;
						}
						sbResult.append(c);
					}
					if (ElescaleParam.breverse) {
						ElescaleParam.weight = Long.parseLong(sbResult.reverse().toString());
					} else {
						ElescaleParam.weight = Long.parseLong(sbResult.toString());
					}
					sbResult.setLength(0);
					availableBytes = inputStream.available();
				}
			} catch (IOException e) {
				logger.error("读取数据异常-message:{}", e.getMessage());
			} catch (Exception e) {
				logger.error("读取数据异常-message:{}", e.getMessage());
			}
		}
	}

	public static ComEventListener getInstance(String portName) {
		if (comEventListener == null) {
			synchronized (ComEventListener.class) {
				if (comEventListener == null) {
					comEventListener = new ComEventListener(portName);
				}
			}
		}
		return comEventListener;
	}

	/**
	 * @Description: 实现初始化动作：获取串口COM、打开串口、获取串口输入流对象、为串口添加事件监听对象
	 *
	 * @author machine
	 * @date 2017年12月20日 上午11:36:09
	 */
	private ComEventListener(String portName) {
		try {
			/* 获取串口、打开窗串口、获取串口的输入流 */
			com = CommPortIdentifier.getPortIdentifier(portName);
			serialCom = (SerialPort) com.open(portName, 1000);

			/* 设置两个端口的参数 */
			try {
				serialCom.setSerialPortParams(9600, // 波特率
						SerialPort.DATABITS_8, // 数据位数
						SerialPort.STOPBITS_1, // 停止位
						SerialPort.PARITY_NONE// 奇偶位
				);
			} catch (UnsupportedCommOperationException e) {
				logger.error("不支持-->>端口:{}-波特率:9600-据位数:8-停止位:1-奇偶位:无-message:{}", portName, e.getMessage());
				return;
			}

			inputStream = serialCom.getInputStream();
			// 向串口添加事件监听对象。
			serialCom.addEventListener(this);
			// 设置当端口有可用数据时触发事件，此设置必不可少。
			serialCom.notifyOnDataAvailable(true);
		} catch (NoSuchPortException e) {
			logger.error("端口:{}没找到-message:{}", portName, e.getMessage());
		} catch (PortInUseException e) {
			logger.error("端口:{}被占用-message:{}", portName, e.getMessage());
		} catch (IOException e) {
			logger.error("端口:{}读取数据异常-message:{}", portName, e.getMessage());
		} catch (TooManyListenersException e) {
			logger.error("端口:{}有多个=监听-message:{}", portName, e.getMessage());
		}
	}

	private volatile static ComEventListener comEventListener;
	private Logger logger = LoggerFactory.getLogger(ComEventListener.class);
}