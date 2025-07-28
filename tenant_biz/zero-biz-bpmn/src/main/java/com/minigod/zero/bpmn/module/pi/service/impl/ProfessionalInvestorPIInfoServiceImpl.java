package com.minigod.zero.bpmn.module.pi.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.biz.common.utils.GlobalExecutorService;
import com.minigod.zero.bpmn.module.account.constants.DictTypeConstant;
import com.minigod.zero.bpmn.module.account.entity.OrganizationOpenInfoEntity;
import com.minigod.zero.bpmn.module.account.enums.OrganizationOpenApproveEnum;
import com.minigod.zero.bpmn.module.account.service.IAccountOpenApplicationService;
import com.minigod.zero.bpmn.module.account.service.IAccountOpenInfoService;
import com.minigod.zero.bpmn.module.account.service.IOrganizationOpenAccountOnlineService;
import com.minigod.zero.bpmn.module.account.vo.AccountOpenApplicationVO;
import com.minigod.zero.bpmn.module.account.vo.AccountOpenInfoVO;
import com.minigod.zero.bpmn.module.constant.PIApplicationMessageConstant;
import com.minigod.zero.bpmn.module.feign.ICustomerInfoClient;
import com.minigod.zero.bpmn.module.feign.vo.CustomerAccountVO;
import com.minigod.zero.bpmn.module.pi.bo.ProfessionalInvestorPIBO;
import com.minigod.zero.bpmn.module.pi.entity.ProfessionalInvestorPIApplicationEntity;
import com.minigod.zero.bpmn.module.pi.entity.ProfessionalInvestorPIInfoEntity;
import com.minigod.zero.bpmn.module.pi.entity.ProfessionalInvestorPIVoucherImageEntity;
import com.minigod.zero.bpmn.module.pi.enums.ApplicationStatusEnum;
import com.minigod.zero.bpmn.module.pi.enums.ApplicationType;
import com.minigod.zero.bpmn.module.pi.mapper.ProfessionalInvestorPIApplicationMapper;
import com.minigod.zero.bpmn.module.pi.mapper.ProfessionalInvestorPIInfoMapper;
import com.minigod.zero.bpmn.module.pi.mapper.ProfessionalInvestorPIVoucherImageMapper;
import com.minigod.zero.bpmn.module.pi.service.IProfessionalInvestorPIApplicationService;
import com.minigod.zero.bpmn.module.pi.service.IProfessionalInvestorPIInfoService;
import com.minigod.zero.bpmn.module.pi.service.IProfessionalInvestorPIVoucherImageService;
import com.minigod.zero.bpmn.module.pi.vo.ProfessionalInvestorPIInfoVO;
import com.minigod.zero.bpmn.utils.ApplicationIdUtil;
import com.minigod.zero.bpmn.utils.DateUtils;
import com.minigod.zero.core.i18n.utils.I18nUtil;
import com.minigod.zero.core.log.exception.ServiceException;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.WebUtil;
import com.minigod.zero.flow.core.common.constant.TaskConstants;
import com.minigod.zero.flow.core.constant.ProcessConstant;
import com.minigod.zero.flow.core.feign.IFlowClient;
import com.minigod.zero.flow.core.utils.FlowUtil;
import com.minigod.zero.flow.core.utils.TaskUtil;
import com.minigod.zero.platform.dto.SendEmailDTO;
import com.minigod.zero.platform.enums.EmailTemplate;
import com.minigod.zero.platform.feign.IPlatformMsgClient;
import com.minigod.zero.system.entity.DictBiz;
import com.minigod.zero.system.feign.IDictBizClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.internal.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 专业投资人PI申请基本信息表 服务实现类
 *
 * @author eric
 * @since 2024-05-07
 */
@Slf4j
@Service
@AllArgsConstructor
public class ProfessionalInvestorPIInfoServiceImpl extends BaseServiceImpl<ProfessionalInvestorPIInfoMapper, ProfessionalInvestorPIInfoEntity> implements IProfessionalInvestorPIInfoService {
	private final IProfessionalInvestorPIVoucherImageService investorPIVoucherImageService;
	private final ProfessionalInvestorPIVoucherImageMapper piVoucherImageMapper;
	private final ProfessionalInvestorPIInfoMapper professionalInvestorPIInfoMapper;
	private final ProfessionalInvestorPIApplicationMapper piApplicationMapper;
	private final IProfessionalInvestorPIApplicationService iProfessionalInvestorPIApplicationService;
	private final IFlowClient iFlowClient;
	private final IDictBizClient iDictBizClient;
	private final IAccountOpenApplicationService iAccountOpenApplicationService;
	private final IAccountOpenInfoService iAccountOpenInfoService;
	private final IOrganizationOpenAccountOnlineService iOrganizationOpenAccountOnlineService;
	@Resource
	private ICustomerInfoClient iCustomerInfoClient;
	@Resource
	private IPlatformMsgClient platformMsgClient;

