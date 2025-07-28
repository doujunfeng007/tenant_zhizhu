package com.minigod.zero.bpmn.module.exchange.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.bpmn.module.constant.ErrorMessageConstant;
import com.minigod.zero.bpmn.module.exchange.entity.CustomerCurrencyExchangeApplication;
import com.minigod.zero.bpmn.module.exchange.entity.CustomerCurrencyExchangeInfo;
import com.minigod.zero.bpmn.module.exchange.enums.CurrencyExcEnum;
import com.minigod.zero.bpmn.module.exchange.mapper.CustomerCurrencyExchangeApplicationMapper;
import com.minigod.zero.bpmn.module.exchange.service.ICustomerCurrencyExchangeApplicationService;
import com.minigod.zero.bpmn.module.exchange.service.ICustomerCurrencyExchangeInfoService;
import com.minigod.zero.bpmn.module.exchange.vo.CurrencyExchangeApplicationExcel;
import com.minigod.zero.bpmn.module.exchange.vo.CurrencyExchangeApplicationVO;
import com.minigod.zero.bpmn.module.exchange.vo.req.CurrencyExchangeQuery;
import com.minigod.zero.bpmn.module.feign.ICustomerInfoClient;
import com.minigod.zero.bpmn.module.feign.dto.AmountDTO;
import com.minigod.zero.bpmn.module.feign.enums.ThawingType;
import com.minigod.zero.core.excel.util.ExcelUtil;
import com.minigod.zero.core.i18n.utils.I18nUtil;
import com.minigod.zero.core.log.exception.ServiceException;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.core.mp.support.Condition;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.core.tool.utils.DateUtil;
import com.minigod.zero.core.tool.utils.ObjectUtil;
import com.minigod.zero.flow.core.common.enums.FlowComment;
import com.minigod.zero.flow.core.feign.IFlowClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

import static com.minigod.zero.bpmn.module.exchange.enums.CurrencyExcEnum.ExchangeProcessStatus.EXCHANGE_PROCESS_STATUS_SUCCESS;

/**
 * @author chen
 * @ClassName CustomerCurrencyExchangeApplicationServiceImpl.java
 * @Description TODO
 * @createTime 2024年03月15日 18:50:00
 */
@Service
public class CustomerCurrencyExchangeApplicationServiceImpl extends BaseServiceImpl<CustomerCurrencyExchangeApplicationMapper, CustomerCurrencyExchangeApplication> implements ICustomerCurrencyExchangeApplicationService {

	@Resource
	private IFlowClient flowClient;

	@Resource
	private ICustomerInfoClient customerInfoClient;

	@Resource
	private ICustomerCurrencyExchangeInfoService currencyExchangeInfoService;


