package com.minigod.zero.biz.common.utils;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.*;

// 添加其他方法来使用 executorService
@Slf4j
public class GlobalExecutorService {

	private static final int MAX_QUOTES_LOG_THREAD = 6;
	// 行情日志收线程
	private static final Map<String, ExecutorService> quotesLogThreadMap = new ConcurrentHashMap<>();

    // 声明为静态变量
    private static ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2,
		new SelfDefinedThreadFactory("ZERO-BIZ %d"));

    public static ExecutorService getExecutor() {
        return executor;
    }

	/**
	 * 自定义线程任务
	 * @param key
	 */
	public static ExecutorService getQuotesLogThread(String key) {
        int selectPushThread = Math.abs(key.hashCode() % MAX_QUOTES_LOG_THREAD);
        String threadKey = "QUOTES-LOG-" + selectPushThread;
        ExecutorService service = quotesLogThreadMap.get(threadKey);
        if (service == null) {
			service = new ThreadPoolExecutor(1, 1, 30, TimeUnit.SECONDS,
				new LinkedBlockingQueue<>(200000), new ThreadPoolExecutor.DiscardOldestPolicy());
			quotesLogThreadMap.put(threadKey, service);
        }
        return service;
    }

	/**
     * 初始化线城池
     *
     * @return
     */
    public static ThreadPoolExecutor initCommonExecutor(String threadKey, int capacity) {
        return new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MINUTES, new LinkedBlockingQueue<>(capacity), new ThreadFactoryBuilder()
                .setNameFormat(threadKey)
                .setUncaughtExceptionHandler((t, e) -> log.error("Thread Name:{},handler error: ",Thread.currentThread().getName(), e))
                .build(), new ThreadPoolExecutor.DiscardOldestPolicy());
    }

}
