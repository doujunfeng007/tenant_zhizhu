package com.minigod.zero.bpmn.module.withdraw.service;

import com.minigod.zero.dbs.bo.DbsTransaction;
import com.minigod.zero.dbs.bo.icn.IcnInfo;
import com.minigod.zero.dbs.bo.icn.IdnInfo;

/**
 * DBS银行回调相关服务类
 * ICN/EDDA ACK2/IDN/Remit ACK2/ACK3
 *
 * @author chenyu
 * @title DbsBankCallbackApiService
 * @date 2023-04-13 0:27
 * @description
 */
public interface DbsBankCallbackApiService {


    /**
     * 接收银行Remit ACT/CHATS 流水信息
     *
     * @param text Remit 流水信息
     */
    void remitAck(String tenantId,String text);

    /**
     * 银行流水通知
     * @param tenantId
     * @param transaction
     */
    void icn(String tenantId, DbsTransaction<IcnInfo> transaction);

    /**
     * 银行流水通知
     * @param tenantId
     * @param transaction
     */
    void idn(String tenantId, DbsTransaction<IdnInfo> transaction);

	/**
	 * edda 授权回调 ack2
	 * @param tenantId
	 * @param body
	 */
	void eddaAck2(String tenantId, String body);
}
