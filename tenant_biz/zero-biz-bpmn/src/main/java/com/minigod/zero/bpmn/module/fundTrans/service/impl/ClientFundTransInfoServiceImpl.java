package com.minigod.zero.bpmn.module.fundTrans.service.impl;

import com.alibaba.nacos.api.config.ConfigService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minigod.zero.bpmn.module.common.service.ISysItemConfigService;
import com.minigod.zero.bpmn.module.deposit.entity.BankCardApplication;
import com.minigod.zero.bpmn.module.deposit.enums.DepositStatusEnum;
import com.minigod.zero.bpmn.module.exchange.enums.CurrencyExcEnum;
import com.minigod.zero.bpmn.module.feign.ICustomerInfoClient;
import com.minigod.zero.bpmn.module.feign.dto.AmountDTO;
import com.minigod.zero.bpmn.module.feign.enums.ThawingType;
import com.minigod.zero.bpmn.module.feign.vo.CustomerAccountVO;
import com.minigod.zero.bpmn.module.fundTrans.domain.ClientFundTransApplication;
import com.minigod.zero.bpmn.module.fundTrans.domain.ClientFundTransQuota;
import com.minigod.zero.bpmn.module.fundTrans.domain.bo.FundTransInfoBo;
import com.minigod.zero.bpmn.module.fundTrans.domain.bo.FundTransQuery;
import com.minigod.zero.bpmn.module.fundTrans.domain.vo.ClientFundTransInfoVO;
import com.minigod.zero.bpmn.module.fundTrans.mapper.ClientFundTransApplicationMapper;
import com.minigod.zero.bpmn.module.fundTrans.service.IClientFundTransQuotaService;
import com.minigod.zero.bpmn.utils.ApplicationIdUtil;
import com.minigod.zero.core.log.exception.ServiceException;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.ObjectUtil;
import com.minigod.zero.cust.feign.ICustAccountClient;
import com.minigod.zero.flow.core.common.constant.TaskConstants;
import com.minigod.zero.flow.core.constant.ProcessConstant;
import com.minigod.zero.flow.core.feign.IFlowClient;
import com.minigod.zero.flow.core.utils.FlowUtil;
import com.minigod.zero.flow.core.utils.TaskUtil;
import com.minigod.zero.system.feign.IDictBizClient;
import com.minigod.zero.system.feign.ISysClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

import com.minigod.zero.bpmn.module.fundTrans.domain.ClientFundTransInfo;
import com.minigod.zero.bpmn.module.fundTrans.mapper.ClientFundTransInfoMapper;
import com.minigod.zero.bpmn.module.fundTrans.service.ClientFundTransInfoService;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: ClientFundTransInfoServiceImpl
 * @Description: ${description}
 * @Author chenyu
 * @Date 2024/12/11
 * @Version 1.0
 */
@Service
//@AllArgsConstructor
@Slf4j
public class ClientFundTransInfoServiceImpl extends ServiceImpl<ClientFundTransInfoMapper, ClientFundTransInfo> implements ClientFundTransInfoService {
	@Autowired
	private IFlowClient flowClient;
	@Autowired
	private ClientFundTransInfoMapper baseMapper;
	@Autowired
	private ClientFundTransApplicationMapper applicationMapper;
	@Autowired

	private IClientFundTransQuotaService clientFundTransQuotaService;
	@Autowired
	private ICustomerInfoClient iCustomerInfoClient;
	private final ExecutorService executorService = new ThreadPoolExecutor(1, 1,
		0L, TimeUnit.MILLISECONDS,
		new LinkedBlockingQueue<Runnable>());

	@Value("${fundTrans.isAuto:0}")
	private Integer fundTransIsAuto;
	@Value("${fundTrans.skipReview:0}")
	private Integer fundTransSkipReview;

