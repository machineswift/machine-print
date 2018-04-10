package com.machine.print.envm;

import java.util.HashMap;
import java.util.Map;

public enum  PrintExceptions {
	/*printCache异常-start  500<code<1000*/
	PRINT_810("PRINT_810","清理缓存UUID参数为空"),
	/*printCache异常-end*/

	/*printCache异常-start  300<code<500*/
	PRINT_310("PRINT_310","没找到配置文件:print-config.properties"),
	PRINT_314("PRINT_314","配置文件:print-config.properties--IOException"),
	PRINT_318("PRINT_318","配置文件:print-config.properties--RunTimeException"),
	/*printCache异常-end*/

	/*配置文件异常-start  1000<code<2000*/
	PRINT_1100("PRINT_1100","没找到配置文件:express-elesheet.properties"),
	PRINT_1200("PRINT_1200","配置文件IO异常:express-elesheet.properties"),
	PRINT_1500("PRINT_1500","配置文件内容格式错误:express-elesheet.properties"),
	PRINT_1700("PRINT_1700","配置文件没找到key异常:express-elesheet.properties"),
	/*配置文件异常-end*/

	/*打印机异常-start  2000<code<3000*/
	PRINT_2100("PRINT_2100","找不到打印机"),
	/*打印机异常-end*/

	/*模板文件异常-start  3000<code<4000*/
	PRINT_3004("PRINT_3004","模板文件-IOException"),
	PRINT_3010("PRINT_3010","模板验证--RunTimeException"),
	PRINT_3100("PRINT_3100","找不到模板文件"),
	/*模板文件异常-end*/

	/*图片文件异常-start  4000<code<5000*/
	PRINT_4004("PRINT_4004","图片文件-IOException"),
	PRINT_4010("PRINT_4010","图片文件格式不正确"),
	PRINT_4100("PRINT_4100","找不到图片"),
	PRINT_4110("PRINT_4110","图片文件不存在"),
	/*图片文件异常-end*/

	/*图片文件异常-start  5000<code<6000*/
	PRINT_5004("PRINT_5004","条形码-IOException"),
	PRINT_5010("PRINT_5010","条形码格式不正确"),
	PRINT_5100("PRINT_5100","找不到条形码"),
	PRINT_5202("PRINT_5202","生成条形码NumberFormatException"),
	PRINT_5203("PRINT_5203","生成条形码NumberFormatException"),
	PRINT_5204("PRINT_5204","生成条形码NumberFormatException"),
	/*图片文件异常-end*/
	
	
	/*报文异常-start  8000<code<9999*/
	PRINT_8010("PRINT_8010","报文必填字段为空");
	/*报文异常-end*/
	
	public String getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}

	PrintExceptions(String key, String value) {
		this.key = key;
		this.value = value;
	}

	/**
	 * 根据key获取value
	 * 
	 * @param key
	 *            : 键值key
	 * @return String
	 */
	public static String getValueByKey(String key) {
		if(null == kvMap) {
			synchronized(lock) {
				if(null == kvMap) {
					kvMap = new HashMap<String, String>();
					PrintExceptions[] enums = PrintExceptions.values();
					for (int i = 0; i < enums.length; i++) {
						kvMap.put(enums[i].getKey().toString(), enums[i].getValue());
					}
				}
			}
		}
		return kvMap.get(key);
	}


	private final String key;
	private final String value;
	private static Object lock = new Object();
	private static Map<String,String> kvMap;
}
