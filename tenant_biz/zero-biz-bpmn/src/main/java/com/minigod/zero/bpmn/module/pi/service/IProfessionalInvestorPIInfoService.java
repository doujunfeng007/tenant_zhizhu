package com.minigod.zero.bpmn.module.pi.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.minigod.zero.bpmn.module.pi.bo.ProfessionalInvestorPIBO;
import com.minigod.zero.bpmn.module.pi.entity.ProfessionalInvestorPIInfoEntity;
import com.minigod.zero.bpmn.module.pi.vo.ProfessionalInvestorPIInfoVO;
import com.minigod.zero.core.tool.api.R;

/**
 * 专业投资人PI申请信息 服务类
 *
 * @author eric
 * @since 2024-05-07
 */
public interface IProfessionalInvestorPIInfoService extends IService<ProfessionalInvestorPIInfoEntity> {
	/**
	 * 专业投资人PI申请信息分页查询
	 *
	 * @param page
	 * @param investorPIInfoVO
	 * @return
	 */
	IPage<ProfessionalInvestorPIInfoVO> selectProfessionalInvestorPIInfoPage(IPage<ProfessionalInvestorPIInfoVO> page, ProfessionalInvestorPIInfoVO investorPIInfoVO);

	/**
	 * 提交专业投资人PI申请
	 *
	 * @param investorPIBO
	 */
	R<String> submitProfessionalInvestorPI(ProfessionalInvestorPIBO investorPIBO);

	/**
	 * 根据ApplicationID查询PI申请信息
	 *
	 * @param applicationId
	 * @return
	 */
	ProfessionalInvestorPIInfoVO queryByApplicationId(String applicationId);

	/**
	 * 撤销专业投资者身份
	 *
	 * @param applicationId
	 * @param revocationReason
	 */
	Boolean revokeProfessionalInvestorStatus(String applicationId, String revocationReason);

	/**
	 * 根据CustID查询PI申请信息
	 *
	 * @return
	 */
	ProfessionalInvestorPIInfoVO queryByClientId();

	R submitProfessionalInvestorPIRepeal();

}