	/**
	 * 专业投资人PI申请信息分页查询
	 *
	 * @param page
	 * @param investorPIInfoVO
	 * @return
	 */
	@Override
	public IPage<ProfessionalInvestorPIInfoVO> selectProfessionalInvestorPIInfoPage(IPage<ProfessionalInvestorPIInfoVO> page, ProfessionalInvestorPIInfoVO investorPIInfoVO) {
		return page.setRecords(professionalInvestorPIInfoMapper.selectProfessionalInvestorFIInfoPage(page, investorPIInfoVO));
	}

	/**
	 * 提交专业投资人PI申请
	 *
	 * @param investorPIBO
	 */
	@Override
	public R<String> submitProfessionalInvestorPI(ProfessionalInvestorPIBO investorPIBO) {
		try {
			if (investorPIBO == null) {
				return R.fail(String.format(I18nUtil.getMessage(PIApplicationMessageConstant.PI_APPLICATION_SUBMIT_FAILED_PARAM_NOTICE), AuthUtil.getCustId()));
			}
			if (investorPIBO.getTreatments() == null || investorPIBO.getTreatments().size() == 0) {
				return R.fail(String.format(I18nUtil.getMessage(PIApplicationMessageConstant.PI_APPLICATION_SUBMIT_FAILED_TREATMENT_NOTICE), AuthUtil.getCustId()));
			}
			if (investorPIBO.getDeclarations() == null || investorPIBO.getDeclarations().size() == 0) {
				return R.fail(String.format(I18nUtil.getMessage(PIApplicationMessageConstant.PI_APPLICATION_SUBMIT_FAILED_DECLARATION_NOTICE), AuthUtil.getCustId()));
			}
			//查询个人开户状态
			AccountOpenInfoVO accountOpenInfoVO = iAccountOpenInfoService.queryByUserId(AuthUtil.getUserId());
			//查询机构开户状态
			OrganizationOpenInfoEntity organizationOpenInfoEntity = iOrganizationOpenAccountOnlineService.queryOrganizationOpenAccountByCustId(AuthUtil.getUserId());
			//如果个人开户和机构开户都查不到该用户的资料说明还没有提交开户申请
			if (accountOpenInfoVO == null && organizationOpenInfoEntity == null) {
				log.warn("提交专业投资人PI申请失败,客户号:【" + AuthUtil.getTenantCustId() + "】开户资料不存在!");
				return R.fail(String.format(I18nUtil.getMessage(PIApplicationMessageConstant.PI_APPLICATION_SUBMIT_FAILED_OPENINFO_NOTICE), AuthUtil.getTenantCustId()));
			}
			//如果是个人开户
			if (accountOpenInfoVO != null) {
				AccountOpenApplicationVO accountOpenApplicationVO = iAccountOpenApplicationService.queryByApplicationId(accountOpenInfoVO.getApplicationId());
				if (accountOpenApplicationVO == null) {
					log.warn(("提交专业投资人PI申请失败,客户号:【" + AuthUtil.getTenantCustId() + "】开户申请记录不存在,请先开户再提交PI申请!"));
					return R.fail(String.format(I18nUtil.getMessage(PIApplicationMessageConstant.PI_APPLICATION_SUBMIT_FAILED_OPENRECORD_NOTICE), AuthUtil.getTenantCustId()));
				}
				//判断开户申请是否开户已经完成，字典配置customer_open_online_flow中，结束状态为8
				if (!accountOpenApplicationVO.getApplicationStatus().equals(8)) {
					log.warn("提交专业投资人PI申请失败,客户号:【" + AuthUtil.getTenantCustId() + "】开户流程还未完成,请先等待流程处理,开户完成后才可提交PI申请!");
					return R.fail(String.format(I18nUtil.getMessage(PIApplicationMessageConstant.PI_APPLICATION_SUBMIT_FAILED_APPLICATION_PENDING_NOTICE), AuthUtil.getTenantCustId()));
				}
			}
			//如果是机构开户
			if (organizationOpenInfoEntity != null) {
				if (!organizationOpenInfoEntity.getApproveStatus().equals(OrganizationOpenApproveEnum.APPROVED.getCode())) {
					log.warn("提交专业投资人PI申请失败,客户号:【" + AuthUtil.getTenantCustId() + "】机构开户申请还未通过,请先等待开户申请通过后再提交PI申请!");
					return R.fail(String.format(I18nUtil.getMessage(PIApplicationMessageConstant.PI_APPLICATION_SUBMIT_FAILED_APPLICATION_UNFINISHED_NOTICE), AuthUtil.getTenantCustId()));
				}
			}

			//根据客户号获取客户证券客户资料表
			R<CustomerAccountVO> customerAccountVOR = iCustomerInfoClient.customerAccountInfo(AuthUtil.getTenantCustId());
			if (!customerAccountVOR.isSuccess()) {
				log.warn(("客户号:【" + AuthUtil.getTenantCustId() + "】在大账户中心查询开户资料失败!"));
				return R.fail(String.format(I18nUtil.getMessage(PIApplicationMessageConstant.PI_APPLICATION_SUBMIT_QUERY_CUSTOMER_ERROR_NOTICE), AuthUtil.getTenantCustId()));
			}
			if (customerAccountVOR.isSuccess() && customerAccountVOR.getData() == null) {
				log.warn(("客户号:【" + AuthUtil.getTenantCustId() + "】在大账户中心开户资料不存在!"));
				return R.fail(String.format(I18nUtil.getMessage(PIApplicationMessageConstant.PI_APPLICATION_SUBMIT_QUERY_CUSTOMER_NOTEXISTED_NOTICE), AuthUtil.getTenantCustId()));
			}

			CustomerAccountVO customerAccountVO = customerAccountVOR.getData();
			log.info("客户号:【" + AuthUtil.getTenantCustId() + "】开户资料:" + JSONObject.toJSONString(customerAccountVO));

			ProfessionalInvestorPIInfoEntity investorPIInfoEntity = professionalInvestorPIInfoMapper.selectOne(new LambdaQueryWrapper<ProfessionalInvestorPIInfoEntity>()
				.eq(ProfessionalInvestorPIInfoEntity::getClientId, customerAccountVO.getCust().getCustId())
				.orderByDesc(ProfessionalInvestorPIInfoEntity::getApplicationTime)
				.last("limit 1"));
			if (investorPIInfoEntity != null) {
				ProfessionalInvestorPIApplicationEntity piApplicationEntity = piApplicationMapper.queryByApplicationId(investorPIInfoEntity.getApplicationId());
				if (piApplicationEntity != null) {
					log.info("客户号:【" + AuthUtil.getTenantCustId() + "】已提交专业投资者PI申请,申请编号:{},当前流程状态:{} 业务状态:{} ,业务状态是否为【6-不通过】:{}",
						piApplicationEntity.getApplicationId(),
						piApplicationEntity.getApplicationStatus() != null ? piApplicationEntity.getApplicationStatus().intValue() : "状态值为空-NULL",
						piApplicationEntity.getStatus() != null ? piApplicationEntity.getStatus().intValue() : "状态值为空-NULL",
						piApplicationEntity.getStatus() != null && piApplicationEntity.getStatus().intValue() != ApplicationStatusEnum.APPLY_FAILED.getStatus());
				} else {
					log.warn("客户号:【" + AuthUtil.getTenantCustId() + "】,未查询到申请记录,记录ApplicationID:" + investorPIInfoEntity.getApplicationId());
				}
				if (piApplicationEntity != null && piApplicationEntity.getStatus().intValue() != ApplicationStatusEnum.APPLY_FAILED.getStatus()) {
					ApplicationStatusEnum applicationStatus = ApplicationStatusEnum.fromStatus(piApplicationEntity.getApplicationStatus());
					ApplicationStatusEnum status = ApplicationStatusEnum.fromStatus(piApplicationEntity.getStatus());
					log.warn("客户号:【" + AuthUtil.getTenantCustId() + "】已提交专业投资者PI申请,流程状态:" + applicationStatus.getDescription() + ",业务状态:" + status.getDescription() + ",终止提交操作!");
					return R.fail(String.format(I18nUtil.getMessage(PIApplicationMessageConstant.PI_APPLICATION_SUBMIT_APPLICATION_ALREADY_NOTICE), applicationStatus.getDescription()));
				}

			}
			//根据当前租户号生成PI申请编号
			String applicationId = ApplicationIdUtil.generateProfessionalInvestorId(AuthUtil.getTenantId());
			//封装PI申请Info信息
			investorPIInfoEntity = convertEntity(customerAccountVO);
			investorPIInfoEntity.setApplicationId(applicationId);
			investorPIInfoEntity.setApplicationTime(new Date());
			investorPIInfoEntity.setApplyDate(new Date());
			investorPIInfoEntity.setTreatment(String.join("&", investorPIBO.getTreatments()));
			investorPIInfoEntity.setDeclaration(String.join("&", investorPIBO.getDeclarations()));

			//封装PI申请Application信息
			ProfessionalInvestorPIApplicationEntity applicationEntity = new ProfessionalInvestorPIApplicationEntity();
			applicationEntity.setApplicationId(applicationId);
			//applicationEntity.setApplicationType(ApplicationType.SUBMIT.getCode());
			applicationEntity.setApplicationTitle(String.format("[%s]专业投资者PI申请", investorPIInfoEntity.getClientAccount()));

			//1.持久化application实体
			boolean isSaved = iProfessionalInvestorPIApplicationService.save(applicationEntity);
			if (isSaved) {
				log.info("持久化application实体成功,ApplicationID：{}", applicationId);
			} else {
				log.error("持久化application实体失败,ApplicationID：{}", applicationId);
				return R.fail(I18nUtil.getMessage(PIApplicationMessageConstant.PI_APPLICATION_SUBMIT_APPLICATION_SAVE_ERROR_NOTICE));
			}

			//2.持久化info实体
			boolean isSuccess = this.save(investorPIInfoEntity);
			if (isSuccess) {
				log.info("持久化info实体成功,ApplicationID：{}", applicationId);
			} else {
				log.error("持久化info实体失败,ApplicationID：{}", applicationId);
				return R.fail(I18nUtil.getMessage(PIApplicationMessageConstant.PI_APPLICATION_SUBMIT_INFO_SAVE_ERROR_NOTICE));
			}

			//3.判断是否有上传凭证图片
			if (investorPIBO.getImageIds() != null && investorPIBO.getImageIds().size() > 0) {
				List<ProfessionalInvestorPIVoucherImageEntity> voucherImageList = investorPIVoucherImageService.queryByIds(investorPIBO.getImageIds());
				if (voucherImageList != null && voucherImageList.size() > 0) {
					for (ProfessionalInvestorPIVoucherImageEntity voucherImage : voucherImageList) {
						voucherImage.setApplicationId(applicationId);
					}
					//批量更新applicationId到凭证图片实体中
					Boolean result = investorPIVoucherImageService.updateBatchById(voucherImageList);
					if (result) {
						log.info("更新凭证图片applicationId成功,ApplicationID：{}", applicationId);
					} else {
						log.error("更新凭证图片applicationId失败,ApplicationID：{}", applicationId);
						return R.fail(I18nUtil.getMessage(PIApplicationMessageConstant.PI_APPLICATION_SUBMIT_IMAGE_SAVE_ERROR_NOTICE));
					}
				} else {
					log.warn("*****没有查询到该用户上传的凭证!******");
				}
			}

			String language = WebUtil.getLanguage();
			String tenantId = AuthUtil.getTenantId();
			//4.提交流程
			GlobalExecutorService.getExecutor().execute(() -> {
				Map<String, Object> variables = new HashMap<>();
				variables.put(TaskConstants.TENANT_ID, tenantId);
				variables.put(TaskConstants.APPLICATION_ID, applicationId);
				variables.put(TaskConstants.APPLICATION_TABLE, FlowUtil.getBusinessTable(ProcessConstant.PROFESSIONAL_INVESTOR_PI_KEY));
				variables.put(TaskConstants.APPLICATION_DICT_KEY, FlowUtil.getBusinessDictKey(ProcessConstant.PROFESSIONAL_INVESTOR_PI_KEY));
				variables.put(TaskConstants.PROCESS_INITIATOR, TaskUtil.getTaskUser());
				R r = iFlowClient.startProcessInstanceByKey(ProcessConstant.PROFESSIONAL_INVESTOR_PI_KEY, variables);
				if (r.isSuccess()) {
					log.info("启动流程成功,流程ID:{},租户ID:{},flowable引擎返回内容:{}", applicationId, tenantId, r.getData());
					R<List<DictBiz>> emailAddress = iDictBizClient.getListByTenantId(tenantId, DictTypeConstant.SYSTEM_EMAIL_ADDRESS);
					if (emailAddress.isSuccess()) {
						List<String> emails = emailAddress.getData().stream().map(DictBiz::getDictKey).collect(Collectors.toList());
						log.info("启动流程成功，准备发送PI认证申请提醒邮件通知->{}", emails);
						// 标题参数
						List<String> titleParams = new ArrayList<>();
						titleParams.add(DateUtils.getTime());
						titleParams.add(customerAccountVO.getCust().getCustName());
						// 正文参数
						List<String> bodyParams = new ArrayList<>();
						bodyParams.add(DateUtils.getTime());
						bodyParams.add(customerAccountVO.getCust().getCustName());
						//发送开户邮件
						SendEmailDTO sendEmailDTO = new SendEmailDTO();
						sendEmailDTO.setAccepts(emails);
						sendEmailDTO.setCode(EmailTemplate.PI_CERTIFICATION_APPROVAL_REMINDER.getCode());
						sendEmailDTO.setTitleParam(titleParams);
						sendEmailDTO.setList(bodyParams);
						sendEmailDTO.setLang(language);

						R sendRes = platformMsgClient.sendEmail(sendEmailDTO);
						if (sendRes != null && sendRes.isSuccess()) {
							log.info("PI认证申请->发送PI认证审核邮件成功,收件人列表:{}", emails);
						} else {
							log.error("PI认证申请->发送PI认证审核邮件失败,收件人列表:{}", emails);
						}
					} else {
						log.error("PI认证申请->发送PI认证审核提示邮件失败,【SYSTEM_EMAIL_ADDRESS配置】收件邮箱地址为空!");
					}
				} else {
					log.error("-->启动流程失败,流程ID:【" + applicationId + "】租户ID:【" + tenantId + "】," +
						"流程类型:【" + ProcessConstant.PROFESSIONAL_INVESTOR_PI_KEY + "】" + ", " +
						"启动变量:" + JSONObject.toJSONString(variables));
				}
			});

			return R.data(applicationEntity.getApplicationId(), I18nUtil.getMessage(PIApplicationMessageConstant.PI_APPLICATION_SUBMIT_SUCCESS_NOTICE));
		} catch (Exception exception) {
			log.error("-->提交专业投资者PI认证申请异常,异常信息:{}, 异常详情:", exception.getMessage(), exception);
			return R.fail(I18nUtil.getMessage(PIApplicationMessageConstant.PI_APPLICATION_SUBMIT_ERROR_NOTICE));
		}
	}

