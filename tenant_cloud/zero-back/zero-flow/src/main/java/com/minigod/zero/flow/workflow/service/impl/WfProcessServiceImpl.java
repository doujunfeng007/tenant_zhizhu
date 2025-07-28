package com.minigod.zero.flow.workflow.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.BetweenFormatter;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.minigod.zero.core.log.exception.ServiceException;
import com.minigod.zero.core.secure.ZeroUser;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.utils.Func;
import com.minigod.zero.flow.core.utils.TaskUtil;
import com.minigod.zero.flow.core.common.constant.TaskConstants;
import com.minigod.zero.flow.flowable.flowable.factory.FlowServiceFactory;
import com.minigod.zero.flow.workflow.domain.bo.WfProcessBo;
import com.minigod.zero.flow.workflow.domain.vo.WfDefinitionVo;
import com.minigod.zero.flow.workflow.domain.vo.WfTaskVo;
import com.minigod.zero.flow.workflow.service.IWfProcessService;
import com.minigod.zero.flow.workflow.service.IWfTaskService;
import com.minigod.zero.flow.workflow.utils.DateUtils;
import com.minigod.zero.user.cache.UserCache;
import com.minigod.zero.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.history.HistoricProcessInstanceQuery;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.repository.ProcessDefinitionQuery;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.flowable.task.api.TaskQuery;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.flowable.task.api.history.HistoricTaskInstanceQuery;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.support.TaskUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author zsdp
 * @createTime 2022/3/24 18:57
 */
@RequiredArgsConstructor
@Service
public class WfProcessServiceImpl extends FlowServiceFactory implements IWfProcessService {

    private final IWfTaskService wfTaskService;

    /**
     * 流程定义列表
     *
     * @param pageQuery 分页参数
     * @return 流程定义分页列表数据
     */
    @Override
    public IPage<WfDefinitionVo> processList(IPage pageQuery) {
        Page<WfDefinitionVo> page = new Page<>();
        // 流程定义列表数据查询
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery()
                .latestVersion()
                .active()
                .processDefinitionTenantId(AuthUtil.getTenantId())
                .orderByProcessDefinitionKey()
                .asc();
        long pageTotal = processDefinitionQuery.count();
        if (pageTotal <= 0) {
            return pageQuery.setRecords(new ArrayList());
        }
        long offset = pageQuery.getSize() * (pageQuery.getCurrent() - 1);
        List<ProcessDefinition> definitionList = processDefinitionQuery.listPage((int) offset, (int) pageQuery.getSize());

        List<WfDefinitionVo> definitionVoList = new ArrayList<>();
        for (ProcessDefinition processDefinition : definitionList) {
            String deploymentId = processDefinition.getDeploymentId();
            Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(deploymentId).singleResult();
            WfDefinitionVo vo = new WfDefinitionVo();
            vo.setDefinitionId(processDefinition.getId());
            vo.setProcessKey(processDefinition.getKey());
            vo.setProcessName(processDefinition.getName());
            vo.setVersion(processDefinition.getVersion());
            vo.setCategory(processDefinition.getCategory());
            vo.setDeploymentId(processDefinition.getDeploymentId());
            vo.setSuspended(processDefinition.isSuspended());
            // 流程定义时间
            vo.setCategory(deployment.getCategory());
            vo.setDeploymentTime(deployment.getDeploymentTime());
            vo.setTenantId(processDefinition.getTenantId());
            definitionVoList.add(vo);
        }
        page.setRecords(definitionVoList);
        page.setTotal(pageTotal);
        return page;
    }

