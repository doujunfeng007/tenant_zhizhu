package com.minigod.zero.bpm.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.minigod.zero.bpm.vo.request.AccountFundReqVo;
import com.minigod.zero.bpm.vo.request.ClientFundWithdrawCallbackProtocol;
import com.minigod.zero.bpm.vo.request.ExtractMoneyReqVo;
import com.minigod.zero.bpm.vo.request.HistoryRecordReqVo;
import com.minigod.zero.bpm.vo.response.ResponseVO;
import com.minigod.zero.bpm.entity.CashExtractingMoneyEntity;
import com.minigod.zero.bpm.vo.CashExtractingMoneyVO;
import com.minigod.zero.bpm.vo.request.*;
import com.minigod.zero.core.tool.api.R;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 提取资金表 服务类
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
public interface ICashExtractingMoneyService extends IService<CashExtractingMoneyEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param cashExtractingMoney
	 * @return
	 */
	IPage<CashExtractingMoneyVO> selectCashExtractingMoneyPage(IPage<CashExtractingMoneyVO> page, CashExtractingMoneyVO cashExtractingMoney);

	/**
	 * 提取资金
	 *
	 * @param custId
	 * @param vo
	 * @return
	 */
	R extractFund(Long custId, ExtractMoneyReqVo vo);

	/**
	 * 获取出金凭证
	 *
	 * @param custId
	 * @param imgIds
	 * @return
	 */
	R getWithdrawImg(Long custId, String imgIds);

	/**
	 * 获取用户资金信息
	 *
	 * @param vo
	 * @return
	 */
	R getExtractableMoney(AccountFundReqVo vo);

	/**
	 * 客户提取资金回调
	 *
	 * @param protocol
	 * @return
	 */
	ResponseVO updateFundWithdrawStatus(ClientFundWithdrawCallbackProtocol protocol);

	/**
	 * 取消出金
	 *
	 * @param vo
	 * @return
	 */
	R cancelClientWithdraw(ExtractMoneyReqVo vo);

	/**
	 * 获取用户最近出金成功银行信息
	 *
	 * @param custId
	 * @param extMethod
	 * @return
	 */
	R withdrawSuccessBank(Long custId, Integer extMethod);

	/**
	 * 获取用户最近出金列表
	 * @param custId
	 * @return
	 */
	R withdrawSuccessBankList(Long custId);

	/**
	 * 查询提取资金记录
	 * @param historyRecord
	 * @return
	 */
	List<CashExtractingMoneyEntity> findExtratingMoney(Long custId, HistoryRecordReqVo historyRecord);

	/**
	 * 查询提取资金总和
	 * @param custId
	 * @return
	 */
	Map<String, BigDecimal> allExtractingMoney(Long custId);

	/**
	 * 执行客户出金数据传送任务
	 * @return
	 */
	R executeClientFundWithdrawApplyJob();
}
