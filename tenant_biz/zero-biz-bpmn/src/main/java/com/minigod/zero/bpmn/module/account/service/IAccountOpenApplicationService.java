package com.minigod.zero.bpmn.module.account.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.bpmn.module.account.bo.OpenAccountCallbackBo;
import com.minigod.zero.bpmn.module.account.bo.query.ApplicationQuery;
import com.minigod.zero.bpmn.module.account.bo.query.BackApplicationQuery;
import com.minigod.zero.bpmn.module.account.bo.query.CABankVerifyQuery;
import com.minigod.zero.bpmn.module.account.vo.AccountBackInfoVO;
import com.minigod.zero.bpmn.module.account.vo.AccountCABankVerifyInfoVO;
import com.minigod.zero.bpmn.module.account.vo.AccountOpenApplicationVO;
import com.minigod.zero.core.mp.base.BaseService;
import com.minigod.zero.bpmn.module.account.entity.AccountOpenApplicationEntity;
import com.minigod.zero.core.mp.support.Query;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 客户开户申请
 *
 * @author Chill
 */
public interface IAccountOpenApplicationService extends BaseService<AccountOpenApplicationEntity> {

	/**
	 * 分页查询客户开户申请
	 *
	 * @param params
	 * @param pageQuery
	 * @return
	 */
	IPage<AccountOpenApplicationVO> queryPageList(ApplicationQuery params, Query pageQuery);

	/**
	 * 分页查询退回给客户的开户申请
	 *
	 * @param params
	 * @param pageQuery
	 * @return
	 */
	IPage<AccountBackInfoVO> queryBackPageList(BackApplicationQuery params, Query pageQuery);

	/**
	 * 分页查询CA银行四要素验证信息
	 *
	 * @param params
	 * @param pageQuery
	 * @return
	 */
	IPage<AccountCABankVerifyInfoVO> queryCABankVerifyInfoPageList(CABankVerifyQuery params, Query pageQuery);

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

	void rejectNode(String applicationId, String taskId, String reason, Integer status);

	void passNode(String applicationId, String taskId, String reason);

	/**
	 * 禁止/允许开户
	 *
	 * @param applicationId
	 * @param blacklist
	 * @param reason
	 * @return
	 */
	Boolean updateBlackListStatus(String applicationId, Integer blacklist, String reason);

	void rejectPreNode(String applicationId, String taskId, String reason);

	void resetAml(String applicationId);

	AccountOpenApplicationVO queryByApplicationId(String applicationId);

	List<AccountOpenApplicationEntity> queryListByNode(String currentNode);

	List<AccountOpenApplicationEntity> queryAmlChecking();

	void updateRefKeyByApplicationId(String applicationId, String refKey, String status);

	void callBackApplication(OpenAccountCallbackBo bo);
}
