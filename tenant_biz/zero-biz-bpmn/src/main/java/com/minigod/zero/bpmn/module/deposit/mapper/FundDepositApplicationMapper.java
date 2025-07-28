package com.minigod.zero.bpmn.module.deposit.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.bpmn.module.deposit.bo.FundDepositApplicationQuery;
import com.minigod.zero.bpmn.module.deposit.entity.FundDepositApplicationEntity;
import com.minigod.zero.bpmn.module.deposit.vo.FundDepositApplicationVO;
import org.apache.ibatis.annotations.Param;

/**
 * 客户入金申请表 Mapper 接口
 *
 * @author taro
 * @since 2024-02-29
 */
public interface FundDepositApplicationMapper extends BaseMapper<FundDepositApplicationEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param applicationQuery
	 * @return
	 */
	IPage<FundDepositApplicationVO> selectFundDepositApplicationPage(IPage page,@Param("vo") FundDepositApplicationQuery applicationQuery);


    void addAssignDrafter( String assignDrafter,String applicationId);

	void clearAssignDrafter(String applicationId);

	void updateApplicationStatusByApplicationId(@Param("applicationId")String applicationId,@Param("status")Integer status);

}
