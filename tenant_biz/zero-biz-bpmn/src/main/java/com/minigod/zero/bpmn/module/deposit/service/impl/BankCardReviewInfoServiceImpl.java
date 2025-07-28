package com.minigod.zero.bpmn.module.deposit.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minigod.zero.biz.common.utils.ProtocolUtils;
import com.minigod.zero.bpmn.module.account.constants.DictTypeConstant;
import com.minigod.zero.bpmn.module.account.entity.AccountOpenInfoEntity;
import com.minigod.zero.bpmn.module.account.mapper.AccountOpenInfoMapper;
import com.minigod.zero.bpmn.module.constant.WithdrawalsFundMessageConstant;
import com.minigod.zero.bpmn.module.deposit.bo.BankCardReviewBo;
import com.minigod.zero.bpmn.module.deposit.entity.BankCardApplication;
import com.minigod.zero.bpmn.module.deposit.entity.BankCardImageEntity;
import com.minigod.zero.bpmn.module.deposit.entity.BankCardInfo;
import com.minigod.zero.bpmn.module.deposit.entity.BankCardReviewInfo;
import com.minigod.zero.bpmn.module.deposit.enums.DepositStatusEnum;
import com.minigod.zero.bpmn.module.deposit.mapper.BankCardApplicationMapper;
import com.minigod.zero.bpmn.module.deposit.mapper.BankCardReviewInfoMapper;
import com.minigod.zero.bpmn.module.deposit.service.BankCardImageService;
import com.minigod.zero.bpmn.module.deposit.service.BankCardInfoService;
import com.minigod.zero.bpmn.module.deposit.service.BankCardReviewInfoService;
import com.minigod.zero.bpmn.module.deposit.vo.BankCardApplicationVO;
import com.minigod.zero.bpmn.utils.ApplicationIdUtil;
import com.minigod.zero.core.i18n.utils.I18nUtil;
import com.minigod.zero.core.log.exception.ServiceException;
import com.minigod.zero.core.redis.lock.RedisLock;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.ObjectUtil;
import com.minigod.zero.core.tool.utils.WebUtil;
import com.minigod.zero.flow.core.common.constant.TaskConstants;
import com.minigod.zero.flow.core.constant.ProcessConstant;
import com.minigod.zero.flow.core.feign.IFlowClient;
import com.minigod.zero.flow.core.utils.FlowUtil;
import com.minigod.zero.flow.core.utils.TaskUtil;
import com.minigod.zero.platform.enums.EmailTemplate;
import com.minigod.zero.platform.utils.EmailUtil;
import com.minigod.zero.system.entity.DictBiz;
import com.minigod.zero.system.feign.IDictBizClient;
import com.minigod.zero.system.feign.IDictClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @ClassName: BankCardReviewInfoServiceImpl
 * @Description: ${description}
 * @Author chenyu
 * @Date 2024/3/13
 * @Version 1.0
 */
@Slf4j
@Service
@AllArgsConstructor
public class BankCardReviewInfoServiceImpl extends ServiceImpl<BankCardReviewInfoMapper, BankCardReviewInfo> implements BankCardReviewInfoService {

	private final IFlowClient flowClient;
	private final IDictClient dictClient;
	private final IDictBizClient dictBizClient;
	private final BankCardInfoService bankCardInfoService;
	private final BankCardImageService bankCardImageService;
	private final AccountOpenInfoMapper accountOpenInfoMapper;
	private final BankCardApplicationMapper bankCardApplicationMapper;
	private final ExecutorService executorService = new ThreadPoolExecutor(1, 1,
		0L, TimeUnit.MILLISECONDS,
		new LinkedBlockingQueue<Runnable>());
	private final static String LOCK_KEY = "LOCK:BANKREVIEW:%s_%s";

	@Override
	public int batchInsert(List<BankCardReviewInfo> list) {
		return baseMapper.batchInsert(list);
	}

