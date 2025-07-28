package com.minigod.zero.platform.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.minigod.zero.biz.common.constant.CommonConstant;
import com.minigod.zero.platform.mapper.PlatformInvestMsgMapper;
import com.minigod.zero.platform.service.IPlatformPushService;
import com.minigod.zero.platform.service.PushNotifyMsgService;
import com.minigod.zero.core.mp.base.BaseEntity;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.minigod.zero.cust.entity.CustDeviceEntity;
import com.minigod.zero.cust.entity.CustInfoEntity;
import com.minigod.zero.cust.feign.ICustDeviceClient;
import com.minigod.zero.cust.feign.ICustInfoClient;
import com.minigod.zero.platform.common.NotifyMsg;
import com.minigod.zero.platform.constants.Constants;
import com.minigod.zero.platform.entity.PlatformInvestMsgEntity;
import com.minigod.zero.platform.enums.InformEnum;
import com.minigod.zero.platform.enums.MsgStaticType;
import com.minigod.zero.platform.enums.PlatformOsTypeEnum;
import com.minigod.zero.platform.service.IPlatformInvestMsgService;
import com.minigod.zero.platform.utils.SequenceService;
import com.minigod.zero.platform.vo.ShowMsg;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.minigod.zero.platform.service.impl.PlatformPushServiceImpl.VERSION_PREFIX;

/**
 * 推送记录表 服务实现类
 *
 * @author 掌上智珠
 * @since 2023-03-11
 */
@Service
@Slf4j
public class PlatformInvestMsgServiceImpl extends BaseServiceImpl<PlatformInvestMsgMapper, PlatformInvestMsgEntity> implements IPlatformInvestMsgService {

	@Resource
	private ICustInfoClient custInfoClient;
	@Resource
	private PushNotifyMsgService pushNotifyMsgService;
	@Resource
	private SequenceService sequenceService;
	@Resource
	private IPlatformPushService platformPushService;
	@Resource
	private ICustDeviceClient custDeviceClient;

	@Override
	public List<ShowMsg> findInvestMsg2(Long userId, Integer[] messageGroups, Integer msgId, Integer page, Integer count) {
		if (count == null) {
			count = Integer.MAX_VALUE;
		}

		Long[] arrayUserId = {userId, Constants.USERID_ALL_USER};
		List<PlatformInvestMsgEntity> list = new LambdaQueryChainWrapper<>(baseMapper)
			.in(PlatformInvestMsgEntity::getUserId, arrayUserId)
			.in(PlatformInvestMsgEntity::getDisplayGroup, messageGroups)
			.eq(PlatformInvestMsgEntity::getStatus, 1)
			.eq(PlatformInvestMsgEntity::getIsDeleted, 0)
			.eq(PlatformInvestMsgEntity::getSendStatus, InformEnum.SendStatusEnum.SUCCESS_SEND.getTypeCode())
			.orderByDesc(PlatformInvestMsgEntity::getCreateTime)
			.last("limit " + (page - 1) * count + "," + count)
			.list();

		List<ShowMsg> resultList = list.stream().map(e -> {
			ShowMsg showMsg = new ShowMsg();
			BeanUtil.copyProperties(e, showMsg);
			return showMsg;
		}).collect(Collectors.toList());

		if (CollectionUtils.isNotEmpty(resultList)) {
			for (ShowMsg bean : resultList) {
				if (bean.getFromUser() == Constants.USERID_ALL_USER) {//全站用户
					bean.setUserIcon("");
					bean.setNickName("系统");
					bean.setUserType(1);
				} else {//个人用户查询关联表
					//查询cust_info表
					CustInfoEntity custInfo = custInfoClient.userInfoByUserId(bean.getFromUser().longValue());

					if (custInfo == null) {
						log.error("findInvestMsg 查无此用户:fromuserid=" + bean.getFromUser());
						continue;
					}
					bean.setUserIcon(custInfo.getCustIcon());
					bean.setNickName(custInfo.getNickName());
					bean.setUserType(custInfo.getCustType() != null ? Integer.valueOf(custInfo.getCustType()) : null);
				}
			}
		}
		return resultList;
	}

	@Override
	public List<ShowMsg> findInvestMsg(Long userId, Integer clientType, Integer messageGroup, Long locateVersion, Integer page, Integer count) {
		count = (count == null) ? Integer.MAX_VALUE : count;

		Long[] arrayUserId = {userId, Constants.USERID_ALL_USER};
		LambdaQueryWrapper<PlatformInvestMsgEntity> wrapper = new QueryWrapper<PlatformInvestMsgEntity>().lambda();
		wrapper.in(PlatformInvestMsgEntity::getUserId, arrayUserId)
			.eq(PlatformInvestMsgEntity::getDisplayGroup, messageGroup)
			.eq(PlatformInvestMsgEntity::getStatus, 1)
			.eq(PlatformInvestMsgEntity::getIsDeleted, 0)
			.eq(PlatformInvestMsgEntity::getSendStatus, InformEnum.SendStatusEnum.SUCCESS_SEND.getTypeCode());

		// 设置客户端类型条件
		setClientTypeCondition(wrapper, clientType);

		wrapper.orderByDesc(PlatformInvestMsgEntity::getUpdVersion);
		setPagination(wrapper, page, count);

		List<PlatformInvestMsgEntity> list = baseMapper.selectList(wrapper);
		List<ShowMsg> resultList = convertToShowMsgList(list);

		// 填充用户信息
		populateUserInfo(resultList);

		return resultList;
	}

