package com.minigod.zero.customer.back.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.minigod.zero.customer.entity.CustomerAgreementInfoEntity;

/**
 * 大账户协议信息表 服务接口
 *
 * @author ken
 * @since 2024-04-11
 */
public interface ICustomerAgreementInfoService extends IService<CustomerAgreementInfoEntity> {

    /**
     * 根据客户ID和账户ID查询协议信息
     *
     * @param custId 客户ID
     * @param accountId 账户ID
     * @return 协议信息
     */
    CustomerAgreementInfoEntity getByCustomerAndAccount(Long custId, String accountId);

    /**
     * 更新协议签署状态
     *
     * @param entity 协议信息
     * @return 是否更新成功
     */
    boolean updateAgreementStatus(CustomerAgreementInfoEntity entity);
}
