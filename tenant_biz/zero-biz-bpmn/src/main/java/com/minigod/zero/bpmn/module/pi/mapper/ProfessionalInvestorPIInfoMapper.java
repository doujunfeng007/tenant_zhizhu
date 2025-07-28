package com.minigod.zero.bpmn.module.pi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.bpmn.module.pi.entity.ProfessionalInvestorPIInfoEntity;
import com.minigod.zero.bpmn.module.pi.vo.ProfessionalInvestorPIInfoVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 专业投资者FI申请基本信息 Mapper 接口
 *
 * @author eric
 * @since 2025-05-07 15:30:19
 */
@Mapper
public interface ProfessionalInvestorPIInfoMapper extends BaseMapper<ProfessionalInvestorPIInfoEntity> {
	/**
	 * 自定义分页查询申请基本信息列表
	 *
	 * @param page
	 * @param professionalInvestorFIInfoVO
	 * @return
	 */
	List<ProfessionalInvestorPIInfoVO> selectProfessionalInvestorFIInfoPage(IPage page, ProfessionalInvestorPIInfoVO professionalInvestorFIInfoVO);

	/**
	 * 根据applicationId查询申请基本信息
	 *
	 * @param applicationId
	 * @return
	 */
	ProfessionalInvestorPIInfoVO queryByApplicationId(String applicationId);
}
