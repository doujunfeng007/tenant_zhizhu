package com.minigod.zero.bpmn.module.account.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.minigod.zero.biz.common.utils.FileUtil;
import com.minigod.zero.bpm.enums.OpenAccountEnum;
import com.minigod.zero.bpmn.module.account.api.TaxItem;
import com.minigod.zero.bpmn.module.account.bo.OpenImgBo;
import com.minigod.zero.bpmn.module.account.dto.*;
import com.minigod.zero.bpmn.module.account.entity.*;
import com.minigod.zero.bpmn.module.account.enums.CustomOpenAccountEnum;
import com.minigod.zero.bpmn.module.account.mapper.AccountAdditionalFileMapper;
import com.minigod.zero.bpmn.module.account.mapper.AccountOpenInfoMapper;
import com.minigod.zero.bpmn.module.account.service.*;
import com.minigod.zero.bpmn.module.account.vo.*;
import com.minigod.zero.bpmn.module.common.mapper.AddressMapper;
import com.minigod.zero.bpmn.module.constant.ModifyOpenAccountMessageConstant;
import com.minigod.zero.bpmn.utils.ImageUtils;
import com.minigod.zero.core.i18n.utils.I18nUtil;
import com.minigod.zero.core.log.exception.ServiceException;
import com.minigod.zero.core.oss.model.ZeroFile;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.api.ResultCode;
import com.minigod.zero.resource.feign.IOssClient;
import com.minigod.zero.system.entity.DictBiz;
import com.minigod.zero.system.feign.IDictBizClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static com.minigod.zero.bpmn.module.account.constants.DictTypeConstant.*;

/**
 * 修改客户开户资料门面服务类实现
 *
 * @author eric
 * @since 2024-08-05 16:44:05
 */
@Slf4j
@Service
@AllArgsConstructor
public class AccountOpenInfoModifyFacadeServiceImpl implements IAccountOpenInfoModifyFacadeService {
	//客户开户详细资料服务类
	private final IAccountOpenInfoModifyService accountOpenInfoModifyService;
	//客户身份确认信息服务类
	private final IAccountIdentityConfirmModifyService accountIdentityConfirmModifyService;
	//客户税务信息服务类
	private final IAccountTaxationInfoModifyService accountTaxationInfoModifyService;
	//客户W-8BEN表格信息服务类
	private final IAccountW8benInfoModifyService accountW8benInfoModifyService;
	//客户开户资料修改申请记录服务类
	private final IAccountOpenInfoModifyApplyService accountOpenInfoModifyApplyService;
	//文件上传服务类
	private final IOssClient iOssClient;
	//客户图片修改服务类
	private final IAccountOpenImageModifyService accountOpenImageModifyService;
	//客户开户详细资料服务类
	private final IAccountOpenInfoService accountOpenInfoService;
	//客户开户图片服务类
	private final IAccountOpenImageService accountOpenImageService;
	//客户开户身份信息服务类
	private final IAccountIdentityConfirmService accountIdentityConfirmService;
	//客户开户税务信息服务类
	private final IAccountTaxationInfoService accountTaxationInfoService;
	//客户开户W-8表格信息服务类
	private final IAccountW8benInfoService accountW8benInfoService;
	//客户开户资料修改同步服务类
	private final IAccountOpenInfoModifySyncService accountOpenInfoModifySyncService;

	private final IDictBizClient dictBizClient;

	private final AccountOpenInfoMapper accountOpenInfoMapper;

	private final AddressMapper addressMapper;
	private final AccountAdditionalFileMapper accountAdditionalFileMapper;
	private final String language = "en-US";

	/**
	 * 处理电话号码
	 *
	 * @param areaCode
	 * @param cityCode
	 * @param areaNumber
	 * @return
	 */
	private String concatTel(String areaCode, String cityCode, String areaNumber) {
		StringBuilder tel = new StringBuilder();
		if (StringUtils.isNotBlank(areaCode)) {
			tel.append(areaCode);
			tel.append("-");
		}
		if (StringUtils.isNotBlank(cityCode)) {
			tel.append(cityCode);
			tel.append("-");
		}
		if (StringUtils.isNotBlank(areaNumber)) {
			tel.append(areaNumber);
		}
		return tel.toString();
	}

