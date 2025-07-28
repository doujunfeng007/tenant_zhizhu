package com.minigod.zero.bpmn.module.account.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.minigod.zero.bpm.enums.OpenAccountEnum;
import com.minigod.zero.bpmn.module.account.entity.*;
import com.minigod.zero.bpmn.module.account.enums.CustomOpenAccountEnum;
import com.minigod.zero.bpmn.module.account.service.*;
import com.minigod.zero.bpmn.module.account.vo.*;
import com.minigod.zero.bpmn.module.feign.ICustomerInfoClient;
import com.minigod.zero.bpmn.module.feign.dto.CustomerOpenAccountInfoDTO;
import com.minigod.zero.bpmn.module.feign.vo.CustomerAccountVO;
import com.minigod.zero.bpmn.module.feign.vo.CustomerAllAccountVO;
import com.minigod.zero.bpmn.module.feign.vo.CustomerTradeAccountVO;
import com.minigod.zero.core.log.exception.ServiceException;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.trade.feign.ICounterOpenAccountClient;
import com.minigod.zero.trade.vo.sjmb.req.ModifyAccountReq;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 客户资料修改同步服务
 *
 * @author eric
 * @since 2024-09-05 10:59:04
 */
@Slf4j
@Service
@AllArgsConstructor
public class AccountOpenInfoModifySyncServiceImpl implements IAccountOpenInfoModifySyncService {
	//客户图片修改服务类
	private final IAccountOpenImageModifyService accountOpenImageModifyService;
	//客户开户图片服务类
	private final IAccountOpenImageService accountOpenImageService;
	//客户开户资料修改申请记录服务类
	private final IAccountOpenInfoModifyApplyService accountOpenInfoModifyApplyService;
	//客户开户详细资料服务类
	private final IAccountOpenInfoModifyService accountOpenInfoModifyService;
	//客户开户详细资料服务类
	private final IAccountOpenInfoService accountOpenInfoService;
	//客户身份确认信息服务类
	private final IAccountIdentityConfirmModifyService accountIdentityConfirmModifyService;
	//客户开户身份信息服务类
	private final IAccountIdentityConfirmService accountIdentityConfirmService;
	//客户税务信息服务类
	private final IAccountTaxationInfoModifyService accountTaxationInfoModifyService;
	//客户开户税务信息服务类
	private final IAccountTaxationInfoService accountTaxationInfoService;
	//客户W-8BEN表格信息服务类
	private final IAccountW8benInfoModifyService accountW8benInfoModifyService;
	//客户开户W-8表格信息服务类
	private final IAccountW8benInfoService accountW8benInfoService;
	//大账户服务类
	private final ICustomerInfoClient customerInfoClient;
	//柜台服务类
	private final ICounterOpenAccountClient counterOpenAccountClient;

	/**
	 * 根据申请记录id查询哪些表修改
	 *
	 * @param applyId
	 * @return
	 */
	private List<Integer> getDataSectionsByApplyId(Long applyId) {
		AccountOpenInfoModifyApplyVO openInfoModifyApplyVO = accountOpenInfoModifyApplyService.selectByApplyId(applyId);
		if (openInfoModifyApplyVO == null) {
			throw new ServiceException("未找到开户修改信息");
		}
		// 查询哪些信息修改（除了开户图片）
		String dataSection = openInfoModifyApplyVO.getDataSection();
		if (StringUtils.isEmpty(dataSection)) {
			return Collections.emptyList();
		}
		List<String> dataSectionList = new ArrayList<>();
		if (dataSection.contains(",")) {
			dataSectionList = Arrays.asList(dataSection.split(","));
		} else {
			dataSectionList.add(dataSection);
		}
		return dataSectionList.stream().map(Integer::valueOf).collect(Collectors.toList());
	}

