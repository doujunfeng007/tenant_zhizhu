package com.minigod.zero.system.common;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class SelfDefinedThreadFactory implements ThreadFactory {

	final AtomicInteger threadNumber = new AtomicInteger(1);
	final String namePrefix;

	public SelfDefinedThreadFactory(String namePrefix) {
		this.namePrefix = namePrefix+"-";
	}

	public Thread newThread(Runnable r) {
		Thread t = new Thread( r,namePrefix + threadNumber.getAndIncrement());
		if (t.isDaemon())
			t.setDaemon(true);
		if (t.getPriority() != Thread.NORM_PRIORITY)
			t.setPriority(Thread.NORM_PRIORITY);
		return t;
	}
}