	/**
	 * 查询专业投资人PI申请信息
	 *
	 * @param applicationId
	 * @return
	 */
	@Override
	public ProfessionalInvestorPIInfoVO queryByApplicationId(String applicationId) {
		LambdaQueryWrapper<ProfessionalInvestorPIInfoEntity> queryWrapper = new LambdaQueryWrapper();
		queryWrapper.eq(ProfessionalInvestorPIInfoEntity::getApplicationId, applicationId);
		queryWrapper.last("limit 1");
		queryWrapper.orderByDesc(ProfessionalInvestorPIInfoEntity::getApplicationTime);
		ProfessionalInvestorPIInfoVO piInfoVO = new ProfessionalInvestorPIInfoVO();
		ProfessionalInvestorPIInfoEntity entity = professionalInvestorPIInfoMapper.selectOne(queryWrapper);
		BeanUtils.copyProperties(entity, piInfoVO);
		return piInfoVO;
	}

	/**
	 * 根据cust_id查询PI申请信息
	 *
	 * @return
	 */
	@Override
	public ProfessionalInvestorPIInfoVO queryByClientId() {
		LambdaQueryWrapper<ProfessionalInvestorPIInfoEntity> queryWrapper = new LambdaQueryWrapper();
		queryWrapper.eq(ProfessionalInvestorPIInfoEntity::getClientId, AuthUtil.getTenantCustId());
		queryWrapper.last("limit 1");
		queryWrapper.orderByDesc(ProfessionalInvestorPIInfoEntity::getApplicationTime);
		ProfessionalInvestorPIInfoVO piInfoVO = new ProfessionalInvestorPIInfoVO();
		ProfessionalInvestorPIInfoEntity entity = professionalInvestorPIInfoMapper.selectOne(queryWrapper);
		BeanUtils.copyProperties(entity, piInfoVO);
		return piInfoVO;
	}

