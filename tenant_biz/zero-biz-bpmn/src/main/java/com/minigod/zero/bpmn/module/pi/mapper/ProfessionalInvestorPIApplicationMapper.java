package com.minigod.zero.bpmn.module.pi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.bpmn.module.pi.bo.query.ProfessionalInvestorPIApplicationQuery;
import com.minigod.zero.bpmn.module.pi.entity.ProfessionalInvestorPIApplicationEntity;
import com.minigod.zero.bpmn.module.pi.vo.ProfessionalInvestorPIApplicationVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 专业投资者FI申请记录 Mapper 接口
 *
 * @author eric
 * @since 2025-05-07 15:05:09
 */
@Mapper
public interface ProfessionalInvestorPIApplicationMapper extends BaseMapper<ProfessionalInvestorPIApplicationEntity> {
	/**
	 * 申请记录分页查询
	 *
	 * @param page
	 * @param applicationQuery
	 * @return
	 */
	IPage<ProfessionalInvestorPIApplicationVO> selectProfessionalInvestorPIApplicationPage(IPage page, @Param("query") ProfessionalInvestorPIApplicationQuery applicationQuery);

	/**
	 * 根据申请ID查询申请记录
	 *
	 * @param applicationId
	 * @return
	 */
	ProfessionalInvestorPIApplicationEntity queryByApplicationId(String applicationId);
	/**
	 * 分配当前处理人
	 *
	 * @param assignDrafter
	 * @param applicationId
	 */
	void addAssignDrafter(String assignDrafter, String applicationId);

	/**
	 * 清空当当前处理人
	 *
	 * @param applicationId
	 */
	void clearAssignDrafter(String applicationId);
}
