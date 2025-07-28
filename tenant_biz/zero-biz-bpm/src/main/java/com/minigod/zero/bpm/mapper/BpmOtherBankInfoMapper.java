package com.minigod.zero.bpm.mapper;

import com.minigod.zero.bpm.vo.request.CashDepositBankQueryReq;
import com.minigod.zero.bpm.entity.BpmOtherBankInfoEntity;
import com.minigod.zero.bpm.vo.BankInfoVO;
import com.minigod.zero.bpm.vo.BpmOtherBankInfoVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 区域入金银行列表 Mapper 接口
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
public interface BpmOtherBankInfoMapper extends BaseMapper<BpmOtherBankInfoEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param bpmOtherBankInfo
	 * @return
	 */
	List<BpmOtherBankInfoVO> selectBpmOtherBankInfoPage(IPage page, BpmOtherBankInfoVO bpmOtherBankInfo);

	/**
	 * 查询银行列表 按银行名称拼音首字母排序 正序
	 * @param cashDepositBankQueryReq 查询条件
	 * @return List<BankInfoVO>
	 */
    List<BankInfoVO> selectBankInfo(CashDepositBankQueryReq cashDepositBankQueryReq);
}
