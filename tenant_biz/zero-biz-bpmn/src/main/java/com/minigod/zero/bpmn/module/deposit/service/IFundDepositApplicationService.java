package com.minigod.zero.bpmn.module.deposit.service;

import com.minigod.zero.bpmn.module.deposit.bo.FundDepositApplicationQuery;
import com.minigod.zero.bpmn.module.deposit.entity.FundDepositApplicationEntity;
import com.minigod.zero.bpmn.module.deposit.vo.DepositRecordVO;
import com.minigod.zero.bpmn.module.deposit.vo.FundDepositApplicationVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 客户入金申请表 服务类
 *
 * @author taro
 * @since 2024-02-29
 */
public interface IFundDepositApplicationService extends IService<FundDepositApplicationEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param applicationQuery
	 * @return
	 */
	IPage<FundDepositApplicationVO> selectFundDepositApplicationPage(IPage<FundDepositApplicationVO> page, FundDepositApplicationQuery applicationQuery);

	/**
	 * 入金申请表分页查询
	 *
	 * @param page
	 * @param applicationQuery
	 * @return
	 */
	IPage<DepositRecordVO> queryPage(IPage<DepositRecordVO> page, FundDepositApplicationQuery applicationQuery);

	DepositRecordVO depositRecordDetail(String applicationId);

	/**
	 * 批量申领任务
	 *
	 * @param applicationIds
	 * @return
	 */
	String batchClaim(Integer applicationStatus, @NotEmpty List<String> applicationIds);

	/**
	 * 批量取消认领
	 *
	 * @param applicationIds
	 */
	void batchUnClaim(@NotEmpty List<String> applicationIds);

	/**
	 * 拒绝节点
	 *
	 * @param applicationId
	 * @param instanceId
	 * @param reason
	 */
	void rejectNode(String applicationId, String instanceId, String reason);

	/**
	 * 通过节点
	 *
	 * @param applicationId
	 * @param taskId
	 * @param reason
	 */
	void passNode(String applicationId, String taskId, String reason);

	/**
	 * 获取入金流程相关信息
	 *
	 * @param applicationId
	 * @return
	 */
	FundDepositApplicationVO queryByApplicationId(String applicationId);

	/**
	 * 核对节点
	 *
	 * @param applicationId
	 * @param taskId
	 * @param refId
	 * @param reason
	 */
	void collateNode(String applicationId, String taskId, String refId, String reason);

	/**
	 * 获取当前流程节点
	 *
	 * @param currentNodeName
	 * @return
	 */
	List<FundDepositApplicationEntity> queryListByNode(String currentNodeName);


}
