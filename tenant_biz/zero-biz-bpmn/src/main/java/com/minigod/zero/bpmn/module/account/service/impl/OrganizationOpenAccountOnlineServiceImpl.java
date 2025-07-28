package com.minigod.zero.bpmn.module.account.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.biz.common.utils.GlobalExecutorService;
import com.minigod.zero.bpm.enums.OpenAccountEnum;
import com.minigod.zero.bpmn.module.account.bo.OrganizationOpenInfoBo;
import com.minigod.zero.bpmn.module.account.bo.OrganizationOpenShareholderInfoBo;
import com.minigod.zero.bpmn.module.account.bo.query.OrganizationOpenInfoQuery;
import com.minigod.zero.bpmn.module.account.constants.DictTypeConstant;
import com.minigod.zero.bpmn.module.account.entity.OrganizationOpenInfoEntity;
import com.minigod.zero.bpmn.module.account.entity.OrganizationOpenShareholderInfoEntity;
import com.minigod.zero.bpmn.module.account.enums.*;
import com.minigod.zero.bpmn.module.account.mapper.OrganizationOpenInfoMapper;
import com.minigod.zero.bpmn.module.account.mapper.OrganizationOpenShareholderInfoMapper;
import com.minigod.zero.bpmn.module.account.service.IOrganizationOpenAccountOnlineService;
import com.minigod.zero.bpmn.module.account.service.IOrganizationOpenShareholderInfoService;
import com.minigod.zero.bpmn.module.account.vo.OrganizationOpenInfoVO;
import com.minigod.zero.bpmn.module.account.vo.OrganizationOpenShareholderInfoVO;
import com.minigod.zero.bpmn.module.constant.OrganizationOpenAccountMessageConstant;
import com.minigod.zero.bpmn.module.feign.IOrganizationAccountInfoClient;
import com.minigod.zero.bpmn.module.feign.dto.OrganizationOpenAccountDTO;
import com.minigod.zero.bpmn.module.feign.vo.CustomerInfoVO;
import com.minigod.zero.bpmn.utils.DateUtils;
import com.minigod.zero.core.i18n.utils.I18nUtil;
import com.minigod.zero.core.log.exception.ServiceException;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.core.oss.model.ZeroFile;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.api.ResultCode;
import com.minigod.zero.core.tool.support.Kv;
import com.minigod.zero.core.tool.utils.WebUtil;
import com.minigod.zero.platform.dto.SendEmailDTO;
import com.minigod.zero.platform.enums.*;
import com.minigod.zero.platform.feign.IPlatformMsgClient;
import com.minigod.zero.platform.utils.PushUtil;
import com.minigod.zero.resource.feign.IOssClient;
import com.minigod.zero.system.entity.DictBiz;
import com.minigod.zero.system.feign.IDictBizClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class OrganizationOpenAccountOnlineServiceImpl extends BaseServiceImpl<OrganizationOpenInfoMapper, OrganizationOpenInfoEntity> implements IOrganizationOpenAccountOnlineService {
	@Resource
	private final OrganizationOpenInfoMapper organizationOpenInfoMapper;
	@Resource
	private final OrganizationOpenShareholderInfoMapper organizationOpenShareholderInfoMapper;
	private final IOrganizationOpenShareholderInfoService organizationOpenShareholderInfoService;
	private final IOssClient ossClient;
	private final IOrganizationAccountInfoClient organizationAccountInfoClient;
	@Resource
	private IPlatformMsgClient platformMsgClient;
	@Resource
	private IDictBizClient dictBizClient;

	/**
	 * 上传文件
	 *
	 * @param type
	 * @param file
	 * @return
	 */
	@Override
	public R<Kv> uploadFile(Integer type, MultipartFile file) {
		OrganizationOpenFileTypeEnum openFileTypeEnum = OrganizationOpenFileTypeEnum.fromType(type);
		if (openFileTypeEnum == null) {
			return R.fail(ResultCode.PARAMETER_DISMATCH);
		}
		try {
			Long startTime = System.currentTimeMillis();
			if (type == null || file == null) {
				return R.fail(ResultCode.PARAM_MISS);
			}
			String originalFileName = file.getOriginalFilename();
			String suffix = StringUtils.substring(originalFileName, originalFileName.lastIndexOf("."), originalFileName.length());
			String uuid = IdUtil.fastSimpleUUID();

			String path = DateUtils.datePath() + "/" + uuid;
			R<ZeroFile> zeroFileR = ossClient.uploadMinioFile(file, path + suffix);

			if (!zeroFileR.isSuccess()) {
				throw new ServiceException(zeroFileR.getMsg());
			}

			log.info("上传文件(" + openFileTypeEnum.getDescription() + ")完成,耗时：" + (System.currentTimeMillis() - startTime));
			return R.data(Kv.create().set("filePath", zeroFileR.getData().getLink()));
		} catch (Exception exception) {
			log.error("上传文件(" + openFileTypeEnum.getDescription() + ")异常,异常信息:", exception);
			return R.fail(String.format(I18nUtil.getMessage(OrganizationOpenAccountMessageConstant.ORGANIZATION_OPEN_ACCOUNT_UPLOADFILE_FAILED_NOTICE), openFileTypeEnum.getDescription()));
		}
	}

	/**
	 * 检测手机号是否已开户
	 *
	 * @param phoneNumber
	 * @param phoneAreaCode
	 * @return
	 */
	@Override
	public Boolean isOpenAccount(String phoneNumber, String phoneAreaCode) {
		R<CustomerInfoVO> customerInfoR = organizationAccountInfoClient.getCustomerInfoByPhone(AuthUtil.getTenantId(), phoneNumber, phoneAreaCode);
		if (customerInfoR.isSuccess()) {
			if (customerInfoR.getData() != null && customerInfoR.getData().getId() != null) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	/**
	 * 提交机构开户资料
	 *
	 * @param organizationOpenInfoBo
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public R<String> submitOrganizationOpenAccount(OrganizationOpenInfoBo organizationOpenInfoBo) {
		if (organizationOpenInfoBo == null) {
			return R.fail(ResultCode.PARAM_MISS);
		}
		if (organizationOpenInfoBo.getOpenAccountAccessWay() == null) {
			return R.fail(I18nUtil.getMessage(OrganizationOpenAccountMessageConstant.ORGANIZATION_OPEN_ACCOUNT_OPEN_METHOD_NULL_NOTICE));
		}
		if (organizationOpenInfoBo.getOpenAccountAccessWay() != OpenAccountEnum.OpenAccountAccessWay.H5.getCode()
			&& organizationOpenInfoBo.getOpenAccountAccessWay() != OpenAccountEnum.OpenAccountAccessWay.APP.getCode()
			&& organizationOpenInfoBo.getOpenAccountAccessWay() != OpenAccountEnum.OpenAccountAccessWay.OFFLINE.getCode()) {
			return R.fail(I18nUtil.getMessage(OrganizationOpenAccountMessageConstant.ORGANIZATION_OPEN_ACCOUNT_OPEN_TYPE_ERROR_NOTICE));
		}
		OrganizationOpenInfoEntity organizationOpenInfoEntity = organizationOpenInfoMapper.selectOne(new LambdaQueryWrapper<OrganizationOpenInfoEntity>()
			.eq(OrganizationOpenInfoEntity::getCustId, AuthUtil.getTenantCustId())
			.in(OrganizationOpenInfoEntity::getApproveStatus, 0, 1));
		if (organizationOpenInfoEntity != null) {
			log.warn("该机构用户已提交开户资料，CustID:{}!", AuthUtil.getTenantCustId());
			return R.fail(I18nUtil.getMessage(OrganizationOpenAccountMessageConstant.ORGANIZATION_OPEN_ACCOUNT_SUBMIT_INFO_ALREADY_NOTICE));
		}
		organizationOpenInfoEntity = organizationOpenInfoMapper.selectOne(new LambdaQueryWrapper<OrganizationOpenInfoEntity>()
			.eq(OrganizationOpenInfoEntity::getContactPhoneArea, organizationOpenInfoBo.getContactPhoneArea())
			.eq(OrganizationOpenInfoEntity::getContactPhoneNumber, organizationOpenInfoBo.getContactPhoneNumber())
			.in(OrganizationOpenInfoEntity::getApproveStatus, 0, 1));
		if (organizationOpenInfoEntity != null) {
			log.warn("该电话号码已存在机构开户信息，电话区号:{},电话号码:{}!", organizationOpenInfoBo.getContactPhoneArea(), organizationOpenInfoBo.getContactPhoneNumber());
			return R.fail(I18nUtil.getMessage(OrganizationOpenAccountMessageConstant.ORGANIZATION_OPEN_ACCOUNT_PHONENUMBER_ALREADY_NOTICE));
		}
		organizationOpenInfoEntity = new OrganizationOpenInfoEntity();
		organizationOpenInfoEntity.setCompanyRegistCertUrl(organizationOpenInfoBo.getCompanyRegistCertUrl());
		organizationOpenInfoEntity.setBusinessRegistCertUrl(organizationOpenInfoBo.getBusinessRegistCertUrl());
		organizationOpenInfoEntity.setPurposeOpenAccount(String.join("&", organizationOpenInfoBo.getPurposeOpenAccount()));
		organizationOpenInfoEntity.setCompanyAddress(organizationOpenInfoBo.getCompanyAddress());
		organizationOpenInfoEntity.setCompanyName(organizationOpenInfoBo.getCompanyName());
		organizationOpenInfoEntity.setContact(organizationOpenInfoBo.getContact());
		organizationOpenInfoEntity.setContactEmail(organizationOpenInfoBo.getContactEmail());
		organizationOpenInfoEntity.setContactPhoneArea(organizationOpenInfoBo.getContactPhoneArea());
		organizationOpenInfoEntity.setContactPhoneNumber(organizationOpenInfoBo.getContactPhoneNumber());
		organizationOpenInfoEntity.setFundingSource(organizationOpenInfoBo.getFundingSource());
		organizationOpenInfoEntity.setBusinessNature(organizationOpenInfoBo.getBusinessNature());
		organizationOpenInfoEntity.setRegistrationLocation(organizationOpenInfoBo.getRegistrationLocation());
		organizationOpenInfoEntity.setSwiftCode(organizationOpenInfoBo.getSwiftCode());
		organizationOpenInfoEntity.setAcceptRisk(organizationOpenInfoBo.getAcceptRisk());
		organizationOpenInfoEntity.setExpiryDate(organizationOpenInfoBo.getExpiryDate());
		organizationOpenInfoEntity.setAccountName(organizationOpenInfoBo.getAccountName());
		organizationOpenInfoEntity.setAccountNumber(organizationOpenInfoBo.getAccountNumber());
		organizationOpenInfoEntity.setBankName(organizationOpenInfoBo.getBankName());
		organizationOpenInfoEntity.setOpenDate(new Date());
		organizationOpenInfoEntity.setApproveStatus(OrganizationOpenApproveEnum.PENDING_AUDIT.getCode());
		organizationOpenInfoEntity.setOpenStatus(OrganizationOpenStatusEnum.PENDING_OPEN.getCode());
		if ((organizationOpenInfoBo.getOpenAccountAccessWay() == OpenAccountEnum.OpenAccountAccessWay.H5.getCode()
			|| organizationOpenInfoBo.getOpenAccountAccessWay() == OpenAccountEnum.OpenAccountAccessWay.APP.getCode())) {
			organizationOpenInfoEntity.setCustId(AuthUtil.getTenantCustId());
		}
		organizationOpenInfoEntity.setOpenAccountAccessWay(organizationOpenInfoBo.getOpenAccountAccessWay());
		boolean result = this.save(organizationOpenInfoEntity);
		if (result) {
			log.info("持久化机构开户资料成功,CustId:{},TenantId:{},开户资料记录ID:{}", AuthUtil.getTenantCustId(), AuthUtil.getTenantId(), organizationOpenInfoEntity.getId());
			if (CollectionUtils.isNotEmpty(organizationOpenInfoBo.getShareholderInfoList())) {
				List<OrganizationOpenShareholderInfoEntity> organizationOpenShareholderInfoEntities = new ArrayList<>();
				for (OrganizationOpenShareholderInfoBo organizationOpenShareholderInfoBo : organizationOpenInfoBo.getShareholderInfoList()) {
					OrganizationOpenShareholderInfoEntity organizationOpenShareholderInfoEntity = new OrganizationOpenShareholderInfoEntity();
					organizationOpenShareholderInfoEntity.setCompanyName(organizationOpenShareholderInfoBo.getCompanyName());
					organizationOpenShareholderInfoEntity.setFirstName(organizationOpenShareholderInfoBo.getFirstName());
					organizationOpenShareholderInfoEntity.setIdNumber(organizationOpenShareholderInfoBo.getIdNumber());
					organizationOpenShareholderInfoEntity.setIsPep(organizationOpenShareholderInfoBo.getIsPep());
					organizationOpenShareholderInfoEntity.setLastName(organizationOpenShareholderInfoBo.getLastName());
					organizationOpenShareholderInfoEntity.setOpenInfoId(organizationOpenInfoEntity.getId());
					ShareholderTitleEnum shareholderTitleEnum = ShareholderTitleEnum.getCode(organizationOpenShareholderInfoBo.getTitle());
					if (shareholderTitleEnum == null) {
						log.warn("机构开户参数对象中，股东信息中，职位:{}，不存在!", organizationOpenShareholderInfoBo.getTitle());
						return R.fail(String.format(I18nUtil.getMessage(OrganizationOpenAccountMessageConstant.ORGANIZATION_OPEN_ACCOUNT_POSITION_NOT_EXIST_NOTICE), organizationOpenShareholderInfoBo.getTitle()));
					}
					ShareholderRiskEnum shareholderRiskEnum = ShareholderRiskEnum.getCode(organizationOpenShareholderInfoBo.getRisk());
					if (shareholderRiskEnum == null) {
						log.warn("机构开户参数对象中，股东信息中，风险等级:{}，不存在!", organizationOpenShareholderInfoBo.getRisk());
						return R.fail(String.format(I18nUtil.getMessage(OrganizationOpenAccountMessageConstant.ORGANIZATION_OPEN_ACCOUNT_RISK_NOT_EXIST_NOTICE), organizationOpenShareholderInfoBo.getRisk()));
					}
					ShareholderTypeEnum shareholderTypeEnum = ShareholderTypeEnum.getCode(organizationOpenShareholderInfoBo.getType());
					if (shareholderTypeEnum == null) {
						log.warn("机构开户参数对象中，股东信息中，类型:{}，不存在!", organizationOpenShareholderInfoBo.getType());
						return R.fail(String.format(I18nUtil.getMessage(OrganizationOpenAccountMessageConstant.ORGANIZATION_OPEN_ACCOUNT_TYPE_NOT_EXIST_NOTICE), organizationOpenShareholderInfoBo.getType()));
					}
					organizationOpenShareholderInfoEntity.setTitle(shareholderTitleEnum.getCode());
					organizationOpenShareholderInfoEntity.setType(shareholderTypeEnum.getCode());
					organizationOpenShareholderInfoEntity.setRisk(shareholderRiskEnum.getCode());
					organizationOpenShareholderInfoEntities.add(organizationOpenShareholderInfoEntity);
				}
				boolean isSuccess = organizationOpenShareholderInfoService.batchSave(organizationOpenShareholderInfoEntities);
				if (isSuccess) {
					log.info("持久化机构开户董事、股东、授权签署信息成功,CustId:{},TenantId:{}", AuthUtil.getTenantCustId(), AuthUtil.getTenantId());
					return R.success(I18nUtil.getMessage(OrganizationOpenAccountMessageConstant.ORGANIZATION_OPEN_ACCOUNT_SUBMIT_SUCCESS_NOTICE));
				} else {
					log.error("持久化机构开户董事、股东、授权签署信息失败,CustId:{},TenantId:{}", AuthUtil.getTenantCustId(), AuthUtil.getTenantId());
					throw new ServiceException(I18nUtil.getMessage(OrganizationOpenAccountMessageConstant.ORGANIZATION_OPEN_ACCOUNT_SUBMIT_FAILED_NOTICE));
				}
			}
			String tenantId = AuthUtil.getTenantId();
			String language = WebUtil.getLanguage();
			GlobalExecutorService.getExecutor().execute(() -> {
				R<List<DictBiz>> emailAddress = dictBizClient.getListByTenantId(tenantId, DictTypeConstant.SYSTEM_EMAIL_ADDRESS);
				if (emailAddress.isSuccess()) {
					List<String> emails = emailAddress.getData().stream().map(DictBiz::getDictKey).collect(Collectors.toList());
					log.info("提交机构开户资料成功，准备发送审核申请提醒邮件通知->{}", emails);
					// 标题参数
					List<String> titleParams = new ArrayList<>();
					titleParams.add(com.minigod.zero.bpmn.utils.DateUtils.getTime());
					titleParams.add(organizationOpenInfoBo.getCompanyName());
					// 内容参数
					List<String> bodyParams = new ArrayList<>();
					bodyParams.add(com.minigod.zero.bpmn.utils.DateUtils.getTime());
					bodyParams.add(organizationOpenInfoBo.getCompanyName());
					bodyParams.add("机构线上开户");
					bodyParams.add(String.format("+%s-%s", organizationOpenInfoBo.getContactPhoneArea(), organizationOpenInfoBo.getContactPhoneNumber()));
					// 发送开户邮件
					SendEmailDTO sendEmailDTO = new SendEmailDTO();
					sendEmailDTO.setAccepts(emails);
					sendEmailDTO.setCode(EmailTemplate.ACCOUNT_OPENING_SUBMISSION_APPROVAL_REMINDER.getCode());
					sendEmailDTO.setTitleParam(titleParams);
					sendEmailDTO.setList(bodyParams);
					sendEmailDTO.setLang(language);

					R sendRes = platformMsgClient.sendEmail(sendEmailDTO);
					if (sendRes != null && sendRes.isSuccess()) {
						log.info("提交开户资料->发送审核提醒邮件成功,收件人列表:{}", emails);
					} else {
						log.error("提交开户资料->发送审核提醒邮件失败,收件人列表:{}", emails);
					}
				} else {
					log.error("提交开户资料->发送审核提醒邮件失败,【SYSTEM_EMAIL_ADDRESS配置】收件邮箱地址为空!");
				}
			});
			return R.success("提交机构开户资料成功!");
		} else {
			log.error("持久化机构开户资料失败,ApplicationID：{},TenantId:{}", AuthUtil.getTenantCustId(), AuthUtil.getTenantId());
			throw new ServiceException(I18nUtil.getMessage(OrganizationOpenAccountMessageConstant.ORGANIZATION_OPEN_ACCOUNT_SAVE_FAILED_NOTICE));
		}
	}

	/**
	 * 根据主键ID获取机构开户信息
	 *
	 * @param id
	 * @return
	 */
	@Override
	public OrganizationOpenInfoVO queryOrganizationOpenAccountById(Long id) {
		OrganizationOpenInfoVO organizationOpenInfoVO = null;
		if (id != null) {
			OrganizationOpenInfoEntity openInfoEntity = organizationOpenInfoMapper.selectByPrimaryKey(id);
			if (openInfoEntity != null) {
				organizationOpenInfoVO = BeanUtil.copyProperties(openInfoEntity, OrganizationOpenInfoVO.class);
				OpenAccountEnum.OpenAccountAccessWay openAccountAccessWay = OpenAccountEnum.OpenAccountAccessWay.getNameByCode(openInfoEntity.getOpenAccountAccessWay());
				if (openAccountAccessWay != null) {
					organizationOpenInfoVO.setOpenAccountAccessWayName(openAccountAccessWay.getMessage());
				}
				if (organizationOpenInfoVO.getCustId() == null || organizationOpenInfoVO.getCustId() == -1) {
					organizationOpenInfoVO.setCustIdStr("");
				} else {
					organizationOpenInfoVO.setCustIdStr(organizationOpenInfoVO.getCustId().toString());
				}
				List<OrganizationOpenShareholderInfoEntity> organizationOpenShareholderInfoEntities = organizationOpenShareholderInfoMapper.selectByOpenInfoId(id);
				if (CollectionUtils.isNotEmpty(organizationOpenShareholderInfoEntities)) {
					List<OrganizationOpenShareholderInfoVO> organizationOpenShareholderInfoVOS = BeanUtil.copyToList(organizationOpenShareholderInfoEntities, OrganizationOpenShareholderInfoVO.class);
					organizationOpenShareholderInfoVOS.forEach(organizationOpenShareholderInfoVO -> {
						transferShareholderInfo(organizationOpenShareholderInfoVO);
					});
					organizationOpenInfoVO.setShareholderInfoList(organizationOpenShareholderInfoVOS);
				}
			}
		}
		return organizationOpenInfoVO;
	}

	@Override
	public OrganizationOpenInfoEntity queryOrganizationOpenAccountByCustId(Long custId) {
		log.info("查询机构开户信息,custId:{},TenantId:{}", custId, AuthUtil.getTenantId());
		LambdaQueryWrapper<OrganizationOpenInfoEntity> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(OrganizationOpenInfoEntity::getCustId, custId);
		queryWrapper.orderByDesc(OrganizationOpenInfoEntity::getUpdateTime);
		queryWrapper.last("limit 1");
		return organizationOpenInfoMapper.selectOne(queryWrapper);
	}

	/**
	 * 提交机构开户审核结果
	 *
	 * @param id
	 * @param approveResult
	 * @param reason
	 * @return
	 */
	@Override
	public R<String> submitApproveResult(Long id, OrganizationOpenApproveEnum approveResult, String reason) {
		if (id == null) {
			return R.fail(I18nUtil.getMessage(OrganizationOpenAccountMessageConstant.ORGANIZATION_OPEN_ACCOUNT_ID_NULL_NOTICE));
		}

		try {
			//使用 synchronized 关键字确保数据一致性
			synchronized (this) {
				OrganizationOpenInfoEntity organizationOpenInfoEntity = organizationOpenInfoMapper.selectByPrimaryKey(id);
				if (organizationOpenInfoEntity == null) {
					return R.fail(I18nUtil.getMessage(OrganizationOpenAccountMessageConstant.ORGANIZATION_OPEN_ACCOUNT_INFO_FAILED_NOTICE));
				}

				// 更新机构开户信息
				organizationOpenInfoEntity.setApproveStatus(approveResult.getCode());
				organizationOpenInfoEntity.setApproveReason(reason);
				organizationOpenInfoEntity.setApproveUserId(AuthUtil.getUserId());
				organizationOpenInfoEntity.setApproveUserName(AuthUtil.getUser().getNickName());
				organizationOpenInfoEntity.setApproveDate(new Date());
				organizationOpenInfoEntity.setUpdateTime(new Date());
				int rows = organizationOpenInfoMapper.updateByPrimaryKey(organizationOpenInfoEntity);
				if (rows <= 0) {
					return R.fail(I18nUtil.getMessage(OrganizationOpenAccountMessageConstant.ORGANIZATION_OPEN_ACCOUNT_UPDATE_FAILED_NOTICE));
				}
				if (approveResult.getCode().equals(OrganizationOpenApproveEnum.REJECTED.getCode())) {
					log.info("机构开户审核提交成功,审批结果为【{}】,不提交机构开户信息到账户中心!", OrganizationOpenApproveEnum.REJECTED.getMessage());
					return R.success(String.format(I18nUtil.getMessage(OrganizationOpenAccountMessageConstant.ORGANIZATION_OPEN_ACCOUNT_SUCCESS_RESULT_NOTICE), OrganizationOpenApproveEnum.REJECTED.getMessage()));
				}

				// 异步任务封装
				GlobalExecutorService.getExecutor().execute(() -> {
					OrganizationOpenAccountDTO organizationOpenAccountDTO = buildOrganizationOpenAccountDTO(organizationOpenInfoEntity);
					R<Map<String, Object>> result = organizationAccountInfoClient.openAccount(organizationOpenAccountDTO);
					log.info("提交机构开户信息->账户中心返回的处理结果:{}", JSONObject.toJSONString(result));
					if (result.isSuccess()) {
						String accountId = result.getData().get("accountId") != null ? result.getData().get("accountId").toString() : null;
						String custId = result.getData().get("custId") != null ? result.getData().get("custId").toString() : null;
						if (StringUtils.isBlank(accountId) || StringUtils.isBlank(custId)) {
							log.error("提交机构开户信息->账户中心返回的AccountId或CustId为空,-->Account:{},CustID:{},TenantId:{}", accountId, custId, organizationOpenInfoEntity.getTenantId());
							return;
						}
						// 更新账户中心返回的AccountId
						organizationOpenInfoEntity.setTradeAccount(accountId);
						organizationOpenInfoEntity.setCustId(Long.valueOf(custId));
						organizationOpenInfoEntity.setOpenStatus(OrganizationOpenStatusEnum.SUCCESS.getCode());
						organizationOpenInfoEntity.setOpenResult(result.getMsg());
						organizationOpenInfoEntity.setUpdateTime(new Date());
						int counts = organizationOpenInfoMapper.updateByPrimaryKey(organizationOpenInfoEntity);
						if (counts > 0) {
							log.info("更新账户中心返回的AccountId->{}到开户资料的TradeAccount成功,CustId:{},TenantId:{}", accountId, organizationOpenInfoEntity.getCustId(), organizationOpenInfoEntity.getTenantId());
							Long openInfoId = organizationOpenInfoEntity.getId();
							int records = organizationOpenShareholderInfoMapper.updateByOpenInfoId(openInfoId, organizationOpenInfoEntity.getCustId());
							if (records > 0) {
								log.info("更新机构开户信息->账户中心返回的CustId->{}到机构开户股东资料表中成功,OpenInfoID:{}, CustId:{},TenantId:{}", custId, openInfoId, organizationOpenInfoEntity.getCustId(), organizationOpenInfoEntity.getTenantId());
							} else {
								log.error("更新机构开户信息->账户中心返回的CustId->{}到机构开户股东资料表中失败,OpenInfoID:{}, CustId:{},TenantId:{}", custId, openInfoId, organizationOpenInfoEntity.getCustId(), organizationOpenInfoEntity.getTenantId());
							}
							String email = organizationOpenInfoEntity.getContactEmail();
							log.info("机构开户成功，准备发送开户邮件->{}", email);
							String name = result.getData().get("name") != null ? result.getData().get("name").toString() : null;
							String initPassword = result.getData().get("password") != null ? result.getData().get("password").toString() : null;
							if (StringUtils.isBlank(email)) {
								log.info("机构开户->发送开户邮件失败，邮箱为空!!!");
								return;
							}
							List<String> param = new ArrayList<>();
							param.add(name);
							param.add(name);
							param.add(accountId);
							param.add(accountId);
							param.add(initPassword);
							//发送开户邮件
							SendEmailDTO sendEmailDTO = new SendEmailDTO();
							sendEmailDTO.setAccepts(Arrays.asList(email));
							sendEmailDTO.setCode(EmailTemplate.OPEN_WEALTH_MANAGEMENT_ACCOUNT.getCode());
							sendEmailDTO.setList(param);
							R sendRes = platformMsgClient.sendEmail(sendEmailDTO);
							if (sendRes != null && sendRes.isSuccess()) {
								log.info("机构开户->发送开户邮件成功!!!");
							} else {
								log.error("机构开户->发送开户邮件失败!!!");
							}

							PushUtil.builder()
								.msgGroup("P")
								.custId(Long.valueOf(custId))
								.group(MsgStaticType.DisplayGroup.SERVICE_MSG)
								.pushType(PushTypeEnum.STRONG_MSG.getTypeValue())
								.templateCode(PushTemplate.ACCOUNT_OPEN_SUCCESS.getCode())
								.sendWay(InformEnum.SendWayTimeEnum.FORTHWITH.getTypeCode())
								.pushAsync();

						} else {
							log.error("更新账户中心返回的AccountId->{}到开户资料的TradeAccount失败,CustId:{},TenantId:{}", accountId, organizationOpenInfoEntity.getCustId(), organizationOpenInfoEntity.getTenantId());
						}
					} else {
						log.error("机构开户资料提交到账户中心失败,CustId:{},TenantId:{},失败原因:{}", organizationOpenInfoEntity.getCustId(), organizationOpenInfoEntity.getTenantId(), result.getMsg());
						organizationOpenInfoEntity.setOpenStatus(OrganizationOpenStatusEnum.FAILED.getCode());
						organizationOpenInfoEntity.setOpenResult(result.getMsg());
						organizationOpenInfoEntity.setUpdateTime(new Date());
						int counts = organizationOpenInfoMapper.updateByPrimaryKey(organizationOpenInfoEntity);
						if (counts > 0) {
							log.info("更新机构开户资料审核状态为【开户失败】成功,CustId:{},TenantId:{}", organizationOpenInfoEntity.getCustId(), organizationOpenInfoEntity.getTenantId());
						}
					}
				});

				return R.success(I18nUtil.getMessage(OrganizationOpenAccountMessageConstant.ORGANIZATION_OPEN_ACCOUNT_REVIEW_SUCCESS_NOTICE));
			}
		} catch (Exception e) {
			log.error("机构开户资料审核结果提交异常,CustId:{},异常详情:{}", AuthUtil.getTenantCustId(), e.getMessage());
			return R.fail(I18nUtil.getMessage(OrganizationOpenAccountMessageConstant.ORGANIZATION_OPEN_ACCOUNT_REVIEW_ERROR_NOTICE));
		}
	}

	/**
	 * 机构开户信息分页查询
	 *
	 * @param page
	 * @param openInfoQuery
	 * @return
	 */
	@Override
	public IPage<OrganizationOpenInfoVO> selectOrganizationOpenInfoPage(IPage<OrganizationOpenInfoVO> page, OrganizationOpenInfoQuery openInfoQuery) {
		if (openInfoQuery == null) {
			openInfoQuery = new OrganizationOpenInfoQuery();
		}
		List<OrganizationOpenInfoEntity> records = organizationOpenInfoMapper.selectByPage(page, openInfoQuery);
		R<List<DictBiz>> dictRiskType = dictBizClient.getList("risk_type");
		List<OrganizationOpenInfoVO> infoVOS = new ArrayList<>();
		records.forEach(record -> {
			OrganizationOpenInfoVO infoVO = new OrganizationOpenInfoVO();
			BeanUtil.copyProperties(record, infoVO);
			transferOpenInfo(infoVO);
			if (dictRiskType.isSuccess()) {
				List<DictBiz> dictBizData = dictRiskType.getData();
				if (dictBizData != null) {
					dictBizData.stream().forEach(d -> {
						if (d.getDictKey() != null && record.getAcceptRisk() != null && d.getDictKey().trim().toString().equals(record.getAcceptRisk().toString())) {
							infoVO.setAcceptRiskName(d.getDictValue());
						}
					});
				}
			}
			List<OrganizationOpenShareholderInfoEntity> shareholderInfoList = organizationOpenShareholderInfoMapper.selectByOpenInfoId(record.getId());
			List<OrganizationOpenShareholderInfoVO> shareholderInfoVOS = new ArrayList<>();
			shareholderInfoList.forEach(shareholderInfo -> {
				OrganizationOpenShareholderInfoVO shareholderInfoVO = new OrganizationOpenShareholderInfoVO();
				BeanUtil.copyProperties(shareholderInfo, shareholderInfoVO);
				transferShareholderInfo(shareholderInfoVO);
				shareholderInfoVOS.add(shareholderInfoVO);
			});
			infoVO.setShareholderInfoList(shareholderInfoVOS);
			infoVOS.add(infoVO);
		});
		return page.setRecords(infoVOS);
	}

	/**
	 * 查询开户资料审核状态
	 *
	 * @return
	 */
	@Override
	public Integer queryApproveStatus() {
		LambdaQueryWrapper<OrganizationOpenInfoEntity> queryWrapper = new LambdaQueryWrapper();
		queryWrapper.eq(OrganizationOpenInfoEntity::getCustId, AuthUtil.getTenantCustId());
		queryWrapper.last("limit 1");
		queryWrapper.orderByDesc(OrganizationOpenInfoEntity::getOpenDate);
		OrganizationOpenInfoEntity openInfoEntity = organizationOpenInfoMapper.selectOne(queryWrapper);

		if (openInfoEntity != null) {
			OrganizationOpenApproveEnum approveEnum = OrganizationOpenApproveEnum.getCode(openInfoEntity.getApproveStatus());
			if (approveEnum != null) {
				return approveEnum.getCode();
			}
		}
		return OrganizationOpenApproveEnum.NO_APPLY.getCode();
	}

	private OrganizationOpenAccountDTO buildOrganizationOpenAccountDTO(OrganizationOpenInfoEntity organizationOpenInfoEntity) {
		OrganizationOpenAccountDTO organizationOpenAccountDTO = new OrganizationOpenAccountDTO();
		organizationOpenAccountDTO.setBankAccountNumber(organizationOpenInfoEntity.getAccountNumber());
		organizationOpenAccountDTO.setBankName(organizationOpenInfoEntity.getBankName());
		organizationOpenAccountDTO.setBusinessRegistrationCertificate(organizationOpenInfoEntity.getBusinessRegistCertUrl());
		organizationOpenAccountDTO.setBusinessNature(organizationOpenInfoEntity.getBusinessNature());
		organizationOpenAccountDTO.setContacts(organizationOpenInfoEntity.getContact());
		organizationOpenAccountDTO.setContactsEmail(organizationOpenInfoEntity.getContactEmail());
		organizationOpenAccountDTO.setAreaCode(organizationOpenInfoEntity.getContactPhoneArea());
		organizationOpenAccountDTO.setContactsMobile(organizationOpenInfoEntity.getContactPhoneNumber());
		organizationOpenAccountDTO.setFundingSource(organizationOpenInfoEntity.getFundingSource());
		organizationOpenAccountDTO.setSwiftCode(organizationOpenInfoEntity.getSwiftCode());
		organizationOpenAccountDTO.setRiskLevel(organizationOpenInfoEntity.getAcceptRisk());
		organizationOpenAccountDTO.setExpiryDate(organizationOpenInfoEntity.getExpiryDate());
		organizationOpenAccountDTO.setAccountName(organizationOpenInfoEntity.getAccountName());
		organizationOpenAccountDTO.setAddress(organizationOpenInfoEntity.getCompanyAddress());
		organizationOpenAccountDTO.setName(organizationOpenInfoEntity.getCompanyName());
		organizationOpenAccountDTO.setRegistrationAddress(organizationOpenInfoEntity.getRegistrationLocation());
		organizationOpenAccountDTO.setRegistrationCertificate(organizationOpenInfoEntity.getCompanyRegistCertUrl());
		organizationOpenAccountDTO.setTarget(organizationOpenInfoEntity.getPurposeOpenAccount());
		organizationOpenAccountDTO.setCustId(organizationOpenInfoEntity.getCustId());
		organizationOpenAccountDTO.setTenantId(organizationOpenInfoEntity.getTenantId());
		organizationOpenAccountDTO.setOpenChannel(organizationOpenInfoEntity.getOpenAccountAccessWay());
		return organizationOpenAccountDTO;
	}

	/**
	 * 转换开户信息
	 *
	 * @param infoVO
	 */
	private void transferOpenInfo(OrganizationOpenInfoVO infoVO) {
		if (infoVO.getApproveStatus() != null) {
			OpenAccountEnum.OrganizationOpenApproveStatus organizationOpenApproveStatus = OpenAccountEnum.OrganizationOpenApproveStatus.getOrganizationOpenApproveStatus(infoVO.getApproveStatus());
			if (organizationOpenApproveStatus != null) {
				infoVO.setApproveStatusName(organizationOpenApproveStatus.getMessage());
			}
		}
		if (infoVO.getOpenStatus() != null) {
			OpenAccountEnum.OrganizationOpenStatus organizationOpenStatus = OpenAccountEnum.OrganizationOpenStatus.getOrganizationOpenStatus(infoVO.getOpenStatus());
			if (organizationOpenStatus != null) {
				infoVO.setOpenStatusName(organizationOpenStatus.getMessage());
			}
		}
		if (infoVO.getOpenAccountAccessWay() != null) {
			OpenAccountEnum.OpenAccountAccessWay openAccountAccessWay = OpenAccountEnum.OpenAccountAccessWay.getNameByCode(infoVO.getOpenAccountAccessWay());
			if (openAccountAccessWay != null) {
				infoVO.setOpenAccountAccessWayName(openAccountAccessWay.getMessage());
			}
		}
		if (infoVO.getCustId() == null || infoVO.getCustId() == -1) {
			infoVO.setCustIdStr("");
		} else {
			infoVO.setCustIdStr(infoVO.getCustId().toString());
		}
	}

	/**
	 * 转换股东信息
	 *
	 * @param shareholderInfoVO
	 */
	private void transferShareholderInfo(OrganizationOpenShareholderInfoVO shareholderInfoVO) {
		switch (shareholderInfoVO.getType()) {
			case 1:
				shareholderInfoVO.setTypeName("先生");
				break;
			case 2:
				shareholderInfoVO.setTypeName("小姐");
				break;
			case 3:
				shareholderInfoVO.setTypeName("博士");
				break;
			case 4:
				shareholderInfoVO.setTypeName("公司");
				break;
		}
		switch (shareholderInfoVO.getTitle()) {
			case 1:
				shareholderInfoVO.setTitleName("董事");
				break;
			case 2:
				shareholderInfoVO.setTitleName("股东");
				break;
			case 3:
				shareholderInfoVO.setTitleName("授权签署");
				break;
		}
		switch (shareholderInfoVO.getRisk()) {
			case 1:
				shareholderInfoVO.setRiskName("低风险");
				break;
			case 2:
				shareholderInfoVO.setRiskName("中风险");
				break;
			case 3:
				shareholderInfoVO.setRiskName("高风险");
				break;
		}
	}
}
