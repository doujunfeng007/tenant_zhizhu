package com.minigod.zero.manage.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.manage.vo.SmsMessageRecord;
import com.minigod.zero.manage.mapper.PlatformMobileContentMapper;
import com.minigod.zero.manage.service.IPlatformMobileContentService;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.core.tool.utils.DateUtil;
import com.minigod.zero.cust.apivo.req.FindCustByPhonesReq;
import com.minigod.zero.cust.feign.ICustInfoClient;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.platform.entity.PlatformMobileContentEntity;
import com.minigod.zero.platform.enums.SmsChannelType;
import com.minigod.zero.platform.vo.PlatformMobileContentVO;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

import static com.minigod.zero.manage.comm.SmsFilterCode.SMS_FILTER_CODE;


/**
 * 短信内容信息表 服务实现类
 *
 * @author 掌上智珠
 * @since 2023-03-23
 */
@Service
@RequiredArgsConstructor
public class PlatformMobileContentServiceImpl extends BaseServiceImpl<PlatformMobileContentMapper, PlatformMobileContentEntity> implements IPlatformMobileContentService {

	@Value("${mobile.captcha.encrypt:0}")
	private Integer MOBILE_CAPTCHA;

	private final ICustInfoClient custInfoClient;

	@Autowired
	private PlatformMobileContentMapper platformMobileContentMapper;


	@Override
	public IPage<PlatformMobileContentVO> selectPlatformMobileContentPage(IPage<PlatformMobileContentVO> page, PlatformMobileContentVO platformMobileContent) {
		if (StringUtils.isNotBlank(platformMobileContent.getSendDate())) {
			Date sendDateStart = DateUtil.parseDate(platformMobileContent.getSendDate());
			Date sendDateEnd = DateUtil.parseDate(platformMobileContent.getSendDate() + " 23:59:59");
			platformMobileContent.setSendDateStart(sendDateStart);
			platformMobileContent.setSendDateEnd(sendDateEnd);
		}
		IPage<PlatformMobileContentEntity> platformMobileContentPage = baseMapper.selectPlatformMobileContentPage(page, platformMobileContent);

		List<PlatformMobileContentEntity> records = platformMobileContentPage.getRecords();
		List<PlatformMobileContentVO> vos = new ArrayList<>(records.size());
		if (CollectionUtils.isNotEmpty(records)) {
			Map<String, Long> phoneCustIdMap = null;
			List<String> phones = records.stream().map(PlatformMobileContentEntity::getCellPhone).distinct().collect(Collectors.toList());
			if (CollectionUtils.isNotEmpty(phones)) {
				String tenantId = AuthUtil.getTenantId();
				if (StringUtils.isBlank(tenantId)) {
					HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
					if (request != null) {
						tenantId = request.getHeader("tenant-id");
					}
				}
				FindCustByPhonesReq req = new FindCustByPhonesReq();
				req.setPhones(phones);
				req.setTenantId(tenantId);
				phoneCustIdMap = custInfoClient.findCustByPhones(req);
			}

			for (PlatformMobileContentEntity content : records) {
				PlatformMobileContentVO vo = new PlatformMobileContentVO();
				BeanUtil.copy(content, vo);

				// 将有交易密码和验证码的用*代替
				String str = vo.getContent();
				if(StringUtils.isNotEmpty(str)){
					if (str.indexOf("交易密码：") > 0) {
						String str1 = str.substring(0, str.indexOf("交易密码：") + 5);
						String str3 = str.substring(str.indexOf("交易密码：") + 11,
							str.length());
						String contentStr = str1 + "********" + str3;
						vo.setContent(contentStr);
					} else if (str.indexOf("交易密码") > 0) {
						String str1 = str.substring(0, str.indexOf("交易密码") + 4);
						String str3 = str.substring(str.indexOf("交易密码") + 10,
							str.length());
						String contentStr = str1 + "******" + str3;
						vo.setContent(contentStr);
					}
					if (MOBILE_CAPTCHA == 1) {
						//旧验证码
						if (str.indexOf("验证码：") >=0) {
							String str1 = str.substring(0, str.indexOf("验证码：") + 4);
							String str3 = str.substring(str.indexOf("验证码：") + 10,
								str.length());
							String contentStr = str1 + "******" + str3;
							vo.setContent(contentStr);
						}
						//新验证码
						else if (str.indexOf("验证码") >=0){
							String str1 = str.substring(0, str.indexOf("验证码：") + 7);
							String str3 = str.substring(str.indexOf("验证码：") + 13,
								str.length());
							String contentStr = str1 + "******" + str3;
							vo.setContent(contentStr);
						}else if (str.indexOf("Verification code:")>=0){
							String str1 = str.substring(0, str.indexOf("Verification code:") + 18);
							String str3 = str.substring(str.indexOf("Verification code:") + 24,
								str.length());
							String contentStr = str1 + "******" + str3;
							vo.setContent(contentStr);
						}else if (str.indexOf("有魚驗證碼:") >=0){
							String str1 = str.substring(0, str.indexOf("有魚驗證碼:") + 6);
							String str3 = str.substring(str.indexOf("有魚驗證碼:") + 12,
								str.length());
							String contentStr = str1 + "******" + str3;
							vo.setContent(contentStr);
						}
					}
				}



				if (phoneCustIdMap != null && phoneCustIdMap.size() > 0) {
					String voCellPhone = vo.getCellPhone().trim();
					if (voCellPhone.contains("-")) {
						String phoneNos[] = voCellPhone.replace("+","").split("-");
						voCellPhone = phoneNos[1];
					}
					Long custId = phoneCustIdMap.get(voCellPhone);
					vo.setUserId(custId);
				}
				//手机号验证码落库有+号,发送短信没有,展示列表做处理,不修改其他的
				if (vo.getCellPhone().contains("-")&&!vo.getCellPhone().contains("+")){
					vo.setCellPhone("+"+vo.getCellPhone());
				}

				vos.add(vo);
			}
		}

		return new Page<PlatformMobileContentVO>(platformMobileContentPage.getCurrent(), platformMobileContentPage.getSize(), platformMobileContentPage.getTotal()).setRecords(vos);
	}