	@Override
	public R submitProfessionalInvestorPIRepeal() {
		LambdaQueryWrapper<ProfessionalInvestorPIInfoEntity> queryWrapper = new LambdaQueryWrapper();
		Long custId = AuthUtil.getCustId();
		queryWrapper.eq(ProfessionalInvestorPIInfoEntity::getClientId, custId);
		queryWrapper.last("limit 1");
		queryWrapper.orderByDesc(ProfessionalInvestorPIInfoEntity::getApplicationTime);
		ProfessionalInvestorPIInfoEntity investorPIInfoEntity = baseMapper.selectOne(queryWrapper);

		int status = 0;
		String applyApplicationId = "";
		if (investorPIInfoEntity != null) {
			LambdaQueryWrapper<ProfessionalInvestorPIApplicationEntity> queryApplicationWrapper = new LambdaQueryWrapper<>();
			queryApplicationWrapper.eq(ProfessionalInvestorPIApplicationEntity::getApplicationId, investorPIInfoEntity.getApplicationId());
			ProfessionalInvestorPIApplicationEntity applicationEntity = piApplicationMapper.selectOne(queryApplicationWrapper);

			if (applicationEntity != null) {
				ApplicationStatusEnum applicationStatusEnum = ApplicationStatusEnum.fromStatus(applicationEntity.getStatus());
				if (applicationStatusEnum != null) {
					applyApplicationId = applicationEntity.getApplicationId();
					status = applicationStatusEnum.getStatus();
				}
			}
		}
		if (status != ApplicationStatusEnum.APPLY_PASS.getStatus()) {
			log.error("专业投资者身份申请未通过审核,无法撤销. custId:{}", custId);
			throw new ServiceException(I18nUtil.getMessage(PIApplicationMessageConstant.PI_APPLICATION_REVOKE_FAILED_NOT_PASS_AUDIT_NOTICE));
		}
		//根据当前租户号生成PI申请编号
		String applicationId = ApplicationIdUtil.generateProfessionalInvestorId(AuthUtil.getTenantId());
		//封装PI申请Application信息
		ProfessionalInvestorPIApplicationEntity applicationEntity = new ProfessionalInvestorPIApplicationEntity();
		applicationEntity.setApplicationId(applicationId);
		//applicationEntity.setApplicationType(ApplicationType.SUBMIT.getCode());
		applicationEntity.setApplicationTitle(String.format("[%s]专业投资者PI撤销", investorPIInfoEntity.getClientAccount()));

		//1.持久化application实体
		boolean isSaved = iProfessionalInvestorPIApplicationService.save(applicationEntity);
		if (isSaved) {
			log.info("持久化application实体成功,ApplicationID：{}", applicationId);
		} else {
			log.error("持久化application实体失败,ApplicationID：{}", applicationId);
			return R.fail(I18nUtil.getMessage(PIApplicationMessageConstant.PI_APPLICATION_SUBMIT_APPLICATION_SAVE_ERROR_NOTICE));
		}
		investorPIInfoEntity.setApplicationId(applicationId);
		investorPIInfoEntity.setApplicationTime(new Date());
		investorPIInfoEntity.setUpdateTime(new Date());
		baseMapper.updateById(investorPIInfoEntity);

		//根据客户号获取客户证券客户资料表
		R<CustomerAccountVO> customerAccountVOR = iCustomerInfoClient.customerAccountInfo(AuthUtil.getCustId());
		if (!customerAccountVOR.isSuccess()) {
			log.warn(("客户号:【" + AuthUtil.getCustId() + "】在大账户中心查询开户资料失败!"));
			return R.fail(String.format(I18nUtil.getMessage(PIApplicationMessageConstant.PI_APPLICATION_SUBMIT_QUERY_CUSTOMER_ERROR_NOTICE), AuthUtil.getCustId()));
		}
		if (customerAccountVOR.isSuccess() && customerAccountVOR.getData() == null) {
			log.warn(("客户号:【" + AuthUtil.getCustId() + "】在大账户中心开户资料不存在!"));
			return R.fail(String.format(I18nUtil.getMessage(PIApplicationMessageConstant.PI_APPLICATION_SUBMIT_QUERY_CUSTOMER_NOTEXISTED_NOTICE), AuthUtil.getCustId()));
		}

		CustomerAccountVO customerAccountVO = customerAccountVOR.getData();
		log.info("客户号:【" + AuthUtil.getCustId() + "】开户资料:" + JSONObject.toJSONString(customerAccountVO));

		String language = WebUtil.getLanguage();
		String tenantId = AuthUtil.getTenantId();
		//4.提交流程
		GlobalExecutorService.getExecutor().execute(() -> {
			Map<String, Object> variables = new HashMap<>();
			variables.put(TaskConstants.TENANT_ID, tenantId);
			variables.put(TaskConstants.APPLICATION_ID, applicationId);
			variables.put(TaskConstants.APPLICATION_TABLE, FlowUtil.getBusinessTable(ProcessConstant.PROFESSIONAL_INVESTOR_PI_KEY));
			variables.put(TaskConstants.APPLICATION_DICT_KEY, FlowUtil.getBusinessDictKey(ProcessConstant.PROFESSIONAL_INVESTOR_PI_KEY));
			variables.put(TaskConstants.PROCESS_INITIATOR, TaskUtil.getTaskUser());
			R r = iFlowClient.startProcessInstanceByKey(ProcessConstant.PROFESSIONAL_INVESTOR_PI_KEY, variables);
			if (r.isSuccess()) {
				log.info("启动流程成功,流程ID:{},租户ID:{},flowable引擎返回内容:{}", applicationId, tenantId, r.getData());
				R<List<DictBiz>> emailAddress = iDictBizClient.getListByTenantId(tenantId, DictTypeConstant.SYSTEM_EMAIL_ADDRESS);
				if (emailAddress.isSuccess()) {
					List<String> emails = emailAddress.getData().stream().map(DictBiz::getDictKey).collect(Collectors.toList());
					log.info("启动流程成功，准备发送PI认证申请提醒邮件通知->{}", emails);
					// 标题参数
					List<String> titleParams = new ArrayList<>();
					titleParams.add(DateUtils.getTime());
					titleParams.add(customerAccountVO.getCust().getCustName());
					// 正文参数
					List<String> bodyParams = new ArrayList<>();
					bodyParams.add(DateUtils.getTime());
					bodyParams.add(customerAccountVO.getCust().getCustName());
					//发送邮件
					SendEmailDTO sendEmailDTO = new SendEmailDTO();
					sendEmailDTO.setAccepts(emails);
					sendEmailDTO.setCode(EmailTemplate.PI_CANCEL_APPROVAL_REMINDER.getCode());
					sendEmailDTO.setTitleParam(titleParams);
					sendEmailDTO.setList(bodyParams);
					sendEmailDTO.setLang(language);

					R sendRes = platformMsgClient.sendEmail(sendEmailDTO);
					if (sendRes != null && sendRes.isSuccess()) {
						log.info("PI认证申请->发送PI认证审核邮件成功,收件人列表:{}", emails);
					} else {
						log.error("PI认证申请->发送PI认证审核邮件失败,收件人列表:{}", emails);
					}
				} else {
					log.error("PI认证申请->发送PI认证审核提示邮件失败,【SYSTEM_EMAIL_ADDRESS配置】收件邮箱地址为空!");
				}
			} else {
				log.error("-->启动流程失败,流程ID:【" + applicationId + "】租户ID:【" + tenantId + "】," +
					"流程类型:【" + ProcessConstant.PROFESSIONAL_INVESTOR_PI_KEY + "】" + ", " +
					"启动变量:" + JSONObject.toJSONString(variables));
			}
		});

		//修改图片表的数据
		LambdaQueryWrapper<ProfessionalInvestorPIVoucherImageEntity> piVoucherImageWrapper = new LambdaQueryWrapper<>();
		piVoucherImageWrapper.eq(ProfessionalInvestorPIVoucherImageEntity::getApplicationId, applyApplicationId);
		ProfessionalInvestorPIVoucherImageEntity professionalInvestorPIVoucherImageEntity = piVoucherImageMapper.selectOne(piVoucherImageWrapper);
		if (professionalInvestorPIVoucherImageEntity != null){
			professionalInvestorPIVoucherImageEntity.setApplicationId(applicationId);
			professionalInvestorPIVoucherImageEntity.setUpdateTime(new Date());
			piVoucherImageMapper.updateById(professionalInvestorPIVoucherImageEntity);
		}
		return R.data(applicationEntity.getApplicationId(), I18nUtil.getMessage(PIApplicationMessageConstant.PI_APPLICATION_SUBMIT_SUCCESS_NOTICE));
	}

