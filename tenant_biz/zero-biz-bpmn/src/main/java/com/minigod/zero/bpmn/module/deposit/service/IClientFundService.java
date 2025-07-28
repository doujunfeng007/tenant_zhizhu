package com.minigod.zero.bpmn.module.deposit.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.minigod.zero.bpmn.module.deposit.bo.AccountDepositBankBo;
import com.minigod.zero.bpmn.module.deposit.bo.DepositBankInfoBo;
import com.minigod.zero.bpmn.module.deposit.bo.HistoryRecordBo;
import com.minigod.zero.bpmn.module.deposit.bo.SecImgBo;
import com.minigod.zero.bpmn.module.deposit.vo.*;
import com.minigod.zero.bpmn.module.feign.vo.CashDepositWay;
import com.minigod.zero.bpmn.module.feign.vo.DepositBankVO;
import com.minigod.zero.bpmn.module.feign.vo.ReceivingBankVO;
import com.minigod.zero.core.tool.api.R;

/**
 * @ClassName: ClientFundService
 * @Description:
 * @Author chenyu
 * @Date 2024/2/29
 * @Version 1.0
 */
public interface IClientFundService {
	/**
	 * 查询资金记录
	 *
	 * @param params
	 * @return
	 */
	HistoryRecordVO findHistoryRecord(HistoryRecordBo params);

	/**
	 * 查询客户入金银行列表
	 *
	 * @param params
	 * @return
	 */
	List<BankCardInfoVO> findAccountDepositBankInfo(AccountDepositBankBo params);

	/**
	 * 查询入金银行设置
	 *
	 * @param params
	 * @return
	 */
	List<DepositBankVO> findDepositBank(DepositBankInfoBo params);

	/**
	 * 查询入金类型设置
	 *
	 * @param params
	 * @return
	 */
	List<SecDepositTypeVO> findDepositTypeSetting(DepositBankInfoBo params);

	/**
	 * FPS 入金收款账户
	 *
	 * @param params
	 * @return
	 */
	ReceivingBankVO fpsReceivingAccount(DepositBankInfoBo params);

	/**
	 * 网银转账 入金收款账户
	 *
	 * @param params
	 * @return
	 */
	ReceivingBankVO receivingAccount(DepositBankInfoBo params);

	/**
	 * 保存入金凭证
	 *
	 * @param params
	 * @return
	 */
	SecImgRespVo saveSecAccImg(SecImgBo params);

	/**
	 * 获取入金凭证列表
	 *
	 * @param params
	 * @return
	 */
	List<SecImgRespVo> findSecAccImg(SecImgBo params);

	/**
	 * 提交入金申请
	 *
	 * @param params
	 */
	R submitDepositFund(DepositVO params);

	/**
	 * 手工入金
	 *
	 * @param manualDepositVO
	 * @return
	 */
	R manualDeposit(ManualDepositVO manualDepositVO);

	/**
	 * 获取入金银行配置信息
	 *
	 * @param payeeBankDetailId
	 * @param supportType
	 * @param currency
	 * @return
	 */
	List<DepositBankVO> getDepositBankByPayeeBankDetailId(Long payeeBankDetailId,
														  String supportType,
														  String currency);

	/**
	 * 查询开户银行卡
	 *
	 * @param bankId
	 * @param bankCode
	 * @return
	 */
	List<BankCardVO> getCustomerBankCard(String bankId, String bankCode);

	/**
	 * 获取入金方式
	 *
	 * @param moneyType
	 * @param bankType
	 * @return
	 */
	R<List<CashDepositWay>> getDepositWays(Integer moneyType, Integer bankType);

	SecImgRespVo saveSecAccImg(MultipartFile multipartFile, Long custId);
}
