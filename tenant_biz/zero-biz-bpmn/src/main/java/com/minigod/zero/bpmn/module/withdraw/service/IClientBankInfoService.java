package com.minigod.zero.bpmn.module.withdraw.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.minigod.zero.bpmn.module.withdraw.bo.ClientBankInfoBo;
import com.minigod.zero.bpmn.module.withdraw.entity.ClientBankInfo;

import java.util.Collection;
import java.util.List;

/**
 * 银行信息记录Service接口
 *
 * @author zsdp
 * @date 2023-03-24
 */
public interface IClientBankInfoService extends IService<ClientBankInfo> {

    /**
     * 查询银行信息记录
     *
     * @param id 银行信息记录主键
     * @return 银行信息记录
     */
    ClientBankInfo queryById(Long id);

    /**
     * 新建 香港银行信息
     * @param bo ClientBankInfoBo
     */
    Long commitApply(ClientBankInfoBo bo);

    /**
     * 查询银行信息记录列表
     *
     * @param bo 银行信息记录
     * @return 银行信息记录集合
     */
    IPage<ClientBankInfo> queryPageList(IPage pageQuery, ClientBankInfoBo bo);

    /**
     * 查询银行信息记录列表
     *
     * @param bo 银行信息记录
     * @return 银行信息记录集合
     */
    List<ClientBankInfo> queryList(ClientBankInfoBo bo);

    /**
     * 查询银行信息记录
     *
     * @param bo 银行信息记录
     * @return 银行信息记录
     */
    ClientBankInfo queryEntity(ClientBankInfoBo bo);

    /**
     * 修改银行信息记录
     *
     * @param bo 银行信息记录
     * @return 结果
     */
    Boolean insertByBo(ClientBankInfoBo bo);

    /**
     * 修改银行信息记录
     *
     * @param bo 银行信息记录
     * @return 结果
     */
    Boolean updateByBo(ClientBankInfoBo bo);

    /**
     * 校验并批量删除银行信息记录信息
     *
     * @param ids 需要删除的银行信息记录主键集合
     * @param isValid 是否校验,true-删除前校验,false-不校验
     * @return 结果
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
