package com.minigod.zero.bpmn.module.exchange.feign;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.minigod.zero.biz.common.utils.JSONUtil;
import com.minigod.zero.bpmn.module.account.mapper.AccountOpenInfoMapper;
import com.minigod.zero.bpmn.module.account.vo.AccountOpenInfoVO;
import com.minigod.zero.bpmn.module.exchange.service.ICurrencyExchangeRateInfoService;
import com.minigod.zero.bpmn.module.exchange.service.ICustomerCurrencyExchangeApplicationService;
import com.minigod.zero.bpmn.module.exchange.service.ICustomerCurrencyExchangeInfoService;
import com.minigod.zero.bpmn.module.feign.ICustomerInfoClient;
import com.minigod.zero.bpmn.module.feign.dto.AmountDTO;
import com.minigod.zero.bpmn.module.feign.enums.ThawingType;
import com.minigod.zero.bpmn.module.feign.vo.CustomerAccountDetailVO;
import com.minigod.zero.bpmn.utils.ApplicationIdUtil;
import com.minigod.zero.core.log.exception.ServiceException;
import com.minigod.zero.core.tenant.annotation.NonDS;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.flow.core.common.constant.TaskConstants;
import com.minigod.zero.flow.core.constant.ProcessConstant;
import com.minigod.zero.flow.core.feign.IFlowClient;
import com.minigod.zero.flow.core.utils.FlowUtil;
import com.minigod.zero.flow.core.utils.TaskUtil;
import com.minigod.zero.bpmn.module.exchange.entity.CurrencyExchangeRateInfo;
import com.minigod.zero.bpmn.module.exchange.entity.CustomerCurrencyExchangeApplication;
import com.minigod.zero.bpmn.module.exchange.entity.CustomerCurrencyExchangeInfo;
import com.minigod.zero.bpmn.module.exchange.enums.CurrencyExcEnum;
import com.minigod.zero.bpmn.module.exchange.vo.CustomerCurrencyExchangeInfoVO;
import com.minigod.zero.platform.constants.CommonTemplateCode;
import com.minigod.zero.platform.enums.InformEnum;
import com.minigod.zero.platform.enums.MsgStaticType;
import com.minigod.zero.platform.enums.PushTemplate;
import com.minigod.zero.platform.utils.PushUtil;
import com.minigod.zero.trade.feign.ICounterFundClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.map.LinkedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author chen
 * @ClassName CurrencyExchangeClient.java
 * @Description TODO
 * @createTime 2024年03月16日 11:03:00
 */
@Slf4j
@NonDS
@RestController
@RequiredArgsConstructor
public class CurrencyExchangeClient implements ICurrencyExchangeClient {

	@Resource
	private ICurrencyExchangeRateInfoService currencyExchangeRateInfoService;

	@Resource
	private ICustomerCurrencyExchangeInfoService customerCurrencyExchangeInfoService;

	@Resource
	private ICustomerCurrencyExchangeApplicationService customerCurrencyExchangeApplicationService;

	@Autowired
	IFlowClient flowClient;

	@Autowired
	ICounterFundClient counterFundClient;

	@Resource
	private ICustomerInfoClient iCustomerInfoClient;
	@Autowired
	private AccountOpenInfoMapper accountOpenInfoMapper;


	@Override
	public List<CurrencyExchangeRateInfo> getMoneyRate() {
		LambdaQueryWrapper<CurrencyExchangeRateInfo> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(CurrencyExchangeRateInfo::getStatus, 1);
		queryWrapper.orderByDesc(CurrencyExchangeRateInfo::getCreateTime);
		return currencyExchangeRateInfoService.list(queryWrapper);
	}

