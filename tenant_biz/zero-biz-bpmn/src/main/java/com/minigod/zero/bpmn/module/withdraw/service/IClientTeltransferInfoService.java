package com.minigod.zero.bpmn.module.withdraw.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.bpmn.module.withdraw.bo.ClientTeltransferInfoBo;
import com.minigod.zero.bpmn.module.withdraw.entity.ClientTeltransferInfo;

import java.util.Collection;
import java.util.List;

/**
 * 客户电汇取款信息Service接口
 *
 * @author chenyu
 * @date 2023-04-06
 */
public interface IClientTeltransferInfoService {

    /**
     * 查询客户电汇取款信息
     *
     * @param id 客户电汇取款信息主键
     * @return 客户电汇取款信息
     */
    ClientTeltransferInfo queryById(Long id);

    /**
     * 查询客户电汇取款信息列表
     *
     * @param bo 客户电汇取款信息
     * @return 客户电汇取款信息集合
     */
    IPage<ClientTeltransferInfo> queryPageList(ClientTeltransferInfoBo bo, IPage pageQuery);

    /**
     * 查询客户电汇取款信息列表
     *
     * @param bo 客户电汇取款信息
     * @return 客户电汇取款信息集合
     */
    List<ClientTeltransferInfo> queryList(ClientTeltransferInfoBo bo);

    /**
     * 查询客户电汇取款信息列表
     *
     * @param bo 客户电汇取款信息
     * @return 客户电汇取款信息
     */
    ClientTeltransferInfo queryEntity(ClientTeltransferInfoBo bo);

    /**
     * 查询客户电汇取款信息列表
     *
     * @param applicationId 流水号
     * @return 客户出金申请
     */
    ClientTeltransferInfo queryByApplicationId(String applicationId);

    /**
     * 修改客户电汇取款信息
     *
     * @param bo 客户电汇取款信息
     * @return 结果
     */
    Boolean insertByBo(ClientTeltransferInfoBo bo);

    /**
     * 修改客户电汇取款信息
     *
     * @param bo 客户电汇取款信息
     * @return 结果
     */
    Boolean updateUserBo(ClientTeltransferInfoBo bo);

    /**
     * 校验并批量删除客户电汇取款信息信息
     *
     * @param ids     需要删除的客户电汇取款信息主键集合
     * @param isValid 是否校验,true-删除前校验,false-不校验
     * @return 结果
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
