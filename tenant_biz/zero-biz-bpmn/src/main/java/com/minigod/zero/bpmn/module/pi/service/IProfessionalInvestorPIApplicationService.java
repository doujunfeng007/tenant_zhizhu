package com.minigod.zero.bpmn.module.pi.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.minigod.zero.bpmn.module.account.enums.PIApplyPdfEnum;
import com.minigod.zero.bpmn.module.pi.bo.query.ProfessionalInvestorPIApplicationQuery;
import com.minigod.zero.bpmn.module.pi.entity.ProfessionalInvestorPIApplicationEntity;
import com.minigod.zero.bpmn.module.pi.vo.PIApplicationStatusVO;
import com.minigod.zero.bpmn.module.pi.vo.ProfessionalInvestorPIApplicationVO;
import com.minigod.zero.core.tool.api.R;

import java.util.List;

/**
 * 专业投资人PI申请记录表 服务类
 *
 * @author eric
 * @since 2024-05-07
 */
public interface IProfessionalInvestorPIApplicationService extends IService<ProfessionalInvestorPIApplicationEntity> {
	/**
	 * 专业投资人申请记录分页查询
	 *
	 * @param page
	 * @param applicationQuery
	 * @return
	 */
	IPage<ProfessionalInvestorPIApplicationVO> selectProfessionalInvestorPIApplicationPage(IPage<ProfessionalInvestorPIApplicationVO> page,
																						   ProfessionalInvestorPIApplicationQuery applicationQuery);

	/**
	 * 获取专业投资人申请记录
	 *
	 * @param applicationId
	 * @return
	 */
	ProfessionalInvestorPIApplicationVO queryByApplicationId(String applicationId);

	/**
	 * 申领专业投资人审核记录
	 *
	 * @return
	 */
	void assignDrafter(String applicationId);

	/**
	 * 审批不通过
	 *
	 * @param applicationId
	 * @param instanceId
	 * @param reason
	 */
	void rejectNode(String applicationId, String instanceId, String reason);

	/**
	 * 审批通过
	 *
	 * @param applicationId
	 * @param taskId
	 * @param reason
	 */
	void passNode(String applicationId, String taskId, String reason);

	/**
	 * 查询审批状态
	 *
	 * @return
	 */
	Integer queryApplicationStatus();

	/**
	 * 根据当前节点查询审批列表
	 *
	 * @param currentNodeName
	 * @return
	 */
	List<ProfessionalInvestorPIApplicationEntity> queryListByNode(String currentNodeName);

	/**
	 * 发送邮件
	 *
	 * @param applicationId
	 * @param piApplyPdfType
	 * @param isApproved
	 */
	void sendEmail(String applicationId, PIApplyPdfEnum piApplyPdfType, Boolean isApproved);

}
