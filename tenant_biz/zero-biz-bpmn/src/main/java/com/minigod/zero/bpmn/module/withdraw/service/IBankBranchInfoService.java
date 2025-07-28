package com.minigod.zero.bpmn.module.withdraw.service;

import com.baomidou.mybatisplus.core.metadata.IPage;




import com.minigod.zero.bpmn.module.withdraw.bo.BankBranchInfoBo;
import com.minigod.zero.bpmn.module.withdraw.entity.BankBranchInfo;

import java.util.List;

/**
 * 分行信息Service接口
 *
 * @author chenyu
 * @date 2023-04-21
 */
public interface IBankBranchInfoService {

    /**
     * 查询分行信息列表
     *
     * @param bo 分行信息
     * @return 分行信息集合
     */
    IPage<BankBranchInfo> queryPageList(IPage pageQuery, BankBranchInfoBo bo);

    /**
     * 查询分行信息列表
     *
     * @param bo 分行信息
     * @return 分行信息集合
     */
    List<BankBranchInfo> queryList(BankBranchInfoBo bo);


}
