package com.minigod.zero.bpm.mapper;

import com.minigod.zero.bpm.entity.CashDepositAccountBankEntity;
import com.minigod.zero.bpm.vo.CashDepositAccountBankVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.bpm.vo.response.DepositAccountBankResp;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 客户银行卡记录 Mapper 接口
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
public interface CashDepositAccountBankMapper extends BaseMapper<CashDepositAccountBankEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param cashDepositAccountBank
	 * @return
	 */
	List<CashDepositAccountBankVO> selectCashDepositAccountBankPage(IPage page, CashDepositAccountBankVO cashDepositAccountBank);

	List<DepositAccountBankResp> findDepositBankInfoByTradeAccount(@Param("tradeAccount") String tradeAccount, @Param("bankCount") Integer bankCount, @Param("bankType") Integer bankType);
}