	/**
	 * 获取客户开户资料详情
	 *
	 * @param userId
	 * @return
	 */
	@Override
	public AccountOpenInfoDetailVO getAccountOpenInfoDetail(Long userId) {
		AccountOpenInfoDetailVO accountOpenInfoDetailVO = new AccountOpenInfoDetailVO();
		if (userId == null) {
			throw new ServiceException(I18nUtil.getMessage(ModifyOpenAccountMessageConstant.MODIFY_OPEN_ACCOUNT_USER_INFO_IS_EMPTY_NOTICE));
		}
		// 根据userid查询applicationId
		AccountOpenInfoVO accountOpenInfoVO = accountOpenInfoService.queryByUserId(userId);
		if (accountOpenInfoVO == null) {
			throw new ServiceException(I18nUtil.getMessage(ModifyOpenAccountMessageConstant.MODIFY_ACCOUNT_OPEN_DATA_NOT_EXIST_NOTICE));
		}
		// 查询客户个人信息
		accountOpenInfoDetailVO.setAccountPersonalInfoVO(BeanUtil.copyProperties(accountOpenInfoVO, AccountPersonalInfoVO.class));
		// 客户地址信息
		accountOpenInfoDetailVO.setAccountAddressInfoVO(BeanUtil.copyProperties(accountOpenInfoVO, AccountAddressInfoVO.class));
		// 客户职业信息
		accountOpenInfoDetailVO.setAccountOccupationInfoVO(BeanUtil.copyProperties(accountOpenInfoVO, AccountOccupationInfoVO.class));
		// 客户资产投资信息
		accountOpenInfoDetailVO.setAccountAssetInvestmentInfoVO(BeanUtil.copyProperties(accountOpenInfoVO, AccountAssetInvestmentInfoVO.class));
		String applicationId = accountOpenInfoVO.getApplicationId();
		// 查询历史图片
		List<AccountOpenImageVO> openImageVOs = accountOpenImageService.queryListByApplicationId(applicationId, null);
		accountOpenInfoDetailVO.setAccountOpenImageVos(openImageVOs);
		//查询历史文件
		List<AccountAdditionalFileVO> accountAdditionalFileVOS = accountAdditionalFileMapper.queryListByApplicationId(applicationId, null);
		accountOpenInfoDetailVO.setAccountAdditionalFileVO(accountAdditionalFileVOS);

		AccountIdentityConfirmVO accountIdentityConfirmVO = accountIdentityConfirmService.queryByApplicationId(applicationId);
		// 客户身份确认信息
		accountOpenInfoDetailVO.setAccountIdentityConfirmVO(accountIdentityConfirmVO);

		AccountTaxInfoVO accountTaxInfoVO = new AccountTaxInfoVO();
		List<AccountTaxationInfoVO> accountTaxationInfoVOs = accountTaxationInfoService.queryListByApplicationId(applicationId);
		if (!accountTaxationInfoVOs.isEmpty()) {
			List<AccountTaxationItemVO> accountTaxationItemVOs = new ArrayList<>();
			for (AccountTaxationInfoVO accountTaxationInfoVO : accountTaxationInfoVOs) {
				AccountTaxationItemVO accountTaxationItemVO = new AccountTaxationItemVO();
				accountTaxationItemVO.setHasTaxCode(accountTaxationInfoVO.getHasTaxNumber());
				accountTaxationItemVO.setReasonDesc(accountTaxationInfoVO.getReasonDesc());
				accountTaxationItemVO.setTaxCode(accountTaxationInfoVO.getTaxNumber());
				accountTaxationItemVO.setTaxFeasonType(accountTaxationInfoVO.getReasonType());
				accountTaxationItemVO.setTaxJurisdiction(accountTaxationInfoVO.getTaxCountry());
				accountTaxationItemVOs.add(accountTaxationItemVO);
			}
			// 客户税务信息
			accountTaxInfoVO.setAccountTaxationItemVOs(accountTaxationItemVOs);
		}
		AccountW8benInfoVO accountW8benInfoVO = accountW8benInfoService.queryByApplicationId(applicationId);
		// W-8BEN表格美国税务局表
		accountTaxInfoVO.setAccountW8benInfoVO(accountW8benInfoVO);

		accountOpenInfoDetailVO.setAccountTaxInfoVO(accountTaxInfoVO);

		return accountOpenInfoDetailVO;
	}