	@Override
	public R submit(CustomerCurrencyExchangeInfo customerCurrencyExchangeInfo) {
		String applicationId = ApplicationIdUtil.generateCurrencyExchangeId(customerCurrencyExchangeInfo.getTenantId());
		/**
		 * 先冻结资金
		 */
		AmountDTO exchangeAmount = new AmountDTO();
		exchangeAmount.setBusinessId(applicationId);
		exchangeAmount.setAmount(customerCurrencyExchangeInfo.getAmountOut());
		exchangeAmount.setAccountId(customerCurrencyExchangeInfo.getFundAccount());
		exchangeAmount.setThawingType(ThawingType.CURRENCY_EXCHANGE_FREEZE.getCode());
		exchangeAmount.setCurrency(CurrencyExcEnum.CurrencyType.getName(customerCurrencyExchangeInfo.getCurrencyOut()));
		R result = iCustomerInfoClient.freezeAmount(exchangeAmount);
		if (!result.isSuccess()){
			throw new ServiceException("兑换失败，冻结失败");
		}
		customerCurrencyExchangeInfo.setCreateTime(new Date());
		customerCurrencyExchangeInfo.setUpdateTime(new Date());
		customerCurrencyExchangeInfo.setStatus(CurrencyExcEnum.AppExchangeStatus.PENDING.getIndex());

		customerCurrencyExchangeInfo.setApplicationId(applicationId);
		customerCurrencyExchangeInfoService.getBaseMapper().insert(customerCurrencyExchangeInfo);
		// 流程审批
		CustomerCurrencyExchangeApplication application = new CustomerCurrencyExchangeApplication();
		application.setApplicationId(applicationId);
		application.setApplicationTitle("货币兑换[" + customerCurrencyExchangeInfo.getClientName() + "]");
		application.setCreateTime(new Date());
		application.setStartTime(new Date());
		customerCurrencyExchangeApplicationService.save(application);

		String key = ProcessConstant.CURRENCY_EXCHANGE;
		String table = FlowUtil.getBusinessTable(key);
		String dictKey = FlowUtil.getBusinessDictKey(key);
		Map<String, Object> variables = new LinkedMap<>();
		variables.put(TaskConstants.IS_BACK, 0);
		variables.put(TaskConstants.PROCESS_INITIATOR, TaskUtil.getTaskUser());
		variables.put(TaskConstants.APPLICATION_TABLE, table);
		variables.put(TaskConstants.APPLICATION_DICT_KEY, dictKey);
		variables.put(TaskConstants.APPLICATION_ID, applicationId);
		variables.put(TaskConstants.TENANT_ID, "000000");
		R flowResult = flowClient.startProcessInstanceByKey(key, variables);
		if (!flowResult.isSuccess()) {
			log.error("启动货币兑换" + applicationId + "流程失败：" + flowResult.getMsg());
			/**
			 * 此时资金冻结状态，需人工在失败处处理
			 */
			customerCurrencyExchangeInfo.setStatus(CurrencyExcEnum.AppExchangeStatus.FAIL.getIndex());
			customerCurrencyExchangeInfoService.updateById(customerCurrencyExchangeInfo);
		}
		return R.success();
	}

	@Override
	public R exchangeJob(Map<String, Object> map) {
		LambdaQueryWrapper<CustomerCurrencyExchangeApplication> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(CustomerCurrencyExchangeApplication::getCurrentNode, "兑换");
		queryWrapper.eq(CustomerCurrencyExchangeApplication::getApplicationStatus, 3);
		List<CustomerCurrencyExchangeApplication> list = customerCurrencyExchangeApplicationService.getBaseMapper().selectList(queryWrapper);
		for (CustomerCurrencyExchangeApplication application : list) {
			try {
				LambdaQueryWrapper<CustomerCurrencyExchangeInfo> wrapper = new LambdaQueryWrapper();
				wrapper.eq(CustomerCurrencyExchangeInfo::getApplicationId, application.getApplicationId());
				CustomerCurrencyExchangeInfo info = customerCurrencyExchangeInfoService.getOne(wrapper);
				/**
				 * 执行兑换  先解冻  再扣款  最后存入
				 */
				AmountDTO outAmount = new AmountDTO();
				outAmount.setAmount(info.getActualAmountOut());
				outAmount.setCurrency(CurrencyExcEnum.CurrencyType.getName(info.getCurrencyOut()));
				outAmount.setAccountId(info.getTradeAccount());
				outAmount.setBusinessId(application.getApplicationId());
				outAmount.setThawingType(ThawingType.CURRENCY_EXCHANGE_SUB_FREEZE.getCode());
				outAmount.setType(0);
				outAmount.setCustId(info.getCustId());
				R result = iCustomerInfoClient.thawingAmount(outAmount);
				if (!result.isSuccess()){
					// 解冻失败
					application.setApplicationStatus(5); //失败
					application.setUpdateTime(new Date());
					customerCurrencyExchangeApplicationService.updateById(application);
					info.setProcessingStatus(2); // 解冻失败
					customerCurrencyExchangeInfoService.updateById(info);
					return R.fail();
				}
				AmountDTO inAmount = new AmountDTO();
				inAmount.setAmount(info.getActualAmountIn());
				inAmount.setBusinessId(info.getApplicationId());
				inAmount.setCurrency(CurrencyExcEnum.CurrencyType.getName(info.getCurrencyIn()));
				inAmount.setAccountId(info.getTradeAccount());
				inAmount.setThawingType(ThawingType.CURRENCY_EXCHANGE_DEPOSIT.getCode());
				result = iCustomerInfoClient.goldDeposit(inAmount);
				if (result.isSuccess()) {
					R r = flowClient.completeTask(application.getTaskId(), "兑换成功", new HashMap<>());
					log.info("兑换定时任务执行结果===" + JSONUtil.toCompatibleJson(r));

					try {
						R<CustomerAccountDetailVO> accountDetail = iCustomerInfoClient.selectCustomerDetailByAccountId(inAmount.getAccountId());
						if (accountDetail.isSuccess()) {
							CustomerAccountDetailVO customerAccountDetailVO = accountDetail.getData();
							List<String> params = new ArrayList<>();
							params.add(String.valueOf(inAmount.getAmount()));
							params.add(String.valueOf(customerAccountDetailVO.getCustId()));

							PushUtil.builder()
								.msgGroup("P")
								.custId(customerAccountDetailVO.getCustId())
								.params(params)
								.group(MsgStaticType.DisplayGroup.SERVICE_MSG)
								.templateCode(PushTemplate.CURRENCY_EXCHANGE.getCode())
								.sendWay(InformEnum.SendWayTimeEnum.FORTHWITH.getTypeCode())
								.pushAsync();
						}

					} catch (Exception e) {
						e.printStackTrace();
						log.error("推送入金消息失败",e);
					}

				} else {
					application.setApplicationStatus(5); //失败
					application.setUpdateTime(new Date());
					customerCurrencyExchangeApplicationService.updateById(application);
					info.setProcessingStatus(3); // 解冻失败
					customerCurrencyExchangeInfoService.updateById(info);
				}
			} catch (Exception e) {
				log.info("兑换异常的预约号为=="+application.getApplicationId());
				log.error("自动兑换任务异常",e);
			}
		}
		return R.success();
	}

