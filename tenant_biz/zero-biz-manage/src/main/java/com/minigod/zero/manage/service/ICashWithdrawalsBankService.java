package com.minigod.zero.manage.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.manage.entity.CashWithdrawalsBankEntity;
import com.minigod.zero.manage.vo.BankSwiftCodeVO;
import com.minigod.zero.manage.dto.CashWithdrawalsBankSaveDTO;
import com.minigod.zero.manage.vo.CashWithdrawalsBankVO;

/**
 * 出金银行卡配置表 服务类
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
public interface ICashWithdrawalsBankService extends IService<CashWithdrawalsBankEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param cashWithdrawalsBank
	 * @return
	 */
	IPage<CashWithdrawalsBankVO> selectCashWithdrawalsBankPage(IPage<CashWithdrawalsBankVO> page, CashWithdrawalsBankVO cashWithdrawalsBank);

	/**
	 * 保存出金银行卡配置
	 *
	 * @param dto
	 */
	void save(CashWithdrawalsBankSaveDTO dto);

	/**
	 * 出金银行配置
	 *
	 * @param bankType
	 * @return
	 */
	R withdrawBankList(Integer bankType);

	/**
	 * 获取银行 swiftCode
	 *
	 * @param bankCode
	 * @return
	 */
	R<BankSwiftCodeVO> bankSwiftCode(String bankCode);

	/**
	 * 根据bankId和bankCode查询出金银行卡配置
	 */
	CashWithdrawalsBankEntity selectByBankCodeAndId(String bankCode, String bankId);
}
