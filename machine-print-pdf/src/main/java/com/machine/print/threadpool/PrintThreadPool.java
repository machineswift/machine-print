package com.machine.print.threadpool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.machine.print.config.PrintConfig;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Description: 线程池工具类(单例模式)
 *
 * @author machine
 * @date 2017年12月20日 上午9:00:49
 */
public class PrintThreadPool {

	/**
	 * @Description: 生成html的线程池
	 */
	public Executor generateHtmlExecutor = new ThreadPoolExecutor(PrintConfig.THREAD_NUM_GEN_HTML, 3, 30, TimeUnit.SECONDS,
			new ArrayBlockingQueue<Runnable>(99999));

	/**
	 * @Description: html转pdf的线程池
	 */
	public Executor html2pdfExecutor = new ThreadPoolExecutor(PrintConfig.THREAD_NUM_GEN_PDF, 8, 30, TimeUnit.SECONDS,
			new ArrayBlockingQueue<Runnable>(99999));

	/**
	 * @Description: 不同打印类型分配一个单列线程池,用于接收消息(解决同时下发大批报文卡顿的问题)
	 *
	 * @author machine
	 * @date 2017年12月20日 上午9:25:03
	 */
	public Executor getPrintTypeExecutor(String printerType) {
		Executor executor = ptintTypeExecutorMap.get(printerType);
		if (null == executor) {
			synchronized (ptintTypeExecutorMapLock) {
				if (null == ptintTypeExecutorMap.get(printerType)) {
					logger.info("Initializing ThreadPoolExecutorr printerName :{} SingleThreadExecutor!", printerType);
					executor = Executors.newSingleThreadExecutor();
					ptintTypeExecutorMap.put(printerType, executor);
				}
			}
		}
		return executor;
	}

	/**
	 * @Description: 执行打印任务的单例线程池 (每个打印机起一个线程池,多个打印机同时打印,并且保证打印顺序)
	 *
	 * @author machine
	 * @date 2017年12月20日 上午9:04:29
	 */
	public Executor getPtintExecutor(String printerName) {
		Executor executor = ptintExecutorMap.get(printerName);
		if (null == executor) {
			synchronized (ptintExecutorMapLock) {
				if (null == ptintExecutorMap.get(printerName)) {
					logger.info("Initializing ThreadPoolExecutorr printerName :{} SingleThreadExecutor!", printerName);
					executor = Executors.newSingleThreadExecutor();
					ptintExecutorMap.put(printerName, executor);
				}
			}
		}
		return executor;
	}

	/**
	 * @Description: 单例模式
	 *
	 * @author machine
	 * @date 2017年12月20日 上午9:06:15
	 */
	public static PrintThreadPool getInstance() {
		if (printThreadPool == null) {
			synchronized (PrintThreadPool.class) {
				if (printThreadPool == null) {
					printThreadPool = new PrintThreadPool();
				}
			}
		}
		return printThreadPool;
	}

	private PrintThreadPool() {
	}

	private volatile static PrintThreadPool printThreadPool;

	private static Object ptintTypeExecutorMapLock = new Object();
	private static Object ptintExecutorMapLock = new Object();
	private Logger logger = LoggerFactory.getLogger(PrintThreadPool.class);

	private static ConcurrentMap<String, Executor> ptintTypeExecutorMap = new ConcurrentHashMap<String, Executor>();
	private static ConcurrentMap<String, Executor> ptintExecutorMap = new ConcurrentHashMap<String, Executor>();
}
