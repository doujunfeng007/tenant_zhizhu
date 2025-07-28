package com.minigod.zero.customer.back.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.customer.entity.CustomerTradeAccountEntity;
import com.minigod.zero.trade.vo.sjmb.req.OpenAccountReq;

import java.util.List;

/**
 * 客户交易账户表 服务接口
 *
 * @author ken
 * @since 2024-04-11
 */
public interface ICustomerTradeAccountService extends IService<CustomerTradeAccountEntity> {

    /**
     * 获取理财账户下的交易账户列表
     */
    List<CustomerTradeAccountEntity> getTradeAccounts(String accountId);

    /**
     * 切换当前选中的交易账户
     */
    boolean switchCurrentAccount(String accountId, String tradeAccount);

    /**
     * 新增交易账户
     */
    boolean addTradeAccount(CustomerTradeAccountEntity entity);

    /**
     * 更新交易账户状态
     */
    boolean updateAccountStatus(String tradeAccount, Integer status);

	/**
	 * 保存交易账号以及子账号
	 * @param openAccountReq
	 * @return
	 */
	R saveTradeAccountAndSubAccount(OpenAccountReq openAccountReq,Long custId);

	/**'
	 * 查询用户交易账号信息
	 * @param accountId
	 * @param businessType
	 * @return
	 */
	CustomerTradeAccountEntity selectTradeByAccountAndType(String accountId, String businessType);

	List<CustomerTradeAccountEntity> selectTradeByAccount(String accountId);

}
