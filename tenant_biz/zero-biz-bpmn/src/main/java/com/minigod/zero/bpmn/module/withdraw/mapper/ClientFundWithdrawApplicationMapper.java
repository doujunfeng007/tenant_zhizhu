package com.minigod.zero.bpmn.module.withdraw.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.bpmn.module.withdraw.bo.ClientFundWithdrawApplicationBo;
import com.minigod.zero.bpmn.module.withdraw.bo.WithdrawInfoDTO;
import com.minigod.zero.bpmn.module.withdraw.entity.ClientFundWithdrawApplication;
import com.minigod.zero.bpmn.module.withdraw.vo.*;
import com.minigod.zero.core.mp.support.Query;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 客户出金申请流程信息Mapper接口
 * @author chenyu
 * @title ClientFundWithdrawApplicationMapper
 * @date 2023-04-04 20:23
 * @description
 */
public interface ClientFundWithdrawApplicationMapper extends BaseMapper<ClientFundWithdrawApplication> {


    /**
     * 分页查询客户取款信息
     * @param query
     * @param page
     * @return
     */
    IPage<ClientFundWithdrawApplicationVo> queryPageList(IPage page, @Param("query") ClientFundWithdrawApplicationBo query);

    /**
     * 分页查询客户取款信息
     * @param query
     * @param page
     * @return
     */
    IPage<ClientFundWithdrawApplicationVo> queryDetailPageList(IPage page ,@Param("query") ClientFundWithdrawApplicationBo query);

    List<ClientFundWithdrawApplicationVo> queryDetailList(@Param("query") ClientFundWithdrawApplicationBo bo);


    /**
     * 分页查询客户取款信息
     * @param query
     * @param page
     * @return
     */
    IPage<ClientFundWithdrawApplicationVo> queryRefundPageList(IPage page,@Param("query") ClientFundWithdrawApplicationBo query);

    /**
     * 查询客户出金申请流程信息
     *
     * @param id 客户出金申请流程信息主键
     * @return 客户出金申请流程信息
     */
    public ClientFundWithdrawApplication selectClientFundWithdrawApplicationByid(Long id);

    /**
     * 查询客户出金申请流程信息列表
     *
     * @param clientFundWithdrawApplication 客户出金申请流程信息
     * @return 客户出金申请流程信息集合
     */
    public List<ClientFundWithdrawApplication> selectClientFundWithdrawApplicationList(ClientFundWithdrawApplication clientFundWithdrawApplication);

    /**
     * 新增客户出金申请流程信息
     *
     * @param clientFundWithdrawApplication 客户出金申请流程信息
     * @return 结果
     */
    public int insertClientFundWithdrawApplication(ClientFundWithdrawApplication clientFundWithdrawApplication);

    /**
     * 修改客户出金申请流程信息
     *
     * @param clientFundWithdrawApplication 客户出金申请流程信息
     * @return 结果
     */
    public int updateClientFundWithdrawApplication(ClientFundWithdrawApplication clientFundWithdrawApplication);

    /**
     * 删除客户出金申请流程信息
     *
     * @param id 客户出金申请流程信息主键
     * @return 结果
     */
    public int deleteClientFundWithdrawApplicationByid(Long id);

    /**
     * 批量删除客户出金申请流程信息
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteClientFundWithdrawApplicationByids(String[] ids);

    /**
     * 统计银行取款报表
     *
     * @param clientFundWithdrawApplicationBo
     * @return
     */
    List<WithdrawlBankSummaryVo> queryBankSummaryReportInfo(ClientFundWithdrawApplicationBo clientFundWithdrawApplicationBo);

    ClientFundWithdrawApplicationVo queryByApplicationId(@Param("query") ClientFundWithdrawApplicationBo query);

	List<WithdrawInfoVO> withdrawalPageList(@Param("param") WithdrawInfoDTO param, IPage page);

	List<WithdrawRefundVO> withdrawalRefundPageList(@Param("param") WithdrawInfoDTO param, IPage page);

	List<WithdrawRefundVO> withdrawalFailPageList(@Param("param") WithdrawInfoDTO param, IPage page);

	WithdrawDetailVO withdrawalDetail(String applicationId);

	WithdrawDetailVO withdrawalRefundDetail(String applicationId);
}
