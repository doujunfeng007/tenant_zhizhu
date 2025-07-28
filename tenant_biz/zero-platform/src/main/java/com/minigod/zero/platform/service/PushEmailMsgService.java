package com.minigod.zero.platform.service;

import com.alibaba.fastjson2.util.UUIDUtils;
import com.itextpdf.text.pdf.PRAcroForm;
import cn.hutool.core.bean.BeanUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.api.ResultCode;
import com.minigod.zero.core.tool.jackson.JsonUtil;
import com.minigod.zero.core.tool.utils.CollectionUtil;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.minigod.zero.platform.common.EmailMsg;
import com.minigod.zero.platform.entity.PlatformEmailContentEntity;
import com.minigod.zero.platform.enums.InformEnum;
import com.minigod.zero.platform.mapper.PlatformEmailContentMapper;
import com.minigod.zero.platform.utils.SmtpUtil;
import com.minigod.zero.resource.utils.MsgEmailUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
@RefreshScope
@RequiredArgsConstructor
public class PushEmailMsgService {

	@Value("${email.enabled:true}")
	private Boolean isMsgSending;

	@Value("${email.smtp.host}")
	private String smtpHost;
	@Value("${email.smtp.port}")
	private String smtpPort;
	@Value("${email.smtp.userName}")
	private String userName;
	@Value("${email.smtp.password}")
	private String password;
	//"text/html;charset=utf-8"
	@Value("${email.smtp.contentType}")
	private String contentType;
	@Value("${email.smtp.sender}")
	private String sender;

	private final PlatformEmailContentMapper platformEmailContentMapper;

	public void pushEmailMsg(final EmailMsg bean) {
		Date now = new Date();
		PlatformEmailContentEntity emailContent = new PlatformEmailContentEntity();
		emailContent.setCreateTime(now);
		emailContent.setUpdateTime(now);
		emailContent.setSendsmtpStatus(0);
		emailContent.setTitle(bean.getTitle());
		emailContent.setUserId(bean.getUserId());
		emailContent.setSendId(bean.getSendId());
		emailContent.setSender(bean.getSender());
		if (StringUtils.isNotBlank(bean.getAccept())) {
			emailContent.setAddress(bean.getAccept());
		} else {
			emailContent.setAddress(StringUtil.join(bean.getAccepts(), ","));
		}
		emailContent.setContent(bean.getContent());
		emailContent.setTenantId(bean.getTenantId());
		emailContent.setFromName(bean.getFromName());
		emailContent.setTempCode(bean.getCode() == null ? 0 : bean.getCode());
		emailContent.setCarbonCopy(StringUtil.join(bean.getCarbonCopy(), ","));
		emailContent.setSendType(InformEnum.SendWayTimeEnum.FORTHWITH.getTypeCode());
		emailContent.setSendStatus(InformEnum.SendStatusEnum.NO_SEND.getTypeCode());
		emailContent.setBlindCarbonCopy(StringUtil.join(bean.getBlindCarbonCopy(), ","));
		if (CollectionUtil.isNotEmpty(bean.getAttachmentUrls())) {
			emailContent.setHasAttach(1);
			emailContent.setAttachUris(StringUtil.join(bean.getAttachmentUrls(), ";"));
		}
		if (isMsgSending) {
			String contentNoMask = bean.getContent().replace("<data_mask>", "").replace("</data_mask>", "");
			//R<List<String>> resp = MsgEmailUtil.sendCloudEmail(bean.getAccept(), bean.getCarbonCopy(), bean.getBlindCarbonCopy(), bean.getSender(), bean.getFromName(), bean.getTitle(), bean.getAttachmentUrls(), null, contentNoMask);
			R<String> resp = null;
			//如果发送邮件单个接收列表不空则直接发送，否则发送批量
			if (StringUtils.isNotBlank(bean.getAccept())) {
				resp =
					SmtpUtil.sendSmtpEmail(bean.getMsgId(), smtpHost, smtpPort, userName, password,
						sender, sender, bean.getAccept(), bean.getTitle(), contentNoMask, contentType,
						emailContent.getCarbonCopy(), emailContent.getBlindCarbonCopy(), emailContent.getAttachUris());
			} else {
				log.info("sendCloudEmail 批量发送收件人列表:{}", JsonUtil.toJson(bean.getAccepts()));
				resp =
					SmtpUtil.sendSmtpEmail(bean.getMsgId(), smtpHost, smtpPort, userName, password,
						sender, sender, bean.getAccepts(), bean.getTitle(), contentNoMask, contentType,
						emailContent.getCarbonCopy(), emailContent.getBlindCarbonCopy(), emailContent.getAttachUris());
			}

			log.info("sendCloudEmail resp:{}, msgId:{}", JsonUtil.toJson(resp), bean.getMsgId());
			if (resp.getCode() != ResultCode.SUCCESS.getCode()) {
				log.error("pushEmailMsg fail msgId:{}", bean.getMsgId());
				emailContent.setSendStatus(InformEnum.SendStatusEnum.FAIL_SEND.getTypeCode());
				emailContent.setErrorMsg(resp.getMsg());
			} else {
				emailContent.setEmailId(resp.getData());
				emailContent.setSendStatus(InformEnum.SendStatusEnum.SEND_ING.getTypeCode());
				emailContent.setDeliveryTime(new Date());
			}
		} else {
			log.info("**********pushEmailMsg平台消息发送通道已经关闭!**********");
		}


		String address = emailContent.getAddress();
		if (address != null && address.contains(",")) {
			// 有多个地址
			String[] addresses = address.split(",");
			for (String addr : addresses) {
				emailContent.setAddress(addr);
				PlatformEmailContentEntity platformEmailContentEntity = new PlatformEmailContentEntity();
				BeanUtil.copyProperties(emailContent, platformEmailContentEntity);
				platformEmailContentMapper.insert(platformEmailContentEntity);
			}
		} else {
			platformEmailContentMapper.insert(emailContent);
		}
	}


}
