package com.minigod.zero.platform.utils;

import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.exception.ZeroException;
import com.minigod.zero.core.tool.utils.CollectionUtil;
import com.minigod.zero.core.tool.utils.SpringUtil;
import com.minigod.zero.core.tool.utils.WebUtil;
import com.minigod.zero.platform.dto.SendNotifyDTO;
import com.minigod.zero.platform.enums.LanguageType;
import com.minigod.zero.platform.feign.IPlatformMsgClient;
import com.minigod.zero.platform.vo.CustomerDeviceInfoVO;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/5/21 19:18
 * @description：
 */
public class PushUtil {

	public static IPlatformMsgClient platformMsgClient;


	public static IPlatformMsgClient getPushClient() {
		if (platformMsgClient == null) {
			platformMsgClient = SpringUtil.getBean(IPlatformMsgClient.class);
		}
		return platformMsgClient;
	}

	public static ThreadPoolExecutor threadPoolExecutor() {
		return SpringUtil.getBean(ThreadPoolExecutor.class);
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private SendNotifyDTO sendNotify = new SendNotifyDTO();

		public Builder custId(Long custId) {
			sendNotify.setLstToUserId(Arrays.asList(custId));
			return this;
		}

		public Builder custId(List<Long> custIds) {
			sendNotify.setLstToUserId(custIds);
			return this;
		}

		public Builder group(Integer group) {
			sendNotify.setDisplayGroup(group);
			return this;
		}

		public Builder templateCode(Integer templateCode) {
			if (StringUtils.isEmpty(sendNotify.getLang())) {
				sendNotify.setLang(WebUtil.getLanguage() == null ? LanguageType.ZH_HANS.getCode() : WebUtil.getLanguage());
			}
			sendNotify.setTemplateCode(templateCode);
			return this;
		}

		public Builder title(String title) {
			sendNotify.setTitle(title);
			return this;
		}

		public Builder msgContent(String msgContent) {
			sendNotify.setMsgContent(msgContent);
			return this;
		}

		public Builder sendWay(Integer sendWay) {
			sendNotify.setSendWay(sendWay);
			return this;
		}

		public Builder msgGroup(String msgGroup) {
			sendNotify.setMsgGroup(msgGroup);
			return this;
		}

		public Builder params(List<String> params) {
			sendNotify.setParams(params);
			return this;
		}

		public Builder param(String param) {
			if (this.sendNotify.getParams() == null) {
				this.sendNotify.setParams(new ArrayList<>());
			}
			this.sendNotify.getParams().add(param);
			return this;
		}

		public Builder fromUserId(Long fromUserId) {
			sendNotify.setFromUserId(fromUserId);
			return this;
		}

		public Builder linkUrl(String linkUrl) {
			sendNotify.setUrl(linkUrl);
			return this;
		}

		public Builder sendTime(Date sendTime) {
			sendNotify.setSendTime(sendTime);
			return this;
		}

		public Builder sendTimeStr(String sendTimeStr) {
			sendNotify.setSendTimeStr(sendTimeStr);
			return this;
		}

		public Builder linkUrlParams(List<String> linkUrlParams) {
			sendNotify.setUrlParams(linkUrlParams);
			return this;
		}

		public Builder deviceInfoList(List<CustomerDeviceInfoVO> deviceInfoList) {
			sendNotify.setDeviceInfoList(deviceInfoList);
			return this;
		}

		public Builder pushType(Integer pushType) {
			sendNotify.setPushType(pushType);
			return this;
		}

		public Builder lang(String lang) {
			sendNotify.setLang(lang);
			return this;
		}

		public Builder tenantId(String tenantId) {
			sendNotify.setTenantId(tenantId);
			return this;
		}

		public void pushAsync() {
			threadPoolExecutor().execute(() -> {
				pushSync();
			});
		}

		public R pushSync() {
			paramCheck(sendNotify);
			return getPushClient().sendNotify(sendNotify);
		}
	}

	private static void paramCheck(SendNotifyDTO sendNotify) {
		if (sendNotify.getTemplateCode() == null && !StringUtils.isEmpty(sendNotify.getLang())) {
			throw new ZeroException("推动失败，推送模板不能为空");
		}
		if (CollectionUtil.isEmpty(sendNotify.getDeviceInfoList())
			&& CollectionUtil.isEmpty(sendNotify.getLstToUserId())) {
			throw new ZeroException("推动失败，推送用户不能为空");
		}
		if (sendNotify.getTemplateCode() == null
			&& StringUtils.isEmpty(sendNotify.getLang())
			&& StringUtils.isEmpty(sendNotify.getMsgContent())) {
			throw new ZeroException("推动失败，推送内容不能为空");
		}
	}

}
