package com.minigod.zero.bpmn.module.withdraw.mapper;





import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.minigod.zero.bpmn.module.withdraw.entity.ClientFundRefundApplication;

import java.util.List;

/**
 * 客户退款申请流程信息Mapper接口
 *
 * @author chenyu
 * @title ClientFundRefundApplicationMapper
 * @date 2023-04-04 20:44
 * @description
 */
public interface ClientFundRefundApplicationMapper extends BaseMapper<ClientFundRefundApplication> {

    /**
     * 查询客户退款申请流程信息
     *
     * @param id 客户退款申请流程信息主键
     * @return 客户退款申请流程信息
     */
    public ClientFundRefundApplication selectClientFundRefundApplicationByid(Long id);

    /**
     * 查询客户退款申请流程信息列表
     *
     * @param clientFundRefundApplication 客户退款申请流程信息
     * @return 客户退款申请流程信息集合
     */
    public List<ClientFundRefundApplication> selectClientFundRefundApplicationList(ClientFundRefundApplication clientFundRefundApplication);

    /**
     * 新增客户退款申请流程信息
     *
     * @param clientFundRefundApplication 客户退款申请流程信息
     * @return 结果
     */
    public int insertClientFundRefundApplication(ClientFundRefundApplication clientFundRefundApplication);

    /**
     * 修改客户退款申请流程信息
     *
     * @param clientFundRefundApplication 客户退款申请流程信息
     * @return 结果
     */
    public int updateClientFundRefundApplication(ClientFundRefundApplication clientFundRefundApplication);

    /**
     * 删除客户退款申请流程信息
     *
     * @param id 客户退款申请流程信息主键
     * @return 结果
     */
    public int deleteClientFundRefundApplicationByid(Long id);

    /**
     * 批量删除客户退款申请流程信息
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteClientFundRefundApplicationByids(String[] ids);

}
