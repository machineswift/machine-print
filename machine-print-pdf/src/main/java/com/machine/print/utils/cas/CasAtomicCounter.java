package com.machine.print.utils.cas;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description: 计数器(非阻塞算法)
 *
 * @author machine
 * @date 2017年12月20日 下午12:40:30
 */
public class CasAtomicCounter {

	private AtomicInteger counter = new AtomicInteger(0);

	public int getNextIndex() {
		for (;;) {
			int cur = counter.get();
			int next = cur + 1;
			if (counter.compareAndSet(cur, next)) {
				return next;
			}
		}
	}

	public int getCounter() {
		return counter.get();
	}
}
