package com.machine.print.threadpool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.machine.print.config.PrintConfig;

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
	 * @Description: pdf转img的线程池
	 */
	public Executor pdf2imgExecutor = new ThreadPoolExecutor(PrintConfig.THREAD_NUM_GEN_IMG, 5, 30, TimeUnit.SECONDS,
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

	private static Object ptintExecutorMapLock = new Object();
	private static Object ptintTypeExecutorMapLock = new Object();
	private Logger logger = LoggerFactory.getLogger(PrintThreadPool.class);
	
	private static ConcurrentMap<String, Executor> ptintTypeExecutorMap = new ConcurrentHashMap<String, Executor>();
	public static ConcurrentMap<String, Executor> html2pdfExecutorMap = new ConcurrentHashMap<String, Executor>();
	public static ConcurrentMap<String, Executor> pdf2imgExecutorMap = new ConcurrentHashMap<String, Executor>();
	public static ConcurrentMap<String, Executor> ptintExecutorMap = new ConcurrentHashMap<String, Executor>();
}
