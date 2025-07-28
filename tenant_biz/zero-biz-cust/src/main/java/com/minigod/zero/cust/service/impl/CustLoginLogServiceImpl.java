package com.minigod.zero.cust.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minigod.zero.cust.entity.CustLoginLogEntity;
import com.minigod.zero.cust.mapper.CustLoginLogMapper;
import com.minigod.zero.cust.service.ICustLoginLogService;
import com.minigod.zero.cust.apivo.CustLoginLogVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 登陆日志表 服务实现类
 *
 * @author 掌上智珠
 * @since 2023-03-27
 */
@Service
public class CustLoginLogServiceImpl extends ServiceImpl<CustLoginLogMapper, CustLoginLogEntity> implements ICustLoginLogService {

    @Resource
    private CustLoginLogMapper custLoginLogMapper;

    @Override
    public IPage<CustLoginLogEntity> selectCustLoginLogPage(IPage<CustLoginLogEntity> page, CustLoginLogVO loginLog) {
		return page.setRecords(custLoginLogMapper.selectCustLoginLogPage(page, loginLog));
    }

    @Override
    public CustLoginLogEntity findLastestCustLoginLog(Long custId, List<Integer> typeList, Integer action) {
        LambdaQueryWrapper<CustLoginLogEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CustLoginLogEntity::getCustId, custId);
        queryWrapper.in(CustLoginLogEntity::getType, typeList);
        queryWrapper.eq(CustLoginLogEntity::getAction, action);
        queryWrapper.orderByDesc(CustLoginLogEntity::getCreateTime);
        queryWrapper.last("limit 1");
        return custLoginLogMapper.selectOne(queryWrapper);
    }
}
