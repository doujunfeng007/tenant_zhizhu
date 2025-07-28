package com.minigod.zero.bpmn.module.pi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.minigod.zero.bpmn.module.pi.entity.ProfessionalInvestorPIVoucherImageEntity;
import com.minigod.zero.bpmn.module.pi.vo.ProfessionalInvestorPIVoucherImageVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 专业投资者FI申请凭证 Mapper 接口
 *
 * @author eric
 * @since 2025-05-07 15:30:19
 */
@Mapper
public interface ProfessionalInvestorPIVoucherImageMapper extends BaseMapper<ProfessionalInvestorPIVoucherImageEntity> {
	/**
	 * 批量保存凭证图片信息
	 *
	 * @param list
	 * @return
	 */
	int batchInsert(List<ProfessionalInvestorPIVoucherImageEntity> list);

	/**
	 * 根据applicationId查询凭证图片信息
	 *
	 * @param applicationId
	 * @return
	 */
	List<ProfessionalInvestorPIVoucherImageVO> queryByApplicationId(String applicationId);
}