	@Override
	@RedisLock(value = LOCK_KEY)
	@Transactional(rollbackFor = Exception.class)
	public String submitBankCardReview(BankCardReviewBo bo) {
		try {
			String applicationId = ApplicationIdUtil.generateBankCardId(AuthUtil.getTenantId());
			validate(bo);
			bankCardInfoService.submitBusinessCheck(bo);
			BankCardApplication application = new BankCardApplication();
			application.setApplicationId(applicationId);
			application.setCreateDept(Long.valueOf(AuthUtil.getDeptId()));
			application.setCreateUser(AuthUtil.getUserId());
			application.setCreateTime(new Date());
			application.setStartTime(new Date());
			application.setTenantId(AuthUtil.getTenantId());
			application.setStatus(DepositStatusEnum.BankCardApplicationStatus.NO_APPLY.getCode());
			application.setApplicationTitle(String.format("[%s]银行卡%s审核", bo.getClientId(), dictClient.getValue("bank_card_application_type", bo.getApplicationType().toString()).getData()));
			bankCardApplicationMapper.insert(application);
			baseMapper.insert(convertInfo(bo, applicationId));
			//判断是否有上传银行卡凭证图片
			if (bo.getImageIds() != null && bo.getImageIds().size() > 0) {
				List<BankCardImageEntity> bankCardImageEntities = bankCardImageService.queryByIds(bo.getImageIds());
				if (bankCardImageEntities != null && bankCardImageEntities.size() > 0) {
					for (BankCardImageEntity bankCardImage : bankCardImageEntities) {
						bankCardImage.setApplicationId(applicationId);
					}
					//批量更新applicationId到凭证图片实体中
					Boolean result = bankCardImageService.updateBatchById(bankCardImageEntities);
					if (result) {
						log.info("更新银行卡凭证图片applicationId成功,ApplicationID：{}", applicationId);
					} else {
						log.error("提交银行卡审批申请更新银行卡凭证图片applicationId失败,ApplicationID：{}", applicationId);
					}
				} else {
					log.warn("*****没有查询到该用户上传的凭证!******");
				}
			}

			String tenantId = AuthUtil.getTenantId();

			executorService.execute(() -> {
				Map<String, Object> variables = new HashMap<>();
				variables.put(TaskConstants.TENANT_ID, tenantId);
				variables.put(TaskConstants.APPLICATION_ID, applicationId);
				variables.put(TaskConstants.APPLICATION_TABLE, FlowUtil.getBusinessTable(ProcessConstant.BANK_CARD_KEY));
				variables.put(TaskConstants.APPLICATION_DICT_KEY, FlowUtil.getBusinessDictKey(ProcessConstant.BANK_CARD_KEY));
				variables.put(TaskConstants.PROCESS_INITIATOR, TaskUtil.getTaskUser());
				//在流转条件BankCardCondition类中使用
				variables.put("applicationType", bo.getApplicationType()); //审核类型
				variables.put("autoAudit", "openAccount".equalsIgnoreCase(bo.getSource())); //是否跳过审核节点
				R r = flowClient.startProcessInstanceByKey(ProcessConstant.BANK_CARD_KEY, variables);
				if (!r.isSuccess()) {
					log.error("提交银行卡审批申请失败,原因：" + r.getMsg());
					throw new ServiceException(I18nUtil.getMessage(WithdrawalsFundMessageConstant.WITHDRAWALS_FUND_SUBMIT_APPLICATION_FAILED_NOTICE));
				}
			});
			sendEmail(bo);
			return applicationId;
		} catch (Exception exception) {
			log.error("银行卡审核提交异常", exception);
			throw new ServiceException(I18nUtil.getMessage(WithdrawalsFundMessageConstant.WITHDRAWALS_FUND_SUBMIT_APPLICATION_FAILED_NOTICE));
		}
	}

	private void sendEmail(BankCardReviewBo bo) {
		try {
			String now = DateUtil.now();
			AccountOpenInfoEntity openInfo = accountOpenInfoMapper.openAccountUserDetail(bo.getUserId());
			String name = openInfo.getClientName();
			if (StringUtils.isEmpty(name)) {
				name = openInfo.getFamilyNameSpell() + " " + openInfo.getGivenNameSpell();
			}
			R<List<DictBiz>> emailAddress = dictBizClient.getListByTenantId(AuthUtil.getTenantId()
				, DictTypeConstant.SYSTEM_EMAIL_ADDRESS);
			//ops发送审核邮件
			List<DictBiz> data = emailAddress.getData();
			List<String> emailList = data.stream().map(DictBiz::getDictKey).collect(Collectors.toList());
			List<String> paramList = new ArrayList<>();
			paramList.add(now);
			paramList.add(name);
			paramList.add(bo.getBankName());
			paramList.add(ProtocolUtils.bankCardFormatter(bo.getBankNo()));

			List<String> titleList = new ArrayList<>();
			titleList.add(now);
			titleList.add(name);
			EmailUtil.builder()
				.lang(WebUtil.getLanguage())
				.titleParams(titleList)
				.paramList(paramList)
				.accepts(emailList)
				.templateCode(EmailTemplate.BANK_CARD_BINDING_NOTIFICATION.getCode())
				.sendAsync();

		} catch (Exception e) {
			log.info("发送邮件失败 {}", e.getStackTrace());
		}
	}

