package com.minigod.zero.customer.back.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.minigod.zero.customer.entity.CustomerTradeSubAccountEntity;
import java.util.List;

/**
 * 交易子账号信息 服务接口
 *
 * @author ken
 * @since 2024-04-11
 */
public interface ICustomerTradeSubAccountService extends IService<CustomerTradeSubAccountEntity> {

    /**
     * 获取交易账号下的子账号列表
     */
    List<CustomerTradeSubAccountEntity> getSubAccounts(String tradeAccount);

    /**
     * 获取理财账户下的所有子账号
     */
    List<CustomerTradeSubAccountEntity> getSubAccountsByAccountId(String accountId);

    /**
     * 新增交易子账号
     */
    boolean addSubAccount(CustomerTradeSubAccountEntity entity);

    /**
     * 批量新增交易子账号
     */
    boolean batchAddSubAccounts(List<CustomerTradeSubAccountEntity> entities);

	List<CustomerTradeSubAccountEntity> selecctSubAccountByTradeId(Long id);

	List<CustomerTradeSubAccountEntity> selectByAccountId(String accountId);

	/**
	 * 更新该业务的资金账号为主账号
	 * @param capitalInfo
	 * @param capitalAccount
	 * @param marketType
	 * @param tradeAccount
	 */
	void updateMasterAccount(CustomerTradeSubAccountEntity capitalInfo, String capitalAccount, String tradeAccount,String marketType);
}
