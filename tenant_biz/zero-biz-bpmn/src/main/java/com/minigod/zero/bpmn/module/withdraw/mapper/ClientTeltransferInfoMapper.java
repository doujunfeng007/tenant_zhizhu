package com.minigod.zero.bpmn.module.withdraw.mapper;





import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.minigod.zero.bpmn.module.withdraw.entity.ClientTeltransferInfo;

import java.util.List;

/**
 * 客户电汇取款信息Mapper接口
 *
 * @author chenyu
 * @date 2023-04-06
 */
public interface ClientTeltransferInfoMapper extends BaseMapper<ClientTeltransferInfo> {

    /**
     * 查询客户电汇取款信息
     *
     * @param id 客户电汇取款信息主键
     * @return 客户电汇取款信息
     */
    public ClientTeltransferInfo selectClientTeltransferInfoByid(Long id);

    /**
     * 查询客户电汇取款信息列表
     *
     * @param clientTeltransferInfo 客户电汇取款信息
     * @return 客户电汇取款信息集合
     */
    public List<ClientTeltransferInfo> selectClientTeltransferInfoList(ClientTeltransferInfo clientTeltransferInfo);

    /**
     * 新增客户电汇取款信息
     *
     * @param clientTeltransferInfo 客户电汇取款信息
     * @return 结果
     */
    public int insertClientTeltransferInfo(ClientTeltransferInfo clientTeltransferInfo);

    /**
     * 修改客户电汇取款信息
     *
     * @param clientTeltransferInfo 客户电汇取款信息
     * @return 结果
     */
    public int updateClientTeltransferInfo(ClientTeltransferInfo clientTeltransferInfo);

    /**
     * 删除客户电汇取款信息
     *
     * @param id 客户电汇取款信息主键
     * @return 结果
     */
    public int deleteClientTeltransferInfoByid(Long id);

    /**
     * 批量删除客户电汇取款信息
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteClientTeltransferInfoByids(String[] ids);

}
