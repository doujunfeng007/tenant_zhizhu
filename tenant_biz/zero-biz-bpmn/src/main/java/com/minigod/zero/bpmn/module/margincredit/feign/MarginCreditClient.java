package com.minigod.zero.bpmn.module.margincredit.feign;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.minigod.zero.biz.common.utils.JSONUtil;
import com.minigod.zero.bpmn.module.margincredit.mapper.IncreaseCreditLimitApplicationMapper;
import com.minigod.zero.bpmn.module.margincredit.service.IIncreaseCreditLimitApplicationService;
import com.minigod.zero.bpmn.module.margincredit.service.IIncreaseCreditLimitService;
import com.minigod.zero.bpmn.utils.ApplicationIdUtil;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tenant.annotation.NonDS;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.flow.core.common.constant.TaskConstants;
import com.minigod.zero.flow.core.constant.ProcessConstant;
import com.minigod.zero.flow.core.feign.IFlowClient;
import com.minigod.zero.flow.core.utils.FlowUtil;
import com.minigod.zero.flow.core.utils.TaskUtil;
import com.minigod.zero.bpmn.module.margincredit.entity.IncreaseCreditLimitApplicationEntity;
import com.minigod.zero.bpmn.module.margincredit.entity.IncreaseCreditLimitEntity;
import com.minigod.zero.bpmn.module.margincredit.enums.MarginCreditEnum;
import com.minigod.zero.bpmn.module.margincredit.vo.CustMarginCreditVO;
import com.minigod.zero.bpmn.module.margincredit.vo.IncreaseCreditLimitVO;
import com.minigod.zero.bpmn.module.margincredit.vo.MarginCreditAdjustApplyDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.map.LinkedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author chen
 * @ClassName MarginCreditClient.java
 * @Description TODO
 * @createTime 2024年03月09日 11:27:00
 */
@Slf4j
@NonDS
@RestController
@RequiredArgsConstructor
public class MarginCreditClient implements IMarginCreditClient {

	@Resource
	private IIncreaseCreditLimitService increaseCreditLimitService;

	@Resource
	private IIncreaseCreditLimitApplicationService increaseCreditLimitApplicationService;

	@Autowired
	private IncreaseCreditLimitApplicationMapper increaseCreditLimitApplicationMapper;

	@Autowired
	IFlowClient flowClient;


	@Override
	public List<CustMarginCreditVO> getByFundAccount(String tradeAccount,String fundAccount) {
		List<CustMarginCreditVO> result =new ArrayList<>();
		CustMarginCreditVO hkdMarginCreditVO =new CustMarginCreditVO();
		hkdMarginCreditVO.setCurrency("HKD");
		hkdMarginCreditVO.setCreditValue(new BigDecimal("0.00"));//当前信用额度，从柜台查询
		hkdMarginCreditVO.setMaxExposure(new BigDecimal("100000.00"));
		hkdMarginCreditVO.setApplyBalance(new BigDecimal("500.00"));

		CustMarginCreditVO usdMarginCreditVO =new CustMarginCreditVO();
		usdMarginCreditVO.setCurrency("USD");
		usdMarginCreditVO.setCreditValue(new BigDecimal("0.00"));//当前信用额度，从柜台查询
		usdMarginCreditVO.setMaxExposure(new BigDecimal("100000.00"));
		usdMarginCreditVO.setApplyBalance(new BigDecimal("500.00"));

		CustMarginCreditVO cnyMarginCreditVO =new CustMarginCreditVO();
		cnyMarginCreditVO.setCurrency("CNY");
		cnyMarginCreditVO.setCreditValue(new BigDecimal("0.00"));//当前信用额度，从柜台查询
		cnyMarginCreditVO.setMaxExposure(new BigDecimal("100000.00"));
		cnyMarginCreditVO.setApplyBalance(new BigDecimal("500.00"));
		result.add(hkdMarginCreditVO);
		result.add(usdMarginCreditVO);
		result.add(cnyMarginCreditVO);

		return result;
	}

