package com.minigod.zero.bpmn.module.bank.service;

import java.util.List;

import com.minigod.zero.bpmn.module.bank.entity.DbsCompanyDepositInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @ClassName: DbsCompanyDepositInfoService
 * @Description: ${description}
 * @Author chenyu
 * @Date 2024/3/7
 * @Version 1.0
 */
public interface DbsCompanyDepositInfoService extends IService<DbsCompanyDepositInfo> {


    int batchInsert(List<DbsCompanyDepositInfo> list);

    /**
     * 查询租户的 DBS入金信息
     *
     * @param tenantId
     * @return
     */
    List<DbsCompanyDepositInfo> queryInfoByTenantId(String tenantId);

}
