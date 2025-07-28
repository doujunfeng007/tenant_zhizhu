package com.minigod.zero.bpmn.module.deposit.mapper;

import com.minigod.zero.bpmn.module.deposit.entity.SecDepositBankEntity;
import com.minigod.zero.bpmn.module.deposit.vo.SecDepositBankVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 入金银行配置表 Mapper 接口
 *
 * @author 掌上智珠
 * @since 2024-03-01
 */
public interface SecDepositBankMapper extends BaseMapper<SecDepositBankEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param secDepositBankVO
	 * @return
	 */
	List<SecDepositBankVO> selectSecDepositBankPage(IPage page, SecDepositBankVO secDepositBankVO);


}
