package com.minigod.zero.bpmn.module.pi.service.impl;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.biz.common.utils.FileUtil;
import com.minigod.zero.bpmn.module.account.entity.OrganizationOpenInfoEntity;
import com.minigod.zero.bpmn.module.account.enums.PIApplyPdfEnum;
import com.minigod.zero.bpmn.module.account.service.IAccountOpenInfoService;
import com.minigod.zero.bpmn.module.account.service.IOrganizationOpenAccountOnlineService;
import com.minigod.zero.bpmn.module.account.vo.AccountOpenInfoVO;
import com.minigod.zero.bpmn.module.common.entity.FileUploadInfoEntity;
import com.minigod.zero.bpmn.module.common.enums.UploadBusinessType;
import com.minigod.zero.bpmn.module.common.enums.UploadFileType;
import com.minigod.zero.bpmn.module.common.service.IFileUploadInfoService;
import com.minigod.zero.bpmn.module.pi.bo.query.ProfessionalInvestorPIApplicationQuery;
import com.minigod.zero.bpmn.module.pi.entity.ProfessionalInvestorPIApplicationEntity;
import com.minigod.zero.bpmn.module.pi.entity.ProfessionalInvestorPIInfoEntity;
import com.minigod.zero.bpmn.module.pi.enums.ApplicationStatusEnum;
import com.minigod.zero.bpmn.module.pi.mapper.ProfessionalInvestorPIApplicationMapper;
import com.minigod.zero.bpmn.module.pi.mapper.ProfessionalInvestorPIInfoMapper;
import com.minigod.zero.bpmn.module.pi.mapper.ProfessionalInvestorPIVoucherImageMapper;
import com.minigod.zero.bpmn.module.pi.service.IProfessionalInvestorPIApplicationService;
import com.minigod.zero.bpmn.module.pi.service.IProfessionalInvestorPIFileGenerateService;
import com.minigod.zero.bpmn.module.pi.service.ProfessionalInvestorPIPassNodeService;
import com.minigod.zero.bpmn.module.pi.vo.PIApplicationStatusVO;
import com.minigod.zero.bpmn.module.pi.vo.ProfessionalInvestorPIApplicationVO;
import com.minigod.zero.bpmn.module.pi.vo.ProfessionalInvestorPIInfoVO;
import com.minigod.zero.bpmn.utils.BusinessLock;
import com.minigod.zero.bpmn.utils.DateUtils;
import com.minigod.zero.bpmn.utils.FileUtils;
import com.minigod.zero.core.log.exception.ServiceException;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.core.oss.model.ZeroFile;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.ObjectUtil;
import com.minigod.zero.flow.core.feign.IFlowClient;
import com.minigod.zero.platform.enums.EmailTemplate;
import com.minigod.zero.platform.enums.LanguageType;
import com.minigod.zero.platform.utils.EmailUtil;
import com.minigod.zero.resource.feign.IOssClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 专业投资人PI申请记录表 服务实现类
 *
 * @author eric
 * @since 2024-05-07
 */