	/**
	 * 撤销专业投资者身份
	 *
	 * @param applicationId
	 * @param revocationReason
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean revokeProfessionalInvestorStatus(String applicationId, String revocationReason) {
		if (StringUtil.isBlank(revocationReason)) {
			throw new ServiceException(I18nUtil.getMessage(PIApplicationMessageConstant.PI_APPLICATION_REVOKE_REASON_NULL_NOTICE));
		}
		LambdaQueryWrapper<ProfessionalInvestorPIApplicationEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		lambdaQueryWrapper.eq(ProfessionalInvestorPIApplicationEntity::getApplicationId, applicationId);
		lambdaQueryWrapper.last("limit 1");
		ProfessionalInvestorPIApplicationEntity applicationEntity = piApplicationMapper.selectOne(lambdaQueryWrapper);
		if (applicationEntity == null) {
			log.error("撤销专业投资者身份失败,未查询到PI认证申请记录,ApplicationID:{}", applicationId);
			throw new ServiceException(I18nUtil.getMessage(PIApplicationMessageConstant.PI_APPLICATION_REVOKE_RECORD_NOTEXISTED_NOTICE));
		}

		//更改审核状态为【已撤销】
		applicationEntity.setApplicationStatus(ApplicationStatusEnum.APPLY_REVOKE.getStatus());
		if (piApplicationMapper.updateById(applicationEntity) > 0) {
			log.info("撤销专业投资者身份成功,申请记录审核状态已更改为:已撤销,ApplicationID:{}", applicationId);
		} else {
			log.error("撤销专业投资者身份失败,未查询到申请记录,ApplicationID:{}", applicationId);
		}

		LambdaQueryWrapper<ProfessionalInvestorPIInfoEntity> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(ProfessionalInvestorPIInfoEntity::getApplicationId, applicationId);
		queryWrapper.last("limit 1");
		ProfessionalInvestorPIInfoEntity infoEntity = professionalInvestorPIInfoMapper.selectOne(queryWrapper);
		if (infoEntity == null) {
			log.error("撤销专业投资者身份失败,未查询到申请信息,ApplicationID:{}", applicationId);
			throw new ServiceException(I18nUtil.getMessage(PIApplicationMessageConstant.PI_APPLICATION_REVOKE_RECORD_NOTEXISTED_NOTICE));
		}
		if (infoEntity.getStatus() == 0) {
			log.error("撤销专业投资者身份失败,申请已撤销,ApplicationID:{}", applicationId);
			throw new ServiceException(I18nUtil.getMessage(PIApplicationMessageConstant.PI_APPLICATION_REVOKE_RECORD_REVOKED_NOTICE));
		}

		//写入撤销原因
		infoEntity.setRevocationReason(revocationReason);
		//写入撤销时间
		infoEntity.setRevocationDate(new Date());

		if (professionalInvestorPIInfoMapper.updateById(infoEntity) > 0) {
			log.info("撤销专业投资者身份成功,ApplicationID:{}", applicationId);
			return true;
		} else {
			log.error("撤销专业投资者身份失败,ApplicationID:{}", applicationId);
			return false;
		}
	}

	/**
	 * 将客户资料信息转换为专业投资者信息
	 *
	 * @param customerAccountVO
	 * @return
	 */
	private ProfessionalInvestorPIInfoEntity convertEntity(CustomerAccountVO customerAccountVO) {
		ProfessionalInvestorPIInfoEntity piInfoEntity = new ProfessionalInvestorPIInfoEntity();
		piInfoEntity.setClientId(customerAccountVO.getCust().getCustId());
		piInfoEntity.setClientAccount(customerAccountVO.getAcct().getTradeAccount());

		//客户姓名
		if (!StringUtils.isBlank(customerAccountVO.getCust().getCustName())) {
			piInfoEntity.setClientName(customerAccountVO.getCust().getCustName());
		} else if (!StringUtils.isBlank(customerAccountVO.getCust().getCustNameSpell())) {
			piInfoEntity.setClientName(customerAccountVO.getCust().getCustNameSpell());
		}

		//客户英文名（如果是机构户没有英文名）
		if (!StringUtils.isBlank(customerAccountVO.getCust().getGivenNameSpell()) && !StringUtils.isBlank(customerAccountVO.getCust().getFamilyNameSpell())) {
			piInfoEntity.setClientEngName(customerAccountVO.getCust().getGivenNameSpell() + " " + customerAccountVO.getCust().getFamilyNameSpell());
		}

		//客户手机号+区号
		piInfoEntity.setSecuritiesPhoneArea(customerAccountVO.getCust().getPhoneArea());
		piInfoEntity.setSecuritiesPhoneNumber(customerAccountVO.getCust().getPhoneNumber());

		//客户性别（如果是机构户没有性别）
		piInfoEntity.setGender(customerAccountVO.getCust().getGender());

		//账户类型
		piInfoEntity.setAccountType(customerAccountVO.getAcct().getAccountType());

		//电邮
		piInfoEntity.setEmail(customerAccountVO.getCust().getEmail());

		//取每年的1月1日
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
		calendar.set(Calendar.MONTH, 0);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		Date startDate = calendar.getTime();
		//piInfoEntity.setRenewalDate(startDate);

		return piInfoEntity;
	}
}
