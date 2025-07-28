package com.minigod.zero.manage.mapper;

import com.minigod.zero.manage.entity.CashWithdrawalsBankEntity;
import com.minigod.zero.manage.vo.CashWithdrawalsBankVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 出金银行卡配置表 Mapper 接口
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
public interface CashWithdrawalsBankMapper extends BaseMapper<CashWithdrawalsBankEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param cashWithdrawalsBank
	 * @return
	 */
	List<CashWithdrawalsBankVO> selectCashWithdrawalsBankPage(IPage page, CashWithdrawalsBankVO cashWithdrawalsBank);


}