	/**
	 * 提交客户开户资料修改
	 *
	 * @param accountOpenInfoModifyJSonDTO
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public boolean submitAccountOpenInfoModify(AccountOpenInfoModifyJSonDTO accountOpenInfoModifyJSonDTO) {
		Long userId = AuthUtil.getCustId();
		if (userId == null) {
			throw new ServiceException(I18nUtil.getMessage(ModifyOpenAccountMessageConstant.MODIFY_OPEN_ACCOUNT_SUBMIT_USER_INFO_IS_EMPTY_NOTICE));
		}
		String tenantId = AuthUtil.getTenantId();
		if (StringUtils.isEmpty(tenantId)) {
			throw new ServiceException(I18nUtil.getMessage(ModifyOpenAccountMessageConstant.MODIFY_OPEN_ACCOUNT_SUBMIT_TENANT_ID_IS_EMPTY_NOTICE));
		}
		// user_id状态是待审核不可重复修改
		AccountOpenInfoModifyApplyEntity checkUserStatus = accountOpenInfoModifyApplyService.getInfoByUserIdStatus(userId);
		if (checkUserStatus != null) {
			throw new ServiceException(I18nUtil.getMessage(ModifyOpenAccountMessageConstant.MODIFY_ACCOUNT_OPEN_DATA_ALREADY_NOTICE));
		}
		// 根据userid查询applicationId
		AccountOpenInfoVO accountOpenInfoVO = accountOpenInfoService.queryByUserId(userId);
		if (accountOpenInfoVO == null) {
			throw new ServiceException(I18nUtil.getMessage(ModifyOpenAccountMessageConstant.MODIFY_ACCOUNT_OPEN_DATA_NOT_EXIST_NOTICE));
		}
		String applicationId = accountOpenInfoVO.getApplicationId();
		//客户个人信息
		AccountPersonalInfoModifyDTO accountPersonalInfoModifyDTO = accountOpenInfoModifyJSonDTO.getAccountPersonalInfo();
		//客户地址信息
		AccountAddressInfoModifyDTO accountAddressInfoModifyDTO = accountOpenInfoModifyJSonDTO.getAccountAddressInfo();
		//客户职业信息
		AccountOccupationInfoModifyDTO accountOccupationInfoModifyDTO = accountOpenInfoModifyJSonDTO.getAccountOccupationInfo();
		//客户资产投资信息
		AccountAssetInvestmentInfoModifyDTO accountAssetInvestmentInfoModifyDTO = accountOpenInfoModifyJSonDTO.getAccountAssetInvestmentInfo();
		//客户身份确认信息
		AccountIdentityConfirmModifyDTO accountIdentityConfirmModifyDTO = accountOpenInfoModifyJSonDTO.getAccountIdentityConfirm();
		//客户税务信息
		AccountTaxationInfoModifyDTO accountTaxationInfoModifyDTO = accountOpenInfoModifyJSonDTO.getAccountTaxationInfo();

		try {
			List<Integer> dataSectionList = new ArrayList<>();
			boolean accountOpenInfoModified = false;
			//客户开户资料申请记录
			AccountOpenInfoModifyApplyEntity accountOpenInfoModifyApplyEntity = new AccountOpenInfoModifyApplyEntity();
			accountOpenInfoModifyApplyEntity.setApplicationId(applicationId);
			accountOpenInfoModifyApplyEntity.setUserId(userId);
			accountOpenInfoModifyApplyEntity.setTenantId(tenantId);
			accountOpenInfoModifyApplyEntity.setApproveStatus(OpenAccountEnum.OpenAccountModifyApproveStatus.APPROVE_PENDING.getCode());
			accountOpenInfoModifyApplyEntity.setUpdateStatus(OpenAccountEnum.UpdateStatusType.UPDATE_PENDING.getCode());
			accountOpenInfoModifyApplyEntity.setSyncStatus(OpenAccountEnum.SyncStatusType.SYNC_PENDING.getCode());
			accountOpenInfoModifyApplyService.insert(accountOpenInfoModifyApplyEntity);

			// 修改开户资料图片的apply_id
			LambdaUpdateWrapper<AccountOpenImageModifyEntity> updateWrapper = Wrappers.lambdaUpdate();
			updateWrapper.set(AccountOpenImageModifyEntity::getApplyId, accountOpenInfoModifyApplyEntity.getId());
			updateWrapper.eq(AccountOpenImageModifyEntity::getApplicationId, applicationId);
			accountOpenImageModifyService.update(updateWrapper);

			//客户开户资料(包含：客户个人信息、客户地址信息、客户职业信息、客户资产投资信息)
			AccountOpenInfoModifyEntity accountOpenInfoModifyEntity = new AccountOpenInfoModifyEntity();
			accountOpenInfoModifyEntity.setApplicationId(applicationId);
			accountOpenInfoModifyEntity.setUserId(userId);
			accountOpenInfoModifyEntity.setApplyId(accountOpenInfoModifyApplyEntity.getId());
			accountOpenInfoModifyEntity.setTenantId(tenantId);

			if (accountPersonalInfoModifyDTO != null) {
				accountOpenInfoModified = true;
				//填充【客户个人信息】到客户开户资料对象
				BeanUtil.copyProperties(accountPersonalInfoModifyDTO, accountOpenInfoModifyEntity);
				String familyPhone = concatTel(accountPersonalInfoModifyDTO.getAreaCode(), accountPersonalInfoModifyDTO.getCityCode(), accountPersonalInfoModifyDTO.getAreaNumber());
				accountOpenInfoModifyEntity.setFamilyPhone(familyPhone);
				dataSectionList.add(CustomOpenAccountEnum.OpenAccountDataType.PERSONAL_PAGE_INFO.getCode());
			}
			if (accountAddressInfoModifyDTO != null) {
				accountOpenInfoModified = true;
				//填充【客户地址信息】到客户开户资料对象
				BeanUtil.copyProperties(accountAddressInfoModifyDTO, accountOpenInfoModifyEntity);
				dataSectionList.add(CustomOpenAccountEnum.OpenAccountDataType.ADDRESS_PAGE_INFO.getCode());
			}
			if (accountOccupationInfoModifyDTO != null) {
				accountOpenInfoModified = true;
				//填充【客户职业信息】到客户开户资料对象
				BeanUtil.copyProperties(accountOccupationInfoModifyDTO, accountOpenInfoModifyEntity);
				String companyPhone = concatTel(accountOccupationInfoModifyDTO.getCompanyPhoneAreaCode(), accountOccupationInfoModifyDTO.getCompanyPhoneCityCode(), accountOccupationInfoModifyDTO.getCompanyPhoneNumber());
				accountOpenInfoModifyEntity.setCompanyPhoneNumber(companyPhone);
				String companyFacsimile = concatTel(accountOccupationInfoModifyDTO.getCompanyFacsimileAreaCode(), accountOccupationInfoModifyDTO.getCompanyFacsimileCityCode(), accountOccupationInfoModifyDTO.getCompanyFacsimile());
				accountOpenInfoModifyEntity.setCompanyFacsimile(companyFacsimile);
				dataSectionList.add(CustomOpenAccountEnum.OpenAccountDataType.OCCUPATION_PAGE_INFO.getCode());
			}
			if (accountAssetInvestmentInfoModifyDTO != null) {
				//填充【客户资产投资信息】到客户开户资料对象
				accountOpenInfoModified = true;
				BeanUtil.copyProperties(accountAssetInvestmentInfoModifyDTO, accountOpenInfoModifyEntity);
				dataSectionList.add(CustomOpenAccountEnum.OpenAccountDataType.ASSET_INVESTMENT_PAGE_INFO.getCode());
			}
			if (accountOpenInfoModified) {
				//持久化客户开户资料表
				accountOpenInfoModifyService.insert(accountOpenInfoModifyEntity);
			}

			//客户身份确认信息
			AccountIdentityConfirmModifyEntity accountIdentityConfirmModifyEntity = new AccountIdentityConfirmModifyEntity();
			accountIdentityConfirmModifyEntity.setApplicationId(applicationId);
			accountIdentityConfirmModifyEntity.setUserId(userId);
			accountIdentityConfirmModifyEntity.setApplyId(accountOpenInfoModifyApplyEntity.getId());
			accountIdentityConfirmModifyEntity.setTenantId(tenantId);

			if (accountIdentityConfirmModifyDTO != null) {
				//填充【客户身份确认信息】到客户身份确认信息对象
				BeanUtil.copyProperties(accountIdentityConfirmModifyDTO, accountIdentityConfirmModifyEntity);
				accountIdentityConfirmModifyService.insert(accountIdentityConfirmModifyEntity);
				dataSectionList.add(CustomOpenAccountEnum.OpenAccountDataType.ACCOUNT_IDENTITY_CONFIRM.getCode());
			}

			//客户税务信息
			if (accountTaxationInfoModifyDTO != null) {
				//填充【客户税务信息】到客户税务信息对象
				//1.客户税务信息
				List<TaxItem> taxInfoList = accountTaxationInfoModifyDTO.getTaxInfoList();
				if (CollUtil.isNotEmpty(taxInfoList)) {
					List<AccountTaxationInfoModifyEntity> taxationInfoList = taxInfoList.stream().map(taxItem -> {
						AccountTaxationInfoModifyEntity accountTaxationInfoModifyEntity = new AccountTaxationInfoModifyEntity();
						accountTaxationInfoModifyEntity.setApplicationId(applicationId);
						accountTaxationInfoModifyEntity.setUserId(userId);
						accountTaxationInfoModifyEntity.setApplyId(accountOpenInfoModifyApplyEntity.getId());
						accountTaxationInfoModifyEntity.setHasTaxNumber(taxItem.getHasTaxCode());
						accountTaxationInfoModifyEntity.setTaxNumber(taxItem.getTaxCode());
						accountTaxationInfoModifyEntity.setReasonDesc(taxItem.getReasonDesc());
						accountTaxationInfoModifyEntity.setReasonType(taxItem.getTaxFeasonType());
						accountTaxationInfoModifyEntity.setTaxCountry(taxItem.getTaxJurisdiction());
						accountTaxationInfoModifyEntity.setTenantId(tenantId);
						return accountTaxationInfoModifyEntity;
					}).collect(Collectors.toList());
					accountTaxationInfoModifyService.saveBatch(taxationInfoList);
				}
				//2. W-8BEN表格美国税务局表修改信息
				AccountW8benInfoModifyEntity accountW8benInfoModifyEntity = new AccountW8benInfoModifyEntity();
				accountW8benInfoModifyEntity.setApplicationId(applicationId);
				accountW8benInfoModifyEntity.setUserId(userId);
				accountW8benInfoModifyEntity.setApplyId(accountOpenInfoModifyApplyEntity.getId());
				accountW8benInfoModifyEntity.setTenantId(tenantId);

				accountW8benInfoModifyEntity.setW8PermanentResidenceAddressCountry(accountTaxationInfoModifyDTO.getPermanentCountryAddress());
				//大陆和台湾地区省市区拆分（1=大陆，4=台湾）
				if (StringUtils.isNotEmpty(accountTaxationInfoModifyDTO.getPermanentCountryAddress()) &&
					accountTaxationInfoModifyDTO.getPermanentCountryAddress().equals("1")) {
					String address = accountTaxationInfoModifyDTO.getPermanentProvinceName() + ", " + accountTaxationInfoModifyDTO.getPermanentCityName() + ", " + accountTaxationInfoModifyDTO.getPermanentCountyName();
					accountW8benInfoModifyEntity.setW8PermanentResidenceProvinceCityCounty(address);
				}
				accountW8benInfoModifyEntity.setW8PermanentResidenceAddress(accountTaxationInfoModifyDTO.getPermanentAddress());
				accountW8benInfoModifyEntity.setW8MailingAddressCountry(accountTaxationInfoModifyDTO.getMailingCountryAddress());
				//大陆和台湾地区省市区拆分（1=大陆，4=台湾）
				if (StringUtils.isNotEmpty(accountTaxationInfoModifyDTO.getMailingCountryAddress()) &&
					accountTaxationInfoModifyDTO.getMailingCountryAddress().equals("1")) {
					String address = accountTaxationInfoModifyDTO.getMailingProvinceName() + ", " + accountTaxationInfoModifyDTO.getMailingCityName() + ", " + accountTaxationInfoModifyDTO.getMailingCountyName();
					accountW8benInfoModifyEntity.setW8MailingAddressProvinceCityCounty(address);
				}
				accountW8benInfoModifyEntity.setW8MailingAddress(accountTaxationInfoModifyDTO.getMailingAddress());
				accountW8benInfoModifyService.insert(accountW8benInfoModifyEntity);

				dataSectionList.add(CustomOpenAccountEnum.OpenAccountDataType.ACCOUNT_TAXATION_INFO.getCode());
			}

			// 修改申请记录表的修改数据标识
			accountOpenInfoModifyApplyEntity.setDataSection(dataSectionList.stream().map(String::valueOf).collect(Collectors.joining(",")));
			return accountOpenInfoModifyApplyService.updateById(accountOpenInfoModifyApplyEntity);
		} catch (Exception exception) {
			//捕获后将异常抛出,以便事务回滚
			exception.printStackTrace();
			throw new ServiceException(String.format(I18nUtil.getMessage(ModifyOpenAccountMessageConstant.MODIFY_OPEN_ACCOUNT_SUBMIT_EXCEPTION_NOTICE), exception.getMessage()));
		}
	}

	/**
	 * 保存用户开户图片数据
	 *
	 * @param params
	 * @return
	 */
	@Override
	public OpenImgVo saveAccountImg(OpenImgBo params) {
		String location = params.getLocation();
		String type = params.getType();
		String base64Img = params.getImgBase64();
		// 参数校验 - 基本
		if (StringUtils.isBlank(location) || StringUtils.isBlank(type) || StringUtils.isBlank(base64Img)) {
			log.error("参数异常: OpenImgResVo_Value_CN");
			throw new ServiceException(I18nUtil.getMessage(ModifyOpenAccountMessageConstant.MODIFY_OPEN_ACCOUNT_SAVE_IMAGE_ERROR_NOTICE));
		}
		Long userId = AuthUtil.getCustId();
		if (userId == null) {
			throw new ServiceException(I18nUtil.getMessage(ModifyOpenAccountMessageConstant.MODIFY_OPEN_ACCOUNT_NO_LOGIN_USER_NOTICE));
		}
		byte[] b = ImageUtils.base64StringToImage(params.getImgBase64());
		String fileName = userId + "_" + params.getType() + "_" + System.currentTimeMillis() + ".jpg";

		MultipartFile file = FileUtil.getMultipartFile(fileName, b);
		R<ZeroFile> ossResp = iOssClient.uploadMinioFile(file, file.getOriginalFilename());
		if (null == ossResp || null == ossResp.getData() || org.apache.commons.lang.StringUtils.isBlank(ossResp.getData().getLink())) {
			throw new ServiceException(ResultCode.H5_DISPLAY_ERROR);
		}
		ZeroFile zeroFile = ossResp.getData();

		// userId查询applyId、applicationId
		AccountOpenInfoVO accountOpenInfoVO = accountOpenInfoService.queryByUserId(userId);
		OpenImgVo resVo = new OpenImgVo();
		if (accountOpenInfoVO == null) {
			return resVo;
		}
		// 查询当前步骤缓存数据
		AccountOpenImageModifyEntity accountOpenImageModifyEntity = accountOpenImageModifyService.selectByApplicationId(accountOpenInfoVO.getApplicationId(), type);
		if (accountOpenImageModifyEntity != null) {
			accountOpenImageModifyEntity.setTenantId(AuthUtil.getTenantId());
			accountOpenImageModifyEntity.setApplicationId(accountOpenInfoVO.getApplicationId());
			accountOpenImageModifyEntity.setStoragePath(zeroFile.getLink());
			accountOpenImageModifyEntity.setUpdateTime(new Date());
			accountOpenImageModifyService.updateById(accountOpenImageModifyEntity);
		} else {
			accountOpenImageModifyEntity = new AccountOpenImageModifyEntity();
			accountOpenImageModifyEntity.setTenantId(AuthUtil.getTenantId());
			accountOpenImageModifyEntity.setApplicationId(accountOpenInfoVO.getApplicationId());
			accountOpenImageModifyEntity.setCreateUser(userId);
			accountOpenImageModifyEntity.setImageLocation(Integer.valueOf(location));
			accountOpenImageModifyEntity.setImageLocationType(Integer.valueOf(type));
			accountOpenImageModifyEntity.setStoragePath(zeroFile.getLink());
			accountOpenImageModifyEntity.setCreateTime(new Date());
			accountOpenImageModifyService.save(accountOpenImageModifyEntity);
		}
		resVo.setImgId(accountOpenImageModifyEntity.getId());
		resVo.setImgUrl(zeroFile.getLink());
		Integer imageLocation = accountOpenImageModifyEntity.getImageLocation();
		resVo.setLocation(imageLocation != null ? String.valueOf(imageLocation) : null);
		Integer imageLocationType = accountOpenImageModifyEntity.getImageLocationType();
		resVo.setType(imageLocationType != null ? String.valueOf(imageLocationType) : null);
		return resVo;
	}

