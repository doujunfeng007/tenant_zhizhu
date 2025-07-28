package com.minigod.zero.resource.service.impl;

import com.minigod.zero.resource.dto.JPushDTO;
import com.minigod.zero.resource.enums.OsTypeEnum;
import com.minigod.zero.resource.handler.JPushHandler;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.CollectionUtil;
import com.minigod.zero.core.tool.utils.StringPool;
import com.minigod.zero.resource.service.IJpushService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;

/**
 * 极光推送服务类实现
 */
@Service
@Slf4j
public class JpushServiceImpl implements IJpushService {

	@Override
	public R saveAndPushMsgToList(JPushDTO jPushDTO) {
		if (StringUtils.isBlank(jPushDTO.getAuthorization())) {
			return R.fail();
		}
		// 组装请求的参数为对象
		PushToList pushToList = new PushToList();
		pushToList.setLstTokenId(jPushDTO.getLstTokenId());
		pushToList.setTitle(jPushDTO.getTitle());
		pushToList.setTransMessage(jPushDTO.getTransMessage());
		pushToList.setOsType(jPushDTO.getOsType());
		pushToList.setPushType(jPushDTO.getPushType());
		pushToList.setOfflinePush(jPushDTO.getOfflinePush());
		if (CollectionUtil.isEmpty(jPushDTO.getLstTokenId())) {
			return R.fail("未接收到设备号");
		}
		if (!OsTypeEnum.containOsType(jPushDTO.getOsType())) {
			return R.fail("未知的设备类型");
		}

		String appKey = null;
		String masterSecret = null;
		try {
			String decodeStr = new String(Base64.getDecoder().decode(jPushDTO.getAuthorization()));
			String[] temp = decodeStr.split(StringPool.COLON);
			appKey = temp[0];
			masterSecret = temp[1];
		} catch (Exception e) {
			log.error("saveAndPushMsgToList decode authorization fail:{}", e.getMessage(), e);
			return R.fail("decode authorization fail");
		}

		return JPushHandler.syncPushMsgToListSend(masterSecret, appKey, jPushDTO.getApnsTest(), pushToList);
	}

	@Override
	public R saveAndPushMsgToApp(JPushDTO jPushDTO) {
		if (StringUtils.isBlank(jPushDTO.getAuthorization())) {
			return R.fail();
		}
		// 组装请求的参数为对象
		PushToApp pushToApp = new PushToApp();
		pushToApp.setTitle(jPushDTO.getTitle());
		pushToApp.setTransMessage(jPushDTO.getTransMessage());
		pushToApp.setOsType(jPushDTO.getOsType());
		pushToApp.setPushType(jPushDTO.getPushType());
		pushToApp.setOfflinePush(jPushDTO.getOfflinePush());
		if (!OsTypeEnum.containOsType(jPushDTO.getOsType())) {
			return R.fail("未知的设备类型");
		}

		String appKey = null;
		String masterSecret = null;
		try {
			String decodeStr = new String(Base64.getDecoder().decode(jPushDTO.getAuthorization()));
			String[] temp = decodeStr.split(StringPool.COLON);
			appKey = temp[0];
			masterSecret = temp[1];
		} catch (Exception e) {
			log.error("saveAndPushMsgToList decode authorization fail:{}", e.getMessage(), e);
			return R.fail("decode authorization fail");
		}

		return JPushHandler.syncPushMsgToAppSend(masterSecret, appKey, jPushDTO.getApnsTest(), pushToApp);
	}

	// 推送给单个用户
	@Data
	public static class PushToSingle {
		private Integer userId;
		private String tokenId;
		private String title;
		private String contentIos;
		private String moduleIos;
		private String transMessage;
		private int osType;
		private int pushType;
		private boolean isOfflinePush;
	}

	// 推送给用户列表
	@Data
	public static class PushToList {
		private List<String> lstTokenId;
		private String title;
		private String transMessage;
		private int osType;
		private int pushType;
		private boolean isOfflinePush;
	}

	// 推送给APP下所有用户
	@Data
	public static class PushToApp {
		private String title;
		private String transMessage;
		private int osType;
		private int pushType;
		private boolean isOfflinePush;
	}
}
