package com.minigod.auth.events.handler;

import com.minigod.auth.service.AppUserDetails;
import com.minigod.zero.core.tool.utils.DateUtil;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.minigod.zero.platform.enums.EmailTemplate;
import com.minigod.zero.platform.utils.EmailUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/7/29 10:56
 * @description：登录成功发送邮件
 */
@Slf4j
@Component
public class AuthenticationSuccessSendEmailHandler implements AuthenticationSuccessHandler{

	private static final String GMT =  "(GMT+8)";

	private static final String PATTERN = "yyyy年MM月dd日 HH:mm:ss";


	@Override
	public void hand(AppUserDetails userDetails) {
		log.info("授权成功发送邮件处理");
		String accountEmail = userDetails.getEmail();
		if (StringUtil.isBlank(accountEmail)){
			log.info("用户{}邮箱为空，发送邮件失败！",userDetails.getUserId());
		}
		String formattedDateTime = DateUtil.format(new Date(), PATTERN);
		List<String> param = new ArrayList<>();
		param.add(GMT);
		param.add(formattedDateTime);
		EmailUtil.builder()
			.paramList(param)
			.accepts(Arrays.asList(accountEmail))
			.templateCode(EmailTemplate.LOGIN_SUCCESS.getCode())
			.sendAsync();
	}

	@Override
	public int order() {
		return Order.SUCCESS.SEND_EMAIL.getCode();
	}
}
