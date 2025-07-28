package com.minigod.zero.bpmn.module.withdraw.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.minigod.zero.bpmn.module.withdraw.bo.BankAreaBo;
import com.minigod.zero.bpmn.module.withdraw.entity.BankArea;
import com.minigod.zero.bpmn.module.withdraw.mapper.BankAreaMapper;
import com.minigod.zero.bpmn.module.withdraw.service.IBankAreaService;
import com.minigod.zero.core.tool.utils.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 区域Service业务层处理
 *
 * @author chenyu
 * @date 2023-04-20
 */
@RequiredArgsConstructor
@Service
public class BankAreaServiceImpl implements IBankAreaService {

    private final BankAreaMapper baseMapper;


    /**
     * 查询区域列表
     *
     * @param bo 区域
     * @return 区域
     */
    @Override
    public IPage<BankArea> queryPageList( IPage<BankArea> pageQuery,BankAreaBo bo) {
        LambdaQueryWrapper<BankArea> lqw = buildQueryWrapper(bo);
        IPage<BankArea> result = baseMapper.selectPage(pageQuery, lqw);
        return result;
    }


    private LambdaQueryWrapper<BankArea> buildQueryWrapper(BankAreaBo bo) {
        LambdaQueryWrapper<BankArea> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtil.isNotBlank(bo.getName()), BankArea::getName, bo.getName());
        lqw.eq(bo.getPid() != null, BankArea::getPid, bo.getPid());
        lqw.eq(bo.getLevel() != null, BankArea::getLevel, bo.getLevel());
        lqw.eq(bo.getStatus() != null, BankArea::getStatus, bo.getStatus());
        return lqw;
    }

}
