package com.minigod.zero.bpm.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.minigod.zero.bpm.vo.request.*;
import com.minigod.zero.bpm.dto.acct.req.CashPolicyReqDto;
import com.minigod.zero.bpm.entity.CashDepositFundsEntity;
import com.minigod.zero.bpm.vo.CashDepositFundsVO;
import com.minigod.zero.bpm.vo.request.*;
import com.minigod.zero.bpm.vo.response.ResponseVO;
import com.minigod.zero.core.tool.api.R;

/**
 * 存入资金表 服务类
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
public interface ICashDepositFundsService extends IService<CashDepositFundsEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param cashDepositFunds
	 * @return
	 */
	IPage<CashDepositFundsVO> selectCashDepositFundsPage(IPage<CashDepositFundsVO> page, CashDepositFundsVO cashDepositFunds);

	/**
	 * 存入资金
	 *
	 * @param custId
	 * @param params
	 * @return
	 */
	R saveIntoMoney(Long custId, CashDepositFundsReqVo params);

	/**
	 * 修改客户快捷入金状态
	 *
	 * @param protocol
	 * @return
	 */
	ResponseVO updateEddaFundsStatus(ClientDepositFundsCallBackProtocol protocol);

	/**
	 * 快捷自动入金申请流水号更新回调
	 *
	 * @param protocol
	 * @return
	 */
	ResponseVO updateEddaFundsApplicationId(ClientEddaFundsCallBackProtocol protocol);

	/**
	 * 修改客户入金状态
	 *
	 * @param protocol
	 * @return
	 */
	ResponseVO updateDepositFundsStatus(ClientDepositFundsCallBackProtocol protocol);


	/**
	 * 资金存入记录查询
	 * @param custId
	 * @param historyRecord
	 * @return
	 */
    R findDepositRecord(Long custId, HistoryRecordReqVo historyRecord);

	/**
	 * 存入资金账号入金记录数量
	 * @param vo
	 * @return
	 */
    R saveMoneyCheckAccount(SecDepositFundsVo vo);

	/**
	 * 执行客户入金数据传送任务
	 * @return
	 */
	R executeClientDepositFundApplyJob();


	R addPolicyPayment(CashPolicyReqDto dto);
}
