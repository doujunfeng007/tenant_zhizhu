package com.minigod.zero.platform.dispatcher;

import com.minigod.zero.platform.common.EmailMsg;
import com.minigod.zero.platform.service.PushEmailMsgService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 邮件消息分发
 *
 * @author wqy
 */
@Service
@Slf4j
public class EmailMsgDispatcher {

	@Resource
	private PushEmailMsgService pushEmailMsgService;

	private final int QUEUE_SIZE = 40000;
	private BlockingQueue<EmailMsg> emailMsgQueue = new LinkedBlockingQueue<>(QUEUE_SIZE);
	private Thread thread;
	private volatile boolean threadRunning = true;

	@PostConstruct
	private void postConstruct() {
		log.info("启动emailMsg本地队列线程");
		threadRunning = true;
		thread = new Thread("emailMsg-Dispatcher-Worker") {
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
							log.info("emailMsg dispatcher, elapsed.sec={}, count={}", sec, count);
						}
					} catch (Exception e) {
						log.error("emailMsg-Dispatcher-Worker", e);
					}
				}
			}
		};
		thread.start();
	}

	@PreDestroy
	private void preDestroy() {
		log.info("关闭emailMsg本地队列线程");
		threadRunning = false;
		thread.interrupt();
	}


	private void doDispatch() throws InterruptedException {
		EmailMsg msg = emailMsgQueue.take();
		try {
			pushEmailMsgService.pushEmailMsg(msg);
		} catch (Exception e) {
			log.error("doDispatch发送邮件消息异常", e);
		}
	}

	/**
	 * 邮件入本地队列，交给dispatcher异步分发
	 *
	 * @param update
	 */
	public void add(EmailMsg update) {
		if (!emailMsgQueue.offer(update)) {
			log.error("emailMsg转发队列溢出，丢弃消息。discard-email msg {}", update);
		}
	}
}
