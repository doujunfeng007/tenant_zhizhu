package com.minigod.zero.bpmn.module.stock.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.minigod.zero.bpmn.module.stock.domain.bo.CustomerAccountStockBO;
import com.minigod.zero.bpmn.module.stock.entity.CustomerAccountStockInfoEntity;

/**
 * 股票增开信息 服务接口
 */
public interface ICustomerAccountStockInfoService extends IService<CustomerAccountStockInfoEntity> {
    void submitInfo(CustomerAccountStockBO bo);
}
