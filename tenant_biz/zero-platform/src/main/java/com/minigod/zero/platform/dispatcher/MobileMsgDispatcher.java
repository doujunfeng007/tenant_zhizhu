package com.minigod.zero.platform.dispatcher;

import com.minigod.zero.platform.common.MobileMsg;
import com.minigod.zero.platform.service.PushMobileMsgService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 短信消息分发
 *
 * @author minigod
 */
@Service
@Slf4j
public class MobileMsgDispatcher {

	@Resource
	@Lazy
	private PushMobileMsgService pushMobileMsgService;

	private final int QUEUE_SIZE = 40000;
	private BlockingQueue<List<MobileMsg>> mobileMsgQueue = new LinkedBlockingQueue<>(QUEUE_SIZE);
	private Thread thread;
	private volatile boolean threadRunning = true;

	@PostConstruct
	private void postConstruct() {
		threadRunning = true;
		thread = new Thread("mobileMsg-Dispatcher-Worker") {
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
							log.info("mobileMsg dispatcher, elapsed.sec={}, count={}", sec, count);
						}
					} catch (Exception e) {
						log.error("mobileMsg-Dispatcher-Worker", e);
					}
				}
			}
		};
		thread.start();
	}

	@PreDestroy
	private void preDestroy() {
		log.info("关闭mobileMsg本地队列线程");
		threadRunning = false;
		thread.interrupt();
	}

	private void doDispatch() throws InterruptedException {
		List<MobileMsg> msgs = mobileMsgQueue.take();
		for (MobileMsg msg : msgs) {
			try {
				pushMobileMsgService.pushMobileMsg(msg);
			} catch (Exception e) {
				log.error("doDispatch发送短信消息异常", e);
			}
		}
	}

	/**
	 * 短信入本地队列，交给dispatcher异步分发
	 *
	 * @param update
	 */
	public void add(List<MobileMsg> update) {
		if (!mobileMsgQueue.offer(update)) {
			log.error("mobileMsg转发队列溢出，丢弃消息。discard-mobile msg {}", update);
		}
	}

}
