package com.minigod.zero.biz.task.config;

import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * xxl-job config
 *
 * @author xuxueli 2017-04-28
 */
@Slf4j
@Configuration
public class XxlJobConfig {

	@Value("${xxl.job.admin.addresses}")
	private String adminAddresses;

	@Value("${xxl.job.accessToken}")
	private String accessToken;

	@Value("${xxl.job.executor.appName}")
	private String appName;

	@Value("${xxl.job.executor.port}")
	private int port;

	@Value("${xxl.job.executor.logPath}")
	private String logPath;

	@Value("${xxl.job.executor.logRetentionDays}")
	private int logRetentionDays;


	@Bean
	public XxlJobSpringExecutor xxlJobExecutor() {
		log.info(">>>>>>>>>>> xxl-job config init.");
		XxlJobSpringExecutor xxlJobExecutor = new XxlJobSpringExecutor();
		xxlJobExecutor.setAdminAddresses(adminAddresses);
		xxlJobExecutor.setAppname(appName);
		xxlJobExecutor.setPort(port);
		xxlJobExecutor.setAccessToken(accessToken);
		xxlJobExecutor.setLogPath(logPath);
		xxlJobExecutor.setLogRetentionDays(logRetentionDays);

		return xxlJobExecutor;
	}

	/**
	 * 针对多网卡、容器内部署等情况，可借助 "spring-cloud-commons" 提供的 "InetUtils" 组件灵活定制注册IP；
	 *
	 *      1、引入依赖：
	 *          <dependency>
	 *             <groupId>org.springframework.cloud</groupId>
	 *             <artifactId>spring-cloud-commons</artifactId>
	 *             <version>${version}</version>
	 *         </dependency>
	 *
	 *      2、配置文件，或者容器启动变量
	 *          spring.cloud.inetutils.preferred-networks: 'xxx.xxx.xxx.'
	 *
	 *      3、获取IP
	 *          String ip_ = inetUtils.findFirstNonLoopbackHostInfo().getIpAddress();
	 */


}
