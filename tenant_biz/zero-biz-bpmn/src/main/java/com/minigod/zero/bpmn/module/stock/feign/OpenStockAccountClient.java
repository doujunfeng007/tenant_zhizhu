package com.minigod.zero.bpmn.module.stock.feign;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.google.protobuf.ServiceException;
import com.minigod.zero.biz.common.constant.TenantConstant;
import com.minigod.zero.bpmn.module.account.mapper.AccountOpenInfoMapper;
import com.minigod.zero.bpmn.module.account.mapper.AccountTaxationInfoMapper;
import com.minigod.zero.bpmn.module.account.service.IAccountOpenImageService;
import com.minigod.zero.bpmn.module.account.service.IAccountOpenInfoService;
import com.minigod.zero.bpmn.module.account.vo.AccountOpenImageVO;
import com.minigod.zero.bpmn.module.account.vo.AccountOpenInfoVO;
import com.minigod.zero.bpmn.module.account.vo.AccountTaxationInfoVO;
import com.minigod.zero.bpmn.module.deposit.enums.DepositStatusEnum;
import com.minigod.zero.bpmn.module.feign.ICustomerInfoClient;
import com.minigod.zero.bpmn.module.feign.dto.*;
import com.minigod.zero.bpmn.module.feign.enums.ThawingType;
import com.minigod.zero.bpmn.module.fundTrans.domain.ClientFundTransApplication;
import com.minigod.zero.bpmn.module.fundTrans.domain.ClientFundTransInfo;
import com.minigod.zero.bpmn.module.fundTrans.feign.IFundTransClient;
import com.minigod.zero.bpmn.module.fundTrans.service.ClientFundTransApplicationService;
import com.minigod.zero.bpmn.module.fundTrans.service.ClientFundTransInfoService;
import com.minigod.zero.bpmn.module.stock.entity.CustomerAccountStockApplicationEntity;
import com.minigod.zero.bpmn.module.stock.entity.CustomerAccountStockInfoEntity;
import com.minigod.zero.bpmn.module.stock.enums.StockStatusEnum;
import com.minigod.zero.bpmn.module.stock.service.ICustomerAccountStockApplicationService;
import com.minigod.zero.bpmn.module.stock.service.ICustomerAccountStockInfoService;
import com.minigod.zero.core.tenant.annotation.NonDS;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.api.ResultCode;
import com.minigod.zero.flow.core.common.enums.FlowComment;
import com.minigod.zero.flow.core.feign.IFlowClient;
import com.minigod.zero.trade.feign.ICounterOpenAccountClient;
import com.minigod.zero.trade.vo.sjmb.req.OpenAccountReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@Slf4j
@NonDS
@RestController
@RequiredArgsConstructor
public class OpenStockAccountClient implements IOpenStockAccountClient {

	private final ICustomerAccountStockApplicationService customerAccountStockApplicationService;
	private final ICustomerAccountStockInfoService customerAccountStockInfoService;
	private final IAccountOpenInfoService accountOpenInfoService;
	private final IFlowClient flowClient;
	private final AccountOpenInfoMapper accountOpenInfoMapper;
	private final AccountTaxationInfoMapper openAccountTaxationInfoMapper;
	private final IAccountOpenImageService customerAccountOpenImageService;

	@Override
	public R openStockAccountJob(Map<String, Object> map) {
		LambdaQueryWrapper<CustomerAccountStockApplicationEntity> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(CustomerAccountStockApplicationEntity::getApplicationStatus, StockStatusEnum.ApplicationStatus.OPEN.getCode());

		List<CustomerAccountStockApplicationEntity> list = customerAccountStockApplicationService.getBaseMapper().selectList(queryWrapper);
		for (CustomerAccountStockApplicationEntity application : list) {
			try {
				LambdaQueryWrapper<CustomerAccountStockInfoEntity> wrapper = new LambdaQueryWrapper();
				wrapper.eq(CustomerAccountStockInfoEntity::getApplicationId, application.getApplicationId());
				CustomerAccountStockInfoEntity info = customerAccountStockInfoService.getOne(wrapper);
				// 开户
				CustomerOpenAccountDTO dto = new CustomerOpenAccountDTO();
				AccountOpenInfoVO accountOpenInfoVO = accountOpenInfoMapper.queryByUserId(Long.valueOf(info.getUserId()));
				List<AccountTaxationInfoVO> accountTaxationInfoVOList = openAccountTaxationInfoMapper.queryListByApplicationId(accountOpenInfoVO.getApplicationId());
				// 开户图片
				List<AccountOpenImageVO> accountOpenImageVOs = customerAccountOpenImageService.queryListByApplicationId(accountOpenInfoVO.getApplicationId());
				// 调用账户中心开户
				accountOpenInfoVO.setStockTypes(TenantConstant.STOCK_TYPE);
				Map<String, String> data = accountOpenInfoService.openAccount(accountOpenInfoVO, accountTaxationInfoVOList, accountOpenImageVOs);

				if (data != null) {
					R r = flowClient.completeTask(application.getTaskId(), "增开股票账户成功", new HashMap<>());
					if (!r.isSuccess()) {
						throw new ServiceException("增开股票账户失败:" + r.getMsg());
					}
					LambdaUpdateWrapper<CustomerAccountStockApplicationEntity> applicationLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
					applicationLambdaUpdateWrapper.set(CustomerAccountStockApplicationEntity::getStatus, StockStatusEnum.ApplicationStatus.END.getCode());
					applicationLambdaUpdateWrapper.set(CustomerAccountStockApplicationEntity::getUpdateTime, new Date());
					applicationLambdaUpdateWrapper.eq(CustomerAccountStockApplicationEntity::getId, application.getId());
					customerAccountStockApplicationService.update(applicationLambdaUpdateWrapper);

					LambdaUpdateWrapper<CustomerAccountStockInfoEntity> infoLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
					infoLambdaUpdateWrapper.set(CustomerAccountStockInfoEntity::getStatus, StockStatusEnum.ApplicationStatus.END.getCode());
					infoLambdaUpdateWrapper.set(CustomerAccountStockInfoEntity::getUpdateTime, new Date());
					infoLambdaUpdateWrapper.eq(CustomerAccountStockInfoEntity::getId, info.getId());
					customerAccountStockInfoService.update(infoLambdaUpdateWrapper);
				} else {
					log.error("用户custId={}，phone={}增开股票账户失败！", accountOpenInfoVO.getUserId(), accountOpenInfoVO.getPhoneNumber());
				}
			} catch (Exception e) {
				flowClient.taskComment(application.getTaskId(), FlowComment.EXCEPTION.getType(), "增开股票账户异常:" + e.getMessage());
				log.error("增开股票账户的预约号为==" + application.getApplicationId());
				log.error("增开股票账户任务异常", e);
			}
		}
		return R.success();
	}
}
