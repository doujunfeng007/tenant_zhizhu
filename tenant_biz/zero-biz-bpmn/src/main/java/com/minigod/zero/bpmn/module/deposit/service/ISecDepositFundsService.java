package com.minigod.zero.bpmn.module.deposit.service;

import com.minigod.zero.bpmn.module.deposit.bo.HistoryRecordBo;
import com.minigod.zero.bpmn.module.deposit.entity.SecDepositFundsEntity;
import com.minigod.zero.bpmn.module.deposit.vo.DepositVO;
import com.minigod.zero.bpmn.module.deposit.vo.ManualDepositVO;
import com.minigod.zero.bpmn.module.deposit.vo.MoneySumVO;
import com.minigod.zero.bpmn.module.deposit.vo.SecDepositFundsVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.core.tool.api.R;

import java.util.List;

/**
 * 存入资金表 服务类
 *
 * @author 掌上智珠
 * @since 2024-03-01
 */
public interface ISecDepositFundsService extends IService<SecDepositFundsEntity> {

	/**
	 * 分页查询存入资金表
	 *
	 * @param page
	 * @param secDepositFundsVO
	 * @return
	 */
	IPage<SecDepositFundsVO> selectSecDepositFundsPage(IPage<SecDepositFundsVO> page, SecDepositFundsVO secDepositFundsVO);

	/**
	 * 查询资金记录
	 *
	 * @param params
	 * @return
	 */
	List<SecDepositFundsEntity> findDepositFunds(HistoryRecordBo params);

	MoneySumVO allDepositFunds(Integer state);

	/**
	 * 提交入金申请
	 *
	 * @param params
	 * @return
	 */
	R submitDepositFund(DepositVO params);

	/**
	 * 手工入金
	 *
	 * @param manualDepositVO
	 * @return
	 */
	R manualDeposit(ManualDepositVO manualDepositVO);

	List<SecDepositFundsVO> selectByIsPushedFalse();

	SecDepositFundsEntity selectByApplicationId(String applicationId);
}