    /**
     * 根据流程定义ID启动流程实例
     *
     * @param procDefId 流程定义Id
     * @param variables 流程变量
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void startProcess(String procDefId, Map<String, Object> variables) {
        try {
            ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                    .processDefinitionId(procDefId).singleResult();
            if (Objects.nonNull(processDefinition) && processDefinition.isSuspended()) {
                throw new ServiceException("流程已被挂起，请先激活流程");
            }
            // 设置流程发起人Id到流程中
            this.buildProcessVariables(variables);
            ProcessInstance processInstance = runtimeService.startProcessInstanceById(procDefId, variables);
            // 给第一步申请人节点设置任务执行人和意见 todo:第一个节点不设置为申请人节点有点问题？
            wfTaskService.startFirstTask(processInstance, variables);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("流程启动错误");
        }
    }

    /**
     * 通过DefinitionKey启动流程
     *
     * @param procDefKey 流程定义Key
     * @param variables  扩展参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ProcessInstance startProcessByDefKey(@NonNull String procDefKey, Map<String, Object> variables) {
        try {
            ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                    .processDefinitionKey(procDefKey).latestVersion().singleResult();
            if (processDefinition != null && processDefinition.isSuspended()) {
                throw new ServiceException("流程已被挂起，请先激活流程");
            }
            this.buildProcessVariables(variables);
            ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(procDefKey, variables);
            return processInstance;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("流程启动错误");
        }
    }

    @Override
    public IPage<WfTaskVo> queryPageOwnProcessList(WfProcessBo bo,IPage pageQuery) {
        Page<WfTaskVo> page = new Page<>();
        HistoricProcessInstanceQuery historicProcessInstanceQuery = historyService.createHistoricProcessInstanceQuery()
                .startedBy(TaskUtil.getTaskUser())
                .processInstanceTenantId(AuthUtil.getTenantId())
                .includeProcessVariables()
                .orderByProcessInstanceStartTime()
                .desc();
        long offset = pageQuery.getSize() * (pageQuery.getCurrent() - 1);
        if(StringUtils.isNotBlank(bo.getApplicationId())){
            historicProcessInstanceQuery.variableValueLike(TaskConstants.APPLICATION_ID,"%"+bo.getApplicationId()+"%");
        }
        List<HistoricProcessInstance> historicProcessInstances = historicProcessInstanceQuery
                .listPage((int) offset, (int) pageQuery.getSize());
        page.setTotal(historicProcessInstanceQuery.count());
        List<WfTaskVo> taskVoList = new ArrayList<>();
        for (HistoricProcessInstance hisIns : historicProcessInstances) {
            WfTaskVo taskVo = new WfTaskVo();
            taskVo.setCreateTime(hisIns.getStartTime());
            taskVo.setFinishTime(hisIns.getEndTime());
            taskVo.setProcInsId(hisIns.getId());
            // 计算耗时
            if (Objects.nonNull(hisIns.getEndTime())) {
                taskVo.setDuration(DateUtils.getDatePoor(hisIns.getEndTime(), hisIns.getStartTime()));
            } else {
                taskVo.setDuration(DateUtils.getDatePoor(DateUtils.getNowDate(), hisIns.getStartTime()));
            }
            // 流程部署实例信息
            Deployment deployment = repositoryService.createDeploymentQuery()
                    .deploymentId(hisIns.getDeploymentId()).singleResult();
            taskVo.setDeployId(hisIns.getDeploymentId());
            taskVo.setProcDefId(hisIns.getProcessDefinitionId());
            taskVo.setProcDefName(hisIns.getProcessDefinitionName());
            taskVo.setProcDefKey(hisIns.getProcessDefinitionKey());
            taskVo.setProcDefVersion(hisIns.getProcessDefinitionVersion());
            taskVo.setCategory(deployment.getCategory());
            // 当前所处流程
            List<Task> taskList = taskService.createTaskQuery()
                    .taskTenantId(AuthUtil.getTenantId())
                    .processInstanceId(hisIns.getId()).list();
            if (CollUtil.isNotEmpty(taskList)) {
                taskVo.setTenantId(taskList.get(0).getTenantId());
                taskVo.setTaskId(taskList.get(0).getId());
                taskVo.setTaskName(taskList.get(0).getName());
                taskVo.setTaskDefKey(taskList.get(0).getTaskDefinitionKey());
                taskVo.setApplicationId((String)getProcessVariables(taskList.get(0).getId()).get(TaskConstants.APPLICATION_ID));
                if (StringUtils.isNotBlank(taskList.get(0).getAssignee())) {
                    User sysUser = UserCache.getUser(TaskUtil.getUserId(taskList.get(0).getAssignee()));
                    taskVo.setAssigneeName(sysUser.getName());
                    taskVo.setAssigneeId(sysUser.getId());
                    /*taskVo.setDeptName(sysUser.getDept().getDeptName());*/
                }
            } else {
                List<HistoricTaskInstance> historicTaskInstance = historyService.createHistoricTaskInstanceQuery()
                        .processInstanceId(hisIns.getId()).orderByHistoricTaskInstanceEndTime().desc().list();
                taskVo.setTenantId(historicTaskInstance.get(0).getTenantId());
                taskVo.setTaskId(historicTaskInstance.get(0).getId());
                taskVo.setTaskName(historicTaskInstance.get(0).getName());
                taskVo.setTaskDefKey(historicTaskInstance.get(0).getTaskDefinitionKey());
                taskVo.setApplicationId((String) historicTaskInstance.get(0).getProcessVariables().get(TaskConstants.APPLICATION_ID));
                if (StringUtils.isNotBlank(historicTaskInstance.get(0).getAssignee())) {
                    User sysUser = UserCache.getUser(TaskUtil.getUserId(historicTaskInstance.get(0).getAssignee()));
                    taskVo.setAssigneeName(sysUser.getName());
                    taskVo.setAssigneeId(sysUser.getId());
					/*taskVo.setDeptName(sysUser.getDept().getDeptName());*/
                }
            }
            taskVoList.add(taskVo);
        }
        page.setRecords(taskVoList);
        return page;
    }

    @Override
    public IPage<WfTaskVo> queryPageTodoProcessList(WfProcessBo bo,IPage pageQuery) {
        Page<WfTaskVo> page = new Page<>();
        TaskQuery taskQuery = taskService.createTaskQuery()
                .active()
                .includeProcessVariables()
                .taskTenantId(AuthUtil.getTenantId())
                .taskCandidateOrAssigned(TaskUtil.getTaskUser())
                .taskCandidateGroupIn(Func.toStrList(TaskUtil.getCandidateGroup()))
                .orderByTaskCreateTime().desc();
        if(StringUtils.isNotBlank(bo.getApplicationId())){
            taskQuery.processVariableValueLike(TaskConstants.APPLICATION_ID,"%"+bo.getApplicationId()+"%");
        }
        page.setTotal(taskQuery.count());
        long offset = pageQuery.getSize() * (pageQuery.getCurrent() - 1);
        List<Task> taskList = taskQuery.listPage((int) offset, (int) pageQuery.getSize());
        List<WfTaskVo> flowList = new ArrayList<>();
        for (Task task : taskList) {
            WfTaskVo flowTask = new WfTaskVo();
            // 当前流程信息
            flowTask.setTenantId(task.getTenantId());
            flowTask.setTaskId(task.getId());
            flowTask.setTaskDefKey(task.getTaskDefinitionKey());
            flowTask.setCreateTime(task.getCreateTime());
            flowTask.setProcDefId(task.getProcessDefinitionId());
            flowTask.setTaskName(task.getName());
            // 流程定义信息
            ProcessDefinition pd = repositoryService.createProcessDefinitionQuery()
                    .processDefinitionId(task.getProcessDefinitionId())
                    .singleResult();
            flowTask.setDeployId(pd.getDeploymentId());
            flowTask.setProcDefName(pd.getName());
            flowTask.setProcDefKey(pd.getKey());
            flowTask.setProcDefVersion(pd.getVersion());
            flowTask.setProcInsId(task.getProcessInstanceId());
            flowTask.setApplicationId((String) task.getProcessVariables().get(TaskConstants.APPLICATION_ID));

            // 流程发起人信息
            HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
                    .processInstanceId(task.getProcessInstanceId())
                    .singleResult();
            if (historicProcessInstance != null) {
                if (historicProcessInstance.getStartUserId() != null) {
                    if (StringUtils.isNotBlank(historicProcessInstance.getStartUserId())) {
                        User user = UserCache.getUser(TaskUtil.getUserId(historicProcessInstance.getStartUserId()));
                        if (user != null) {
                            flowTask.setStartUserId(user.getName());
                            flowTask.setStartUserName(user.getName());
							/*flowTask.setStartDeptName(startUser.getDept().getDeptName());*/
                        }

                    }
                }
            }
            // 流程变量
            flowTask.setProcVars(this.getProcessVariables(task.getId()));

            flowList.add(flowTask);
        }
        page.setRecords(flowList);
        return page;
    }

    @Override
    public IPage<WfTaskVo> queryPageClaimProcessList(WfProcessBo processBo, IPage pageQuery) {
        Page<WfTaskVo> page = new Page<>();
        TaskQuery taskQuery = taskService.createTaskQuery()
                .active()
                .includeProcessVariables()
                .taskCandidateUser(TaskUtil.getTaskUser())
                .taskTenantId(AuthUtil.getTenantId())
                .taskCandidateGroupIn(Func.toStrList(TaskUtil.getCandidateGroup())) //用户组
                .orderByTaskCreateTime().desc();
        if (StringUtils.isNotBlank(processBo.getProcessName())) {
            taskQuery.processDefinitionNameLike("%" + processBo.getProcessName() + "%");
        }
        if (StringUtils.isNotBlank(processBo.getApplicationId())) {
            taskQuery.processVariableValueLike(TaskConstants.APPLICATION_ID,"%" + processBo.getApplicationId() + "%");
        }

        page.setTotal(taskQuery.count());
        long offset = pageQuery.getSize() * (pageQuery.getCurrent() - 1);
        List<Task> taskList = taskQuery.listPage((int) offset, (int) pageQuery.getSize());
        List<WfTaskVo> flowList = new ArrayList<>();
        for (Task task : taskList) {
            WfTaskVo flowTask = new WfTaskVo();
            // 当前流程信息
            flowTask.setTenantId(task.getTenantId());
            flowTask.setTaskId(task.getId());
            flowTask.setTaskDefKey(task.getTaskDefinitionKey());
            flowTask.setCreateTime(task.getCreateTime());
            flowTask.setProcDefId(task.getProcessDefinitionId());
            flowTask.setTaskName(task.getName());
            // 流程定义信息
            ProcessDefinition pd = repositoryService.createProcessDefinitionQuery()
                    .processDefinitionId(task.getProcessDefinitionId())
                    .singleResult();
            flowTask.setDeployId(pd.getDeploymentId());
            flowTask.setProcDefName(pd.getName());
            flowTask.setProcDefKey(pd.getKey());
            flowTask.setProcDefVersion(pd.getVersion());
            flowTask.setProcInsId(task.getProcessInstanceId());
            flowTask.setApplicationId((String) task.getProcessVariables().get(TaskConstants.APPLICATION_ID));
            // 流程发起人信息
            HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
                    .processInstanceId(task.getProcessInstanceId())
                    .singleResult();
            if (historicProcessInstance != null) {
                if (historicProcessInstance.getStartUserId() != null) {
                    User user = UserCache.getUser(TaskUtil.getUserId(historicProcessInstance.getStartUserId()));
                    if (user != null) {
                        flowTask.setStartUserId(user.getName());
                        flowTask.setStartUserName(user.getName());
                        /*flowTask.setStartDeptName(startUser.getDept().getDeptName());*/
                    }
                }
            }
            flowList.add(flowTask);
        }
        page.setRecords(flowList);
        return page;
    }

    @Override
    public IPage<WfTaskVo> queryPageFinishedProcessList(WfProcessBo bo ,IPage pageQuery) {
        Page<WfTaskVo> page = new Page<>();
        Long userId = AuthUtil.getUserId();
        HistoricTaskInstanceQuery taskInstanceQuery = historyService.createHistoricTaskInstanceQuery()
                .includeProcessVariables()
                .finished()
                .taskAssignee(userId.toString())
                .orderByHistoricTaskInstanceEndTime()
                .desc();
        long offset = pageQuery.getSize() * (pageQuery.getCurrent() - 1);
        if(StringUtils.isNotBlank(bo.getApplicationId())){
            taskInstanceQuery.processVariableValueLike(TaskConstants.APPLICATION_ID,"%"+bo.getApplicationId()+"%");
        }
        List<HistoricTaskInstance> historicTaskInstanceList = taskInstanceQuery.listPage((int) offset, (int) pageQuery.getSize());
        List<WfTaskVo> hisTaskList = Lists.newArrayList();
        for (HistoricTaskInstance histTask : historicTaskInstanceList) {
            WfTaskVo flowTask = new WfTaskVo();
            // 当前流程信息
            flowTask.setTenantId(histTask.getTenantId());
            flowTask.setTaskId(histTask.getId());
            // 审批人员信息
            flowTask.setCreateTime(histTask.getCreateTime());
            flowTask.setFinishTime(histTask.getEndTime());
            flowTask.setDuration(DateUtil.formatBetween(histTask.getDurationInMillis(), BetweenFormatter.Level.SECOND));
            flowTask.setProcDefId(histTask.getProcessDefinitionId());
            flowTask.setTaskDefKey(histTask.getTaskDefinitionKey());
            flowTask.setTaskName(histTask.getName());

            // 流程定义信息
            ProcessDefinition pd = repositoryService.createProcessDefinitionQuery()
                    .processDefinitionId(histTask.getProcessDefinitionId())
                    .singleResult();
            flowTask.setDeployId(pd.getDeploymentId());
            flowTask.setProcDefName(pd.getName());
            flowTask.setProcDefKey(pd.getKey());
            flowTask.setProcDefVersion(pd.getVersion());
            flowTask.setProcInsId(histTask.getProcessInstanceId());
            flowTask.setHisProcInsId(histTask.getProcessInstanceId());

            // 流程发起人信息
            HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
                    .processInstanceId(histTask.getProcessInstanceId())
                    .singleResult();
            if (StringUtils.isNotBlank(historicProcessInstance.getStartUserId())) {
                User user = UserCache.getUser(TaskUtil.getUserId(historicProcessInstance.getStartUserId()));
                if (user != null) {
                    flowTask.setStartUserId(user.getName());
                    flowTask.setStartUserName(user.getName());

                }
            }

            // 流程变量
            flowTask.setProcVars(this.getProcessVariables(histTask.getId()));

            hisTaskList.add(flowTask);
        }
        page.setTotal(taskInstanceQuery.count());
        page.setRecords(hisTaskList);
        return page;
    }

    /**
     * 扩展参数构建
     *
     * @param variables 扩展参数
     */
    private void buildProcessVariables(Map<String, Object> variables) {
        String userIdStr = "1";
        identityService.setAuthenticatedUserId(userIdStr);
        variables.put(TaskConstants.PROCESS_INITIATOR, userIdStr);
    }


    /**
     * 获取流程变量
     *
     * @param taskId 任务ID
     * @return 流程变量
     */
    private Map<String, Object> getProcessVariables(String taskId) {
        HistoricTaskInstance historicTaskInstance = historyService.createHistoricTaskInstanceQuery()
                .includeProcessVariables()
                .finished()
                .taskId(taskId)
                .singleResult();
        if (Objects.nonNull(historicTaskInstance)) {
            return historicTaskInstance.getProcessVariables();
        }
        return taskService.getVariables(taskId);
    }
}
