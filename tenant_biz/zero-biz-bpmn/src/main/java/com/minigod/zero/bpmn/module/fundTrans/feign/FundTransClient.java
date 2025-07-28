package com.minigod.zero.bpmn.module.fundTrans.feign;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.google.protobuf.ServiceException;
import com.minigod.zero.biz.common.utils.JSONUtil;
import com.minigod.zero.bpmn.module.deposit.enums.DepositStatusEnum;
import com.minigod.zero.bpmn.module.exchange.entity.CustomerCurrencyExchangeApplication;
import com.minigod.zero.bpmn.module.exchange.entity.CustomerCurrencyExchangeInfo;
import com.minigod.zero.bpmn.module.exchange.enums.CurrencyExcEnum;
import com.minigod.zero.bpmn.module.feign.ICustomerInfoClient;
import com.minigod.zero.bpmn.module.feign.dto.AmountDTO;
import com.minigod.zero.bpmn.module.feign.enums.ThawingType;
import com.minigod.zero.bpmn.module.feign.vo.CustomerAccountDetailVO;
import com.minigod.zero.bpmn.module.fundTrans.domain.ClientFundTransApplication;
import com.minigod.zero.bpmn.module.fundTrans.domain.ClientFundTransInfo;
import com.minigod.zero.bpmn.module.fundTrans.service.ClientFundTransApplicationService;
import com.minigod.zero.bpmn.module.fundTrans.service.ClientFundTransInfoService;
import com.minigod.zero.core.tenant.annotation.NonDS;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.flow.core.common.enums.FlowComment;
import com.minigod.zero.flow.core.feign.IFlowClient;
import com.minigod.zero.platform.enums.InformEnum;
import com.minigod.zero.platform.enums.MsgStaticType;
import com.minigod.zero.platform.enums.PushTemplate;
import com.minigod.zero.platform.utils.PushUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@Slf4j
@NonDS
@RestController
@RequiredArgsConstructor
public class FundTransClient implements IFundTransClient {

	private final ClientFundTransApplicationService clientFundTransApplicationService;
	private final ClientFundTransInfoService clientFundTransInfoService;
	private final ICustomerInfoClient iCustomerInfoClient;
	private final IFlowClient flowClient;

	@Override
	public R fundTransWithdrawJob(Map<String, Object> map) {
		LambdaQueryWrapper<ClientFundTransApplication> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(ClientFundTransApplication::getApplicationStatus, DepositStatusEnum.ApplicationFundTransStatus.WITHDRAW.getCode());

		List<ClientFundTransApplication> list = clientFundTransApplicationService.getBaseMapper().selectList(queryWrapper);
		for (ClientFundTransApplication application : list) {
			try {
				LambdaQueryWrapper<ClientFundTransInfo> wrapper = new LambdaQueryWrapper();
				wrapper.eq(ClientFundTransInfo::getApplicationId, application.getApplicationId());
				ClientFundTransInfo info = clientFundTransInfoService.getOne(wrapper);
				/**
				 * 执行兑换  先解冻  再扣款
				 */
				AmountDTO outAmount = new AmountDTO();
				outAmount.setAmount(info.getWithdrawAmount());
				outAmount.setCurrency(info.getCurrency());
				outAmount.setAccountId(info.getClientId());
				outAmount.setBusinessType(info.getWithdrawBusinessType());
				outAmount.setBusinessId(application.getApplicationId());
				outAmount.setThawingType(ThawingType.CURRENCY_EXCHANGE_SUB_FREEZE.getCode());
				outAmount.setType(0);
				outAmount.setCustId(info.getUserId());
				log.info("调拨划拨参数"+ JSON.toJSONString(outAmount));
				R result = iCustomerInfoClient.thawingAmount(outAmount);
				if (result.isSuccess()){
					R r = flowClient.completeTask(application.getTaskId(), "划拨成功", new HashMap<>());
					if(!r.isSuccess()){
						throw new ServiceException("划拨失败:"+r.getMsg());
					}
					LambdaUpdateWrapper<ClientFundTransApplication> applicationLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
					applicationLambdaUpdateWrapper.set(ClientFundTransApplication::getStatus,DepositStatusEnum.FundTransStatus.DEPOIST.getCode());
					applicationLambdaUpdateWrapper.set(ClientFundTransApplication::getUpdateTime,new Date());
					applicationLambdaUpdateWrapper.eq(ClientFundTransApplication::getId,application.getId());
					clientFundTransApplicationService.update(applicationLambdaUpdateWrapper);

					LambdaUpdateWrapper<ClientFundTransInfo> infoLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
					infoLambdaUpdateWrapper.set(ClientFundTransInfo::getStatus,DepositStatusEnum.FundTransStatus.DEPOIST.getCode());
					infoLambdaUpdateWrapper.set(ClientFundTransInfo::getUpdateTime,new Date());
					infoLambdaUpdateWrapper.eq(ClientFundTransInfo::getId,info.getId());
					clientFundTransInfoService.update(infoLambdaUpdateWrapper);
				}else{
					throw new ServiceException("划拨失败:"+result.getMsg());
				}

			} catch (Exception e) {
				flowClient.taskComment(application.getTaskId(), FlowComment.EXCEPTION.getType() ,"划拨异常:"+ e.getMessage());
				log.error("调拨划拨异常的预约号为=="+application.getApplicationId());
				log.error("调拨划拨任务异常",e);
			}
		}
		return R.success();
	}

