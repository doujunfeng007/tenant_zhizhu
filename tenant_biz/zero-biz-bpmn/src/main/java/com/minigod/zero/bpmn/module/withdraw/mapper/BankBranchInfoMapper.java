package com.minigod.zero.bpmn.module.withdraw.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.minigod.zero.bpmn.module.withdraw.entity.BankBranchInfo;

import java.util.List;

/**
 * 分行信息Mapper接口
 *
 * @author chenyu
 * @date 2023-04-21
 */
public interface BankBranchInfoMapper extends BaseMapper<BankBranchInfo> {

    /**
     * 查询分行信息
     *
     * @param id 分行信息主键
     * @return 分行信息
     */
    public BankBranchInfo selectBankBranchInfoByid(Long id);

    /**
     * 查询分行信息列表
     *
     * @param bankBranchInfo 分行信息
     * @return 分行信息集合
     */
    public List<BankBranchInfo> selectBankBranchInfoList(BankBranchInfo bankBranchInfo);

    /**
     * 新增分行信息
     *
     * @param bankBranchInfo 分行信息
     * @return 结果
     */
    public int insertBankBranchInfo(BankBranchInfo bankBranchInfo);

    /**
     * 修改分行信息
     *
     * @param bankBranchInfo 分行信息
     * @return 结果
     */
    public int updateBankBranchInfo(BankBranchInfo bankBranchInfo);

    /**
     * 删除分行信息
     *
     * @param id 分行信息主键
     * @return 结果
     */
    public int deleteBankBranchInfoByid(Long id);

    /**
     * 批量删除分行信息
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBankBranchInfoByids(String[] ids);

}