	@Override
	public IPage<CurrencyExchangeApplicationVO> queryPageList(CurrencyExchangeQuery query, Query pageQuery) {
		IPage<CurrencyExchangeApplicationVO> result = baseMapper.queryPageList(Condition.getPage(pageQuery), query);
		result.getRecords().forEach(item -> {
			//手机号码拼接
			item.setPhoneAreaNumber(item.getPhoneArea() + "-" + item.getPhoneNumber());
			// 兑换方式名称
			if (item.getExchangeType() != null) {
				switch (CurrencyExcEnum.ExchangeMode.valueOf(item.getExchangeType())) {
					case EXCHANGE_MODE_UNKNOWN:
						item.setExchangeTypeName(CurrencyExcEnum.ExchangeMode.EXCHANGE_MODE_UNKNOWN.getValue());
						break;
					case EXCHANGE_MODE_ONLINE:
						item.setExchangeTypeName(CurrencyExcEnum.ExchangeMode.EXCHANGE_MODE_ONLINE.getValue());
						break;
					case EXCHANGE_MODE_MANUAL:
						item.setExchangeTypeName(CurrencyExcEnum.ExchangeMode.EXCHANGE_MODE_MANUAL.getValue());
						break;
				}
			}
			// 兑换方向名称
			if (item.getExchangeDirection() != null) {
				switch (CurrencyExcEnum.ExchangeType.valueOf(item.getExchangeDirection())) {
					case EXCHANGE_TYPE_HKD_TO_USD:
						item.setExchangeDirectionName(CurrencyExcEnum.ExchangeType.EXCHANGE_TYPE_HKD_TO_USD.getDescCn());
						break;
					case EXCHANGE_TYPE_USD_TO_HKD:
						item.setExchangeDirectionName(CurrencyExcEnum.ExchangeType.EXCHANGE_TYPE_USD_TO_HKD.getDescCn());
						break;
					case EXCHANGE_TYPE_CNY_TO_USD:
						item.setExchangeDirectionName(CurrencyExcEnum.ExchangeType.EXCHANGE_TYPE_CNY_TO_USD.getDescCn());
						break;
					case EXCHANGE_TYPE_USD_TO_CNY:
						item.setExchangeDirectionName(CurrencyExcEnum.ExchangeType.EXCHANGE_TYPE_USD_TO_CNY.getDescCn());
						break;
					case EXCHANGE_TYPE_HKD_TO_CNY:
						item.setExchangeDirectionName(CurrencyExcEnum.ExchangeType.EXCHANGE_TYPE_HKD_TO_CNY.getDescCn());
						break;
					case EXCHANGE_TYPE_CNY_TO_HKD:
						item.setExchangeDirectionName(CurrencyExcEnum.ExchangeType.EXCHANGE_TYPE_CNY_TO_HKD.getDescCn());
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

			// 货币兑换审核状态
			if (item.getAppStatus() != null) {
				switch (CurrencyExcEnum.AppExchangeStatus.valueOf(item.getAppStatus())) {
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
			if (item.getApplicationStatus() != null) {
				switch (CurrencyExcEnum.ExchangeApplicationStatus.valueOf(item.getApplicationStatus())) {
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
		});
		return result;
	}

	@Override
	public void exportCheckList(CurrencyExchangeQuery query, Query pageQuery, HttpServletResponse response) {
		IPage<CurrencyExchangeApplicationVO> pageList = baseMapper.queryPageList(Condition.getPage(pageQuery), query);
		List<CurrencyExchangeApplicationVO> list = pageList.getRecords();
		List<CurrencyExchangeApplicationExcel> result = new ArrayList<>();
		list.forEach(item -> {
			//手机号码拼接
			item.setPhoneAreaNumber(item.getPhoneArea() + " " + item.getPhoneNumber());
			// 兑换方式名称
			if (item.getExchangeType() != null) {
				switch (CurrencyExcEnum.ExchangeMode.valueOf(item.getExchangeType())) {
					case EXCHANGE_MODE_UNKNOWN:
						item.setExchangeTypeName(CurrencyExcEnum.ExchangeMode.EXCHANGE_MODE_UNKNOWN.getValue());
						break;
					case EXCHANGE_MODE_ONLINE:
						item.setExchangeTypeName(CurrencyExcEnum.ExchangeMode.EXCHANGE_MODE_ONLINE.getValue());
						break;
					case EXCHANGE_MODE_MANUAL:
						item.setExchangeTypeName(CurrencyExcEnum.ExchangeMode.EXCHANGE_MODE_MANUAL.getValue());
						break;
				}
			}

			// 兑换方向名称
			if (item.getExchangeDirection() != null) {
				switch (CurrencyExcEnum.ExchangeType.valueOf(item.getExchangeDirection())) {
					case EXCHANGE_TYPE_HKD_TO_USD:
						item.setExchangeDirectionName(CurrencyExcEnum.ExchangeType.EXCHANGE_TYPE_HKD_TO_USD.getDescCn());
						break;
					case EXCHANGE_TYPE_USD_TO_HKD:
						item.setExchangeDirectionName(CurrencyExcEnum.ExchangeType.EXCHANGE_TYPE_USD_TO_HKD.getDescCn());
						break;
					case EXCHANGE_TYPE_CNY_TO_USD:
						item.setExchangeDirectionName(CurrencyExcEnum.ExchangeType.EXCHANGE_TYPE_CNY_TO_USD.getDescCn());
						break;
					case EXCHANGE_TYPE_USD_TO_CNY:
						item.setExchangeDirectionName(CurrencyExcEnum.ExchangeType.EXCHANGE_TYPE_USD_TO_CNY.getDescCn());
						break;
					case EXCHANGE_TYPE_HKD_TO_CNY:
						item.setExchangeDirectionName(CurrencyExcEnum.ExchangeType.EXCHANGE_TYPE_HKD_TO_CNY.getDescCn());
						break;
					case EXCHANGE_TYPE_CNY_TO_HKD:
						item.setExchangeDirectionName(CurrencyExcEnum.ExchangeType.EXCHANGE_TYPE_CNY_TO_HKD.getDescCn());
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
			// 货币兑换审核状态
			if (item.getAppStatus() != null) {
				switch (CurrencyExcEnum.AppExchangeStatus.valueOf(item.getAppStatus())) {
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
			if (item.getApplicationStatus() != null) {
				switch (CurrencyExcEnum.ExchangeApplicationStatus.valueOf(item.getApplicationStatus())) {
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
			CurrencyExchangeApplicationExcel excel = BeanUtil.copy(item, CurrencyExchangeApplicationExcel.class);
			result.add(excel);
		});
		ExcelUtil.export(response, "兑换审核记录" + DateUtil.time(), "兑换审核记录", result, CurrencyExchangeApplicationExcel.class);
	}

	@Override
	public CustomerCurrencyExchangeApplication queryDetailByApplication(String applicationId) {
		LambdaQueryWrapper<CustomerCurrencyExchangeApplication> queryWrapper = new LambdaQueryWrapper();
		queryWrapper.eq(CustomerCurrencyExchangeApplication::getApplicationId, applicationId);
		CustomerCurrencyExchangeApplication info = baseMapper.selectOne(queryWrapper);
		return info;
	}

	@Override
	public String batchClaim(Integer applicationStatus, List<String> applicationIds) {
		StringBuilder stringBuilder = new StringBuilder("您申领的流程号:" + applicationIds + "<br/>");
		List<String> failClaim = new ArrayList();
		List<String> notFindClaim = new ArrayList();
		List<String> suceessClaim = new ArrayList<>();
		LambdaQueryWrapper<CustomerCurrencyExchangeApplication> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.in(CustomerCurrencyExchangeApplication::getApplicationId, applicationIds);
		queryWrapper.eq(ObjectUtil.isNotEmpty(applicationStatus), CustomerCurrencyExchangeApplication::getApplicationStatus, applicationStatus);
		List<CustomerCurrencyExchangeApplication> applications = baseMapper.selectList(queryWrapper);
		if (applications.size() == 0) {
			throw new ServiceException("所选任务已全被申领");
		}

		for (CustomerCurrencyExchangeApplication application : applications) {
			if (application.getAssignDrafter() == null) {
				try {
					R r = flowClient.taskClaim(application.getTaskId());
					if (r.isSuccess()) {
						baseMapper.addAssignDrafter(application.getApplicationId(), AuthUtil.getUserId().toString());
						suceessClaim.add(application.getApplicationId());
						log.debug("成功认领:" + application.getApplicationId() + "<br/>" + "任务ID:" + application.getTaskId());
					} else {
						log.error(String.format("流程:%s 认领任务失败,原因: %s", application.getApplicationId(), r.getMsg()));
						failClaim.add(application.getApplicationId());
					}
				} catch (Exception e) {
					log.error(String.format("流程:%s 认领任务异常,原因: %s", application.getApplicationId(), e.getMessage()));
					failClaim.add(application.getApplicationId());
				}
			} else if (!application.getApplicationStatus().equals(applicationStatus)) {
				log.error(String.format("流程:%s 认领任务失败,原因: 任务状态和当前状态不匹配,任务状态：%s, 当前申领状态：%s", application.getApplicationId(), application.getApplicationStatus(), applicationStatus));
				notFindClaim.add(application.getApplicationId());
			} else {
				log.error(String.format("流程:%s 认领任务失败,原因: 不满足认领条件!", application.getApplicationId()));
				failClaim.add(application.getApplicationId());
			}
		}
		if (suceessClaim != null && suceessClaim.size() > 0) {
			stringBuilder.append("成功认领:" + suceessClaim + "<br/>");
		}
		if (failClaim != null && failClaim.size() > 0) {
			stringBuilder.append("失败认领:" + failClaim + "<br/>");
		}
		if (notFindClaim != null && notFindClaim.size() > 0) {
			stringBuilder.append("任务不存在:" + notFindClaim + "<br/>");
		}
		return stringBuilder.toString();
	}

	@Override
	public void batchUnClaim(List<String> applicationIds) {
		LambdaQueryWrapper<CustomerCurrencyExchangeApplication> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.in(CustomerCurrencyExchangeApplication::getApplicationId, applicationIds);
		List<CustomerCurrencyExchangeApplication> applications = baseMapper.selectList(queryWrapper);
		for (CustomerCurrencyExchangeApplication application : applications) {
			baseMapper.clearAssignDrafter(application.getApplicationId());
			R r = flowClient.taskUnclaim(application.getTaskId());
			if (!r.isSuccess()) {
				throw new ServiceException(r.getMsg());
			}
		}

	}

	@Override
	public void rejectNode(String applicationId, String taskId, String reason, Integer status) {
		CustomerCurrencyExchangeApplication application = baseMapper.queryByApplicationId(applicationId);
		application.setApprovalOpinion(reason);
		application.setApplicationStatus(CurrencyExcEnum.ExchangeApplicationStatus.EXCHANGE_APPLICATION_STATUS_REFUSE.getIndex());
		baseMapper.updateById(application);
		R r = flowClient.taskFinish(application.getInstanceId(), null, reason);
		if (!r.isSuccess()) {
			throw new ServiceException("流程处理失败:" + r.getMsg());
		}

	}

	@Override
	public void passNode(String applicationId, String taskId, String reason) {
		Map<String, Object> map = new HashMap<>();
		map.put("status", 1);
		R r = flowClient.completeTask(taskId, reason, map);
		if (!r.isSuccess()) {
			throw new ServiceException(r.getMsg());
		}
		CustomerCurrencyExchangeApplication application = baseMapper.queryByApplicationId(applicationId);
		CustomerCurrencyExchangeApplication update = new CustomerCurrencyExchangeApplication();
		update.setId(application.getId());
		update.setLastApprovalUser(AuthUtil.getUser().getNickName());
		update.setLastApprovalTime(new Date());
		update.setUpdateTime(new Date());
		update.setApprovalOpinion(reason);
		baseMapper.updateById(update);

	}

	@Override
	public void hangUp(String applicationId) {
		CustomerCurrencyExchangeInfo info = currencyExchangeInfoService.queryInfoByApplication(applicationId);
		info.setStatus(CurrencyExcEnum.AppExchangeStatus.HANG_UP.getIndex());
		info.setUpdateTime(new Date());
		info.setUpdateUser(AuthUtil.getUserId());
		currencyExchangeInfoService.updateById(info);

		CustomerCurrencyExchangeApplication application = baseMapper.queryByApplicationId(applicationId);
		application.setApplicationStatus(CurrencyExcEnum.ExchangeApplicationStatus.EXCHANGE_APPLICATION_STATUS_HANG_UP.getIndex());//挂起
		application.setUpdateTime(new Date());
		baseMapper.updateById(application);

	}

	@Override
	public void unHangUp(String applicationId) {
		CustomerCurrencyExchangeInfo info = currencyExchangeInfoService.queryInfoByApplication(applicationId);
		info.setStatus(CurrencyExcEnum.AppExchangeStatus.PENDING.getIndex());
		info.setUpdateTime(new Date());
		info.setUpdateUser(AuthUtil.getUserId());
		currencyExchangeInfoService.updateById(info);

		CustomerCurrencyExchangeApplication application = baseMapper.queryByApplicationId(applicationId);

		if (application.getCurrentNode().equals("初审")) {
			application.setApplicationStatus(CurrencyExcEnum.ExchangeApplicationStatus.EXCHANGE_APPLICATION_STATUS_FIRST_AUDIT.getIndex());
		}
		if (application.getCurrentNode().equals("复审")) {
			application.setApplicationStatus(CurrencyExcEnum.ExchangeApplicationStatus.EXCHANGE_APPLICATION_STATUS_SECOND_AUDIT.getIndex());
		}
		baseMapper.updateById(application);
	}

	@Override
	public void doProcessingComplete(String applicationId, String instanceId) {
		R r = flowClient.taskFinish(instanceId, FlowComment.NORMAL.getType(), "完成");
		if (r.isSuccess()) {
			CustomerCurrencyExchangeInfo info = currencyExchangeInfoService.queryInfoByApplication(applicationId);
			info.setStatus(CurrencyExcEnum.AppExchangeStatus.PASS.getIndex());
			info.setProcessingStatus(EXCHANGE_PROCESS_STATUS_SUCCESS.getIndex());
			info.setUpdateTime(new Date());
			info.setUpdateUser(AuthUtil.getUserId());
			currencyExchangeInfoService.updateById(info);

			CustomerCurrencyExchangeApplication application = baseMapper.queryByApplicationId(applicationId);
			application.setApplicationStatus(CurrencyExcEnum.ExchangeApplicationStatus.EXCHANGE_APPLICATION_STATUS_INPUT_SUCCESS.getIndex());
			application.setUpdateTime(new Date());
			application.setUpdateUser(AuthUtil.getUserId());
			baseMapper.updateById(application);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public R cancel(String applicationId) {
		CustomerCurrencyExchangeApplication application = baseMapper.queryByApplicationId(applicationId);
		R r = flowClient.taskFinish(application.getInstanceId(), FlowComment.NORMAL.getType(), "完成");
		if (r.isSuccess()) {
			application.setApplicationStatus(CurrencyExcEnum.ExchangeApplicationStatus.EXCHANGE_APPLICATION_STATUS_CANCEL.getIndex());
			application.setUpdateTime(new Date());
			application.setUpdateUser(AuthUtil.getUserId());
			baseMapper.updateById(application);

			CustomerCurrencyExchangeInfo info = currencyExchangeInfoService.queryInfoByApplication(applicationId);
			info.setStatus(CurrencyExcEnum.AppExchangeStatus.CANCEL.getIndex());
			info.setUpdateTime(new Date());
			info.setUpdateUser(AuthUtil.getUserId());
			currencyExchangeInfoService.updateById(info);

			AmountDTO outAmount = new AmountDTO();
			outAmount.setAmount(info.getAmountOut());
			outAmount.setBusinessId(info.getApplicationId());
			outAmount.setCurrency(CurrencyExcEnum.CurrencyType.getName(info.getCurrencyOut()));
			outAmount.setAccountId(info.getTradeAccount());
			outAmount.setThawingType(ThawingType.FREEZE_TO_AVAILABLE.getCode());
			outAmount.setType(1);
			R result = customerInfoClient.thawingAmount(outAmount);
			if (!result.isSuccess()) {
				log.error("调用账户中心解冻金额失败，错误信息：" + result.getMsg());
				throw new ServiceException(I18nUtil.getMessage(ErrorMessageConstant.ERROR_UNFREEZE_FUNDS_FAILED_NOTICE));

			}
		}
		return R.success();
	}

	@Override
	public void cancelFail(String applicationId) {
		CustomerCurrencyExchangeApplication application = baseMapper.queryByApplicationId(applicationId);
		application.setApplicationStatus(CurrencyExcEnum.ExchangeApplicationStatus.EXCHANGE_APPLICATION_STATUS_CANCEL_FAIL.getIndex());
		application.setUpdateTime(new Date());
		application.setUpdateUser(AuthUtil.getUserId());
		baseMapper.updateById(application);
	}

	@Override
	public void rejectPreNode(String applicationId, String taskId, String reason) {
		R r = flowClient.taskReject(taskId, reason);
		if (!r.isSuccess()) {
			throw new ServiceException(r.getMsg());
		}
		CustomerCurrencyExchangeApplication application = baseMapper.queryByApplicationId(applicationId);
		application.setApprovalOpinion(reason);
		baseMapper.updateById(application);
	}
}