	private void setClientTypeCondition(LambdaQueryWrapper<PlatformInvestMsgEntity> wrapper, Integer clientType) {
		Integer[] arrayClientType;
		if (clientType == PlatformOsTypeEnum.OS_ALL.getTypeValue()) {
			arrayClientType = new Integer[]{
				PlatformOsTypeEnum.OS_ALL.getTypeValue(),
				PlatformOsTypeEnum.OS_ANDROID.getTypeValue(),
				PlatformOsTypeEnum.OS_IOS.getTypeValue()
			};
		} else {
			arrayClientType = new Integer[]{
				PlatformOsTypeEnum.OS_ALL.getTypeValue(),
				clientType
			};
		}
		wrapper.in(PlatformInvestMsgEntity::getClientType, arrayClientType);
	}

	private void setPagination(LambdaQueryWrapper<PlatformInvestMsgEntity> wrapper, Integer page, Integer count) {
		if (page != null && count != null) {
			wrapper.last("limit " + (page - 1) * count + "," + count);
		}
	}

	private List<ShowMsg> convertToShowMsgList(List<PlatformInvestMsgEntity> list) {
		return list.stream()
			.map(e -> {
				ShowMsg showMsg = new ShowMsg();
				BeanUtil.copyProperties(e, showMsg);
				return showMsg;
			})
			.collect(Collectors.toList());
	}

	private void populateUserInfo(List<ShowMsg> resultList) {
		if (CollectionUtils.isNotEmpty(resultList)) {
			for (ShowMsg bean : resultList) {
				if (bean.getFromUser() == Constants.USERID_ALL_USER) { // 全站用户
					bean.setUserIcon("");
					bean.setNickName("系统");
					bean.setUserType(1); // TODO 授权人是否需要推送
				} else { // 个人用户查询关联表
					CustInfoEntity custInfo = custInfoClient.userInfoByUserId(bean.getFromUser().longValue());
					if (custInfo == null) {
						log.error("findInvestMsg 查无此用户:fromuserid=" + bean.getFromUser());
						continue;
					}
					bean.setUserIcon(custInfo.getCustIcon());
					bean.setNickName(custInfo.getNickName());
					bean.setUserType(custInfo.getCustType() != null ? Integer.valueOf(custInfo.getCustType()) : null);
				}
			}
		}
	}

	@Override
	public PlatformInvestMsgEntity findLastInvestMsg(Long userId, Integer messageGroup, Integer clientType) {
		Long[] arrayUserId = {userId, Constants.USERID_ALL_USER};
		LambdaQueryWrapper<PlatformInvestMsgEntity> wrapper = new QueryWrapper<PlatformInvestMsgEntity>().lambda();
		wrapper.in(PlatformInvestMsgEntity::getUserId, arrayUserId)
			.eq(PlatformInvestMsgEntity::getDisplayGroup, messageGroup)
			.eq(PlatformInvestMsgEntity::getStatus, 1)
			.eq(PlatformInvestMsgEntity::getIsDeleted, 0)
			.eq(PlatformInvestMsgEntity::getSendStatus, InformEnum.SendStatusEnum.SUCCESS_SEND.getTypeCode());

		if (clientType == PlatformOsTypeEnum.OS_ALL.getTypeValue()) {
			Integer[] arrayClientType = {PlatformOsTypeEnum.OS_ALL.getTypeValue(), PlatformOsTypeEnum.OS_ANDROID.getTypeValue(), PlatformOsTypeEnum.OS_IOS.getTypeValue()};
			wrapper.in(PlatformInvestMsgEntity::getClientType, arrayClientType);
		} else {
			Integer[] arrayClientType = {PlatformOsTypeEnum.OS_ALL.getTypeValue(), clientType};
			wrapper.in(PlatformInvestMsgEntity::getClientType, arrayClientType);
		}

		wrapper.orderByDesc(PlatformInvestMsgEntity::getUpdVersion)
			.last("limit 1");
		return baseMapper.selectOne(wrapper);
	}

	@Override
	public PlatformInvestMsgEntity findInvestMsgByMsgId(Long msgId) {
		return baseMapper.selectById(msgId);
	}

	@Override
	public PlatformInvestMsgEntity findInvestMsgForLastOne(Long userId, Integer msgCode) {
		LambdaQueryWrapper<PlatformInvestMsgEntity> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(PlatformInvestMsgEntity::getUserId, userId);
		queryWrapper.eq(PlatformInvestMsgEntity::getMsgGroup, msgCode);
		queryWrapper.orderByDesc(PlatformInvestMsgEntity::getUpdVersion);
		queryWrapper.last("limit 1");
		return baseMapper.selectOne(queryWrapper);
	}

