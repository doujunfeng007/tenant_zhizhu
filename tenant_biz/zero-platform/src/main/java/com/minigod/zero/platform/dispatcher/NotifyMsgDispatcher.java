package com.minigod.zero.platform.dispatcher;

import com.minigod.zero.platform.common.NotifyMsg;
import com.minigod.zero.platform.service.PushNotifyMsgService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 通知栏消息分发
 *
 * @author wqy
 */
@Service
@Slf4j
public class NotifyMsgDispatcher {

	@Resource
	private PushNotifyMsgService pushNotifyMsgService;

	private final int QUEUE_SIZE = 40000;
	private BlockingQueue<NotifyMsg> notifyMsgQueue = new LinkedBlockingQueue<>(QUEUE_SIZE);
	private Thread thread;
	private volatile boolean threadRunning = true;

	@PostConstruct
	private void postConstruct() {
		log.info("启动notifyMsg本地队列线程");
		threadRunning = true;
		thread = new Thread("notifyMsg-Dispatcher-Worker") {
			@Override
			public void run() {
				long start = System.currentTimeMillis();
				long count = 0;
				while (threadRunning) {
					try {
						doDispatch();
						count++;
						if (count % 10000 == 0) {
							long sec = (System.currentTimeMillis() - start) / 1000;
							log.info("notifyMsg dispatcher, elapsed.sec={}, count={}", sec, count);
						}
					} catch (Exception e) {
						log.error("notifyMsg-Dispatcher-Worker", e);
					}
				}
			}
		};
		thread.start();
	}

	@PreDestroy
	private void preDestroy() {
		log.info("关闭notifyMsg本地队列线程");
		threadRunning = false;
		thread.interrupt();
	}

	private void doDispatch() throws InterruptedException {
		NotifyMsg msg = notifyMsgQueue.take();
		try {
			pushNotifyMsgService.pushNotifyMsg(msg);
		} catch (Exception e) {
			log.error("doDispatch发送推送消息异常", e);
		}
	}

	/**
	 * 消息入本地队列，交给dispatcher异步分发
	 *
	 * @param update
	 */
	public void add(NotifyMsg update) {
		if (!notifyMsgQueue.offer(update)) {
			log.error("notifyMsg转发队列溢出，丢弃消息。discard-notify msg {}", update);
		}
	}

}
