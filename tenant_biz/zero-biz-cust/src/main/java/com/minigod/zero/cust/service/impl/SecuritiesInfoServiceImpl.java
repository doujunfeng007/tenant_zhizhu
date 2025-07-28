package com.minigod.zero.cust.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minigod.zero.bpm.entity.BpmSecuritiesInfoEntity;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.enums.CustEnums;
import com.minigod.zero.cust.mapper.CustInfoMapper;
import com.minigod.zero.cust.mapper.SecuritiesInfoMapper;
import com.minigod.zero.cust.service.ISecuritiesInfoService;
import com.minigod.zero.cust.vo.CustContactVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 证券客户资料表 服务实现类
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
@Service
public class SecuritiesInfoServiceImpl extends ServiceImpl<SecuritiesInfoMapper, BpmSecuritiesInfoEntity> implements ISecuritiesInfoService {

	@Resource
	private SecuritiesInfoMapper securitiesInfoMapper;
	@Resource
	private CustInfoMapper custInfoMapper;

	@Override
	public CustContactVO getCustContactInfo(Long custId,String tradeAccount) {
		if (CustEnums.CustType.AUTHOR.getCode() == AuthUtil.getCustType()) {
			// 公司户授权人从custInfo表获取
			return custInfoMapper.getCustContactInfo(custId,tradeAccount);
		} else if(CustEnums.CustType.ESOP.getCode() == AuthUtil.getCustType()){
			// ESOP用户
			return securitiesInfoMapper.getCustContactInfo(custId);
		} else {
			CustContactVO custContact = securitiesInfoMapper.getCustContactInfo(custId);
			if(custContact == null || StringUtils.isBlank(custContact.getPhoneArea()) || StringUtils.isBlank(custContact.getPhoneNumber())){
				custContact = custInfoMapper.getCustContactInfo2fa(custId,tradeAccount);
			}
			return custContact;
		}
	}

}
