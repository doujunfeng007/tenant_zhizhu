package com.minigod.zero.bpmn.module.deposit.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.minigod.zero.bpmn.module.deposit.entity.BankCardImageEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BankCardImageMapper extends BaseMapper<BankCardImageEntity> {
	/**
	 * 批量插入银行卡凭证
	 *
	 * @param list
	 * @return
	 */
	int batchInsert(List<BankCardImageEntity> list);

	/**
	 * 根据预约流水号查询银行卡凭证
	 *
	 * @param applicationId
	 * @return
	 */
	List<BankCardImageEntity> queryByApplicationId(String applicationId);
}
