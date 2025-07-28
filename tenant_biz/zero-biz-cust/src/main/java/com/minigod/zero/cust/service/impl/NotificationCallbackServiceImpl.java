package com.minigod.zero.cust.service.impl;

import com.alibaba.fastjson.JSON;
import com.minigod.zero.biz.common.constant.CommonConstant;
import com.minigod.zero.cust.service.ICustDeviceService;
import com.minigod.zero.cust.service.INotificationCallbackService;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.minigod.zero.cust.entity.CustDeviceEntity;
import com.minigod.zero.cust.vo.PriceReminderCallbackVO;
import com.minigod.zero.cust.vo.SendNotifyVO;
import com.minigod.zero.platform.constants.PushUrlProtocol;
import com.minigod.zero.platform.dto.SendNotifyDTO;
import com.minigod.zero.platform.enums.MsgStaticType;
import com.minigod.zero.platform.feign.IPlatformMsgClient;
import com.minigod.zero.platform.feign.IPlatformSysMsgClient;
import com.minigod.zero.platform.vo.PlatformSysMsgVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
public class NotificationCallbackServiceImpl implements INotificationCallbackService {

	@Resource
	private IPlatformMsgClient platformMsgClient;
	@Resource
	private ICustDeviceService custDeviceService;
	@Resource
	private IPlatformSysMsgClient platformSysMsgClient;

	@Override
	public R<Object> priceReminder(PriceReminderCallbackVO reqVo) {
		if (null == reqVo) {
			return R.fail("股价提醒回调失败：参数错误");
		}
		try {
			SendNotifyDTO dto = new SendNotifyDTO();
			dto.setDisplayGroup(MsgStaticType.DisplayGroup.MTK_NOTIFY);
			dto.setTemplateCode(reqVo.getTemplateCode());
			dto.setTitle((String) reqVo.getParamMap().get("title"));//为兼容以前的功能，标题不采用模板标题，用之前沿用的标题
			dto.setLstToUserId(reqVo.getLstToUserId());
			String[] array = new String[]{(String) reqVo.getParamMap().get("stkName"), reqVo.getParamMap().get("price") + "",
				reqVo.getParamMap().get("changePct") + "", reqVo.getParamMap().get("setValue") + ""};
			List<String> paramsList = Stream.of(array).collect(Collectors.toList());
			dto.setParams(paramsList);
			dto.setUrl(PushUrlProtocol.AppUrlEnum.STOCK_DEATIL.getUrlProtocol());
			List<String> urlParams = new ArrayList<>(2);
			urlParams.add((String) reqVo.getParamMap().get("assetId"));
			urlParams.add((String) reqVo.getParamMap().get("secsType"));
			dto.setUrlParams(urlParams);
			R r = platformMsgClient.sendNotify(dto);
			log.info("调用platform返回：" + JSON.toJSONString(r));
		} catch (Exception e) {
			return R.fail("股价提醒发送系统通知异常:" + e.getMessage());
		}
		return R.success();
	}

	@Override
	public R<Object> sendNotify(SendNotifyVO sendNotifyVO) {
		if (null == sendNotifyVO) {
			return R.fail("模板通知回调失败：参数错误");
		}
		try {
			SendNotifyDTO sendNotifyDTO = BeanUtil.copy(sendNotifyVO, SendNotifyDTO.class);
			String lang = CommonConstant.ZH_CN;
			CustDeviceEntity custDevice = custDeviceService.getCustDevice(sendNotifyDTO.getLstToUserId().get(0));
			if (custDevice != null && StringUtil.isNotBlank(custDevice.getLang())) {
				lang = custDevice.getLang();
			}
			sendNotifyVO.setLang(lang);
			if (lang.equalsIgnoreCase(CommonConstant.ZH_HK)){
				sendNotifyDTO.setParams(sendNotifyVO.getParamsHant());
			}
			if (lang.equalsIgnoreCase(CommonConstant.EN_US)){
				sendNotifyDTO.setParams(sendNotifyVO.getParamsEn());
			}

			int displayGroup = sendNotifyVO.getDisplayGroup();
			if (displayGroup == MsgStaticType.DisplayGroup.SYSTEM_MSG) {
				PlatformSysMsgVO newSysMsg = new PlatformSysMsgVO();
				newSysMsg.setTempCode(sendNotifyDTO.getTemplateCode());
				newSysMsg.setTargetId(sendNotifyDTO.getLstToUserId().get(0));
				newSysMsg.setParams(sendNotifyDTO.getParams());
				newSysMsg.setLang(lang);
				platformSysMsgClient.saveSysMsg(newSysMsg);
			}
			platformMsgClient.sendNotify(sendNotifyDTO);
		} catch (Exception e) {
			return R.fail("模板通知回调异常:" + e.getMessage());
		}
		return R.success();
	}
}
