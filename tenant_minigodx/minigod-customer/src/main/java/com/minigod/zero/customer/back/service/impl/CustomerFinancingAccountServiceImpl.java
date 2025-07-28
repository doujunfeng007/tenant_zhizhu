/*
 *  Copyright (c) 2018-2028, MiniGod All rights reserved.
 */
package com.minigod.zero.customer.back.service.impl;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.customer.back.service.CustomerStatementAccountService;
import com.minigod.zero.customer.back.service.ICustomerFinancingAccountService;
import com.minigod.zero.customer.entity.CustomerBasicInfoEntity;
import com.minigod.zero.customer.entity.CustomerFinancingAccountEntity;
import com.minigod.zero.customer.mapper.*;
import com.minigod.zero.customer.vo.CustAssetsQuarterVO;
import com.minigod.zero.customer.vo.CustomerFinancingAccountVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 客户理财账户表 服务实现类
 *
 * @author ken
 * @since 2024-04-11
 */
@Slf4j
@Service
public class CustomerFinancingAccountServiceImpl extends BaseServiceImpl<CustomerFinancingAccountMapper, CustomerFinancingAccountEntity> implements ICustomerFinancingAccountService {


	@Override
	public IPage<CustomerFinancingAccountVO> selectCustomerFinancingAccountPage(IPage<CustomerFinancingAccountVO> page, CustomerFinancingAccountVO financingAccount) {
		return page.setRecords(baseMapper.selectCustomerFinancingAccountPage(page, financingAccount));
	}

}