	@Override
	public R fundTransDepositJob(Map<String, Object> map) {
		LambdaQueryWrapper<ClientFundTransApplication> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(ClientFundTransApplication::getApplicationStatus, DepositStatusEnum.ApplicationFundTransStatus.DEPOSIT.getCode());

		List<ClientFundTransApplication> list = clientFundTransApplicationService.getBaseMapper().selectList(queryWrapper);
		for (ClientFundTransApplication application : list) {
			try {
				LambdaQueryWrapper<ClientFundTransInfo> wrapper = new LambdaQueryWrapper();
				wrapper.eq(ClientFundTransInfo::getApplicationId, application.getApplicationId());
				ClientFundTransInfo info = clientFundTransInfoService.getOne(wrapper);
				/**
				 * 入账
				 */
				AmountDTO inAmount = new AmountDTO();
				inAmount.setAmount(info.getDepositAmount());
				inAmount.setCurrency(info.getCurrency());
				inAmount.setAccountId(info.getClientId());
				inAmount.setBusinessType(info.getDepositBusinessType());
				inAmount.setBusinessId(application.getApplicationId());
				inAmount.setThawingType(ThawingType.CURRENCY_EXCHANGE_DEPOSIT.getCode());
				log.info("调拨入账参数"+ JSON.toJSONString(inAmount));
				R result = iCustomerInfoClient.goldDeposit(inAmount);
				if (result.isSuccess()){
					R r = flowClient.completeTask(application.getTaskId(), "入账成功", new HashMap<>());
					if(!r.isSuccess()){
						throw new ServiceException("入账失败:"+r.getMsg());
					}

					LambdaUpdateWrapper<ClientFundTransApplication> applicationLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
					applicationLambdaUpdateWrapper.set(ClientFundTransApplication::getStatus,DepositStatusEnum.FundTransStatus.FINISH.getCode());
					applicationLambdaUpdateWrapper.set(ClientFundTransApplication::getUpdateTime,new Date());
					applicationLambdaUpdateWrapper.eq(ClientFundTransApplication::getId,application.getId());
					clientFundTransApplicationService.update(applicationLambdaUpdateWrapper);

					LambdaUpdateWrapper<ClientFundTransInfo> infoLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
					infoLambdaUpdateWrapper.set(ClientFundTransInfo::getStatus,DepositStatusEnum.FundTransStatus.FINISH.getCode());
					infoLambdaUpdateWrapper.set(ClientFundTransInfo::getUpdateTime,new Date());
					infoLambdaUpdateWrapper.eq(ClientFundTransInfo::getId,info.getId());
					clientFundTransInfoService.update(infoLambdaUpdateWrapper);
				}else{
					throw new ServiceException("入账失败:"+result.getMsg());
				}

			} catch (Exception e) {
				flowClient.taskComment(application.getTaskId(), FlowComment.EXCEPTION.getType() ,"入账异常:"+ e.getMessage());
				log.error("调拨入账异常的预约号为=="+application.getApplicationId());
				log.error("调拨入账任务异常",e);
			}
		}
		return R.success();
	}
}