@Slf4j
@Service
public class ProfessionalInvestorPIApplicationServiceImpl
	extends BaseServiceImpl<ProfessionalInvestorPIApplicationMapper, ProfessionalInvestorPIApplicationEntity>
	implements IProfessionalInvestorPIApplicationService {
	private final ProfessionalInvestorPIVoucherImageMapper voucherImageMapper;
	private final ProfessionalInvestorPIInfoMapper investorPIInfoMapper;
	private final ProfessionalInvestorPIApplicationMapper applicationMapper;
	private final ProfessionalInvestorPIPassNodeService passNodeService;
	private final IProfessionalInvestorPIFileGenerateService piFileGenerateService;
	private final IAccountOpenInfoService accountOpenInfoService;
	private final IOrganizationOpenAccountOnlineService iOrganizationOpenAccountOnlineService;
	private final IFileUploadInfoService fileUploadInfoService;
	private final IFlowClient flowClient;
	private final IOssClient ossClient;

	public ProfessionalInvestorPIApplicationServiceImpl(ProfessionalInvestorPIVoucherImageMapper voucherImageMapper,
														ProfessionalInvestorPIInfoMapper investorPIInfoMapper,
														ProfessionalInvestorPIApplicationMapper applicationMapper,
														ProfessionalInvestorPIPassNodeService passNodeService,
														@Lazy IProfessionalInvestorPIFileGenerateService piFileGenerateService,
														IAccountOpenInfoService accountOpenInfoService,
														IOrganizationOpenAccountOnlineService iOrganizationOpenAccountOnlineService,
														IFileUploadInfoService fileUploadInfoService,
														IFlowClient flowClient,
														IOssClient ossClient) {
		this.voucherImageMapper = voucherImageMapper;
		this.investorPIInfoMapper = investorPIInfoMapper;
		this.applicationMapper = applicationMapper;
		this.passNodeService = passNodeService;
		this.piFileGenerateService = piFileGenerateService;
		this.accountOpenInfoService = accountOpenInfoService;
		this.iOrganizationOpenAccountOnlineService = iOrganizationOpenAccountOnlineService;
		this.fileUploadInfoService = fileUploadInfoService;
		this.flowClient = flowClient;
		this.ossClient = ossClient;
	}

	/**
	 * 专业投资人申请表分页查询
	 *
	 * @param page
	 * @param applicationQuery
	 * @return
	 */
	@Override
	public IPage<ProfessionalInvestorPIApplicationVO> selectProfessionalInvestorPIApplicationPage(IPage<ProfessionalInvestorPIApplicationVO> page, ProfessionalInvestorPIApplicationQuery applicationQuery) {
		applicationQuery.setTenantId(AuthUtil.getTenantId());
		IPage<ProfessionalInvestorPIApplicationVO> result = applicationMapper.selectProfessionalInvestorPIApplicationPage(page, applicationQuery);
		result.getRecords().forEach(item -> {
			if (item.getStatus() != null) {
				switch (item.getStatus()) {
					case 1:
						item.setStatusName(ApplicationStatusEnum.PRELIMINARY_REVIEW.getDescription());
						break;
					case 2:
						item.setStatusName(ApplicationStatusEnum.UNDER_REVIEW.getDescription());
						break;
					case 3:
						item.setStatusName(ApplicationStatusEnum.APPLY_SUCCESS.getDescription());
						break;
					case 4:
						item.setStatusName(ApplicationStatusEnum.APPLY_PASS.getDescription());
						break;
					case 5:
						item.setStatusName(ApplicationStatusEnum.APPLY_EXPIRED.getDescription());
						break;
					case 6:
						item.setStatusName(ApplicationStatusEnum.APPLY_FAILED.getDescription());
						break;
					case 7:
						item.setStatusName(ApplicationStatusEnum.APPLY_REVOKE.getDescription());
						break;
					case 8:
						item.setStatusName(ApplicationStatusEnum.APPLY_ERROR.getDescription());
				}
			}
			if (item.getApplicationStatus() != null) {
				switch (item.getApplicationStatus()) {
					case 0:
						item.setApplicationStatusName(ApplicationStatusEnum.PENDING.getDescription());
						break;
					case 1:
						item.setApplicationStatusName(ApplicationStatusEnum.PRELIMINARY_REVIEW.getDescription());
						break;
					case 2:
						item.setApplicationStatusName(ApplicationStatusEnum.UNDER_REVIEW.getDescription());
						break;
					case 3:
						item.setApplicationStatusName(ApplicationStatusEnum.APPLY_SUCCESS.getDescription());
						break;
					case 4:
						item.setApplicationStatusName(ApplicationStatusEnum.APPLY_PASS.getDescription());
						break;
					case 99:
						item.setApplicationStatusName(ApplicationStatusEnum.APPLY_END.getDescription());
						break;
				}
			}
		});
		return result;
	}

	/**
	 * 申领专业投资人申请记录
	 *
	 * @param applicationId
	 * @return
	 */
	@Override
	public void assignDrafter(String applicationId) {
		LambdaQueryWrapper<ProfessionalInvestorPIApplicationEntity> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(ProfessionalInvestorPIApplicationEntity::getApplicationId, applicationId);
		ProfessionalInvestorPIApplicationEntity applicationEntity = baseMapper.selectOne(queryWrapper);
		if (ObjectUtil.isEmpty(applicationEntity)) {
			throw new ServiceException(String.format("申领专业投资人申请记录任务失败,未查询到申请记录,申请ID:%s", applicationId));
		}
		if (applicationEntity.getAssignDrafter() == null) {
			// 认领任务
			R claimTask = flowClient.taskClaim(applicationEntity.getTaskId());
			if (claimTask.isSuccess()) {
				baseMapper.addAssignDrafter(applicationId, AuthUtil.getUserId().toString());
				log.info("专业投资人申请申领:" + applicationId + "<br/>" + "任务ID:" + applicationEntity.getTaskId());
			} else {
				log.error(String.format("专业投资人申请,PI认证流程:%s 认领任务失败,原因: %s", applicationId, claimTask.getMsg()));
				throw new ServiceException(String.format("申领专业投资人申请记录任务失败,原因:%s", claimTask.getMsg()));
			}
		} else {
			log.error(String.format("专业投资人申请流程:%s 认领任务失败,原因: 不满足认领条件(已经被他人申领)!", applicationEntity.getApplicationId()));
			throw new ServiceException(String.format("申领专业投资人申请记录任务失败,原因: 不满足认领条件(已经被他人申领)!"));
		}
	}

	/**
	 * 获取专业投资人申请记录
	 *
	 * @param applicationId
	 * @return
	 */
	@Override
	public ProfessionalInvestorPIApplicationVO queryByApplicationId(String applicationId) {
		ProfessionalInvestorPIApplicationVO result = new ProfessionalInvestorPIApplicationVO();
		LambdaQueryWrapper<ProfessionalInvestorPIApplicationEntity> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(ProfessionalInvestorPIApplicationEntity::getApplicationId, applicationId);
		ProfessionalInvestorPIApplicationEntity applicationEntity = baseMapper.selectOne(queryWrapper);
		if (ObjectUtil.isNotEmpty(applicationEntity)) {
			BeanUtils.copyProperties(applicationEntity, result);
			result.setInfo(investorPIInfoMapper.queryByApplicationId(applicationId));
			result.setImagesList(voucherImageMapper.queryByApplicationId(applicationId));
		}
		if (result.getStatus() != null) {
			switch (result.getStatus()) {
				case 1:
					result.setStatusName(ApplicationStatusEnum.PRELIMINARY_REVIEW.getDescription());
					break;
				case 2:
					result.setStatusName(ApplicationStatusEnum.UNDER_REVIEW.getDescription());
					break;
				case 3:
					result.setStatusName(ApplicationStatusEnum.APPLY_SUCCESS.getDescription());
					break;
				case 4:
					result.setStatusName(ApplicationStatusEnum.APPLY_PASS.getDescription());
					break;
				case 5:
					result.setStatusName(ApplicationStatusEnum.APPLY_EXPIRED.getDescription());
					break;
				case 6:
					result.setStatusName(ApplicationStatusEnum.APPLY_FAILED.getDescription());
					break;
				case 7:
					result.setStatusName(ApplicationStatusEnum.APPLY_REVOKE.getDescription());
					break;
				case 8:
					result.setStatusName(ApplicationStatusEnum.APPLY_ERROR.getDescription());
			}
		}
		if (result.getApplicationStatus() != null) {
			switch (result.getApplicationStatus()) {
				case 0:
					result.setApplicationStatusName(ApplicationStatusEnum.PENDING.getDescription());
					break;
				case 1:
					result.setApplicationStatusName(ApplicationStatusEnum.PRELIMINARY_REVIEW.getDescription());
					break;
				case 2:
					result.setApplicationStatusName(ApplicationStatusEnum.UNDER_REVIEW.getDescription());
					break;
				case 3:
					result.setApplicationStatusName(ApplicationStatusEnum.APPLY_SUCCESS.getDescription());
					break;
				case 4:
					result.setApplicationStatusName(ApplicationStatusEnum.APPLY_PASS.getDescription());
					break;
				case 99:
					result.setApplicationStatusName(ApplicationStatusEnum.APPLY_END.getDescription());
					break;
			}
		}
		return result;
	}

	/**
	 * 审批不通过
	 *
	 * @param applicationId
	 * @param instanceId
	 * @param reason
	 */
	@Override
	public void rejectNode(String applicationId, String instanceId, String reason) {
		BusinessLock businessLock = new BusinessLock(true) {
			@Override
			public void business() {
				/**
				 * 这里使用了Redis锁，因此事务注解不能直接加在外层方法上，而实际的业务逻辑是在 BusinessLock 的匿名内部类中执行
				 由于使用了匿名内部类，这会导致事务上下文无法正确传播到内部类中
				 Spring的事务管理是基于代理的，而内部类的方法调用会绕过代理机制。
				 */
				passNodeService.doUnPassNode(applicationId, instanceId, reason);
				sendEmail(applicationId, PIApplyPdfEnum.NOTICE_REGARDED_AS_PROFESSIONAL_INVESTORS, false);
			}
		};
		String lockKey = "PROFESSIONAL_INVESTOR_PI_REJECT_NODE:" + applicationId;
		businessLock.lock(lockKey);
	}

	/**
	 * 审批通过
	 *
	 * @param applicationId
	 * @param taskId
	 * @param reason
	 */
	@Override
	public void passNode(String applicationId, String taskId, String reason) {
		BusinessLock businessLock = new BusinessLock(true) {
			@Override
			public void business() {
				/**
				 * 这里使用了Redis锁，因此事务注解不能直接加在外层方法上，而实际的业务逻辑是在 BusinessLock 的匿名内部类中执行
				 由于使用了匿名内部类，这会导致事务上下文无法正确传播到内部类中
				 Spring的事务管理是基于代理的，而内部类的方法调用会绕过代理机制。
				 */
				passNodeService.doPassNode(applicationId, taskId, reason);
			}
		};
		String lockKey = "PROFESSIONAL_INVESTOR_PI_PASS_NODE:" + applicationId;
		businessLock.lock(lockKey);
	}

	/**
	 * 查询审批状态
	 *
	 * @return
	 */
	@Override
	public Integer queryApplicationStatus() {
		LambdaQueryWrapper<ProfessionalInvestorPIInfoEntity> queryWrapper = new LambdaQueryWrapper();
		queryWrapper.eq(ProfessionalInvestorPIInfoEntity::getClientId, AuthUtil.getTenantCustId());
		queryWrapper.last("limit 1");
		queryWrapper.orderByDesc(ProfessionalInvestorPIInfoEntity::getApplicationTime);
		ProfessionalInvestorPIInfoEntity investorPIInfoEntity = investorPIInfoMapper.selectOne(queryWrapper);

		if (investorPIInfoEntity != null) {
			LambdaQueryWrapper<ProfessionalInvestorPIApplicationEntity> queryApplicationWrapper = new LambdaQueryWrapper<>();
			queryApplicationWrapper.eq(ProfessionalInvestorPIApplicationEntity::getApplicationId, investorPIInfoEntity.getApplicationId());
			ProfessionalInvestorPIApplicationEntity applicationEntity = baseMapper.selectOne(queryApplicationWrapper);

			if (applicationEntity != null) {
				ApplicationStatusEnum applicationStatusEnum = ApplicationStatusEnum.fromStatus(applicationEntity.getStatus());
				if (applicationStatusEnum != null) {
					return applicationStatusEnum.getStatus();
				}
			}
		}
		return ApplicationStatusEnum.NO_APPLY.getStatus();
	}

	/**
	 * 根据当前节点查询审批列表
	 *
	 * @param currentNodeName
	 * @return
	 */
	@Override
	public List<ProfessionalInvestorPIApplicationEntity> queryListByNode(String currentNodeName) {
		LambdaQueryWrapper<ProfessionalInvestorPIApplicationEntity> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(ProfessionalInvestorPIApplicationEntity::getCurrentNode, currentNodeName);
		queryWrapper.orderByDesc(ProfessionalInvestorPIApplicationEntity::getLastApprovalTime);
		return baseMapper.selectList(queryWrapper);
	}

	/**
	 * 发送邮件
	 *
	 * @param applicationId
	 * @param piApplyPdfType
	 * @param isApproved
	 */
	public void sendEmail(String applicationId, PIApplyPdfEnum piApplyPdfType, Boolean isApproved) {
		try {
			// 查询PI申请信息
			ProfessionalInvestorPIInfoVO infoVO = investorPIInfoMapper.queryByApplicationId(applicationId);
			if (infoVO == null) {
				log.error("查询PI申请信息失败，applicationId为：{} 的记录不存在!", applicationId);
				return;
			}
			// 查询开户信息
			AccountOpenInfoVO accountOpenInfoVO = accountOpenInfoService.queryByUserId(infoVO.getClientId());

			//查询机构开户状态
			OrganizationOpenInfoEntity organizationOpenInfoEntity = iOrganizationOpenAccountOnlineService.queryOrganizationOpenAccountByCustId(infoVO.getClientId());
			//如果个人开户和机构开户都查不到该用户的资料说明还没有提交开户申请
			if (accountOpenInfoVO == null && organizationOpenInfoEntity == null) {
				log.error("查询开户信息失败，userId为：{} 的记录不存在!", infoVO.getClientId());
				return;
			}
			//收件人
			List<String> emailList = new ArrayList<>();
			//如果是个人户
			if (accountOpenInfoVO != null) {
				emailList.add(accountOpenInfoVO.getEmail());
			}
			// 如果是机构户
			if (organizationOpenInfoEntity != null){
				emailList.add(organizationOpenInfoEntity.getContactEmail());
			}
			//如果是审批通过
			if (isApproved) {
				// 生成PDF文件
				String pdfPath = piFileGenerateService.generatePDFFile(applicationId, piApplyPdfType);
				if (StringUtils.isEmpty(pdfPath)) {
					log.error("生成PDF文件失败，PDF文件路径为空，PDF文件生成失败，请检查！");
					return;
				}
				log.info("PDF文件:《{}》生成成功，PDF文件路径为：{}", piApplyPdfType.getName(), pdfPath);
				MultipartFile file = FileUtil.getMultipartFile(FileUtils.file(pdfPath));
				String originalFileName = file.getOriginalFilename();
				String suffix = StringUtils.substring(originalFileName, originalFileName.lastIndexOf("."), originalFileName.length());
				String uuid = IdUtil.fastSimpleUUID();
				String fileName = piApplyPdfType.getName() + suffix;

				String path = DateUtils.datePath() + "/" + uuid;
				log.info("PDF文件上传路径为：{},文件名:{}", path, fileName);
				long startTime = System.currentTimeMillis();
				R<ZeroFile> zeroFileR = ossClient.uploadMinioFile(file, path + suffix);
				if (!zeroFileR.isSuccess()) {
					log.error("文件上传失败，失败原因:{}，请检查！", zeroFileR.getMsg());
					throw new ServiceException(zeroFileR.getMsg());
				}
				log.info("上传文件(" + piApplyPdfType.getName() + ")完成,耗时：" + (System.currentTimeMillis() - startTime));
				FileUploadInfoEntity fileUploadInfo = new FileUploadInfoEntity();
				fileUploadInfo.setFileName(fileName);
				fileUploadInfo.setFileUrl(zeroFileR.getData().getLink());
				fileUploadInfo.setFileType(UploadFileType.PI_FILE_NOTICE_REGARDED_AS_PROFESSIONAL_INVESTORS.getFileType());
				fileUploadInfo.setOssId(zeroFileR.getData().getAttachId().toString());
				fileUploadInfo.setBusinessId(Long.valueOf(applicationId));
				fileUploadInfo.setBusinessType(UploadBusinessType.PI_NOTICE_REGARDED_AS_PROFESSIONAL_INVESTORS.getBusinessType());
				fileUploadInfo.setCreateUser(AuthUtil.getUserId());
				fileUploadInfo.setCreateDept(Long.valueOf(StringUtils.isEmpty(AuthUtil.getDeptId()) ? "-1" : AuthUtil.getDeptId()));
				fileUploadInfo.setCreateTime(new Date());
				log.info("准备持久化文件附件表，文件信息为：{}", JSONObject.toJSONString(fileUploadInfo));
				boolean result = fileUploadInfoService.save(fileUploadInfo);
				if (!result) {
					log.error("文件保存到附件上传表失败,跳过邮件发送步骤，请检查！");
				} else {
					log.info("文件保存到附件上传表成功,文件ID为:{},开始邮件发送步骤!", fileUploadInfo.getId());
					//邮件标题参数列表
					List<String> titleParams = new ArrayList<>();
					titleParams.add(infoVO.getClientAccount());

					//邮件内容参数列表
					List<String> paramList = new ArrayList<>();
					paramList.add(StringUtils.isEmpty(infoVO.getClientName()) ? infoVO.getClientEngName() : infoVO.getClientName());

					log.info("发送《关于被视为专业投资者的通知》邮件-(审批通过)，收件人：{}", emailList);
					EmailUtil.builder()
						.lang(LanguageType.ZH_HANT.getCode())
						.titleParams(titleParams)
						.accepts(emailList)
						.paramList(paramList)
						//这里需要新建一个邮件模板
						.templateCode(EmailTemplate.PI_PASS_APPROVAL_REMINDER.getCode())
						//添加附件
						.attachmentUrls(Arrays.asList(fileUploadInfo.getFileUrl()))
						.sendAsync();
					log.info("发送《关于被视为专业投资者的通知》邮件成功-(审批通过)，收件人：{}", emailList);
				}
			} else {
				//邮件标题参数列表
				List<String> titleParams = new ArrayList<>();
				titleParams.add(infoVO.getClientAccount());

				//邮件内容参数列表
				List<String> paramList = new ArrayList<>();
				paramList.add(StringUtils.isEmpty(infoVO.getClientName()) ? infoVO.getClientEngName() : infoVO.getClientName());

				log.info("发送《关于被视为专业投资者的通知》邮件-(审批未通过)，收件人：{}", emailList);
				EmailUtil.builder()
					.lang(LanguageType.ZH_HANT.getCode())
					.titleParams(titleParams)
					.accepts(emailList)
					.paramList(paramList)
					//这里需要新建一个邮件模板
					.templateCode(EmailTemplate.PI_NO_APPROVAL_REMINDER.getCode())
					.sendAsync();
				log.info("发送《关于被视为专业投资者的通知》邮件成功-(审批未通过)，收件人：{}", emailList);
			}
		} catch (Exception e) {
			log.error("发送邮件失败，applicationId为：{} 的邮件发送失败，请检查！异常详情->", applicationId, e);
		}
	}

}
