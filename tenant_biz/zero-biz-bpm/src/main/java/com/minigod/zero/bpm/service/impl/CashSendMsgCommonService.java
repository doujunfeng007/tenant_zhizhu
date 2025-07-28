package com.minigod.zero.bpm.service.impl;

import com.minigod.zero.bpm.dto.BpmAccountRespDto;
import com.minigod.zero.bpm.service.openAccount.IBpmAccountService;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.minigod.zero.platform.dto.SendEmailDTO;
import com.minigod.zero.platform.dto.SendNotifyDTO;
import com.minigod.zero.platform.enums.MsgStaticType;
import com.minigod.zero.platform.feign.IPlatformMsgClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * @author Zhe.Xiao
 */
@Service
@Slf4j
public class CashSendMsgCommonService {

	@Resource
	private IPlatformMsgClient platformMsgClient;
	@Resource
	private IBpmAccountService bpmAccountService;

	@Async("asyncExecutor")
	public void sendPushEmail(Long custId, String lang, int pushCode, int emailCode) {
		try {
			//推送通知
			SendNotifyDTO sendNotifyDTO = new SendNotifyDTO();
			sendNotifyDTO.setDisplayGroup(MsgStaticType.DisplayGroup.SERVICE_MSG);
			sendNotifyDTO.setTemplateCode(pushCode);
			sendNotifyDTO.setLstToUserId(Arrays.asList(custId));
			sendNotifyDTO.setLang(lang);
			platformMsgClient.sendNotify(sendNotifyDTO);
			//邮件通知
			String email = null;
			R<BpmAccountRespDto> resp = bpmAccountService.getAccountRespDto(custId);
			if (resp.isSuccess() && null != resp.getData()) {
				BpmAccountRespDto acct = resp.getData();
				if (null != acct.getCust()) {
					email = acct.getCust().getEmail();
				}
			}
			if (StringUtil.isNotBlank(email)) {
				SendEmailDTO sendEmailDTO = new SendEmailDTO();
				sendEmailDTO.setAccepts(Arrays.asList(email));
				sendEmailDTO.setCode(emailCode);
				sendEmailDTO.setLang(lang);
				platformMsgClient.sendEmail(sendEmailDTO);
			} else {
				log.info("用户{}出入金业务邮件未获取到邮箱, pushCode:{}, emailCode:{}", custId, pushCode, emailCode);
			}
		} catch (Exception e) {
			log.error("用户{}出入金业务发送通知, pushCode:{}, emailCode:{}, 异常:{}", custId, pushCode, emailCode, e.getMessage(), e);
		}
	}

}
