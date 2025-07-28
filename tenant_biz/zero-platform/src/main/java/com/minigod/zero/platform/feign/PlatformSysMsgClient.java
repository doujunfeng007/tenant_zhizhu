package com.minigod.zero.platform.feign;

import com.minigod.zero.platform.feign.IPlatformSysMsgClient;
import com.minigod.zero.biz.common.constant.CommonConstant;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tenant.annotation.NonDS;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.minigod.zero.platform.constants.Constants;
import com.minigod.zero.platform.entity.PlatformCommonTemplateEntity;
import com.minigod.zero.platform.entity.PlatformSysMsgEntity;
import com.minigod.zero.platform.enums.InformEnum;
import com.minigod.zero.platform.feign.IPlatformSysMsgClient;
import com.minigod.zero.platform.service.IPlatformCommonTemplateService;
import com.minigod.zero.platform.service.IPlatformSysMsgService;
import com.minigod.zero.platform.utils.PlatformUtil;
import com.minigod.zero.platform.utils.SequenceService;
import com.minigod.zero.platform.vo.PlatformSysMsgVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@NonDS
@RestController
@RequiredArgsConstructor
@Slf4j
public class PlatformSysMsgClient implements IPlatformSysMsgClient {

	@Resource
	private IPlatformSysMsgService platformSysMsgService;
	@Resource
	private SequenceService sequenceService;
	@Resource
	private IPlatformCommonTemplateService platformCommonTemplateService;

	@Override
	public List<PlatformSysMsgEntity> findInformSysMsg(Long lngVersion, Integer count, Long userId) {
		Integer clientType = PlatformUtil.getClientType();
		return platformSysMsgService.findInformSysMsg(lngVersion, count, userId, clientType);
	}

	@Override
	public R pushUnsendSysMsg() {
		platformSysMsgService.pushUnsendSysMsg();
		return R.success();
	}

	@Override
	public Boolean saveSysMsg(PlatformSysMsgVO entity) {
		Integer tempCode = entity.getTempCode();
		PlatformCommonTemplateEntity platformCommonTemplateEntity = platformCommonTemplateService.selectByTempCode(tempCode);
		if (platformCommonTemplateEntity == null){
			return Boolean.FALSE;
		}

		String lang = entity.getLang();
		List<String> params = entity.getParams();
		if (lang.equals(CommonConstant.ZH_CN)){
			entity.setTitle(platformCommonTemplateEntity.getTitle());
			entity.setContent(platformCommonTemplateEntity.getContent());
			if (CollectionUtils.isNotEmpty(params)){
				entity.setContent(PlatformUtil.getMsgStr(platformCommonTemplateEntity.getContent(), params));
			}
		}
		if (lang.equals(CommonConstant.ZH_HK)){
			entity.setTitle(platformCommonTemplateEntity.getTitleHant());
			entity.setContent(platformCommonTemplateEntity.getContentHant());
			if (CollectionUtils.isNotEmpty(params)){
				entity.setContent(PlatformUtil.getMsgStr(platformCommonTemplateEntity.getContentHant(), params));
			}
		}
		if (lang.equals(CommonConstant.EN_US)){
			entity.setTitle(platformCommonTemplateEntity.getTitleEn());
			entity.setContent(platformCommonTemplateEntity.getContentEn());
			if (CollectionUtils.isNotEmpty(params)){
				entity.setContent(PlatformUtil.getMsgStr(platformCommonTemplateEntity.getContentEn(), params));
			}
		}

		long updateVersion = sequenceService.nextId(Constants.SysMsgContants.SYS_MSG_VERSION);
		entity.setUpdVersion(updateVersion);

		if (entity.getClientType() == null) {
			entity.setClientType(Constants.SysMsgContants.SYS_MSG_CLIENT_TYPE_ALL);
		}
		if (StringUtil.isBlank(entity.getMsgGroup())) {
			entity.setMsgGroup(Constants.SysMsgContants.SYS_MSG_GROUP_PERNSON);
		}
		if (entity.getMsgGroup().equals(Constants.SysMsgContants.SYS_MSG_GROUP_ALL)){
			entity.setTargetId(0L);
		}
		if (entity.getSendType() == null) {
			entity.setSendType(Constants.SysMsgContants.SYS_MSG_PUSH_STRONG);
		}
		if (entity.getSendWay() == null) {
			entity.setSendWay(Constants.SysMsgContants.SYS_MSG_SENDWAY_NOW);
		}
		if (entity.getPopFlag() == null) {
			entity.setPopFlag(Constants.SysMsgContants.PROP_FLAG_NO);
		}

		entity.setSendStatus(InformEnum.SendStatusEnum.SUCCESS_SEND.getTypeCode());//发送状态更新为已发送
		entity.setSendTime(new Date());
		entity.setStatus(1);
		entity.setIsDeleted(0);
		Date now = new Date();
		entity.setCreateTime(now);
		entity.setUpdateTime(now);
		entity.setCreateUser(AuthUtil.getUserId());
		entity.setUpdateUser(AuthUtil.getUserId());

		PlatformSysMsgEntity platformSysMsgEntity = BeanUtil.copy(entity, PlatformSysMsgEntity.class);
		return platformSysMsgService.save(platformSysMsgEntity);
	}
}
