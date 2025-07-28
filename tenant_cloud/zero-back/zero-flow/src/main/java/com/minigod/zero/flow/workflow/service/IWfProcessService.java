package com.minigod.zero.flow.workflow.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.flow.workflow.domain.bo.WfProcessBo;
import com.minigod.zero.flow.workflow.domain.vo.WfDefinitionVo;
import com.minigod.zero.flow.workflow.domain.vo.WfTaskVo;
import org.flowable.engine.runtime.ProcessInstance;

import java.util.Map;

/**
 * @author zsdp
 * @createTime 2022/3/24 18:57
 */
public interface IWfProcessService {

    /**
     * 查询可发起流程列表
     * @param pageIPage 分页参数
     * @return
     */
    IPage<WfDefinitionVo> processList(IPage pageIPage);

    /**
     * 启动流程实例
     * @param procDefId 流程定义ID
     * @param variables 扩展参数
     */
    void startProcess(String procDefId, Map<String, Object> variables);

    /**
     * 通过DefinitionKey启动流程
     * @param procDefKey 流程定义Key
     * @param variables 扩展参数
     */
    ProcessInstance startProcessByDefKey(String procDefKey, Map<String, Object> variables);

    /**
     * 查询我的流程列表
     * @param pageIPage 分页参数
     */
    IPage<WfTaskVo> queryPageOwnProcessList(WfProcessBo bo,IPage pageIPage);

    /**
     * 查询代办任务列表
     * @param pageIPage 分页参数
     */
    IPage<WfTaskVo> queryPageTodoProcessList(WfProcessBo processBo,IPage pageIPage);

    /**
     * 查询待签任务列表
     * @param pageIPage 分页参数
     */
    IPage<WfTaskVo> queryPageClaimProcessList(WfProcessBo processBo, IPage pageIPage);

    /**
     * 查询已办任务列表
     * @param pageIPage 分页参数
     */
    IPage<WfTaskVo> queryPageFinishedProcessList(WfProcessBo processBo,IPage pageIPage);
}
