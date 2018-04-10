package com.machine.print.utils.cas;

import java.util.concurrent.atomic.AtomicInteger;

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
