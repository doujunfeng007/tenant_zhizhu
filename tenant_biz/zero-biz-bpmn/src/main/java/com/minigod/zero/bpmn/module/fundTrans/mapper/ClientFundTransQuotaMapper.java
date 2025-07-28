package com.minigod.zero.bpmn.module.fundTrans.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.minigod.zero.bpmn.module.fundTrans.domain.ClientFundTransQuota;

import java.util.List;

/**
 *  划拨额度Mapper接口
 *
 * @author zsdp
 * @date 2024-12-25
 */
public interface ClientFundTransQuotaMapper extends BaseMapper<ClientFundTransQuota> {

    /**
     * 查询 划拨额度
     *
     * @param id  划拨额度主键
     * @return  划拨额度
     */
    public ClientFundTransQuota selectClientFundTransQuotaByid(Long id);

    /**
     * 查询 划拨额度列表
     *
     * @param clientFundTransQuota  划拨额度
     * @return  划拨额度集合
     */
    public List<ClientFundTransQuota> selectClientFundTransQuotaList(ClientFundTransQuota clientFundTransQuota);

    /**
     * 新增 划拨额度
     *
     * @param clientFundTransQuota  划拨额度
     * @return 结果
     */
    public int insertClientFundTransQuota(ClientFundTransQuota clientFundTransQuota);

    /**
     * 修改 划拨额度
     *
     * @param clientFundTransQuota  划拨额度
     * @return 结果
     */
    public int updateClientFundTransQuota(ClientFundTransQuota clientFundTransQuota);

    /**
     * 删除 划拨额度
     *
     * @param id  划拨额度主键
     * @return 结果
     */
    public int deleteClientFundTransQuotaByid(Long id);

    /**
     * 批量删除 划拨额度
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteClientFundTransQuotaByids(String[] ids);

}