	@Override
	public R pageList(IPage<SmsMessageRecord> page, String startTime, String endTime, String phone, String sendStatus) {
		List<PlatformMobileContentEntity> list =
			platformMobileContentMapper.pageList(page,startTime,endTime,phone,AuthUtil.getTenantId(),sendStatus);
		if (CollectionUtils.isEmpty(list)){
			return R.data(page);
		}
		List<SmsMessageRecord> recordList = new ArrayList<>();
		list.stream().forEach(message->{
			SmsMessageRecord record = new SmsMessageRecord();
			record.setId(message.getId());

			record.setCellPhone(message.getCellPhone());
			record.setErrorMessage(message.getErrorMsg());
			record.setDiscription(message.getDiscription());
			record.setChannelName(SmsChannelType.fromCode(message.getChannel()));
			//推送状态(0-未发送，1-已发送 2-发送失败)
			String statusName = null;
			Integer status = message.getSendStatus();
			if (status == 0){
				statusName = "未发送";
			}
			if (status == 1){
				statusName = "发送成功";
			}
			if (status == 2){
				statusName = "发送失败";
			}
			if (status == 3){
				statusName = "发送中";
			}
			record.setSendStatusName(statusName);
			if (message.getSendTime() != null){
				record.setSendTime(DateUtil.formatDateTime(message.getSendTime()));
			}
			if (Arrays.stream(SMS_FILTER_CODE).anyMatch(code -> code.equals(String.valueOf(message.getTempCode())))){
				message.setContent(message.getContent().replaceAll("\\d{6}", "******"));
			}
			record.setContent(message.getContent());
			recordList.add(record);
		});
		page.setRecords(recordList);
		return R.data(page);
	}


}
