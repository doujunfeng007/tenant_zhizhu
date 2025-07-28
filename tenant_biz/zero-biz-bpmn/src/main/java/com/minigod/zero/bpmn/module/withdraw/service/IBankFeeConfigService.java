package com.minigod.zero.bpmn.module.withdraw.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.bpmn.module.withdraw.bo.BankFeeConfigBo;
import com.minigod.zero.bpmn.module.withdraw.entity.BankFeeConfig;

import java.util.List;

/**
 * 取款手续费Service接口
 *
 * @author chenyu
 * @date 2023-04-06
 */
public interface IBankFeeConfigService {

	/**
	 * 新建 取款手续费
	 *
	 * @param bo BankFeeConfigBo
	 */
	Long commitApply(BankFeeConfigBo bo);

	/**
	 * 修改取款手续费
	 *
	 * @param bo 取款手续费
	 * @return 结果
	 */
	Long updateFeeConfig(BankFeeConfigBo bo);

	/**
	 * 查询取款手续费
	 *
	 * @param id 取款手续费主键
	 * @return 取款手续费
	 */
	BankFeeConfig queryById(Long id);

	/**
	 * 查询取款手续费列表
	 *
	 * @param bo 取款手续费
	 * @return 取款手续费集合
	 */
	IPage<BankFeeConfig> queryPageList(IPage query, BankFeeConfigBo bo);

	/**
	 * 查询取款手续费列表
	 *
	 * @param bo 取款手续费
	 * @return 取款手续费集合
	 */
	List<BankFeeConfig> queryList(BankFeeConfigBo bo);

	/**
	 * 删除取款手续费
	 *
	 * @param asList
	 * @param b
	 * @return
	 */
	boolean deleteWithValidByIds(List<String> asList, boolean b);

	/**
	 * 查询手续费银行列表 -所有银行
	 *
	 * @param bo
	 * @return
	 */
	List<BankFeeConfig> bankList(BankFeeConfigBo bo);
}
