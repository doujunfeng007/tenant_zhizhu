package com.minigod.zero.platform.service.impl;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.sendcloud.sdk.util.ResponseData;
import com.minigod.zero.core.mp.base.BaseEntity;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.CollectionUtil;
import com.minigod.zero.core.tool.utils.DateUtil;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.minigod.zero.platform.entity.PlatformEmailContentEntity;
import com.minigod.zero.platform.enums.InformEnum;
import com.minigod.zero.platform.service.IHkSmtpSendService;
import com.minigod.zero.platform.service.IPlatformEmailContentService;
import com.minigod.zero.platform.utils.SmtpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author Zhe.Xiao
 */
@Service
@Slf4j
public class HkSmtpSendServiceImpl implements IHkSmtpSendService {

	@Value("${email.smtp.sendTitleLike.monthlyStatement:zhizhu Securities Limited - Monthly Statement}")
	private String monthlyStatementTitle;
	@Value("${email.smtp.sendTitleLike.dailyStatement:zhizhu Securities Limited - Daily Statement}")
	private String dailyStatementTitle;
	@Value("${email.smtp.host:smtp.minigod.com}")
	private String smtpHost;
	@Value("${email.smtp.port:25}")
	private String smtpPort;
	@Value("${email.smtp.monthlySendDate:5}")
	private Integer monthlySendDate;
	@Value("${email.sendcloud.emailStatusUrl:https://api.sendcloud.net/apiv2/data/emailStatus}")
	private String emailStatusUrl;
	@Value("${email.sendcloud.apiUser:zhizhu_app2}")
	private String apiUser;
	@Value("${email.sendcloud.apiKey:f325ce7b2db6c48e2d2974a41bfb97d1}")
	private String apiKey;
	@Resource
	private IPlatformEmailContentService platformEmailContentService;

	private final String DAILY = "daily";
	private final String MONTHLY = "monthly";

	/**
	 * 结单邮件发送香港SMTP服务器
	 * @param type daily/monthly
	 */
	@Async("asyncExecutor")
	@Override
	public void statementSendHkSmtp(String type) throws Exception {

		String dateStart = null;
		String titleLike = null;

		if (type.equals(DAILY)) {
			//日结单：如果是晚上执行，则查询当天的，如果是凌晨执行，则查询昨天的（去掉发送香港SMTP已成功或已失败的）
			GregorianCalendar ca = new GregorianCalendar();
			if (ca.get(GregorianCalendar.AM_PM) == 0) {
				//AM
				dateStart = DateFormatUtils.format(DateUtil.minusDays(new Date(), 1), "yyyy-MM-dd 00:00:00");
			} else {
				//PM
				dateStart = DateFormatUtils.format(new Date(), "yyyy-MM-dd 00:00:00");
			}
			titleLike = dailyStatementTitle;
		} else if (type.equals(MONTHLY)) {
			//月结单：如果当前日期是5号，则查询当天的，如果是当前日期是6号，则查询昨天的（去掉发送香港SMTP已成功或已失败的）
			int dayOfMonth = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
			if (dayOfMonth == monthlySendDate + 1) {
				dateStart = DateFormatUtils.format(DateUtil.minusDays(new Date(), 1), "yyyy-MM-dd 00:00:00");
			} else {
				dateStart = DateFormatUtils.format(new Date(), "yyyy-MM-dd 00:00:00");
			}
			titleLike = monthlyStatementTitle;
		} else {
			log.error("{}StatementSendHkSmtp unknown type", type);
			return;
		}

		List<PlatformEmailContentEntity> emailContents = new LambdaQueryChainWrapper<>(platformEmailContentService.getBaseMapper())
			.ge(BaseEntity::getCreateTime, dateStart)
			.likeRight(PlatformEmailContentEntity::getTitle, titleLike)
			.and(wrapper -> wrapper.eq(PlatformEmailContentEntity::getSendsmtpStatus, 0).or().isNull(PlatformEmailContentEntity::getSendsmtpStatus))
			.list();

		//过滤出需要发送香港SMTP的（发送sendcloud失败或者发送成功但退信）
		List<PlatformEmailContentEntity> sendSmtpList = new ArrayList<>();

		for (PlatformEmailContentEntity e : emailContents) {
			if (e.getSendStatus() == null) {
				log.error("{}StatementSendHkSmtp sendStatus为null, id:{}", type, e.getEmailId());
				continue;
			}
			if (e.getSendStatus() == InformEnum.SendStatusEnum.FAIL_SEND.getTypeCode()) {
				sendSmtpList.add(e);
			} else if (e.getSendStatus() == InformEnum.SendStatusEnum.SUCCESS_SEND.getTypeCode()) {
				String emailId = e.getEmailId();
				if (StringUtil.isBlank(emailId)) {
					log.error("{}StatementSendHkSmtp 邮件未记录emailId, id:{}", type, e.getId());
					continue;
				}
				String status = sendCloudEmailStatus(emailId, 3);
				if (StringUtil.isNotBlank(status)) {
					PlatformEmailContentEntity update = new PlatformEmailContentEntity();
					update.setId(e.getId());
					update.setSendcloudStatus(status);
					update.setUpdateTime(new Date());
					platformEmailContentService.getBaseMapper().updateById(update);
					if (!status.equals("送达")) {
						sendSmtpList.add(e);
					}
				} else {
					log.error("{}StatementSendHkSmtp 查询sendCloudEmailStatus为空, emailId:{}", type, e.getEmailId());
				}
			}

			if (sendSmtpList.size() == 50) {
				break;
			}
		}


		log.info("{}StatementSendHkSmtp 共{}封", type, sendSmtpList.size());
		String contentType = "text/html;charset=utf-8";
		int successNum = 0;
		int failNum = 0;
		for (PlatformEmailContentEntity entity : sendSmtpList) {
			String cc = entity.getCarbonCopy().replace(";", ",");
			String bcc = entity.getBlindCarbonCopy().replace(";", ",");
			R r = SmtpUtil.sendSmtpEmail(entity.getEmailId(), smtpHost, smtpPort, "", "", entity.getSender(), entity.getFromName(),
				entity.getAddress(), entity.getTitle(), entity.getContent(), contentType, cc, bcc, entity.getAttachUris());
			PlatformEmailContentEntity update = new PlatformEmailContentEntity();
			if (r.isSuccess()) {
				update.setSendsmtpStatus(1);
				successNum++;
			} else {
				update.setSendsmtpStatus(2);
				failNum++;
			}
			update.setId(entity.getId());
			update.setUpdateTime(new Date());
			platformEmailContentService.updateById(update);
			Thread.sleep(10000);
		}
		log.info("{}StatementSendHkSmtp 成功{}封/失败{}封", type, successNum, failNum);
	}

