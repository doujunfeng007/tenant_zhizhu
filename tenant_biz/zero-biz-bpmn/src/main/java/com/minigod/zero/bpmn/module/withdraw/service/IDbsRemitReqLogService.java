package com.minigod.zero.bpmn.module.withdraw.service;






import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.minigod.zero.bpmn.module.withdraw.bo.DbsRemitReqLogBo;
import com.minigod.zero.bpmn.module.withdraw.entity.DbsRemitReqLog;

import java.util.Collection;
import java.util.List;

/**
 * todo
 *
 * @author chenyu
 * @title IDbsRemitReqLogService
 * @date 2023-04-04 21:02
 * @description
 */
public interface IDbsRemitReqLogService extends IService<DbsRemitReqLog> {

//    /**
//     * 查询DBS汇款请求日志
//     *
//     * @param id DBS汇款请求日志主键
//     * @return DBS汇款请求日志
//     */
//    DbsRemitReqLog queryById(Long id);

    /**
     * 查询DBS汇款请求日志列表
     *
     * @param bo DBS汇款请求日志
     * @return DBS汇款请求日志集合
     */
    IPage<DbsRemitReqLog> queryPageList(DbsRemitReqLogBo bo, IPage pageQuery);

    /**
     * 查询DBS汇款请求日志列表
     *
     * @param bo DBS汇款请求日志
     * @return DBS汇款请求日志集合
     */
    List<DbsRemitReqLog> queryList(DbsRemitReqLogBo bo);

//    /**
//     * 修改DBS汇款请求日志
//     *
//     * @param bo DBS汇款请求日志
//     * @return 结果
//     */
//    Boolean insertByBo(DbsRemitReqLogBo bo);
//
//    /**
//     * 修改DBS汇款请求日志
//     *
//     * @param bo DBS汇款请求日志
//     * @return 结果
//     */
//    Boolean updateUserBo(DbsRemitReqLogBo bo);

    /**
     * 校验并批量删除DBS汇款请求日志信息
     *
     * @param ids 需要删除的DBS汇款请求日志主键集合
     * @param isValid 是否校验,true-删除前校验,false-不校验
     * @return 结果
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    DbsRemitReqLog selectOne(DbsRemitReqLogBo getDbsRemitReq);

//    DbsRemitReqLogVo queryVoById(Long reqLogId);
}
