package com.minigod.zero.customer.back.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.bpmn.module.account.entity.OrganizationOpenInfoEntity;
import com.minigod.zero.bpmn.module.feign.IOrganizationAccountOpenInfoClient;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.CollectionUtil;
import com.minigod.zero.core.tool.utils.ObjectUtil;
import com.minigod.zero.customer.back.service.IOrganizationBasicInfoService;
import com.minigod.zero.customer.bo.OrganizationInfoQueryBO;
import com.minigod.zero.customer.emuns.CustomerStatus;
import com.minigod.zero.customer.entity.*;
import com.minigod.zero.customer.enums.CustomerRole;
import com.minigod.zero.customer.enums.FundRiskLevel;
import com.minigod.zero.customer.mapper.*;
import com.minigod.zero.customer.vo.OrganizationBasicInfoVO;
import com.minigod.zero.customer.vo.OrganizationDetailVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName: com.minigod.zero.customer.back.service.impl.IOrganizationBasicInfoServiceImpl
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/7/23 10:34
 * @Version: 1.0
 */
@Service
public class IOrganizationBasicInfoServiceImpl  implements IOrganizationBasicInfoService {
	@Resource
	private  OrganizationBasicInfoMapper organizationBasicInfoMapper;
	@Resource
	private CustomerInfoMapper customerInfoMapper;

	@Resource
	private CustomerFundTradingAccountMapper customerFundTradingAccountMapper;
	@Resource
	private CustomerFinancingAccountMapper customerFinancingAccountMapper;
	@Resource
	private IOrganizationAccountOpenInfoClient organizationAccountOpenInfoClient;
	@Resource
	private CustomerIdentityInfoMapper customerIdentityInfoMapper;




	@Override
	public OrganizationDetailVO detail(Long id) {
		OrganizationDetailVO organizationDetailVO = new OrganizationDetailVO();
		OrganizationBasicInfo organizationBasicInfo = organizationBasicInfoMapper.selectById(id);
		BeanUtils.copyProperties(organizationBasicInfo,organizationDetailVO);
		if (ObjectUtil.isEmpty(organizationDetailVO.getUpdateTime())) {
			organizationDetailVO.setUpdateTime(organizationDetailVO.getCreateTime());
		}
		CustomerFinancingAccountEntity customerFinancingAccountEntity = customerFinancingAccountMapper.selectByCustId(organizationBasicInfo.getCustId());
		organizationDetailVO.setAccountId(customerFinancingAccountEntity.getAccountId());
		organizationDetailVO.setOpenAccountTime(organizationBasicInfo.getCreateTime());
		R<OrganizationOpenInfoEntity> infoEntityR = organizationAccountOpenInfoClient.queryOrganizationOpenAccountById(organizationBasicInfo.getCustId());
		if (infoEntityR.isSuccess()){
			Date createTime = infoEntityR.getData().getCreateTime();
			organizationDetailVO.setCreateTime(createTime);
		}
		CustomerInfoEntity customerInfoEntity = customerInfoMapper.selectByCustId(organizationBasicInfo.getCustId());
		if (ObjectUtil.isNotEmpty(customerInfoEntity)){
			organizationDetailVO.setAreaCode(customerInfoEntity.getAreaCode());
			organizationDetailVO.setRiskLevel(customerInfoEntity.getRiskLevel());
			organizationDetailVO.setRiskLevelName(FundRiskLevel.getByCode(customerInfoEntity.getRiskLevel()).getDesc());
		}
		//角色信息
		List<CustomerIdentityInfo> customerPcRoles = customerIdentityInfoMapper.selectByCustId(organizationBasicInfo.getCustId());
		String roleName = "";
		if (!CollectionUtil.isEmpty(customerPcRoles)) {
			roleName = customerPcRoles.stream().map(CustomerIdentityInfo::getRoleName).collect(Collectors.joining(","));
		} else {
			roleName = CustomerRole.ORDINARY.getDesc();
		}
		organizationDetailVO.setRoleName(roleName);

		return organizationDetailVO;
	}

	@Override
	public IPage<OrganizationBasicInfoVO> list(IPage<OrganizationBasicInfo> page, OrganizationInfoQueryBO bo) {
		LambdaQueryWrapper<OrganizationBasicInfo> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(OrganizationBasicInfo::getIsDeleted, 0);
		queryWrapper.like(ObjectUtil.isNotEmpty(bo.getContacts()), OrganizationBasicInfo::getContacts, bo.getContacts());
		queryWrapper.like(ObjectUtil.isNotEmpty(bo.getName()), OrganizationBasicInfo::getName, bo.getName());
		queryWrapper.like(ObjectUtil.isNotEmpty(bo.getContactsEmail()), OrganizationBasicInfo::getContactsEmail, bo.getContactsEmail());
		queryWrapper.like(ObjectUtil.isNotEmpty(bo.getContactsMobile()), OrganizationBasicInfo::getContactsMobile, bo.getContactsMobile());
		queryWrapper.orderByDesc(OrganizationBasicInfo::getCreateTime);

		IPage<OrganizationBasicInfoVO> records = organizationBasicInfoMapper.selectPage(page, queryWrapper).convert(item ->{
			OrganizationBasicInfoVO vo = new OrganizationBasicInfoVO();
			BeanUtils.copyProperties(item, vo);
			if (ObjectUtil.isNotEmpty(item.getCustId())){
				CustomerInfoEntity customerInfoEntity = customerInfoMapper.selectByCustId(item.getCustId());
				if (ObjectUtil.isNotEmpty(customerInfoEntity)){
					vo.setAreaCode(customerInfoEntity.getAreaCode());
					vo.setStatus(customerInfoEntity.getStatus());
					vo.setStatusName(CustomerStatus.getByCode(customerInfoEntity.getStatus()).getDesc());
				}else{
					return vo;
				}

				//基金交易账号
				CustomerFundTradingAccountEntity fundTradingAccountEntity = customerFundTradingAccountMapper.selectByCustId(item.getCustId());
				if (fundTradingAccountEntity != null){
					vo.setRiskLevel(fundTradingAccountEntity.getRiskLevel());
					vo.setRiskLevelName(FundRiskLevel.getByCode(vo.getRiskLevel()).getDesc());
				}

				//角色信息
				List<CustomerIdentityInfo> customerPcRoles = customerIdentityInfoMapper.selectByCustId(item.getCustId());
				String roleName = "";
				if (!CollectionUtil.isEmpty(customerPcRoles)) {
					roleName = customerPcRoles.stream().map(CustomerIdentityInfo::getRoleName).collect(Collectors.joining(","));
				} else {
					roleName = CustomerRole.ORDINARY.getDesc();
				}
				CustomerFinancingAccountEntity financingAccount =
					customerFinancingAccountMapper.selectByCustId(customerInfoEntity.getId());
				if (financingAccount != null){
					vo.setAccountId(financingAccount.getAccountId());
				}
				vo.setRoleName(roleName);
			}
			return vo;
		});

		return records;

    }
}
