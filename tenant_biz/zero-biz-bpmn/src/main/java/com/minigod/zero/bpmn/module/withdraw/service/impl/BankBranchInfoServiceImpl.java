package com.minigod.zero.bpmn.module.withdraw.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.minigod.zero.bpmn.module.withdraw.bo.BankBranchInfoBo;
import com.minigod.zero.bpmn.module.withdraw.entity.BankBranchInfo;
import com.minigod.zero.bpmn.module.withdraw.mapper.BankBranchInfoMapper;
import com.minigod.zero.bpmn.module.withdraw.service.IBankBranchInfoService;
import com.minigod.zero.core.tool.utils.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 分行信息Service业务层处理
 *
 * @author chenyu
 * @date 2023-04-21
 */
@RequiredArgsConstructor
@Service
public class BankBranchInfoServiceImpl implements IBankBranchInfoService {

    private final BankBranchInfoMapper baseMapper;



    /**
     * 查询分行信息列表
     *
     * @param bo 分行信息
     * @return 分行信息
     */
    @Override
    public IPage<BankBranchInfo> queryPageList(IPage pageQuery, BankBranchInfoBo bo) {
        LambdaQueryWrapper<BankBranchInfo> lqw = buildQueryWrapper(bo);
        IPage<BankBranchInfo> result = baseMapper.selectPage(pageQuery, lqw);
        return result;
    }

    private LambdaQueryWrapper<BankBranchInfo> buildQueryWrapper(BankBranchInfoBo bo) {
        LambdaQueryWrapper<BankBranchInfo> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtil.isNotBlank(bo.getBranchCode()), BankBranchInfo::getBranchCode, bo.getBranchCode());
        lqw.like(StringUtil.isNotBlank(bo.getBranchName()), BankBranchInfo::getBranchName, bo.getBranchName());
        lqw.like(StringUtil.isNotBlank(bo.getAddress()), BankBranchInfo::getAddress, bo.getAddress());
        lqw.like(StringUtil.isNotBlank(bo.getBranchNameSerach()), BankBranchInfo::getBranchName, bo.getBranchNameSerach());
        lqw.eq(bo.getStatus() != null, BankBranchInfo::getStatus, bo.getStatus());
        if(null != bo.getBranchNameList() && bo.getBranchNameList().size() > 0){
            lqw.and(wrapper ->{
                bo.getBranchNameList().stream().filter(branchName->StringUtil.isNotBlank(branchName)).forEach(branchName->{
                    wrapper.and(wq -> wq.like(BankBranchInfo::getBranchName, branchName));
                });
            });
        }
        lqw.orderByAsc(BankBranchInfo::getBranchCode);
        return lqw;
    }

    /**
     * 查询分行信息列表
     *
     * @param bo 分行信息
     * @return 分行信息
     */
    @Override
    public List<BankBranchInfo> queryList(BankBranchInfoBo bo) {
        LambdaQueryWrapper<BankBranchInfo> lqw = buildQueryWrapper(bo);
        return baseMapper.selectList(lqw);
    }


}
