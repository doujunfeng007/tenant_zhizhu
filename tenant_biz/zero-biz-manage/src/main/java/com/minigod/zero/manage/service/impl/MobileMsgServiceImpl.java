package com.minigod.zero.manage.service.impl;

import com.minigod.zero.core.tool.exception.ZeroException;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.sms.model.SmsData;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.manage.dto.EmailDTO;
import com.minigod.zero.manage.dto.PushDTO;
import com.minigod.zero.manage.dto.SmsDTO;
import com.minigod.zero.manage.proxy.CustomerProxy;
import com.minigod.zero.manage.service.MobileMsgService;
import com.minigod.zero.manage.vo.CustomerAccountVO;
import com.minigod.zero.platform.constants.Constants;
import com.minigod.zero.platform.enums.PushTemplate;
import com.minigod.zero.platform.utils.EmailUtil;
import com.minigod.zero.platform.utils.PushUtil;
import com.minigod.zero.platform.utils.SmsUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class MobileMsgServiceImpl implements MobileMsgService {

	@Autowired
	private CustomerProxy customerProxy;

	@Override
	public R sendMessage(SmsDTO sendSmsVO) {
		String language = sendSmsVO.getLanguage();
		String phoneStr = sendSmsVO.getCellPhone();
		Integer templateCode = sendSmsVO.getTemplateCode();
		String templateParam = sendSmsVO.getTemplateParam();
		String custIdStr = sendSmsVO.getCustId();
		if (templateCode == null){
			throw new ZeroException("发送失败，模板编号不能为空");
		}
		if (StringUtils.isEmpty(language)){
			throw new ZeroException("发送失败，语种不能为空");
		}
		List<SmsData> list = new ArrayList<>();
		if(!StringUtils.isEmpty(phoneStr)){
			list = sendSmsByCellPhone(phoneStr);
		}
		else if (!StringUtils.isEmpty(custIdStr)){
			list = sendSmsByCustId(custIdStr);
		}else{
			return R.fail();
		}
		List<String> sendParam = new ArrayList<>();
		if (!StringUtils.isEmpty(templateParam)){
			sendParam = Arrays.asList(templateParam.split(","));
		}
		for (SmsData data : list){
			SmsUtil.builder()
				.tenantId(AuthUtil.getTenantId())
				.templateCode(sendSmsVO.getTemplateCode())
				.areaCode(data.getAreaCode())
				.phoneNumber(data.getPhone())
				.language(language)
				.listParam(sendParam)
				.sendAsync();
		}
		return R.success();
	}

	@Override
	public R sendMessage(EmailDTO emailDTO) {
		if (StringUtils.isEmpty(emailDTO.getAccepts())
			&& StringUtils.isEmpty(emailDTO.getCustId())){
			throw new ZeroException("发送失败，接收用户不能为空");
		}
		if (StringUtils.isEmpty(emailDTO.getTitle())){
			throw new ZeroException("发送失败，邮件标题不能为空");
		}
		if (StringUtils.isEmpty(emailDTO.getContent())){
			throw new ZeroException("发送失败，邮件内容不能为空");
		}
		List<String> mailboxList = new ArrayList<>();
		if (!StringUtils.isEmpty(emailDTO.getAccepts())){
			mailboxList = sendEmailByMailbox(emailDTO.getAccepts());
		}
		if (!StringUtils.isEmpty(emailDTO.getCustId())){
			mailboxList = sendEmailByCustId(emailDTO.getCustId());
		}
		EmailUtil.builder()
			.accepts(mailboxList)
			.title(emailDTO.getTitle())
			.content(emailDTO.getContent())
			.tenantId(AuthUtil.getTenantId())
			.attachmentUrls(Arrays.asList(emailDTO.getAttachmentUrls().split(",")))
			.carbonCopy(Arrays.asList(emailDTO.getCarbonCopy().split(",")))
			.sendAsync();
		return R.success();
	}

	@Override
	public R sendMessage(PushDTO pushDTO) {
		if (StringUtils.isEmpty(pushDTO.getCustId())){
			throw new ZeroException("发送失败，接收用户不能为空");
		}
		if (StringUtils.isEmpty(pushDTO.getTitle())){
			throw new ZeroException("发送失败，推送标题不能为空");
		}
		if (StringUtils.isEmpty(pushDTO.getContent())){
			throw new ZeroException("发送失败，推送内容不能为空");
		}
		List<Long>  lstToUserId = new ArrayList<>();
		String[] custIdArray = pushDTO.getCustId().split(",");
		for(String custId : custIdArray){
			lstToUserId.add(Long.valueOf(custId));
		}
		PushUtil.builder()
			.msgGroup("P")
			.custId(lstToUserId)
			.fromUserId(Constants.USERID_ALL_USER)
			.pushType(pushDTO.getSendType())
			.group(pushDTO.getDisplayGroup())
			.sendWay(pushDTO.getSendWay())
			.sendTime(pushDTO.getSendTime())
			.msgContent(pushDTO.getContent())
			.title(pushDTO.getTitle())
			.templateCode(PushTemplate.LOGGED_IN_ON_ANOTHER_DEVICE.getCode()).pushAsync();
		return R.success();
	}

	private List<String> sendEmailByCustId(String custIdStr){
		if (StringUtils.isEmpty(custIdStr)){
			return null;
		}
		String[] custIdArray = custIdStr.split(",");
		if (custIdArray.length == 0){
			return null;
		}
		List<String> list = new ArrayList<>();
		for (String custId : custIdArray){
			R<CustomerAccountVO> result = customerProxy.customerAccountInfo(Long.valueOf(custId));
			if (!result.isSuccess()){
				log.error("custId={}邮件发送失败:{}",custId,result.getMsg());
				continue;
			}
			CustomerAccountVO customerAccount = result.getData();
			String email = customerAccount.getCust().getEmail();
			if (!StringUtils.isEmpty(email)){
				list.add(email);
			}
		}
		return list;
	}

	private List<String> sendEmailByMailbox(String accepts){
		if (StringUtils.isEmpty(accepts)){
			return null;
		}
		String[] mailboxArray = accepts.split(",");
		List<String> list = new ArrayList<>();
		for (String mailbox : mailboxArray){
			list.add(mailbox);
		}
		return list;
	}


	private List<SmsData> sendSmsByCellPhone(String phoneStr){
		String[] cellPhones = phoneStr.split(",");
		if (cellPhones.length == 0){
			return null;
		}
		List<SmsData> sendList = new ArrayList<>();
		for (String cellPhone : cellPhones){
			String[] sendPhone = cellPhone.split("-");
			if (sendPhone.length == 2){
				SmsData sendData = new SmsData();
				sendData.setPhone(sendPhone[1]);
				sendData.setAreaCode(sendPhone[0]);
				sendList.add(sendData);
			}
		}
		return sendList;
	}

	private List<SmsData> sendSmsByCustId(String custIdStr){
		if (StringUtils.isEmpty(custIdStr)){
			return null;
		}
		String[] custIdArray = custIdStr.split(",");
		if (custIdArray.length == 0){
			return null;
		}
		List<SmsData> sendList = new ArrayList<>();
		for (String custId : custIdArray){
			if (StringUtils.isEmpty(custId)){
				continue;
			}
			R<CustomerAccountVO> result = customerProxy.customerAccountInfo(Long.valueOf(custId));
			if (!result.isSuccess()){
				log.error("custId={}短信发送失败:{}",custId,result.getMsg());
				continue;
			}
			CustomerAccountVO customerAccount = result.getData();
			SmsData sendData = new SmsData();
			sendData.setPhone(customerAccount.getCust().getPhoneNumber());
			sendData.setAreaCode(customerAccount.getCust().getPhoneArea());
			sendList.add(sendData);
		}
		return sendList;
	}


}
