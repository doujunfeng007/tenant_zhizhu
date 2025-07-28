package com.minigod.zero.bpmn.module.margincredit.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.bpmn.module.margincredit.entity.IncreaseCreditLimitApplicationEntity;
import com.minigod.zero.bpmn.module.margincredit.vo.IncreaseCreditLimitApplicationVO;
import com.minigod.zero.bpmn.module.margincredit.vo.req.IncreaseCreditQuery;
import com.minigod.zero.core.mp.base.BaseService;
import com.minigod.zero.core.mp.support.Query;

import java.util.List;

/**
 * @ClassName IncreaseCreditLimitApplicationService.java
 * @author chen
 * @Description TODO
 * @createTime 2024年03月09日 17:24:00
 */
public interface IIncreaseCreditLimitApplicationService extends BaseService<IncreaseCreditLimitApplicationEntity> {

	/**
	 * 审核列表
	 * @param query
	 * @param pageQuery
	 * @return
	 */
	IPage<IncreaseCreditLimitApplicationVO> queryPageList(IncreaseCreditQuery query, Query pageQuery);

	/**
	 * 批量申领
	 * @param applicationStatus
	 * @param applicationIds
	 * @return
	 */
	String batchClaim(Integer applicationStatus, List<String> applicationIds);

	/**
	 * 批量取消申领
	 * @param applicationIds
	 */
	void batchUnClaim(List<String> applicationIds);

	/**
	 * 驳回
	 * @param applicationId
	 * @param taskId
	 * @param reason
	 * @param status
	 */
	void rejectNode(String applicationId, String taskId, String reason, Integer status);

	/**
	 * 驳回至上一节点
	 * @param applicationId
	 * @param taskId
	 * @param reason
	 */
	void rejectPreNode(String applicationId, String taskId, String reason);

	/**
	 * 通过
	 * @param applicationId
	 * @param taskId
	 * @param reason
	 */
	void passNode(String applicationId, String taskId, String reason);
}