	@Override
	public void submitInfo(FundTransInfoBo bo) {
		String tenantId = StringUtils.isNotBlank(bo.getTenantId()) ? bo.getTenantId() : AuthUtil.getTenantId();
		Long userId = ObjectUtil.isNotEmpty(bo.getUserId()) ? bo.getUserId() : AuthUtil.getTenantCustId();
		String accountId = ObjectUtil.isNotEmpty(bo.getAccountId()) ? bo.getAccountId() : AuthUtil.getTenantUser().getAccount();

		BigDecimal quoat = clientFundTransQuotaService.queryQuota(bo.getWithdrawBusinessType(), bo.getDepositBusinessType(), bo.getCurrency());
		if (quoat != null) {
			if (bo.getAmount().compareTo(quoat) == 1) {
				throw new ServiceException("划拨额度超过" + bo.getAmount().setScale(2) + bo.getCurrency());
			}
		}
		ClientFundTransInfo info = new ClientFundTransInfo();
		info.setClientId(accountId);
		R<CustomerAccountVO> customerAccountVOR = iCustomerInfoClient.customerAccountInfo(userId);
		String clientName = "";
		String clientNameSpell = "";
		if (customerAccountVOR.isSuccess()) {
			clientName = customerAccountVOR.getData().cust().getCustName();
			clientNameSpell = customerAccountVOR.getData().cust().getCustNameSpell();
		}
		String applicationId = ApplicationIdUtil.generateFundTransId(AuthUtil.getTenantId());


		info.setClientName(clientName);
		info.setClientNameSpell(clientNameSpell);
		info.setTenantId(bo.getTenantId());
		info.setUserId(userId);
		info.setAmount(bo.getAmount());
		info.setFee(new BigDecimal(0));
		info.setWithdrawAccount(bo.getWithdrawAccount());
		info.setWithdrawBusinessType(bo.getWithdrawBusinessType());
		info.setWithdrawAmount(bo.getAmount());
		info.setDepositAccount(bo.getDepositAccount());
		info.setDepositBusinessType(bo.getDepositBusinessType());
		info.setDepositAmount(bo.getAmount().subtract(info.getFee()));
		info.setCreateTime(new Date());
		info.setUpdateTime(new Date());
		info.setCurrency(bo.getCurrency());
		info.setDepositAccount(bo.getDepositAccount());
		info.setStatus(DepositStatusEnum.FundTransStatus.NO_APPLY.getCode());
		info.setApplicationId(applicationId);

		/**
		 * 先冻结资金
		 */
		AmountDTO exchangeAmount = new AmountDTO();
		exchangeAmount.setBusinessId(applicationId);
		exchangeAmount.setAmount(info.getAmount());
		exchangeAmount.setAccountId(info.getWithdrawAccount());
		exchangeAmount.setThawingType(ThawingType.CURRENCY_EXCHANGE_FREEZE.getCode());
		exchangeAmount.setCurrency(info.getCurrency());
		exchangeAmount.setBusinessType(info.getWithdrawBusinessType());
		R result = iCustomerInfoClient.freezeAmount(exchangeAmount);
		if (!result.isSuccess()) {
			throw new ServiceException("调拨申请失败，冻结金额失败");
		}
		baseMapper.insert(info);

		ClientFundTransApplication application = new ClientFundTransApplication();
		application.setApplicationId(applicationId);
		application.setCreateDept(AuthUtil.getDeptId());
		application.setCreateUser(AuthUtil.getUserId());
		application.setCreateTime(new Date());
		application.setTenantId(AuthUtil.getTenantId());
		application.setStatus(DepositStatusEnum.FundTransStatus.NO_APPLY.getCode());
		application.setApplicationStatus(DepositStatusEnum.ApplicationFundTransStatus.COMMIT.getCode());
		application.setApplicationTitle(String.format("[%s]资金调拨审核", accountId));
		applicationMapper.insert(application);

		executorService.execute(() -> {
			Map<String, Object> variables = new HashMap<>();
			variables.put(TaskConstants.TENANT_ID, tenantId);
			variables.put(TaskConstants.APPLICATION_ID, applicationId);
			variables.put(TaskConstants.APPLICATION_TABLE, FlowUtil.getBusinessTable(ProcessConstant.FUND_TRANS_KEY));
			variables.put(TaskConstants.APPLICATION_DICT_KEY, FlowUtil.getBusinessDictKey(ProcessConstant.FUND_TRANS_KEY));
			variables.put(TaskConstants.PROCESS_INITIATOR, TaskUtil.getTaskUser());
			//在流转条件BankCardCondition类中使用
			variables.put("isAuto", fundTransIsAuto); //是否跳自动审核
			variables.put("skipReview", fundTransSkipReview); //是否跳过复审审核节点 isAuto 为1时不生效
			R r = flowClient.startProcessInstanceByKey(ProcessConstant.FUND_TRANS_KEY, variables);
			if (!r.isSuccess()) {
				log.error("提交调拨审批申请失败,原因：" + r.getMsg());
				throw new ServiceException(r.getMsg());
			}
		});
	}

	@Override
	public IPage<ClientFundTransInfoVO> queryInfoPage(IPage<ClientFundTransInfoVO> page, FundTransQuery bo) {
		bo.setUserId(AuthUtil.getTenantCustId());
		AmountDTO amountDTO = new AmountDTO();
		amountDTO.setBusinessType("FUND");
		amountDTO.setAmount(new BigDecimal(1000));
		amountDTO.setCurrency("HKD");
		amountDTO.setBusinessId(ApplicationIdUtil.generateDepositId("000000"));
		amountDTO.setCustId(AuthUtil.getTenantCustId());
		amountDTO.setAccountId(AuthUtil.getTenantUser().getAccount());
		amountDTO.setType(0);
		amountDTO.setThawingType(1);
		R deposit = iCustomerInfoClient.goldDeposit(amountDTO);
		return baseMapper.queryInfoPage(page, bo);
	}
}