	@Override
	public R accountDepositJob(Map<String, Object> map) {
		LambdaQueryWrapper<CustomerCurrencyExchangeApplication> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(CustomerCurrencyExchangeApplication::getCurrentNode, "入账");
		List<CustomerCurrencyExchangeApplication> list = customerCurrencyExchangeApplicationService.getBaseMapper().selectList(queryWrapper);
		for (CustomerCurrencyExchangeApplication application : list) {
			try {
				R r = flowClient.completeTask(application.getTaskId(), "入账成功", new HashMap<>());
				log.info("执行货币兑换入账任务applicationId ,{}==== 返回结果，{}",application.getApplicationId(),JSONUtil.toCompatibleJson(r));
				LambdaUpdateWrapper<CustomerCurrencyExchangeInfo> updateWrapper = new LambdaUpdateWrapper<>();
				updateWrapper.set(CustomerCurrencyExchangeInfo::getUpdateTime, new Date());
				updateWrapper.eq(CustomerCurrencyExchangeInfo::getApplicationId, application.getApplicationId());

				if (r.isSuccess()) {
					//todo  兑换货币需要调用入账接口 待定 入账返回值需要填在下面
					updateWrapper.set(CustomerCurrencyExchangeInfo::getStatus, CurrencyExcEnum.AppExchangeStatus.PASS.getIndex());
					updateWrapper.set(CustomerCurrencyExchangeInfo::getProcessingStatus, 4);

					//updateWrapper.set(CustomerCurrencyExchangeInfo::getActualAmountIn, );
					//updateWrapper.set(CustomerCurrencyExchangeInfo::getActualAmountOut, );
					//updateWrapper.set(CustomerCurrencyExchangeInfo::getActualExchangeRate, );
				}else {
					updateWrapper.set(CustomerCurrencyExchangeInfo::getStatus, CurrencyExcEnum.AppExchangeStatus.FAIL.getIndex());
					updateWrapper.set(CustomerCurrencyExchangeInfo::getProcessingStatus, 3);
				}
				customerCurrencyExchangeInfoService.update(updateWrapper);


			} catch (Exception e) {
				log.info("兑换入账异常的预约号为=="+application.getApplicationId());
				log.error("入账任务异常", e);
			}
		}
		return R.success();
	}

	@Override
	public R cancel(String applicationId) {
		CustomerCurrencyExchangeInfo info=	customerCurrencyExchangeInfoService.queryInfoByApplication(applicationId);
		return customerCurrencyExchangeApplicationService.cancel(applicationId);

	}

	@Override
	public List<CustomerCurrencyExchangeInfoVO> queryExchangeList(CustomerCurrencyExchangeInfo customerCurrencyExchangeInfo) {
		List<CustomerCurrencyExchangeInfoVO> list=customerCurrencyExchangeInfoService.selectList(customerCurrencyExchangeInfo);
		return list;
	}
}
