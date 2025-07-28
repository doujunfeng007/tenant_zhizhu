/*
 *  Copyright (c) 2018-2028, MiniGod All rights reserved.
 */
package com.minigod.zero.customer.back.service.impl;

import com.alibaba.fastjson.JSON;
import com.minigod.zero.customer.back.service.ICustomerLoginLogService;
import com.minigod.zero.customer.dto.CustomerLoginLogDTO;
import com.minigod.zero.customer.entity.CustomerLoginLogEntity;
import com.minigod.zero.customer.enums.CustEnums;
import com.minigod.zero.customer.enums.CustLoginEnum;
import com.minigod.zero.customer.vo.CustomerLoginLogVO;
import com.minigod.zero.customer.mapper.CustomerLoginLogMapper;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 登陆日志表 服务实现类
 *
 * @author ken
 * @since 2024-04-11
 */
@Slf4j
@Service
public class CustomerLoginLogServiceImpl extends BaseServiceImpl<CustomerLoginLogMapper, CustomerLoginLogEntity> implements ICustomerLoginLogService {

	/**
	 * 查询客户登录日志
	 *
	 * @param page
	 * @param loginLog
	 * @return
	 */
	@Override
	public IPage<CustomerLoginLogVO> selectCustomerLoginLogPage(IPage<CustomerLoginLogVO> page, CustomerLoginLogDTO loginLog) {
		List<CustomerLoginLogVO> list = baseMapper.selectCustomerLoginLogPage(page, loginLog);
		list.forEach(vo -> transferVo(vo));
		return page.setRecords(list);
	}

	/**
	 * 查询游客登录日志
	 *
	 * @param page
	 * @param loginLog
	 * @return
	 */
	@Override
	public IPage<CustomerLoginLogVO> selectVisitorLoginLogPage(IPage<CustomerLoginLogVO> page, CustomerLoginLogDTO loginLog) {
		log.info("-->查询游客用户登录日志参数action:{}", JSON.toJSONString(loginLog));
		List<CustomerLoginLogVO> list = baseMapper.selectVisitorLoginLogPage(page, loginLog);
		list.forEach(vo -> transferVo(vo));
		return page.setRecords(list);
	}

	/**
	 * 查询开户用户登录日志
	 *
	 * @param page
	 * @param loginLog
	 * @return
	 */
	@Override
	public IPage<CustomerLoginLogVO> selectAccountUserLoginLogPage(IPage<CustomerLoginLogVO> page, CustomerLoginLogDTO loginLog) {
		log.info("-->查询开户用户登录日志参数action:{}", JSON.toJSONString(loginLog));
		List<CustomerLoginLogVO> list = baseMapper.selectAccountUserLoginLogPage(page, loginLog);
		list.forEach(vo -> transferVo(vo));
		return page.setRecords(list);
	}

	/**
	 * 转换枚举类型名称
	 *
	 * @param vo
	 */
	private void transferVo(CustomerLoginLogVO vo) {
		if (vo == null) {
			return;
		}

		if (vo.getAreaCode() != null && !vo.getAreaCode().equals("") && vo.getPhoneNumber() != null && !vo.getPhoneNumber().equals("")) {
			vo.setPhoneAreaNumber(vo.getAreaCode() + "-" + vo.getPhoneNumber());
		} else if (vo.getPhoneNumber() != null && !vo.getPhoneNumber().equals("")) {
			vo.setPhoneAreaNumber(vo.getPhoneNumber());
		}
		if (vo.getAction() != null) {
			switch (vo.getAction()) {
				case 1:
					vo.setActionName(CustLoginEnum.action.IN.getInfo());
					break;
				case 0:
					vo.setActionName(CustLoginEnum.action.OUT.getInfo());
					break;
				case -1:
					vo.setActionName(CustLoginEnum.action.ERROR.getInfo());
					break;
				default:
					break;
			}
		}
		if (vo.getLoginType() != null) {
			switch (vo.getLoginType()) {
				case 1:
					vo.setLoginTypeName(CustEnums.LoginWayType.REGISTER.getDesc());
					break;
				case 2:
					vo.setLoginTypeName(CustEnums.LoginWayType.CORP_AUTHOR.getDesc());
					break;
				case 3:
					vo.setLoginTypeName(CustEnums.LoginWayType.ACCOUNT.getDesc());
					break;
				case 4:
					vo.setLoginTypeName(CustEnums.LoginWayType.TRADE_ACCT.getDesc());
					break;
				case 5:
					vo.setLoginTypeName(CustEnums.LoginWayType.SOCIAL.getDesc());
					break;
				case 6:
					vo.setLoginTypeName(CustEnums.LoginWayType.ESOP_ACCT.getDesc());
					break;
				default:
					vo.setLoginTypeName(CustEnums.LoginWayType.UNKNOWN.getDesc());
			}
		}
		if (vo.getType() != null) {

			switch (vo.getType()) {
				case 1:
					vo.setTypeName(CustLoginEnum.type.LOGIN.getInfo());
					break;
				case 2:
					vo.setTypeName(CustLoginEnum.type.TRD_UNLOCK.getInfo());
					break;
				case 3:
					vo.setTypeName(CustLoginEnum.type.IP_OFFSITE.getInfo());
					break;
			}
		}
		if (vo.getOsType() != null) {
			switch (vo.getOsType()) {
				case 0:
					vo.setOsTypeName(CustLoginEnum.osType.ANDROID.getInfo());
					break;
				case 1:
					vo.setOsTypeName(CustLoginEnum.osType.IOS.getInfo());
					break;
				case 2:
					vo.setOsTypeName(CustLoginEnum.osType.WP.getInfo());
					break;
				case 4:
					vo.setOsTypeName(CustLoginEnum.osType.WINDOWS.getInfo());
					break;
			}
		}
	}
}