	@Override
	public void bindBankInfo(String applicationId) {
		BankCardApplicationVO applicationVO = bankCardApplicationMapper.queryApplicationId(applicationId);
		BankCardInfo bankCardInfo = new BankCardInfo();
		BeanUtils.copyProperties(applicationVO.getReviewInfo(), bankCardInfo);
		bankCardInfo.setAuthSign(1);
		bankCardInfo.setBindSource(applicationVO.getReviewInfo().getBindSource());
		bankCardInfo.setRegisterTime(new Date());
		bankCardInfo.setComFund("0");
		bankCardInfo.setEddaFund("0");
		bankCardInfo.setStatus(1);
		bankCardInfo.setCreateTime(new Date());
		bankCardInfo.setBankId(applicationVO.getReviewInfo().getBankId());
		bankCardInfo.setCreateUser(applicationVO.getLastApprovalUser());
		bankCardInfo.setId(null);
		bankCardInfoService.save(bankCardInfo);

		BankCardReviewInfo reviewInfo = applicationVO.getReviewInfo();
		reviewInfo.setIsFinish(1);
		reviewInfo.setBankCardId(bankCardInfo.getId());
		baseMapper.updateById(reviewInfo);

		LambdaUpdateWrapper<BankCardApplication> updateWrapper = new LambdaUpdateWrapper<>();
		updateWrapper.eq(BankCardApplication::getApplicationId, applicationId);
		updateWrapper.set(BankCardApplication::getStatus, DepositStatusEnum.BankCardApplicationStatus.PENDING_AUDIT.getCode());
		updateWrapper.set(BankCardApplication::getUpdateTime, new Date());
		bankCardApplicationMapper.update(null, updateWrapper);
	}

	@Override
	public void unbindBankInfo(String applicationId) {
		BankCardApplicationVO applicationVO = bankCardApplicationMapper.queryApplicationId(applicationId);
		LambdaUpdateWrapper<BankCardInfo> updateWrapper = new LambdaUpdateWrapper<>();
		updateWrapper.set(BankCardInfo::getStatus, 0);
		updateWrapper.eq(BankCardInfo::getId, applicationVO.getReviewInfo().getBankCardId());
		bankCardInfoService.update(updateWrapper);
		BankCardReviewInfo reviewInfo = applicationVO.getReviewInfo();
		reviewInfo.setIsFinish(1);
		baseMapper.updateById(reviewInfo);

		LambdaUpdateWrapper<BankCardApplication> updateApplicationWrapper = new LambdaUpdateWrapper<>();
		updateApplicationWrapper.eq(BankCardApplication::getApplicationId, applicationId);
		updateApplicationWrapper.set(BankCardApplication::getStatus, DepositStatusEnum.BankCardApplicationStatus.PENDING_AUDIT.getCode());
		updateApplicationWrapper.set(BankCardApplication::getUpdateTime, new Date());
		bankCardApplicationMapper.update(null, updateApplicationWrapper);
	}

	@Override
	public void editBankInfo(String applicationId) {
		BankCardApplicationVO applicationVO = bankCardApplicationMapper.queryApplicationId(applicationId);
		BankCardReviewInfo reviewInfo = applicationVO.getReviewInfo();
		BankCardInfo bankCardInfo = new BankCardInfo();
		BeanUtils.copyProperties(applicationVO.getReviewInfo(), bankCardInfo);
		bankCardInfo.setId(reviewInfo.getBankCardId());
		bankCardInfo.setAuthSign(1);
		bankCardInfo.setBindSource(applicationVO.getReviewInfo().getBindSource());
		bankCardInfo.setRegisterTime(new Date());
		bankCardInfo.setComFund("0");
		bankCardInfo.setStatus(1);
		bankCardInfo.setUpdateTime(new Date());
		bankCardInfoService.updateById(bankCardInfo);

		reviewInfo.setIsFinish(1);
		baseMapper.updateById(reviewInfo);

		LambdaUpdateWrapper<BankCardApplication> updateApplicationWrapper = new LambdaUpdateWrapper<>();
		updateApplicationWrapper.eq(BankCardApplication::getApplicationId, applicationId);
		updateApplicationWrapper.set(BankCardApplication::getStatus, DepositStatusEnum.BankCardApplicationStatus.PENDING_AUDIT.getCode());
		updateApplicationWrapper.set(BankCardApplication::getUpdateTime, new Date());
		bankCardApplicationMapper.update(null, updateApplicationWrapper);
	}

	private void validate(BankCardReviewBo bo) {
		if (bo.getApplicationType() != 1) {
			if (ObjectUtil.isEmpty(bo.getBankCardId())) {
				String applicationType = dictClient.getValue("bank_card_application_type", bo.getApplicationType().toString()).getData();
				throw new ServiceException(String.format(I18nUtil.getMessage(WithdrawalsFundMessageConstant.WITHDRAWALS_FUND_BANK_CARD_ID_EMPTY_NOTICE), applicationType));
			}
		}
	}

	private BankCardReviewInfo convertInfo(BankCardReviewBo bankCardReviewBo, String applicationId) {
		BankCardReviewInfo info = new BankCardReviewInfo();
		BeanUtils.copyProperties(bankCardReviewBo, info);
		if (info.getApplicationType() == 1) {
			info.setBankCardId(null);
		}
		info.setTenantId(AuthUtil.getTenantId());
		info.setUserId(AuthUtil.getTenantCustId());
		info.setApplicationId(applicationId);
		return info;
	}
}
