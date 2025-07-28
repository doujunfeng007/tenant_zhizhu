package com.minigod.zero.bpmn.module.deposit.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.minigod.zero.bpmn.module.deposit.bo.SubmitDepositBo;
import com.minigod.zero.bpmn.module.deposit.entity.FundDepositInfoEntity;
import com.minigod.zero.bpmn.module.deposit.entity.SecDepositFundsEntity;
import com.minigod.zero.bpmn.module.deposit.vo.FundDepositInfoVO;

/**
 * 客户入金申请信息表 服务类
 *
 * @author taro
 * @since 2024-02-29
 */
public interface IFundDepositInfoService extends IService<FundDepositInfoEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param fundDepositInfo
	 * @return
	 */
	IPage<FundDepositInfoVO> selectFundDepositInfoPage(IPage<FundDepositInfoVO> page, FundDepositInfoVO fundDepositInfo);

	/**
	 * 提交入金申请
	 * @param secDepositFundsEntity
	 */
	void submitDeposit(SecDepositFundsEntity secDepositFundsEntity);

	/**
	 * 查询入金信息
	 * @param applicationId
	 * @return
	 */
	FundDepositInfoVO queryByApplicationId(String applicationId);

	/**
	 * 单个流水入账到柜台
	 * @param tenantId
	 * @param applicationId
	 */
    void depositToCounter(String tenantId,String applicationId,String taskId);

	/**
	 * edda 首次入金需要大于 1W
	 * @param params
	 */
	void validateFirstDeposit(SubmitDepositBo params);
}
