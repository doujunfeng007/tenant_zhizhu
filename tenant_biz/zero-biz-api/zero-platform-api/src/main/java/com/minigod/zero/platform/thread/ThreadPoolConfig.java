package com.minigod.zero.platform.thread;

import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.cloud.nacos.NacosConfigProperties;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.api.config.listener.Listener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.concurrent.*;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/6/3 15:53
 * @description：
 */
@Slf4j
@RefreshScope
@Configuration
public class ThreadPoolConfig{

	@Value("${thread.pool.corePoolSize}")
	private int corePoolSize;

	@Value("${thread.pool.maxPoolSize}")
	private int maximumPoolSize;

	@Value("${thread.pool.queueCapacity}")
	private int queueCapacity;

	@Value("${thread.pool.keepAliveSeconds}")
	private long keepAliveSeconds;

	@Resource
	private NacosConfigManager nacosConfigManager;

	@Resource
	private NacosConfigProperties nacosConfigProperties;

	@Bean(destroyMethod = "shutdown")
	public ThreadPoolExecutor threadPoolExecutor() {
		return new ThreadPoolExecutor(
			corePoolSize, maximumPoolSize,keepAliveSeconds, TimeUnit.SECONDS,
			new ArrayBlockingQueue<>(queueCapacity),
			Executors.defaultThreadFactory(),
			new DefaultRejectedExecutionHandler()
		);
	}

	@PostConstruct
	public void init() throws Exception {
		nacosConfigManager.getConfigService().addListener("zero.yaml",nacosConfigProperties.getGroup(),
			new Listener() {
				@Override
				public Executor getExecutor() {
					return null;
				}
				@Override
				public void receiveConfigInfo(String configInfo) {
					Yaml yaml = new Yaml(new Constructor(JSONObject.class));
					InputStream inputStream = new ByteArrayInputStream(configInfo.getBytes());
					JSONObject data = yaml.load(inputStream);
					JSONObject pool = data.getJSONObject("thread").getJSONObject("pool");
					threadPoolExecutor().setCorePoolSize(pool.getInteger("corePoolSize"));
					threadPoolExecutor().setMaximumPoolSize(pool.getInteger("maxPoolSize"));
				}
			});
	}

	/**
	 * 拒绝策略
	 */
	class DefaultRejectedExecutionHandler implements RejectedExecutionHandler{

		@Override
		public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
		}
	}

}
