package com.minigod.zero.bpmn.module.withdraw.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minigod.zero.bpmn.module.withdraw.bo.DbsRemitReqLogBo;
import com.minigod.zero.bpmn.module.withdraw.entity.DbsRemitReqLog;
import com.minigod.zero.bpmn.module.withdraw.mapper.DbsRemitReqLogMapper;
import com.minigod.zero.bpmn.module.withdraw.service.IDbsRemitReqLogService;
import com.minigod.zero.core.tool.utils.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * DBS汇款请求日志Service业务层处理
 *
 * @author chenyu
 * @title DbsRemitReqLogServiceImpl
 * @date 2023-04-04 21:03
 * @description
 */
@RequiredArgsConstructor
@Service
public class DbsRemitReqLogServiceImpl extends ServiceImpl<DbsRemitReqLogMapper, DbsRemitReqLog> implements IDbsRemitReqLogService {

    /**
     * 查询DBS汇款请求日志
     *
     * @param id DBS汇款请求日志主键
     * @return DBS汇款请求日志
     */
//    @Override
//    public DbsRemitReqLog queryById(Long id){
//        return baseMapper.selectById(id);
//    }

    /**
     * 查询DBS汇款请求日志列表
     *
     * @param bo DBS汇款请求日志
     * @return DBS汇款请求日志
     */
    @Override
    public IPage<DbsRemitReqLog> queryPageList(DbsRemitReqLogBo bo, IPage pageQuery) {
        LambdaQueryWrapper<DbsRemitReqLog> lqw = buildQueryWrapper(bo);
        IPage<DbsRemitReqLog> result = baseMapper.selectPage(pageQuery, lqw);
        return result;
    }

    /**
     * 查询DBS汇款请求日志列表
     *
     * @param bo DBS汇款请求日志
     * @return DBS汇款请求日志
     */
    @Override
    public List<DbsRemitReqLog> queryList(DbsRemitReqLogBo bo) {
        LambdaQueryWrapper<DbsRemitReqLog> lqw = buildQueryWrapper(bo);
        return baseMapper.selectList(lqw);
    }

    private LambdaQueryWrapper<DbsRemitReqLog> buildQueryWrapper(DbsRemitReqLogBo bo) {
        LambdaQueryWrapper<DbsRemitReqLog> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtil.isNotBlank(bo.getApplicationId()), DbsRemitReqLog::getApplicationId, bo.getApplicationId());
        lqw.eq(StringUtil.isNotBlank(bo.getTxnType()), DbsRemitReqLog::getTxnType, bo.getTxnType());
        lqw.eq(StringUtil.isNotBlank(bo.getMsgId()), DbsRemitReqLog::getMsgId, bo.getMsgId());
        lqw.eq(StringUtil.isNotBlank(bo.getTxnRefId()), DbsRemitReqLog::getTxnRefId, bo.getTxnRefId());
        lqw.eq(StringUtil.isNotBlank(bo.getCusRef()), DbsRemitReqLog::getCusRef, bo.getCusRef());
        lqw.eq(StringUtil.isNotBlank(bo.getDdaRef()), DbsRemitReqLog::getDdaRef, bo.getDdaRef());
        lqw.eq(StringUtil.isNotBlank(bo.getReqStatus()), DbsRemitReqLog::getReqStatus, bo.getReqStatus());
        lqw.eq(StringUtil.isNotBlank(bo.getEnqStatus()), DbsRemitReqLog::getEnqStatus, bo.getEnqStatus());
        lqw.eq(StringUtil.isNotBlank(bo.getEnqType()), DbsRemitReqLog::getEnqType, bo.getEnqType());
        lqw.eq(StringUtil.isNotBlank(bo.getRejCode()), DbsRemitReqLog::getRejCode, bo.getRejCode());
        lqw.eq(StringUtil.isNotBlank(bo.getRejDescription()), DbsRemitReqLog::getRejDescription, bo.getRejDescription());
        lqw.eq(StringUtil.isNotBlank(bo.getReqMessage()), DbsRemitReqLog::getReqMessage, bo.getReqMessage());
        lqw.eq(bo.getReqTime() != null, DbsRemitReqLog::getReqTime, bo.getReqTime());
        lqw.eq(StringUtil.isNotBlank(bo.getAck1Message()), DbsRemitReqLog::getAck1Message, bo.getAck1Message());
        lqw.eq(bo.getAck1Time() != null, DbsRemitReqLog::getAck1Time, bo.getAck1Time());
        lqw.eq(StringUtil.isNotBlank(bo.getAck2Message()), DbsRemitReqLog::getAck2Message, bo.getAck2Message());
        lqw.eq(bo.getAck2Time() != null, DbsRemitReqLog::getAck2Time, bo.getAck2Time());
        lqw.eq(StringUtil.isNotBlank(bo.getAck3Message()), DbsRemitReqLog::getAck3Message, bo.getAck3Message());
        lqw.eq(bo.getAck3Time() != null, DbsRemitReqLog::getAck3Time, bo.getAck3Time());
        return lqw;
    }

//    /**
//     * 新增DBS汇款请求日志
//     *
//     * @param bo DBS汇款请求日志
//     * @return 结果
//     */
//    @Override
//    public Boolean insertByBo(DbsRemitReqLogBo bo) {
//        DbsRemitReqLog add = BeanUtil.toBean(bo, DbsRemitReqLog.class);
//        validEntityBeforeSave(add);
//        add.setCreateTime(new Date());
//        boolean flag = baseMapper.insert(add) > 0;
//        if (flag) {
//            bo.setId(add.getId());
//        }
//        return flag;
//    }
//
//    /**
//     * 修改DBS汇款请求日志
//     *
//     * @param bo DBS汇款请求日志
//     * @return 结果
//     */
//    @Override
//    public Boolean updateUserBo(DbsRemitReqLogBo bo) {
//        DbsRemitReqLog update = BeanUtil.toBean(bo, DbsRemitReqLog.class);
//        validEntityBeforeSave(update);
//        update.setUpdateTime(new Date());
//        return baseMapper.updateById(update) > 0;
//    }

    /**
     * 保存前的数据校验
     *
     * @param entity 实体类数据
     */
    private void validEntityBeforeSave(DbsRemitReqLog entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除DBS汇款请求日志
     *
     * @param ids 需要删除的DBS汇款请求日志主键
     * @return 结果
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    @Override
    public DbsRemitReqLog selectOne(DbsRemitReqLogBo getDbsRemitReq) {
        LambdaQueryWrapper<DbsRemitReqLog> lqw = Wrappers.lambdaQuery();
        lqw.last("limit 1");
        return baseMapper.selectOne(lqw);
    }

//    @Override
//    public DbsRemitReqLogVo queryVoById(Long reqLogId) {
//        DbsRemitReqLog log =  baseMapper.selectById(reqLogId);
//        if(ObjectUtil.isNotEmpty(log)){
//            DbsRemitReqLogVo vo = BeanUtil.toBean(log, DbsRemitReqLogVo.class);
//            return vo;
//        }
//        return null;
//    }
}
