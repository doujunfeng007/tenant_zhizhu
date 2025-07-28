package com.minigod.zero.bpmn.module.exchange.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.minigod.zero.bpmn.module.common.entity.FileUploadInfoEntity;
import com.minigod.zero.bpmn.module.common.enums.UploadBusinessType;
import com.minigod.zero.bpmn.module.common.service.IFileUploadInfoService;
import com.minigod.zero.bpmn.module.exchange.entity.CustomerCurrencyExchangeApplication;
import com.minigod.zero.bpmn.module.exchange.entity.CustomerCurrencyExchangeInfo;
import com.minigod.zero.bpmn.module.exchange.enums.CurrencyExcEnum;
import com.minigod.zero.bpmn.module.exchange.mapper.CustomerCurrencyExchangeInfoMapper;
import com.minigod.zero.bpmn.module.exchange.service.ICustomerCurrencyExchangeApplicationService;
import com.minigod.zero.bpmn.module.exchange.service.ICustomerCurrencyExchangeInfoService;
import com.minigod.zero.bpmn.module.exchange.vo.CustomerCurrencyExchangeInfoVO;
import com.minigod.zero.bpmn.module.feign.ICustomerInfoClient;
import com.minigod.zero.bpmn.module.feign.vo.CustomerDetailVO;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.BeanUtil;

/**
 * @author chen
 * @ClassName CustomerCurrencyExchangeInfoServiceImpl.java
 * @Description TODO
 * @createTime 2024年03月15日 18:50:00
 */
@Service
public class CustomerCurrencyExchangeInfoServiceImpl extends BaseServiceImpl<CustomerCurrencyExchangeInfoMapper, CustomerCurrencyExchangeInfo> implements ICustomerCurrencyExchangeInfoService {

	@Autowired
	private IFileUploadInfoService fileUploadInfoService;
	@Autowired
	private ICustomerInfoClient customerInfoClient;

	@Autowired
	@Lazy
	private ICustomerCurrencyExchangeApplicationService currencyExchangeApplicationService;


	@Override
	public CustomerCurrencyExchangeInfoVO queryDetailByApplication(String applicationId) {

		CustomerCurrencyExchangeInfoVO vo = new CustomerCurrencyExchangeInfoVO();
		CustomerCurrencyExchangeInfo info = queryInfoByApplication(applicationId);
		BeanUtil.copy(info, vo);

		CustomerCurrencyExchangeApplication application = currencyExchangeApplicationService.queryDetailByApplication(applicationId);
		vo.setCustomerCurrencyExchangeApplication(application);

		List<FileUploadInfoEntity> files = fileUploadInfoService.selectListByBusinessType(info.getId(), UploadBusinessType.CURRENCY_EXCHANGE_CERTIFICATE.getBusinessType());
		vo.setFileList(files);

		R<CustomerDetailVO> customerDetailR = customerInfoClient.selectCustomerDetail(info.getCustId());
		if (customerDetailR.isSuccess()) {
			CustomerDetailVO customerDetailData = customerDetailR.getData();
			vo.setIdKind(customerDetailData.getIdKind());
			vo.setIdCard(customerDetailData.getIdCard());
			vo.setGivenNameSpell(customerDetailData.getCustomerEnName());
			vo.setPhoneNumber(customerDetailData.getAreaCode() + "-" + customerDetailData.getPhoneNum());
			vo.setOpenAccountTime(customerDetailData.getOpenAccountTime());
		}

		return vo;
	}

	@Override
	public CustomerCurrencyExchangeInfo queryInfoByApplication(String applicationId) {
		LambdaQueryWrapper<CustomerCurrencyExchangeInfo> queryWrapper = new LambdaQueryWrapper();
		queryWrapper.eq(CustomerCurrencyExchangeInfo::getApplicationId, applicationId);
		CustomerCurrencyExchangeInfo info = baseMapper.selectOne(queryWrapper);
		return info;
	}