	/*public static void main(String[] args) {
		String emailId = "1733391518114_221258_15787_8226.sc-10_9_12_206-inbound0$wudan989@outlook.com";
		String apiUser = "digifingroup";
		String apiKey = "2cbe8f36c7d07588b95568ad2b262e93";
		String url = "https://api.sendcloud.net/apiv2/data/emailStatus "+ "?days=1&emailIds=" + emailId + "&apiUser=" + apiUser + "&apiKey=" + apiKey;
		//String url = "https://api.sendcloud.net/apiv2/data/emailStatus "+ "?days=1&email=" + "finance@lionfin.com" + "&apiUser=" + apiUser + "&apiKey=" + apiKey;
		log.info("sendCloudEmailStatus url:{}", url);
		String result = HttpUtil.get(url);
		System.out.println(result);
	}*/
	public String sendCloudEmailStatus(String emailId, int days) {
		String url = emailStatusUrl + "?days=" + days + "&emailIds=" + emailId + "&apiUser=" + apiUser + "&apiKey=" + apiKey;
		log.info("sendCloudEmailStatus url:{}", url);
		String result = HttpUtil.get(url);
		log.info("sendCloudEmailStatus result:{}", result);

		if (StringUtil.isBlank(result)) {
			log.error("sendCloudEmailStatus result is blank, emailId:{}", emailId);
			return null;
		}
		ResponseData responseData = JSON.parseObject(result, ResponseData.class);
		if (responseData == null) {
			log.error("sendCloudEmailStatus responseData is null, emailId:{}", emailId);
			return null;
		}
		String info = responseData.getInfo();
		JSONObject infoObj = JSON.parseObject(info);
		if (infoObj == null) {
			log.error("sendCloudEmailStatus info is null, emailId:{}", emailId);
			return null;
		}
		JSONArray voList = infoObj.getJSONArray("voList");
		if (CollectionUtil.isEmpty(voList)) {
			log.error("sendCloudEmailStatus voList is empty, emailId:{}", emailId);
			return null;
		}
		JSONObject jsonObject = voList.getJSONObject(0);
		if (jsonObject == null) {
			return null;
		}
		return jsonObject.getString("status");
	}
}
