package com.minigod.zero.bpmn.module.deposit.service;

import com.minigod.zero.bpmn.module.deposit.entity.SecDepositAccountBankEntity;
import com.minigod.zero.bpmn.module.deposit.vo.SecDepositAccountBankVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 客户银行卡记录 服务类
 *
 * @author 掌上智珠
 * @since 2024-03-01
 */
@Deprecated
public interface ISecDepositAccountBankService extends IService<SecDepositAccountBankEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param secDepositAccountBankVO
	 * @return
	 */
	IPage<SecDepositAccountBankVO> selectSecDepositAccountBankPage(IPage<SecDepositAccountBankVO> page, SecDepositAccountBankVO secDepositAccountBankVO);

	/**
	 * 查询用户银行卡信息
	 * @param bankCount 数量
	 * @param bankType 类型
	 * @return
	 */
    List<SecDepositAccountBankVO> findAccountDepositBankInfo(Integer bankCount, Integer bankType);
}