	/**
	 * 同步修改记录到正式表以及大账户中心
	 *
	 * @param applyId
	 * @param applicationId
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void sync(Long applyId, String applicationId) {
		LambdaUpdateWrapper<AccountOpenInfoModifyApplyEntity> updateStatusWrapper = Wrappers.lambdaUpdate();
		updateStatusWrapper.eq(AccountOpenInfoModifyApplyEntity::getId, applyId);
		try {
			boolean allUpdateImageRes = false;
			boolean updatePersonalInfoRes = false;
			boolean updateIdentityConfirmRes = false;
			boolean updateTaxRes = false;
			boolean updateW8TaxRes = false;
			// 查询是否有图片的修改
			List<AccountOpenImageModifyVO> openImageModifyVos = accountOpenImageModifyService.getOpenImageModifyByApplyId(applyId);
			if (CollUtil.isNotEmpty(openImageModifyVos)) {
				List<AccountOpenImageEntity> accountOpenImages = openImageModifyVos.stream().map(openImageModifyVo -> BeanUtil.copyProperties(openImageModifyVo, AccountOpenImageEntity.class)).collect(Collectors.toList());
				// 判断图片是否都更新成功
				allUpdateImageRes = accountOpenImages.stream().allMatch(accountOpenImage ->
					accountOpenImageService.saveOrUpdate(accountOpenImage, new UpdateWrapper<AccountOpenImageEntity>().lambda().eq(AccountOpenImageEntity::getApplicationId, accountOpenImage.getApplicationId())));
			}

			// 查询修改了哪些表的数据
			List<Integer> dataSections = getDataSectionsByApplyId(applyId);
			// 更新个人信息表
			if (dataSections.contains(CustomOpenAccountEnum.OpenAccountDataType.PERSONAL_PAGE_INFO.getCode()) ||
				dataSections.contains(CustomOpenAccountEnum.OpenAccountDataType.ADDRESS_PAGE_INFO.getCode()) ||
				dataSections.contains(CustomOpenAccountEnum.OpenAccountDataType.OCCUPATION_PAGE_INFO.getCode()) ||
				dataSections.contains(CustomOpenAccountEnum.OpenAccountDataType.ASSET_INVESTMENT_PAGE_INFO.getCode())
			) {
				AccountOpenInfoModifyVO accountOpenInfoModifyVO = accountOpenInfoModifyService.selectByApplyId(applyId);
				if (accountOpenInfoModifyVO != null) {
					AccountOpenInfoEntity accountOpenInfo = BeanUtil.copyProperties(accountOpenInfoModifyVO, AccountOpenInfoEntity.class);
					updatePersonalInfoRes = accountOpenInfoService.update(accountOpenInfo, new UpdateWrapper<AccountOpenInfoEntity>().lambda().eq(AccountOpenInfoEntity::getApplicationId, accountOpenInfoModifyVO.getApplicationId()));
					if (updatePersonalInfoRes) {
						// 已开户客户资料同步至柜台
						R<CustomerAccountVO> result = customerInfoClient.customerAccountInfo(accountOpenInfoModifyVO.getUserId());
						if (result.isSuccess()) {
							CustomerAccountVO.TradeAccount acct = result.getData().getAcct();
							if (acct != null) {
								R<CustomerAllAccountVO> accoutResult = customerInfoClient.customerAllAccountByAccountId(Long.parseLong(acct.getTradeAccount()));
								if (accoutResult.isSuccess()) {
									CustomerAllAccountVO customerAllAccountVO = accoutResult.getData();
									if (customerAllAccountVO.getTradeAccountList() != null) {
										for (CustomerTradeAccountVO customerTradeAccountVO : customerAllAccountVO.getTradeAccountList()) {
											if ("SEC".equals(customerTradeAccountVO.getAccountType())) {
												ModifyAccountReq modifyAccountReq = new ModifyAccountReq();
												modifyAccountReq.setFundAccount(customerTradeAccountVO.getTradeAccount());
												modifyAccountReq.setEmail(accountOpenInfo.getEmail() != null ? accountOpenInfo.getEmail() : null);
												modifyAccountReq.setContactAddress(accountOpenInfo.getContactAddress() != null ? accountOpenInfo.getContactAddress() : null);
												modifyAccountReq.setFamilyAddress(accountOpenInfo.getFamilyAddress() != null ? accountOpenInfo.getFamilyAddress() : null);
												counterOpenAccountClient.updateAccount(modifyAccountReq);
											}
										}
									}
								}
							}
						}
					}
				}
			}
			// 更新身份确认信息
			if (dataSections.contains(CustomOpenAccountEnum.OpenAccountDataType.ACCOUNT_IDENTITY_CONFIRM.getCode())) {
				AccountIdentityConfirmModifyVO confirmModifyVO = accountIdentityConfirmModifyService.selectByApplyId(applyId);
				if (confirmModifyVO != null) {
					AccountIdentityConfirmEntity accountIdentityConfirmEntity = BeanUtil.copyProperties(confirmModifyVO, AccountIdentityConfirmEntity.class);
					updateIdentityConfirmRes = accountIdentityConfirmService.update(accountIdentityConfirmEntity, new UpdateWrapper<AccountIdentityConfirmEntity>().lambda().eq(AccountIdentityConfirmEntity::getApplicationId, confirmModifyVO.getApplicationId()));
				}
			}
			// 更新税务信息
			if (dataSections.contains(CustomOpenAccountEnum.OpenAccountDataType.ACCOUNT_TAXATION_INFO.getCode())) {
				// 1.客户税务信息
				List<AccountTaxationInfoModifyVO> taxationInfoModifyVos = accountTaxationInfoModifyService.selectByApplyId(applyId);
				if (CollUtil.isNotEmpty(taxationInfoModifyVos)) {
					List<AccountTaxationInfoEntity> taxInfoList = taxationInfoModifyVos.stream().map(taxInfoModifyVo -> BeanUtil.copyProperties(taxInfoModifyVo, AccountTaxationInfoEntity.class)).collect(Collectors.toList());
					//先删除之前的数据
					accountTaxationInfoService.deleteByApplicationId(applicationId);
					//再批量保存现在修改提交的数据
					updateTaxRes = accountTaxationInfoService.saveBatch(taxInfoList);
				}

				// 2.W-8BEN表格美国税务局表修改信息
				AccountW8benInfoModifyVO w8benInfoModifyVo = accountW8benInfoModifyService.selectByApplyId(applyId);
				if (w8benInfoModifyVo != null) {
					AccountW8benInfoEntity accountW8benInfoEntity = BeanUtil.copyProperties(w8benInfoModifyVo, AccountW8benInfoEntity.class);
					updateW8TaxRes = accountW8benInfoService.update(accountW8benInfoEntity, new UpdateWrapper<AccountW8benInfoEntity>().lambda().eq(AccountW8benInfoEntity::getApplicationId, w8benInfoModifyVo.getApplicationId()));
				}
			}

			if (allUpdateImageRes || updatePersonalInfoRes || updateIdentityConfirmRes || updateTaxRes || updateW8TaxRes) {
				// 正式表更新成功调用账户中心接口同步
				AccountOpenInfoModifyVO accountOpenInfoModifyVO = accountOpenInfoModifyService.selectByApplyId(applyId);
				AccountOpenInfoVO accountOpenInfoVO = accountOpenInfoService.queryByApplicationId(accountOpenInfoModifyVO.getApplicationId());
				CustomerOpenAccountInfoDTO customerOpenAccountInfoDTO = new CustomerOpenAccountInfoDTO();
				BeanUtil.copyProperties(accountOpenInfoVO, customerOpenAccountInfoDTO);
				customerOpenAccountInfoDTO.setCustId(accountOpenInfoVO.getUserId());
				log.info("更新客户开户信息到大账户中心，提交参数：{}", customerOpenAccountInfoDTO);
				R r = customerInfoClient.updateOpenAccountInfo(customerOpenAccountInfoDTO);
				if (r.isSuccess()) {
					log.info("更新客户开户信息到大账户中心成功，返回信息：{}", r.getMsg());
					// 修改更新、同步状态
					updateStatusWrapper.set(AccountOpenInfoModifyApplyEntity::getUpdateStatus, OpenAccountEnum.UpdateStatusType.UPDATE_SUCCESS.getCode());
					updateStatusWrapper.set(AccountOpenInfoModifyApplyEntity::getSyncStatus, OpenAccountEnum.SyncStatusType.SYNC_SUCCESS.getCode());
				} else {
					log.error("更新客户开户信息到大账户中心失败，失败原因：{}", r.getMsg());
					updateStatusWrapper.set(AccountOpenInfoModifyApplyEntity::getUpdateStatus, OpenAccountEnum.UpdateStatusType.UPDATE_FAIL.getCode());
					updateStatusWrapper.set(AccountOpenInfoModifyApplyEntity::getSyncStatus, OpenAccountEnum.SyncStatusType.SYNC_FAIL.getCode());
				}
				updateStatusWrapper.set(AccountOpenInfoModifyApplyEntity::getUpdateResult, r.getMsg());
				updateStatusWrapper.set(AccountOpenInfoModifyApplyEntity::getSyncResult, r.getMsg());

				//重新生成w8文件 和 个人表格
				log.info("重新生成w8文件 和 个人表格");
				accountOpenInfoService.generateW8Pdf(applicationId);
				accountOpenInfoService.generateSelfPdf(applicationId);
				//String path = customerAccOpenReportGenerate.generateReport(accountOpenInfoEntity.getApplicationId(), accountPdfEnum);

			}
		} catch (Exception e) {
			updateStatusWrapper.set(AccountOpenInfoModifyApplyEntity::getUpdateStatus, OpenAccountEnum.UpdateStatusType.UPDATE_FAIL.getCode());
			updateStatusWrapper.set(AccountOpenInfoModifyApplyEntity::getUpdateResult, e.getMessage());
			updateStatusWrapper.set(AccountOpenInfoModifyApplyEntity::getSyncStatus, OpenAccountEnum.SyncStatusType.SYNC_FAIL.getCode());
			updateStatusWrapper.set(AccountOpenInfoModifyApplyEntity::getSyncResult, e.getMessage());
			log.error("异步更新正式表数据异常,异常详情->{}", e.getMessage());
			throw new ServiceException("更新正式表数据异常!");
		}
		accountOpenInfoModifyApplyService.update(updateStatusWrapper);
	}
}
