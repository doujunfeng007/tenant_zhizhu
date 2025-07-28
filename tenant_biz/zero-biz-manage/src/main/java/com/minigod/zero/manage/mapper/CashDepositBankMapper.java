package com.minigod.zero.manage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.manage.entity.CashDepositBankEntity;
import com.minigod.zero.manage.vo.DepositBankVO;
import com.minigod.zero.manage.vo.CashDepositBankVO;

import java.util.List;

/**
 * 入金银行信息维护表 Mapper 接口
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
public interface CashDepositBankMapper extends BaseMapper<CashDepositBankEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param cashDepositBank
	 * @return
	 */
	List<CashDepositBankVO> selectCashDepositBankPage(IPage page, CashDepositBankVO cashDepositBank);


	List<DepositBankVO> getAllDepositBank(Integer bankType);
}
