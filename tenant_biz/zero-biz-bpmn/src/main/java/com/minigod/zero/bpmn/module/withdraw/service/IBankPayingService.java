package com.minigod.zero.bpmn.module.withdraw.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.bpmn.module.withdraw.bo.BankPayingBo;
import com.minigod.zero.bpmn.module.withdraw.entity.BankPaying;
import com.minigod.zero.core.mp.support.Query;

import java.util.Collection;
import java.util.List;

/**
 * 公司付款银行信息Service接口
 *
 * @author chenyu
 * @date 2023-04-06
 */
public interface IBankPayingService {

	/**
	 * 根据交易账号 银行编码 币种 取款类型 查询付款银行信息
	 *
	 * @param serviceType
	 * @param recvBankCode
	 * @param recvCcy
	 * @param tenantId
	 * @param transferType
	 * @return
	 */
	BankPaying getPayingBank(String serviceType, String recvBankCode, String recvCcy, String tenantId, Integer transferType);

	/**
	 * 查询公司付款银行信息
	 *
	 * @param id 公司付款银行信息主键
	 * @return 公司付款银行信息
	 */
	BankPaying queryById(Long id);

	/**
	 * 查询银行信息记录
	 *
	 * @param bo 银行信息记录
	 * @return 银行信息记录
	 */
	BankPaying queryEntity(BankPayingBo bo);

	/**
	 * 查询公司付款银行列表
	 */
	List<BankPaying> queryBankList(BankPayingBo bo);

	/**
	 * 查询公司付款银行信息列表
	 *
	 * @param query 分页参数
	 * @param bo    公司付款银行信息
	 * @return 公司付款银行信息集合
	 */
	IPage<BankPaying> queryPageList(Query query, BankPayingBo bo);

	/**
	 * 查询公司付款银行信息列表
	 *
	 * @param bo 公司付款银行信息
	 * @return 公司付款银行信息集合
	 */
	List<BankPaying> queryList(BankPayingBo bo);

	/**
	 * 修改公司付款银行信息
	 *
	 * @param bo 公司付款银行信息
	 * @return 结果
	 */
	Boolean insertByBo(BankPayingBo bo);

	/**
	 * 修改公司付款银行信息
	 *
	 * @param bo 公司付款银行信息
	 * @return 结果
	 */
	Boolean updateUserBo(BankPayingBo bo);

	/**
	 * 校验并批量删除公司付款银行信息信息
	 *
	 * @param ids     需要删除的公司付款银行信息主键集合
	 * @param isValid 是否校验,true-删除前校验,false-不校验
	 * @return 结果
	 */
	Boolean deleteWithValidByIds(List<Long> ids, Boolean isValid);
}
