package com.minigod.zero.bpmn.module.deposit.mapper;

import com.minigod.zero.bpmn.module.deposit.entity.FundDepositInfoEntity;
import com.minigod.zero.bpmn.module.deposit.vo.FundDepositInfoVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 客户入金申请信息表 Mapper 接口
 *
 * @author taro
 * @since 2024-02-29
 */
public interface FundDepositInfoMapper extends BaseMapper<FundDepositInfoEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param fundDepositInfo
	 * @return
	 */
	List<FundDepositInfoVO> selectFundDepositInfoPage(IPage page, FundDepositInfoVO fundDepositInfo);


	FundDepositInfoVO queryByApplicationId(String applicationId);
}
