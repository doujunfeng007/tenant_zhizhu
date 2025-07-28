package com.minigod.zero.customer.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/5/30 14:47
 * @description：
 */
@Data
@Component
@RefreshScope
@Configuration
public class GoldProperties {
	/**
	 * 是否开启自动换汇0 不开启，1开启
	 */
	@Value("${minigod.automaticDeduction}")
	private int automaticDeduction;
	/**
	 * 游客是否可以查看活利得产品
	 * 0不校验pi等级可见活利得，1校验是否开通PI
	 */
	@Value("${minigod.hasHldPermission}")
	private int hasHldPermission;
	/**
	 * 最小入金金额
	 */
	@Value("${open_account.deposit_amount.min}")
	private BigDecimal minDepositAmount;
	/**
	 * 手工入金密码
	 */
	@Value("${manual.deposit.password}")
	private String manualDepositPassword;
}
