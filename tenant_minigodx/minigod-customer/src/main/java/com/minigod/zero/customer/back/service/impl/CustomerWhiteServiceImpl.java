package com.minigod.zero.customer.back.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.common.exceptions.BusinessException;
import com.minigod.common.exceptions.ResultCode;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.CollectionUtil;
import com.minigod.zero.customer.back.service.CustomerWhiteService;
import com.minigod.zero.customer.entity.CustomerFinancingAccountEntity;
import com.minigod.zero.customer.entity.CustomerInfoEntity;
import com.minigod.zero.customer.entity.CustomerWhiteList;
import com.minigod.zero.customer.enums.FinancingAccountStatus;
import com.minigod.zero.customer.mapper.CustomerFinancingAccountMapper;
import com.minigod.zero.customer.mapper.CustomerInfoMapper;
import com.minigod.zero.customer.mapper.CustomerWhiteListMapper;
import com.minigod.zero.customer.vo.CustomerWhiteListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/8/2 18:49
 * @description：
 */
@Service
public class CustomerWhiteServiceImpl implements CustomerWhiteService {
	@Autowired
	private CustomerInfoMapper customerInfoMapper;

	@Autowired
	private CustomerWhiteListMapper customerWhiteListMapper;

	@Autowired
	private CustomerFinancingAccountMapper customerFinancingAccountMapper;

	@Override
	public R addWhiteList(Long custId) {
		if (custId == null){
			throw new BusinessException(ResultCode.PARAM_MISS.getMessage());
		}
		CustomerWhiteList customerWhite = customerWhiteListMapper.selectByCustId(custId,AuthUtil.getTenantId());
		if (customerWhite != null){
			throw new BusinessException("用户已存在白名单");
		}
		CustomerInfoEntity customerInfo = customerInfoMapper.selectByCustId(custId);
		if (customerInfo == null){
			throw new BusinessException("用户信息异常，无法添加白名单");
		}
		CustomerWhiteList customerWhiteList = new CustomerWhiteList();
		CustomerFinancingAccountEntity financingAccount = customerFinancingAccountMapper.selectByCustId(custId);
		if (financingAccount != null && financingAccount.getStatus() != FinancingAccountStatus.NOT_ACTIVE.getCode()){
			customerWhiteList.setAccountId(financingAccount.getAccountId());
		}
		customerWhiteList.setCreator(AuthUtil.getUser().getUserId());
		customerWhiteList.setCreatorName(AuthUtil.getUserName());
		customerWhiteList.setCreateTime(new Date());
		customerWhiteList.setCustId(custId);
		customerWhiteList.setTenantId(AuthUtil.getTenantId());
		customerWhiteListMapper.insertSelective(customerWhiteList);
		return R.success();
	}

	@Override
	public R updateStatus(Long custId, Integer status) {
		if (custId == null || status == null){
			throw new BusinessException(ResultCode.PARAM_MISS.getMessage());
		}
		CustomerInfoEntity customerInfo = customerInfoMapper.selectByCustId(custId);
		if (customerInfo == null){
			throw new BusinessException("用户信息异常，修改失败");
		}
		CustomerWhiteList customerWhite = customerWhiteListMapper.selectByCustId(custId,AuthUtil.getTenantId());
		if (customerWhite == null){
			throw new BusinessException("白名单信息不存在，修改失败");
		}
		customerWhite.setStatus(status);
		customerWhiteListMapper.updateByPrimaryKeySelective(customerWhite);
		return R.success();
	}

	@Override
	public R queryPage(IPage<CustomerWhiteListVO> page, String keyword) {
		List<CustomerWhiteListVO> list = customerWhiteListMapper.queryCustomerWhiteListPage(page,keyword);
		if (!CollectionUtil.isEmpty(list)){
			list.stream().forEach(customerWhiteList -> {
				if (customerWhiteList.getStatus() == 0){
					customerWhiteList.setStatusName("有效");
				}
				if (customerWhiteList.getStatus() == 1){
					customerWhiteList.setStatusName("无效");
				}
			});
		}
		page.setRecords(list);
		return R.data(page);
	}
}