	@Override
	public List<PlatformInvestMsgEntity> findAllUnsendInvestMsg(Date date) {
		LambdaQueryWrapper<PlatformInvestMsgEntity> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(PlatformInvestMsgEntity::getSendStatus, InformEnum.SendStatusEnum.NO_SEND.getTypeCode())
			.eq(PlatformInvestMsgEntity::getSendWay, InformEnum.SendWayTimeEnum.TIMING.getTypeCode())
			.notIn(PlatformInvestMsgEntity::getDisplayGroup, MsgStaticType.DisplayGroup.SYSTEM_MSG)
			.le(PlatformInvestMsgEntity::getSendTime, date)
			.eq(BaseEntity::getStatus, 1)
			.eq(BaseEntity::getIsDeleted, 0);
		return baseMapper.selectList(queryWrapper);
	}

	@Override
	@Async("asyncExecutor")
	public void pushUnsendInvestMsg() {
		try {
			List<PlatformInvestMsgEntity> unsendMsgs = findAllUnsendInvestMsg(new Date());
			for (PlatformInvestMsgEntity unsendMsg : unsendMsgs) {
				log.info("pushUnsendInvestMsg:{}", JSON.toJSONString(unsendMsg));
				pushInvestMsg(unsendMsg);
			}
		} catch (Exception e) {
			log.error("发送定时消息异常", e);
		}
	}

	private void pushInvestMsg(PlatformInvestMsgEntity unsendMsg) {
		boolean isAll = unsendMsg.getMsgGroup() != null && unsendMsg.getMsgGroup().equals(InformEnum.MsgGroupEnum.ALL.getTypeCode());

		NotifyMsg nm = new NotifyMsg();
		nm.setMsgId(IdUtil.getSnowflakeNextIdStr());
		nm.setSendWay(InformEnum.SendWayTimeEnum.TIMING.getTypeCode());
		nm.setDevType(unsendMsg.getClientType());
		nm.setMsgGroup(unsendMsg.getMsgGroup());
		nm.setDisplayGroup(unsendMsg.getDisplayGroup());
		nm.setUrl(unsendMsg.getUrl());
		if (isAll) {
			//TODO:全站推送根据多语言标签推
			nm.setTitle(unsendMsg.getTitle());
			nm.setMsg(unsendMsg.getMsgContent());
		} else {
			//个人发送是多条记录中的一条
			if (null == unsendMsg.getUserId()) {
				log.info("investMsg id:{} 个人发送userId为空", unsendMsg.getId());
				return;
			}
			CustDeviceEntity custDevice = custDeviceClient.getCustDevice(unsendMsg.getUserId());
			if (custDevice != null) {
				String lang = custDevice.getLang();
				if (lang.equals(CommonConstant.ZH_CN)) {
					nm.setTitle(unsendMsg.getTitle());
					nm.setMsg(unsendMsg.getMsgContent());
				} else if (lang.equals(CommonConstant.EN_US)) {
					nm.setTitle(StringUtil.isNotBlank(unsendMsg.getTitleEn()) ? unsendMsg.getTitleEn() : unsendMsg.getTitle());
					nm.setMsg(StringUtil.isNotBlank(unsendMsg.getMsgContentEn()) ? unsendMsg.getMsgContentEn() : unsendMsg.getMsgContent());
				} else {
					nm.setTitle(StringUtil.isNotBlank(unsendMsg.getTitleHant()) ? unsendMsg.getTitleHant() : unsendMsg.getTitle());
					nm.setMsg(StringUtil.isNotBlank(unsendMsg.getMsgContentHant()) ? unsendMsg.getMsgContentHant() : unsendMsg.getMsgContent());
				}
			}
			nm.setUserIds(Arrays.asList(unsendMsg.getUserId()));
		}

		//发送
		pushNotifyMsgService.pushNotifyMsg(nm);

		PlatformInvestMsgEntity update = new PlatformInvestMsgEntity();
		update.setId(unsendMsg.getId());
		update.setSendStatus(InformEnum.SendStatusEnum.SUCCESS_SEND.getTypeCode());
		long lupdateVersion = sequenceService.nextId(VERSION_PREFIX + unsendMsg.getDisplayGroup());
		update.setUpdVersion(lupdateVersion);
		update.setUpdateTime(new Date());
		baseMapper.updateById(update);

		if (isAll) {
			//修改未读消息数目,对于全站类型的消息，需要修改所有用户的未读消息记录
			List<Long> allCustId = custInfoClient.allCustId();
			platformPushService.updateUnReadNum(allCustId, unsendMsg.getDisplayGroup(), unsendMsg.getClientType());
		} else {
			//修改未读消息数目
			platformPushService.updateUnReadNum(Arrays.asList(unsendMsg.getUserId()), unsendMsg.getDisplayGroup(), unsendMsg.getClientType());
		}
	}

}
