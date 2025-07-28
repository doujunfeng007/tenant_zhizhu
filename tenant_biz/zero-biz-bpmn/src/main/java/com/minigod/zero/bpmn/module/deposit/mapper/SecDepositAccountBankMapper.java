package com.minigod.zero.bpmn.module.deposit.mapper;

import com.minigod.zero.bpmn.module.deposit.entity.SecDepositAccountBankEntity;
import com.minigod.zero.bpmn.module.deposit.vo.SecDepositAccountBankVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 客户银行卡记录 Mapper 接口
 *
 * @author 掌上智珠
 * @since 2024-03-01
 */
public interface SecDepositAccountBankMapper extends BaseMapper<SecDepositAccountBankEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param secDepositAccountBankVO
	 * @return
	 */
	List<SecDepositAccountBankVO> selectSecDepositAccountBankPage(IPage page, SecDepositAccountBankVO secDepositAccountBankVO);


}
