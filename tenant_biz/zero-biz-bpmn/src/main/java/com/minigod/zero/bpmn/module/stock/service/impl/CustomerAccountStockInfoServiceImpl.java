package com.minigod.zero.bpmn.module.stock.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minigod.zero.bpmn.module.deposit.enums.DepositStatusEnum;
import com.minigod.zero.bpmn.module.feign.dto.AmountDTO;
import com.minigod.zero.bpmn.module.feign.enums.ThawingType;
import com.minigod.zero.bpmn.module.feign.vo.CustomerAccountVO;
import com.minigod.zero.bpmn.module.fundTrans.domain.ClientFundTransApplication;
import com.minigod.zero.bpmn.module.fundTrans.domain.ClientFundTransInfo;
import com.minigod.zero.bpmn.module.fundTrans.mapper.ClientFundTransApplicationMapper;
import com.minigod.zero.bpmn.module.stock.domain.bo.CustomerAccountStockBO;
import com.minigod.zero.bpmn.module.stock.entity.CustomerAccountStockApplicationEntity;
import com.minigod.zero.bpmn.module.stock.entity.CustomerAccountStockInfoEntity;
import com.minigod.zero.bpmn.module.stock.enums.StockStatusEnum;
import com.minigod.zero.bpmn.module.stock.mapper.CustomerAccountStockApplicationMapper;
import com.minigod.zero.bpmn.module.stock.mapper.CustomerAccountStockInfoMapper;
import com.minigod.zero.bpmn.module.stock.service.ICustomerAccountStockInfoService;
import com.minigod.zero.bpmn.utils.ApplicationIdUtil;
import com.minigod.zero.core.log.exception.ServiceException;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.ObjectUtil;
import com.minigod.zero.flow.core.common.constant.TaskConstants;
import com.minigod.zero.flow.core.constant.ProcessConstant;
import com.minigod.zero.flow.core.feign.IFlowClient;
import com.minigod.zero.flow.core.utils.FlowUtil;
import com.minigod.zero.flow.core.utils.TaskUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 股票增开信息 服务实现类
 * @author Administrator
 */
@Service
public class CustomerAccountStockInfoServiceImpl extends ServiceImpl<CustomerAccountStockInfoMapper, CustomerAccountStockInfoEntity>
	implements ICustomerAccountStockInfoService {

	@Autowired
	private IFlowClient flowClient;
	@Autowired
	private CustomerAccountStockApplicationMapper applicationMapper;

	private final ExecutorService executorService = new ThreadPoolExecutor(1, 1,
		0L, TimeUnit.MILLISECONDS,
		new LinkedBlockingQueue<Runnable>());

	@Override
	public void submitInfo(CustomerAccountStockBO bo) {
		String tenantId = StringUtils.isNotBlank(bo.getTenantId()) ? bo.getTenantId() : AuthUtil.getTenantId();
		Long userId = ObjectUtil.isNotEmpty(bo.getUserId()) ? bo.getUserId() : AuthUtil.getTenantCustId();
		CustomerAccountStockInfoEntity info = new CustomerAccountStockInfoEntity();
		BeanUtils.copyProperties(bo, info);

		String applicationId = ApplicationIdUtil.generateFundTransId(AuthUtil.getTenantId());

		info.setApplicationId(applicationId);
		info.setOpenAccountType(StockStatusEnum.OpenAccountType.OFFLINE.getCode());
		info.setStatus(StockStatusEnum.OpenAccountModifyApproveStatus.APPROVE_PENDING.getCode());
		baseMapper.insert(info);

		CustomerAccountStockApplicationEntity application = new CustomerAccountStockApplicationEntity();
		application.setApplicationId(applicationId);
		application.setCreateDept(AuthUtil.getDeptId());
		application.setCreateUser(AuthUtil.getUserId());
		application.setCreateTime(new Date());
		application.setTenantId(AuthUtil.getTenantId());
		application.setStatus(StockStatusEnum.OpenAccountModifyApproveStatus.APPROVE_PENDING.getCode());
		application.setApplicationStatus(StockStatusEnum.ApplicationStatus.COMMIT.getCode());
		application.setApplicationTitle(String.format("[%s]增开股票账户", bo.getUserId()));
		applicationMapper.insert(application);

		executorService.execute(() -> {
			Map<String, Object> variables = new HashMap<>();
			variables.put(TaskConstants.TENANT_ID, tenantId);
			variables.put(TaskConstants.APPLICATION_ID, applicationId);
			variables.put(TaskConstants.APPLICATION_TABLE, FlowUtil.getBusinessTable(ProcessConstant.OPEN_STOCK_ACCOUNT_KEY));
			variables.put(TaskConstants.APPLICATION_DICT_KEY, FlowUtil.getBusinessDictKey(ProcessConstant.OPEN_STOCK_ACCOUNT_KEY));
			variables.put(TaskConstants.PROCESS_INITIATOR, TaskUtil.getTaskUser());
			R r = flowClient.startProcessInstanceByKey(ProcessConstant.OPEN_STOCK_ACCOUNT_KEY, variables);
			if (!r.isSuccess()) {
				log.error("增开股票账户审批申请失败,原因：" + r.getMsg());
				throw new ServiceException(r.getMsg());
			}
		});
	}
}