	@Override
	public List<CustomerCurrencyExchangeInfoVO> selectList(CustomerCurrencyExchangeInfo customerCurrencyExchangeInfo) {
		LambdaQueryWrapper<CustomerCurrencyExchangeInfo> queryWrapper = new LambdaQueryWrapper();
		queryWrapper.eq(CustomerCurrencyExchangeInfo::getCustId, customerCurrencyExchangeInfo.getCustId());
		if (null != customerCurrencyExchangeInfo.getStatus()) {
			queryWrapper.eq(CustomerCurrencyExchangeInfo::getStatus, customerCurrencyExchangeInfo.getStatus());
		}
		if (null != customerCurrencyExchangeInfo.getCurrencyOut()) {
			queryWrapper.eq(CustomerCurrencyExchangeInfo::getCurrencyOut, customerCurrencyExchangeInfo.getCurrencyOut());
		}
		if (null != customerCurrencyExchangeInfo.getCurrencyIn()) {
			queryWrapper.eq(CustomerCurrencyExchangeInfo::getCurrencyIn, customerCurrencyExchangeInfo.getCurrencyIn());
		}
		queryWrapper.orderByDesc(CustomerCurrencyExchangeInfo::getCreateTime);
		List<CustomerCurrencyExchangeInfo> list = baseMapper.selectList(queryWrapper);
		List<CustomerCurrencyExchangeInfoVO> result = BeanUtil.copy(list, CustomerCurrencyExchangeInfoVO.class);
		result.forEach(item -> {
			CustomerCurrencyExchangeApplication application = currencyExchangeApplicationService.queryDetailByApplication(item.getApplicationId());
			// 货币兑换审核状态
			if (item.getStatus() != null) {
				switch (CurrencyExcEnum.AppExchangeStatus.valueOf(item.getStatus())) {
					case SUBMIT:
						item.setAppStatusName(CurrencyExcEnum.AppExchangeStatus.SUBMIT.getValue());
						break;
					case PENDING:
						item.setAppStatusName(CurrencyExcEnum.AppExchangeStatus.PENDING.getValue());
						break;
					case PASS:
						item.setAppStatusName(CurrencyExcEnum.AppExchangeStatus.PASS.getValue());
						break;
					case REJECT:
						item.setAppStatusName(CurrencyExcEnum.AppExchangeStatus.REJECT.getValue());
						break;
					case CANCEL:
						item.setAppStatusName(CurrencyExcEnum.AppExchangeStatus.CANCEL.getValue());
						break;
					case FAIL:
						item.setAppStatusName(CurrencyExcEnum.AppExchangeStatus.FAIL.getValue());
						break;
					case HANG_UP:
						item.setAppStatusName(CurrencyExcEnum.AppExchangeStatus.HANG_UP.getValue());
						break;
				}
			}

			// 货币兑换申请状态名称
			if (application != null && application.getApplicationStatus() != null) {
				switch (CurrencyExcEnum.ExchangeApplicationStatus.valueOf(application.getApplicationStatus())) {
					case EXCHANGE_APPLICATION_STATUS_SUBMIT:
						item.setApplicationStatusName(CurrencyExcEnum.ExchangeApplicationStatus.EXCHANGE_APPLICATION_STATUS_SUBMIT.getValue());
						break;
					case EXCHANGE_APPLICATION_STATUS_FIRST_AUDIT:
						item.setApplicationStatusName(CurrencyExcEnum.ExchangeApplicationStatus.EXCHANGE_APPLICATION_STATUS_FIRST_AUDIT.getValue());
						break;
					case EXCHANGE_APPLICATION_STATUS_SECOND_AUDIT:
						item.setApplicationStatusName(CurrencyExcEnum.ExchangeApplicationStatus.EXCHANGE_APPLICATION_STATUS_SECOND_AUDIT.getValue());
						break;
					case EXCHANGE_APPLICATION_STATUS_EXCHANGEING:
						item.setApplicationStatusName(CurrencyExcEnum.ExchangeApplicationStatus.EXCHANGE_APPLICATION_STATUS_EXCHANGEING.getValue());
						break;
					case EXCHANGE_APPLICATION_STATUS_INPUTING:
						item.setApplicationStatusName(CurrencyExcEnum.ExchangeApplicationStatus.EXCHANGE_APPLICATION_STATUS_INPUTING.getValue());
						break;
					case EXCHANGE_APPLICATION_STATUS_EXCHANGE_FAIL:
						item.setApplicationStatusName(CurrencyExcEnum.ExchangeApplicationStatus.EXCHANGE_APPLICATION_STATUS_EXCHANGE_FAIL.getValue());
						break;
					case EXCHANGE_APPLICATION_STATUS_REFUSE:
						item.setApplicationStatusName(CurrencyExcEnum.ExchangeApplicationStatus.EXCHANGE_APPLICATION_STATUS_REFUSE.getValue());
						break;
					case EXCHANGE_APPLICATION_STATUS_HANG_UP:
						item.setApplicationStatusName(CurrencyExcEnum.ExchangeApplicationStatus.EXCHANGE_APPLICATION_STATUS_HANG_UP.getValue());
						break;
					case EXCHANGE_APPLICATION_STATUS_CANCEL:
						item.setApplicationStatusName(CurrencyExcEnum.ExchangeApplicationStatus.EXCHANGE_APPLICATION_STATUS_CANCEL.getValue());
						break;
					case EXCHANGE_APPLICATION_STATUS_CANCEL_FAIL:
						item.setApplicationStatusName(CurrencyExcEnum.ExchangeApplicationStatus.EXCHANGE_APPLICATION_STATUS_CANCEL_FAIL.getValue());
						break;
					case EXCHANGE_APPLICATION_STATUS_INPUT_SUCCESS:
						item.setApplicationStatusName(CurrencyExcEnum.ExchangeApplicationStatus.EXCHANGE_APPLICATION_STATUS_INPUT_SUCCESS.getValue());
						break;
				}
			}
			// 柜台处理状态名称
			if (item.getProcessingStatus() != null) {
				switch (CurrencyExcEnum.ExchangeProcessStatus.valueOf(item.getProcessingStatus())) {
					case EXCHANGE_PROCESS_STATUS_SUCCESS:
						item.setProcessingStatusName(CurrencyExcEnum.ExchangeProcessStatus.EXCHANGE_PROCESS_STATUS_SUCCESS.getValue());
						break;
					case EXCHANGE_PROCESS_STATUS_UNFREEZE_FAIL:
						item.setProcessingStatusName(CurrencyExcEnum.ExchangeProcessStatus.EXCHANGE_PROCESS_STATUS_UNFREEZE_FAIL.getValue());
						break;
					case EXCHANGE_PROCESS_STATUS_FREEZE_FAIL:
						item.setProcessingStatusName(CurrencyExcEnum.ExchangeProcessStatus.EXCHANGE_PROCESS_STATUS_FREEZE_FAIL.getValue());
						break;
					case EXCHANGE_PROCESS_STATUS_INPUT_FAIL:
						item.setProcessingStatusName(CurrencyExcEnum.ExchangeProcessStatus.EXCHANGE_PROCESS_STATUS_INPUT_FAIL.getValue());
						break;
					case EXCHANGE_PROCESS_STATUS_DEDUCTION_FAIL:
						item.setProcessingStatusName(CurrencyExcEnum.ExchangeProcessStatus.EXCHANGE_PROCESS_STATUS_DEDUCTION_FAIL.getValue());
						break;
				}
			}
		});
		return result;
	}
}
