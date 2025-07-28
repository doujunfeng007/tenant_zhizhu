package com.minigod.zero.cust.service.impl;

import com.minigod.zero.biz.common.enums.LanguageEnum;
import com.minigod.zero.biz.common.utils.ProtocolUtils;
import com.minigod.zero.cust.service.ICustLoginService;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.core.tool.enums.CustEnums;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.core.tool.utils.WebUtil;
import com.minigod.zero.cust.cache.AcctInfo;
import com.minigod.zero.cust.cache.CustInfo;
import com.minigod.zero.cust.entity.CustDeviceEntity;
import com.minigod.zero.cust.entity.CustInfoEntity;
import com.minigod.zero.cust.mapper.CustAccountInfoMapper;
import com.minigod.zero.cust.mapper.CustInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 李长春
 * @email cloud@bdie.com.cn
 * @WeChat 138264386257
 * @date 2023/3/28
 */
@Slf4j
@Service
public class CustLoginServiceImpl extends BaseServiceImpl<CustInfoMapper, CustInfoEntity> implements ICustLoginService {

	@Resource
	private CustAccountInfoMapper custAccountInfoMapper;
	@Resource
	private CustLoginAsyncService custLoginAsyncService;

	@Override
	public CustInfo getCustInfo(Long custId, CustDeviceEntity custDevice, String sourceType, Integer loginType) {
		// 客户基本信息
		CustInfo custInfo = baseMapper.getCustInfo(custId);

		// 是否为个人+ESOP户
		if (CustEnums.CustType.ESOP.getCode() == custInfo.getCustType()) {
			custInfo.setEsopStatus(custInfo.getBindCust() == null ? 1 : 2);
		} else if (CustEnums.CustType.GENERAL.getCode() == custInfo.getCustType()) {
			custInfo.setEsopStatus(custInfo.getBindCust() == null ? 0 : 2);
		} else {
			custInfo.setEsopStatus(0);
		}

		if (custInfo != null) {
			// 客户账号列表（公司户会存在多个）
			List<AcctInfo> acctList = custAccountInfoMapper.getAcctList(custId);
			if (LanguageEnum.EN.getCode().equals(WebUtil.getLanguage())) {
				acctList = acctList.stream().map(acctInfo -> {
						acctInfo.setCompanyName(acctInfo.getCompanyNameEn());
						return acctInfo;
					}).collect(Collectors.toList());
			}
			custInfo.setAcctList(acctList);
			// 敏感数据脱敏
			if (StringUtils.isNotEmpty(custInfo.getCellPhone())) {
				custInfo.setCellPhone(ProtocolUtils.phone2Star(custInfo.getAreaCode(), custInfo.getCellPhone()));
			}
			custInfo.setCellEmail(ProtocolUtils.maskEmail(custInfo.getCellEmail()));
		}
		// 异步兜底设备上报
		custLoginAsyncService.recordLoginInfo(custId, custDevice);
		// 异步记录登录日志
		CustInfo custInfoSource = BeanUtil.copy(custInfo, CustInfo.class);
		custLoginAsyncService.saveLoginLog(custId, custDevice, custInfoSource, sourceType, WebUtil.getIP(), loginType);
		return custInfo;
	}

}
