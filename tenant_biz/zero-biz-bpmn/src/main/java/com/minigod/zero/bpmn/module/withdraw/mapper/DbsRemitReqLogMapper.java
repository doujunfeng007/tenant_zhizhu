package com.minigod.zero.bpmn.module.withdraw.mapper;





import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.minigod.zero.bpmn.module.withdraw.entity.DbsRemitReqLog;

import java.util.List;

/**
 * DBS汇款请求日志Mapper接口
 *
 * @author chenyu
 * @title DbsRemitReqLogMapper
 * @date 2023-04-04 21:02
 * @description
 */
public interface DbsRemitReqLogMapper extends BaseMapper<DbsRemitReqLog> {

    /**
     * 查询DBS汇款请求日志
     *
     * @param id DBS汇款请求日志主键
     * @return DBS汇款请求日志
     */
    public DbsRemitReqLog selectDbsRemitReqLogByid(Long id);

    /**
     * 查询DBS汇款请求日志列表
     *
     * @param dbsRemitReqLog DBS汇款请求日志
     * @return DBS汇款请求日志集合
     */
    public List<DbsRemitReqLog> selectDbsRemitReqLogList(DbsRemitReqLog dbsRemitReqLog);

    /**
     * 新增DBS汇款请求日志
     *
     * @param dbsRemitReqLog DBS汇款请求日志
     * @return 结果
     */
    public int insertDbsRemitReqLog(DbsRemitReqLog dbsRemitReqLog);

    /**
     * 修改DBS汇款请求日志
     *
     * @param dbsRemitReqLog DBS汇款请求日志
     * @return 结果
     */
    public int updateDbsRemitReqLog(DbsRemitReqLog dbsRemitReqLog);

    /**
     * 删除DBS汇款请求日志
     *
     * @param id DBS汇款请求日志主键
     * @return 结果
     */
    public int deleteDbsRemitReqLogByid(Long id);

    /**
     * 批量删除DBS汇款请求日志
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDbsRemitReqLogByids(String[] ids);

}