	/**
	 * 根据申请记录id查询哪些表修改
	 *
	 * @param
	 * @return
	 */
	public List<Integer> getDataSectionsByApplyId(Long applyId) {
		AccountOpenInfoModifyApplyVO openInfoModifyApplyVO = accountOpenInfoModifyApplyService.selectByApplyId(applyId);
		if (openInfoModifyApplyVO == null) {
			throw new ServiceException(I18nUtil.getMessage(ModifyOpenAccountMessageConstant.MODIFY_OPEN_ACCOUNT_NOT_FOUND_APPLY_RECORD_NOTICE));
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
	 * 根据申请记录id查询所有开户详情
	 *
	 * @param applyId
	 * @return
	 */
	@Override
	public AccountOpenInfoModifyDetailVO getAccountOpenModifyDetail(String applyId) {
		Long applyIdLong = Long.valueOf(applyId);
		AccountOpenInfoModifyApplyVO openInfoModifyApplyVO = accountOpenInfoModifyApplyService.selectByApplyId(applyIdLong);
		if (openInfoModifyApplyVO == null) {
			throw new ServiceException(I18nUtil.getMessage(ModifyOpenAccountMessageConstant.MODIFY_OPEN_ACCOUNT_NOT_FOUND_APPLY_RECORD_NOTICE));
		}
		AccountOpenInfoModifyDetailVO accountOpenInfoModifyDetailVO = new AccountOpenInfoModifyDetailVO();
		OpenAccountEnum.OpenAccountModifyApproveStatus approveStatus = OpenAccountEnum.OpenAccountModifyApproveStatus.getOpenAccountModifyApproveStatus(openInfoModifyApplyVO.getApproveStatus());
		if (approveStatus != null) {
			openInfoModifyApplyVO.setApplyStatusName(approveStatus.getMessage());
		}
		// 开户资料修改申请记录
		accountOpenInfoModifyDetailVO.setAccountOpenInfoModifyApplyVO(openInfoModifyApplyVO);
		// 查询历史图片修改
		List<AccountOpenImageModifyVO> openImageModifyVos = accountOpenImageModifyService.getOpenImageModifyByApplyId(applyIdLong);
		accountOpenInfoModifyDetailVO.setAccountOpenImageModifyVOs(openImageModifyVos);
		// 查询历史图片
		List<AccountOpenImageVO> openImageVOs = accountOpenImageService.queryListByApplicationId(openInfoModifyApplyVO.getApplicationId(), null);
		accountOpenInfoModifyDetailVO.setAccountOpenImageVos(openImageVOs);
		// 查询哪些表修改的标识
		List<Integer> dataSections = getDataSectionsByApplyId(applyIdLong);
		if (CollUtil.isEmpty(dataSections)) {
			return accountOpenInfoModifyDetailVO;
		}

		//修改的信息
		AccountOpenInfoModifyVO accountOpenInfoModifyVO = accountOpenInfoModifyService.selectByApplyId(applyIdLong);
		//开户信息
		AccountOpenInfoVO accountOpenInfoVO = accountOpenInfoService.queryByApplicationId(openInfoModifyApplyVO.getApplicationId());
		// 个人信息
		if (dataSections.contains(CustomOpenAccountEnum.OpenAccountDataType.PERSONAL_PAGE_INFO.getCode())) {
			if (accountOpenInfoModifyVO != null) {
				// 查询客户个人信息修改
				accountOpenInfoModifyDetailVO.setAccountPersonalInfoModifyVO(BeanUtil.copyProperties(accountOpenInfoModifyVO, AccountPersonalInfoModifyVO.class));
			}
			if (accountOpenInfoVO != null) {
				// 查询客户个人信息
				accountOpenInfoModifyDetailVO.setAccountPersonalInfoVO(BeanUtil.copyProperties(accountOpenInfoVO, AccountPersonalInfoVO.class));
			}
		}
		// 地址信息
		if (dataSections.contains(CustomOpenAccountEnum.OpenAccountDataType.ADDRESS_PAGE_INFO.getCode())) {
			if (accountOpenInfoModifyVO != null) {
				// 客户地址信息修改
				accountOpenInfoModifyService.transferAddressName(accountOpenInfoModifyVO);
				accountOpenInfoModifyDetailVO.setAccountAddressInfoModifyVO(BeanUtil.copyProperties(accountOpenInfoModifyVO, AccountAddressInfoModifyVO.class));
			}
			if (accountOpenInfoVO != null) {
				// 客户地址信息
				accountOpenInfoModifyDetailVO.setAccountAddressInfoVO(BeanUtil.copyProperties(accountOpenInfoVO, AccountAddressInfoVO.class));
			}
		}
		// 职业信息
		if (dataSections.contains(CustomOpenAccountEnum.OpenAccountDataType.OCCUPATION_PAGE_INFO.getCode())) {
			if (accountOpenInfoModifyVO != null) {
				// 客户职业信息修改
				AccountOccupationInfoModifyVO occupationInfoModifyVO = new AccountOccupationInfoModifyVO();
				BeanUtil.copyProperties(accountOpenInfoModifyVO, occupationInfoModifyVO);
				// 就业情况类型
				if (accountOpenInfoModifyVO.getProfessionCode() != null) {
					List<String> professionCodeList = getValuesByCode(PROFESSION_CODE, String.valueOf(accountOpenInfoModifyVO.getProfessionCode()));
					if (CollUtil.isNotEmpty(professionCodeList)) {
						occupationInfoModifyVO.setProfessionCodeName(professionCodeList.get(0));
					}
				}
				// 职业职位
				if (accountOpenInfoModifyVO.getJobPosition() != null) {
					List<String> jobPositionList = getValuesByCode(JOB_POSITION, String.valueOf(accountOpenInfoModifyVO.getJobPosition()));
					if (CollUtil.isNotEmpty(jobPositionList)) {
						occupationInfoModifyVO.setJobPositionName(jobPositionList.get(0));
					}
				}
				// 业务性质
				if (accountOpenInfoModifyVO.getCompanyBusinessNature() != null) {
					List<String> businessNatureList = getValuesByCode(BUSINESS_NATURE, String.valueOf(accountOpenInfoModifyVO.getCompanyBusinessNature()));
					if (CollUtil.isNotEmpty(businessNatureList)) {
						occupationInfoModifyVO.setCompanyBusinessNatureName(businessNatureList.get(0));
					}
				}
				accountOpenInfoModifyDetailVO.setAccountOccupationInfoModifyVO(occupationInfoModifyVO);
			}
			if (accountOpenInfoVO != null) {
				AccountOccupationInfoVO accountOccupationInfoVO = new AccountOccupationInfoVO();
				BeanUtil.copyProperties(accountOpenInfoVO, accountOccupationInfoVO);
				// 就业情况类型
				if (accountOpenInfoVO.getProfessionCode() != null) {
					List<String> professionCodeList = getValuesByCode(PROFESSION_CODE, String.valueOf(accountOpenInfoVO.getProfessionCode()));
					if (CollUtil.isNotEmpty(professionCodeList)) {
						accountOccupationInfoVO.setProfessionCodeName(professionCodeList.get(0));
					}
				}

				// 职业职位
				if (accountOpenInfoVO.getJobPosition() != null) {
					List<String> jobPositionList = getValuesByCode(JOB_POSITION, String.valueOf(accountOpenInfoVO.getJobPosition()));
					if (CollUtil.isNotEmpty(jobPositionList)) {
						accountOccupationInfoVO.setJobPositionName(jobPositionList.get(0));
					}
				}

				// 业务性质
				if (accountOpenInfoVO.getCompanyBusinessNature() != null) {
					List<String> businessNatureList = getValuesByCode(BUSINESS_NATURE, String.valueOf(accountOpenInfoVO.getCompanyBusinessNature()));
					if (CollUtil.isNotEmpty(businessNatureList)) {
						accountOccupationInfoVO.setCompanyBusinessNatureName(businessNatureList.get(0));
					}
				}

				// 客户职业信息
				accountOpenInfoModifyDetailVO.setAccountOccupationInfoVO(accountOccupationInfoVO);
			}
		}
		// 资产信息
		if (dataSections.contains(CustomOpenAccountEnum.OpenAccountDataType.ASSET_INVESTMENT_PAGE_INFO.getCode())) {
			if (accountOpenInfoModifyVO != null) {
				// 客户资产投资信息修改
				AccountAssetInvestmentInfoModifyVO assetInvestmentModifyVO = new AccountAssetInvestmentInfoModifyVO();
				BeanUtil.copyProperties(accountOpenInfoModifyVO, assetInvestmentModifyVO);
				// 全年收入(港币)
				if (accountOpenInfoModifyVO.getAnnualIncome() != null) {
					List<String> annualIncomeHkdList = getValuesByCode(ANNUAL_INCOME_HKD, String.valueOf(accountOpenInfoModifyVO.getAnnualIncome()));
					if (CollUtil.isNotEmpty(annualIncomeHkdList)) {
						assetInvestmentModifyVO.setAnnualIncomeName(annualIncomeHkdList.get(0));
					}
				}
				// 净资产(港币)
				if (accountOpenInfoModifyVO.getNetAsset() != null) {
					List<String> netAssetList = getValuesByCode(NET_ASSET, String.valueOf(accountOpenInfoModifyVO.getNetAsset()));
					if (CollUtil.isNotEmpty(netAssetList)) {
						assetInvestmentModifyVO.setNetAssetName(netAssetList.get(0));
					}
				}
				// 预计每月交易金额(港币)
				if (accountOpenInfoModifyVO.getTradeAmount() != null) {
					List<String> tradeAmountList = getValuesByCode(TRADE_STOCK_MONEY, String.valueOf(accountOpenInfoModifyVO.getTradeAmount()));
					if (CollUtil.isNotEmpty(tradeAmountList)) {
						assetInvestmentModifyVO.setTradeAmountName(tradeAmountList.get(0));
					}
				}
				// 客户资产投资信息修改
				accountOpenInfoModifyService.transferTradeName(assetInvestmentModifyVO);
				accountOpenInfoModifyDetailVO.setAccountAssetInvestmentInfoModifyVO(assetInvestmentModifyVO);
			}
			if (accountOpenInfoVO != null) {
				// 客户资产投资信息
				AccountAssetInvestmentInfoVO assetInvestmentVO = new AccountAssetInvestmentInfoVO();
				BeanUtil.copyProperties(accountOpenInfoVO, assetInvestmentVO);
				// 全年收入(港币)
				if (accountOpenInfoVO.getAnnualIncome() != null) {
					List<String> annualIncomeHkdList = getValuesByCode(ANNUAL_INCOME_HKD, String.valueOf(accountOpenInfoVO.getAnnualIncome()));
					if (CollUtil.isNotEmpty(annualIncomeHkdList)) {
						assetInvestmentVO.setAnnualIncomeName(annualIncomeHkdList.get(0));
					}
				}
				// 净资产(港币)
				if (accountOpenInfoVO.getNetAsset() != null) {
					List<String> netAssetList = getValuesByCode(NET_ASSET, String.valueOf(accountOpenInfoVO.getNetAsset()));
					if (CollUtil.isNotEmpty(netAssetList)) {
						assetInvestmentVO.setNetAssetName(netAssetList.get(0));
					}
				}

				// 预计每月交易金额(港币)
				if (accountOpenInfoVO.getTradeAmount() != null) {
					List<String> tradeAmountList = getValuesByCode(TRADE_STOCK_MONEY, String.valueOf(accountOpenInfoVO.getTradeAmount()));
					if (CollUtil.isNotEmpty(tradeAmountList)) {
						assetInvestmentVO.setTradeAmountName(tradeAmountList.get(0));
					}
				}
				// 将交易名称根据字典及编码转换
				accountOpenInfoModifyService.transferTradeName(assetInvestmentVO);
				accountOpenInfoModifyDetailVO.setAccountAssetInvestmentInfoVO(assetInvestmentVO);
			}
		}
		// 身份确认信息
		if (dataSections.contains(CustomOpenAccountEnum.OpenAccountDataType.ACCOUNT_IDENTITY_CONFIRM.getCode())) {
			AccountIdentityConfirmModifyVO accountIdentityConfirmModifyVO = accountIdentityConfirmModifyService.selectByApplyId(applyIdLong);
			// 客户身份确认信息修改
			accountOpenInfoModifyDetailVO.setAccountIdentityConfirmModifyVO(accountIdentityConfirmModifyVO);
			AccountIdentityConfirmVO accountIdentityConfirmVO = accountIdentityConfirmService.queryByApplicationId(openInfoModifyApplyVO.getApplicationId());
			// 客户身份确认信息
			accountOpenInfoModifyDetailVO.setAccountIdentityConfirmVO(accountIdentityConfirmVO);
		}
		// 税务信息
		if (dataSections.contains(CustomOpenAccountEnum.OpenAccountDataType.ACCOUNT_TAXATION_INFO.getCode())) {
			AccountTaxInfoModifyVO accountTaxInfoModifyVO = new AccountTaxInfoModifyVO();
			List<AccountTaxationInfoModifyVO> accountTaxationInfoModifyVos = accountTaxationInfoModifyService.selectByApplyId(applyIdLong);
			if (CollUtil.isNotEmpty(accountTaxationInfoModifyVos)) {
				accountTaxationInfoModifyVos.forEach(accountTaxModifyVo -> {
					accountTaxModifyVo.setTaxCountry(dictBizClient.getValue(COUNTRY_OR_REGION, accountTaxModifyVo.getTaxCountry()).getData());
				});
			}
			// 客户税务信息修改
			accountTaxInfoModifyVO.setAccountTaxationInfoModifyVos(accountTaxationInfoModifyVos);
			// W-8BEN表格美国税务局表修改
			AccountW8benInfoModifyVO accountW8benInfoModifyVO = accountW8benInfoModifyService.selectByApplyId(applyIdLong);
			accountTaxInfoModifyVO.setAccountW8benInfoModifyVO(accountW8benInfoModifyVO);
			accountOpenInfoModifyDetailVO.setAccountTaxInfoModifyVO(accountTaxInfoModifyVO);

			AccountTaxInfoVO accountTaxInfoVO = new AccountTaxInfoVO();
			List<AccountTaxationInfoVO> accountTaxationInfoVOs = accountTaxationInfoService.queryListByApplicationId(openInfoModifyApplyVO.getApplicationId());
			if (CollUtil.isNotEmpty(accountTaxationInfoVOs)) {
				accountTaxationInfoVOs.forEach(accountTaxInfoVo -> {
					accountTaxInfoVo.setTaxCountry(dictBizClient.getValue(COUNTRY_OR_REGION, accountTaxInfoVo.getTaxCountry()).getData());
				});
			}
			// 客户税务信息
			accountTaxInfoVO.setAccountTaxationInfoVOs(accountTaxationInfoVOs);
			AccountW8benInfoVO accountW8benInfoVO = accountW8benInfoService.queryByApplicationId(openInfoModifyApplyVO.getApplicationId());
			// W-8BEN表格美国税务局表
			accountTaxInfoVO.setAccountW8benInfoVO(accountW8benInfoVO);

			accountOpenInfoModifyDetailVO.setAccountTaxInfoVO(accountTaxInfoVO);
		}

		return accountOpenInfoModifyDetailVO;
	}

	/**
	 * 根据字典的code、key查询所有的字典值
	 *
	 * @param
	 * @return
	 */
	public List<String> getValuesByCode(String code, String dictKey) {
		R<List<DictBiz>> dictRiskType = dictBizClient.getListByTenantId(AuthUtil.getTenantId(), code);
		if (!dictRiskType.isSuccess()) {
			return Collections.emptyList();
		}
		List<DictBiz> dictBizData = dictRiskType.getData();
		if (CollUtil.isEmpty(dictBizData)) {
			return Collections.emptyList();
		}
		List<String> dictValues = new ArrayList<>();
		dictBizData.forEach(d -> {
			if (d.getDictKey().equals(dictKey)) {
				dictValues.add(d.getDictValue());
			}
		});
		return dictValues;
	}

	/**
	 * 审核
	 *
	 * @param modifyApplyApproveDTO
	 * @return
	 */
	@Override
	public Boolean approveByApplyId(AccountOpenInfoModifyApplyApproveDTO modifyApplyApproveDTO) {
		String applyId = modifyApplyApproveDTO.getApplyId();
		if (StringUtils.isEmpty(applyId)) {
			throw new ServiceException(I18nUtil.getMessage(ModifyOpenAccountMessageConstant.MODIFY_OPEN_ACCOUNT_AUDIT_OPERATION_APPLY_ID_NOTICE));
		}
		Long applyIdLong = Long.valueOf(applyId);
		// 查询是否有审核记录
		AccountOpenInfoModifyApplyEntity accountOpenInfoModifyApply = accountOpenInfoModifyApplyService.getById(applyIdLong);
		if (accountOpenInfoModifyApply == null) {
			throw new ServiceException(I18nUtil.getMessage(ModifyOpenAccountMessageConstant.MODIFY_OPEN_ACCOUNT_AUDIT_OPERATION_RECORD_NOTICE));
		}
		String applicationId = accountOpenInfoModifyApply.getApplicationId();

		// 更新审核状态
		LambdaUpdateWrapper<AccountOpenInfoModifyApplyEntity> updateWrapper = Wrappers.lambdaUpdate();
		updateWrapper.set(AccountOpenInfoModifyApplyEntity::getApproveStatus, modifyApplyApproveDTO.getApproveStatus());
		updateWrapper.set(AccountOpenInfoModifyApplyEntity::getApproveComment, modifyApplyApproveDTO.getApproveComment());
		updateWrapper.eq(AccountOpenInfoModifyApplyEntity::getId, applyIdLong);
		boolean isSuccess = accountOpenInfoModifyApplyService.update(updateWrapper);

		// 如果状态是审核通过，异步更新数据到正式库
		if (isSuccess && modifyApplyApproveDTO.getApproveStatus().equals(OpenAccountEnum.OrganizationOpenApproveStatus.APPROVE_SUCCESS.getCode())) {
			CompletableFuture.runAsync(() -> {
				accountOpenInfoModifySyncService.sync(applyIdLong, applicationId);
			});
		}
		return isSuccess;
	}
}
