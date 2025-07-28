package com.minigod.zero.bpm.service;

import com.minigod.zero.bpm.vo.request.CashDepositBankQueryReq;
import com.minigod.zero.bpm.entity.BpmOtherBankInfoEntity;
import com.minigod.zero.bpm.vo.BankInfoVO;
import com.minigod.zero.bpm.vo.BpmOtherBankInfoVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.bpm.vo.request.OtherBankInfoQueryReq;

import java.util.List;

/**
 * 区域入金银行列表 服务类
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
public interface IBpmOtherBankInfoService extends IService<BpmOtherBankInfoEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param bpmOtherBankInfo
	 * @return
	 */
	IPage<BpmOtherBankInfoVO> selectBpmOtherBankInfoPage(IPage<BpmOtherBankInfoVO> page, BpmOtherBankInfoVO bpmOtherBankInfo);

	/**
	 * 获取其他银行列表
	 *
	 * @param cashDepositBankQueryReq 查询条件
	 * @return List<BankInfoVO>
	 */
	List<BankInfoVO> findDepositBankList(CashDepositBankQueryReq cashDepositBankQueryReq);

	/**
	 * 获取其他银行卡信息
	 *
	 * @param otherBankInfoQueryReq 查询条件
	 * @return String[]
	 */
	String[] otherBankInfo(OtherBankInfoQueryReq otherBankInfoQueryReq);

	/**
	 * 获取其他银行卡列表信息
	 *
	 * @param otherBankInfoQueryReq 查询条件
	 * @return List<BpmOtherBankInfoEntity>
	 */
	List<BpmOtherBankInfoEntity> otherBankInfoList(OtherBankInfoQueryReq otherBankInfoQueryReq);

	/**
	 * 根据bankId查询银行信息
	 * @param bankId
	 * @return
	 */
    List<BpmOtherBankInfoEntity> queryByBankId(String bankId);

	/**
	 * 查询银行列表
	 * @param cashDepositBankQueryReq 查询条件
	 * @return List<BankInfoVO>
	 */
    List<BankInfoVO> selectBankInfo(CashDepositBankQueryReq cashDepositBankQueryReq);
}
