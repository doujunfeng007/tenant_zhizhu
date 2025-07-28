package com.minigod.zero.manage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.manage.entity.CashSupportCurrencyBankEntity;
import com.minigod.zero.manage.vo.CashSupportCurrencyBankVO;

import java.util.List;

/**
 * 入金银行 付款方式 币种 收款银行关联表 Mapper 接口
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
public interface CashSupportCurrencyBankMapper extends BaseMapper<CashSupportCurrencyBankEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param cashSupportCurrencyBank
	 * @return
	 */
	List<CashSupportCurrencyBankVO> selectCashSupportCurrencyBankPage(IPage page, CashSupportCurrencyBankVO cashSupportCurrencyBank);

	/**
	 * 删除
	 * @param id
	 * @return
	 */
	int deleteById(Long id);

}
