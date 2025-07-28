package com.minigod.zero.bpmn.module.account.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.minigod.zero.bpmn.module.account.entity.AccountOpenInfoModifyEntity;
import com.minigod.zero.bpmn.module.account.mapper.AccountOpenInfoModifyMapper;
import com.minigod.zero.bpmn.module.account.service.IAccountOpenInfoModifyService;
import com.minigod.zero.bpmn.module.account.vo.AccountAssetInvestmentInfoModifyVO;
import com.minigod.zero.bpmn.module.account.vo.AccountAssetInvestmentInfoVO;
import com.minigod.zero.bpmn.module.account.vo.AccountOpenInfoModifyVO;
import com.minigod.zero.bpmn.module.common.service.AddressService;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.system.feign.IDictBizClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.minigod.zero.bpmn.module.account.constants.DictTypeConstant.COUNTRY_OR_REGION;
import static com.minigod.zero.bpmn.module.account.constants.DictTypeConstant.TRADE_STOCK_FREQUENCY;

/**
 * 客户开户详细资料修改服务
 *
 * @author eric
 * @since 2024-08-02 19:13:12
 */
@Slf4j
@Service
@AllArgsConstructor
public class AccountOpenInfoModifyServiceImpl extends BaseServiceImpl<AccountOpenInfoModifyMapper, AccountOpenInfoModifyEntity> implements IAccountOpenInfoModifyService {
	private final AddressService sysAddressService;
	private final IDictBizClient dictBizClient;

	/**
	 * 持久化客户开户详细资料修改记录
	 *
	 * @param entity
	 * @return
	 */
	@Override
	public boolean insert(AccountOpenInfoModifyEntity entity) {
		return this.save(entity);
	}

	/**
	 * 将地址名称根据字典及编码转换
	 *
	 * @param modifyVO
	 * @return
	 */
	@Override
	public void transferAddressName(AccountOpenInfoModifyVO modifyVO) {
		//公司地址转换
		modifyVO.setCompanyRepublicName(dictBizClient.getValue(COUNTRY_OR_REGION, modifyVO.getCompanyRepublicName()).getData());
		modifyVO.setCompanyProvinceName(sysAddressService.getAddressName(modifyVO.getCompanyProvinceName()));
		modifyVO.setCompanyCityName(sysAddressService.getAddressName(modifyVO.getCompanyCityName()));
		modifyVO.setCompanyCountyName(sysAddressService.getAddressName(modifyVO.getCompanyCountyName()));

		//联系地址转换
		modifyVO.setContactRepublicName(dictBizClient.getValue(COUNTRY_OR_REGION, modifyVO.getContactRepublicName()).getData());
		modifyVO.setContactProvinceName(sysAddressService.getAddressName(modifyVO.getContactProvinceName()));
		modifyVO.setContactCityName(sysAddressService.getAddressName(modifyVO.getContactCityName()));
		modifyVO.setContactCountyName(sysAddressService.getAddressName(modifyVO.getContactCountyName()));

		//永久地址装换
		modifyVO.setPermanentRepublicName(dictBizClient.getValue(COUNTRY_OR_REGION, modifyVO.getPermanentRepublicName()).getData());
		modifyVO.setPermanentProvinceName(sysAddressService.getAddressName(modifyVO.getPermanentProvinceName()));
		modifyVO.setPermanentCityName(sysAddressService.getAddressName(modifyVO.getPermanentCityName()));
		modifyVO.setPermanentCountyName(sysAddressService.getAddressName(modifyVO.getPermanentCountyName()));

		//住宅地址
		modifyVO.setFamilyRepublicName(dictBizClient.getValue(COUNTRY_OR_REGION, modifyVO.getFamilyRepublicName()).getData());
		modifyVO.setFamilyProvinceName(sysAddressService.getAddressName(modifyVO.getFamilyProvinceName()));
		modifyVO.setFamilyCityName(sysAddressService.getAddressName(modifyVO.getFamilyCityName()));
		modifyVO.setFamilyCountyName(sysAddressService.getAddressName(modifyVO.getFamilyCountyName()));
	}

	/**
	 * 将交易名称根据字典及编码转换
	 *
	 * @param modifyVO
	 * @return
	 */
	@Override
	public void transferTradeName(AccountAssetInvestmentInfoModifyVO modifyVO) {
		modifyVO.setTradeFrequencyName(dictBizClient.getValue(TRADE_STOCK_FREQUENCY, modifyVO.getTradeFrequency().toString()).getData());
	}

	/**
	 * 将交易名称根据字典及编码转换
	 *
	 * @param accountAssetInvestmentInfoVO
	 */
	@Override
	public void transferTradeName(AccountAssetInvestmentInfoVO accountAssetInvestmentInfoVO) {
		accountAssetInvestmentInfoVO.setTradeFrequencyName(dictBizClient.getValue(TRADE_STOCK_FREQUENCY, accountAssetInvestmentInfoVO.getTradeFrequency().toString()).getData());
	}

	/**
	 * 根据修改申请ID查询客户开户详细资料记录
	 *
	 * @param applyId
	 * @return
	 */
	@Override
	public AccountOpenInfoModifyVO selectByApplyId(Long applyId) {
		LambdaQueryWrapper<AccountOpenInfoModifyEntity> queryWrapper = Wrappers.lambdaQuery();
		queryWrapper.eq(AccountOpenInfoModifyEntity::getApplyId, applyId);
		queryWrapper.orderByDesc(AccountOpenInfoModifyEntity::getCreateTime);

		AccountOpenInfoModifyVO modifyVO = BeanUtil.copyProperties(this.getOne(queryWrapper), AccountOpenInfoModifyVO.class);
		return modifyVO;
	}

	/**
	 * 根据开户申请ID查询客户开户详细资料修改记录
	 *
	 * @param applicationId
	 * @return
	 */
	@Override
	public List<AccountOpenInfoModifyVO> selectByApplicationId(String applicationId) {
		LambdaQueryWrapper<AccountOpenInfoModifyEntity> queryWrapper = new LambdaQueryWrapper();
		queryWrapper.eq(AccountOpenInfoModifyEntity::getApplicationId, applicationId);
		queryWrapper.orderByDesc(AccountOpenInfoModifyEntity::getCreateTime);
		List<AccountOpenInfoModifyEntity> entities = this.list(queryWrapper);
		List<AccountOpenInfoModifyVO> modifyVOS = BeanUtil.copyProperties(entities, AccountOpenInfoModifyVO.class);
		return modifyVOS;
	}

	/**
	 * 根据用户ID查询客户开户详细资料修改记录
	 *
	 * @param userId
	 * @return
	 */
	@Override
	public List<AccountOpenInfoModifyVO> selectByUserId(Long userId) {
		LambdaQueryWrapper<AccountOpenInfoModifyEntity> queryWrapper = new LambdaQueryWrapper();
		queryWrapper.eq(AccountOpenInfoModifyEntity::getUserId, userId);
		queryWrapper.orderByDesc(AccountOpenInfoModifyEntity::getCreateTime);
		List<AccountOpenInfoModifyEntity> entities = this.list(queryWrapper);
		List<AccountOpenInfoModifyVO> modifyVOS = BeanUtil.copyProperties(entities, AccountOpenInfoModifyVO.class);
		return modifyVOS;
	}
}
