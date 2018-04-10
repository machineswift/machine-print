package com.machine.print.utils.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import com.machine.print.common.PrintCommonParam;

/**
 * @Description: FileUtils
 *
 * @author machine
 * @date 2017年12月18日 下午9:59:37
*/
public class FileUtils {

	/**
	 * @Description: 文件落地
	 *
	 * @author machine
	 * @date 2017年12月18日 下午9:59:24
	*/
	public static void string2File(String path, String content) throws IOException {
		File file = new File(path);
		file.delete();
		file.createNewFile();
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"));
		writer.write(content);
		writer.close();
	}

	/**
	 * @Description: 以行为单位读取文件，常用于读面向行的格式化文件
	 *
	 * @author machine
	 * @date 2017年12月18日 下午9:58:43
	 */
	public static String readFileByLines(String fileName) throws IOException {
		File file = new File(fileName);
		return readFileByLines(file);
	}

	/**
	 * @Description: 以行为单位读取文件，常用于读面向行的格式化文件
	 *
	 * @author machine
	 * @date 2017年12月18日 下午9:58:43
	 */
	public static String readFileByLines(File file) throws IOException {
		StringBuilder sbResult = new StringBuilder();
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
		String strLine = null;
		while ((strLine = reader.readLine()) != null) {
			sbResult.append(strLine).append(PrintCommonParam.LINE_SEPRATER);
		}
		reader.close();
		return sbResult.toString();
	}

	/**
	 * @Description: 递归删除文件夹
	 *
	 * @author machine
	 * @date 2017年12月18日 下午9:59:09
	*/
	public static void delDir(File f) {
		// 判断是否是一个目录, 不是的话跳过, 直接删除; 如果是一个目录, 先将其内容清空.
		if (f.isDirectory()) {
			// 获取子文件/目录
			File[] subFiles = f.listFiles();
			// 遍历该目录
			for (File subFile : subFiles) {
				// 递归调用删除该文件: 如果这是一个空目录或文件, 一次递归就可删除. 如果这是一个非空目录, 多次
				// 递归清空其内容后再删除
				delDir(subFile);
			}
		}
		// 删除空目录或文件
		f.delete();
	}

	/**
	 * 递归创建文件夹
	 */
	public static void mkDir(File file) {
		if (file.getParentFile().exists()) {
			file.mkdir();
		} else {
			mkDir(file.getParentFile());
			file.mkdir();
		}
	}

}