	@Override
//	@Transactional(rollbackFor = Exception.class)
	public R submit(MarginCreditAdjustApplyDTO dto) {

		IncreaseCreditLimitEntity increaseCreditLimit =new IncreaseCreditLimitEntity();
		increaseCreditLimit.setTradeAccount(dto.getTradeAccount());
		increaseCreditLimit.setFundAccount(dto.getCapitalAccount());
		increaseCreditLimit.setCustId(dto.getCustId());
		if(1 ==dto.getApplyType()){
			increaseCreditLimit.setTenantId(AuthUtil.getTenantId());
		}else{
			increaseCreditLimit.setTenantId(dto.getTenantId());
		}
		increaseCreditLimit.setStatus(MarginCreditEnum.MarginCreditAppStatus.PENDING.getIndex());
		increaseCreditLimit.setCreateTime(new Date());
		for (MarginCreditAdjustApplyDTO.LineCreditInfo lineCreditInfo : dto.getLineCreditInfo()) {
			if ("HKD".equals(lineCreditInfo.getCurrency())) {
				increaseCreditLimit.setApplyQuota(lineCreditInfo.getLineCreditApply());
				increaseCreditLimit.setCurrentLineCredit(lineCreditInfo.getLineCreditBefore());
				increaseCreditLimit.setHkLimitVal(lineCreditInfo.getLineCreditApply());
			}
		}
		increaseCreditLimit.setClientName(dto.getClientName());
		increaseCreditLimit.setClientNameSpell(dto.getClientNameSpell());
		// 线上申请
		increaseCreditLimit.setApplyType(dto.getApplyType());
		String applicationId = ApplicationIdUtil.generateMarginCreditId(increaseCreditLimit.getTenantId());
		increaseCreditLimit.setApplicationId(applicationId);
		increaseCreditLimitService.getBaseMapper().insert(increaseCreditLimit);

		IncreaseCreditLimitApplicationEntity applicationEntity =new IncreaseCreditLimitApplicationEntity();
		applicationEntity.setApplicationId(applicationId);
		applicationEntity.setApplicationTitle("信用额度[" + increaseCreditLimit.getClientName() + "]");
		applicationEntity.setCreateTime(new Date());
		applicationEntity.setStartTime(new Date());
//		applicationEntity.setCurrentNode("开始");
		increaseCreditLimitApplicationService.save(applicationEntity);
		log.info("保存信用额度审批表=="+ JSONUtil.toCompatibleJson(applicationEntity));

		String key = ProcessConstant.MARGIN_CREDIT;
		String table = FlowUtil.getBusinessTable(key);
		String dictKey = FlowUtil.getBusinessDictKey(key);
		Map<String, Object> variables = new LinkedMap<>();
		variables.put(TaskConstants.IS_BACK, 0);
		variables.put(TaskConstants.PROCESS_INITIATOR, TaskUtil.getTaskUser());
		variables.put(TaskConstants.APPLICATION_TABLE, table);
		variables.put(TaskConstants.APPLICATION_DICT_KEY, dictKey);
		variables.put(TaskConstants.APPLICATION_ID, applicationId);
		variables.put(TaskConstants.TENANT_ID,"000000");
		R flowResult =  flowClient.startProcessInstanceByKey(key, variables);

		if(!flowResult.isSuccess()){
			log.error("启动信用额度"+applicationId+"流程失败："+flowResult.getMsg());
			increaseCreditLimitService.getBaseMapper().deleteById(increaseCreditLimit.getId());
			increaseCreditLimitApplicationService.getBaseMapper().deleteById(applicationEntity.getId());
			return flowResult;
		}
		/**
		 * 开启工作流
		 */
		return R.success();
	}

	@Override
	public List<IncreaseCreditLimitVO> selectListByCustId(Long custId) {
		List<IncreaseCreditLimitVO> results =new ArrayList<>();

		LambdaQueryWrapper<IncreaseCreditLimitEntity> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(IncreaseCreditLimitEntity::getCustId, custId);
		queryWrapper.orderByDesc(IncreaseCreditLimitEntity::getCreateTime);
		List<IncreaseCreditLimitEntity> list=increaseCreditLimitService.getBaseMapper().selectList(queryWrapper);
		for (IncreaseCreditLimitEntity increaseCreditLimit : list) {
			IncreaseCreditLimitVO vo =new IncreaseCreditLimitVO();
			BeanUtil.copy(increaseCreditLimit, vo);
			IncreaseCreditLimitApplicationEntity entity =increaseCreditLimitApplicationMapper.queryByApplicationId(increaseCreditLimit.getApplicationId());
			vo.setApplicationEntity(entity);
			results.add(vo);
		}
		return results;
	}

	@Override
	public boolean isReviewData(Long custId) {

		List<Integer> applicationStatus =new ArrayList<>();
		applicationStatus.add(MarginCreditEnum.MarginCreditApplicationStatus.PENDING.getIndex());
		applicationStatus.add(MarginCreditEnum.MarginCreditApplicationStatus.REVIEW.getIndex());

		List<IncreaseCreditLimitApplicationEntity> list =increaseCreditLimitApplicationMapper.queryListByStatus(custId,applicationStatus);
        if(list.size() >0){
			return true;
		}
		return false;
	}

	@Override
	public R marginCreditArchiveJob(Map<String, Object> map) {
		LambdaQueryWrapper<IncreaseCreditLimitApplicationEntity> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(IncreaseCreditLimitApplicationEntity::getCurrentNode, "归档");
		List<IncreaseCreditLimitApplicationEntity> list = increaseCreditLimitApplicationMapper.selectList(queryWrapper);
		for (IncreaseCreditLimitApplicationEntity application : list) {
			try {
				R r = flowClient.completeTask(application.getTaskId(), "归档成功", new HashMap<>());

				/**
				 * 修改app显示状态
				 */
				IncreaseCreditLimitEntity increaseCreditLimit =increaseCreditLimitService.queryByApplicationId(application.getApplicationId());
				increaseCreditLimit.setStatus(MarginCreditEnum.MarginCreditAppStatus.PASS.getIndex());
				increaseCreditLimit.setUpdateTime(new Date());
				increaseCreditLimitService.updateById(increaseCreditLimit);
			} catch (Exception e) {
				log.error("执行信用额度归档任务异常ApplicationId:{}",application.getApplicationId());
				log.error("执行信用额度归档任务异常",e);
			}

		}
		return R.success();
	}
}
