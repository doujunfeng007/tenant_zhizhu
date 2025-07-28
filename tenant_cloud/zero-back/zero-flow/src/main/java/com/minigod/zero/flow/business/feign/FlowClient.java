
package com.minigod.zero.flow.business.feign;

import cn.hutool.core.util.ObjectUtil;
import com.minigod.zero.bpmn.module.feign.IBpmnFlowClient;
import com.minigod.zero.core.log.exception.ServiceException;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.flow.core.common.constant.TaskConstants;
import com.minigod.zero.flow.core.common.enums.FlowComment;
import com.minigod.zero.flow.core.entity.FlowTable;
import com.minigod.zero.flow.core.entity.TaskBo;
import com.minigod.zero.flow.core.utils.TaskUtil;
import com.minigod.zero.flow.flowable.flowable.flow.FlowableUtils;
import lombok.AllArgsConstructor;
import org.flowable.bpmn.model.EndEvent;
import org.flowable.bpmn.model.FlowElement;
import org.flowable.bpmn.model.Process;
import org.flowable.bpmn.model.UserTask;
import org.flowable.common.engine.api.FlowableException;
import org.flowable.common.engine.api.FlowableObjectNotFoundException;
import org.flowable.editor.language.json.converter.util.CollectionUtils;
import org.flowable.engine.*;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.Execution;
import org.flowable.engine.runtime.ProcessInstance;
import com.minigod.zero.core.tenant.annotation.NonDS;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.support.Kv;
import com.minigod.zero.core.tool.utils.Func;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.minigod.zero.flow.core.entity.ZeroFlow;
import com.minigod.zero.flow.core.feign.IFlowClient;
import org.flowable.task.api.Task;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 流程远程调用实现类
 *
 * @author Chill
 */
@NonDS
@RestController
@AllArgsConstructor
public class FlowClient implements IFlowClient {

    private final RuntimeService runtimeService;
    private final IdentityService identityService;
    private final TaskService taskService;
    private final RepositoryService repositoryService;
    private final HistoryService historyService;
    private final IBpmnFlowClient bpmnFlowClient;

    @Override
    @PostMapping(START_PROCESS_INSTANCE_BY_ID)
    @Transactional(rollbackFor = Exception.class)
    public R<ZeroFlow> startProcessInstanceById(String processDefinitionId, @RequestBody Map<String, Object> variables) {
        checkVariables(variables);
        // 设置流程启动用户
        identityService.setAuthenticatedUserId(TaskUtil.getTaskUser());
        // 开启流程
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinitionId, variables);
        // 组装流程通用类
        ZeroFlow flow = new ZeroFlow();
        BeanUtils.copyProperties(processInstance, flow);
        FlowTable flowTable = new FlowTable();
        flowTable.setTableName((String) variables.get(TaskConstants.APPLICATION_TABLE));
        flowTable.setApplicationId((String) variables.get(TaskConstants.APPLICATION_ID));
        flowTable.setInstanceId(processInstance.getId());
        flowTable.setDeployId(processInstance.getDeploymentId());
        flowTable.setProcessInstanceId(processInstance.getProcessInstanceId());
        flowTable.setDefinitionId(processInstance.getProcessDefinitionId());
        flowTable.setTenantId(processInstance.getTenantId());
        R r = bpmnFlowClient.flowCallBack(flowTable);
        if (!r.isSuccess()) {
            throw new ServiceException("流程业务处理异常");
        }
        return R.data(flow);
    }

    private void checkVariables(Map<String, Object> variables) {
        if (variables != null) {
            if (!variables.containsKey(TaskConstants.APPLICATION_ID)) {
                throw new ServiceException("缺少" + TaskConstants.APPLICATION_ID + "变量");
            }
            if (!variables.containsKey(TaskConstants.APPLICATION_TABLE)) {
                throw new ServiceException("缺少" + TaskConstants.APPLICATION_TABLE + "变量");
            }
            if (!variables.containsKey(TaskConstants.APPLICATION_DICT_KEY)) {
                throw new ServiceException("缺少" + TaskConstants.APPLICATION_DICT_KEY + "变量");
            }
            if (!variables.containsKey(TaskConstants.TENANT_ID)) {
                throw new ServiceException("缺少" + TaskConstants.TENANT_ID + "变量");
            }
        } else {
            throw new ServiceException("缺少变量");

        }
    }

    @Override
    @PostMapping(START_PROCESS_INSTANCE_BY_KEY)
    public R<ZeroFlow> startProcessInstanceByKey(String businessKey, @RequestBody Map<String, Object> variables) {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionTenantId((String) variables.get(TaskConstants.TENANT_ID))
                .processDefinitionKey(businessKey).latestVersion().singleResult();
        if(ObjectUtil.isNull(processDefinition)){
            throw new ServiceException(String.format("该租户未配置%s的流程",businessKey));
        }
        if ( processDefinition.isSuspended()) {
            throw new ServiceException("流程已被挂起，请先激活流程");
        }
        checkVariables(variables);
        // 设置流程启动用户
        identityService.setAuthenticatedUserId(TaskUtil.getTaskUser());
        // 开启流程
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId(), variables);
        // 组装流程通用类
        ZeroFlow flow = new ZeroFlow();
        BeanUtils.copyProperties(processInstance, flow);
        FlowTable flowTable = new FlowTable();
        flowTable.setTableName((String) variables.get(TaskConstants.APPLICATION_TABLE));
        flowTable.setApplicationId((String) variables.get(TaskConstants.APPLICATION_ID));
        flowTable.setInstanceId(processInstance.getId());
        flowTable.setDeployId(processInstance.getDeploymentId());
        flowTable.setProcessInstanceId(processInstance.getProcessInstanceId());
        flowTable.setDefinitionId(processInstance.getProcessDefinitionId());
        flowTable.setTenantId(processInstance.getTenantId());
        R r = bpmnFlowClient.flowCallBack(flowTable);
        if (!r.isSuccess()) {
            throw new ServiceException("流程业务处理异常");
        }
        return R.data(flow);
    }

    @Override
    @PostMapping(COMPLETE_TASK)
    public R completeTask(String taskId, String comment, @RequestBody Map<String, Object> variables) {
        // 当前任务 task
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (ObjectUtil.isNull(task)) {
            throw new RuntimeException("任务已被处理！");
        }
        if (task.isSuspended()) {
            throw new RuntimeException("任务处于挂起状态");
        }
        try {
            // 增加评论
            if (StringUtil.isNoneBlank(comment)) {
                taskService.addComment(task.getId(), task.getProcessInstanceId(), FlowComment.NORMAL.getType(), comment);
            }
            // 非空判断
            if (Func.isEmpty(variables)) {
                variables = Kv.create();
            }
            // 完成任务
            taskService.complete(taskId, variables);
            return R.success("流程提交成功");
        } catch (FlowableObjectNotFoundException e) {
            return R.fail("任务节点不存在");
        }
    }

    @Override
    @GetMapping(TASK_VARIABLE)
    public R<Object> taskVariable(String taskId, String variableName) {
        return R.data(taskService.getVariable(taskId, variableName));
    }

    @Override
    @GetMapping(TASK_VARIABLES)
    public R<Map<String, Object>> taskVariables(String taskId) {
        return R.data(taskService.getVariables(taskId));
    }

    @Override
    @PostMapping(TASK_CLAIM)
    public R taskClaim(String taskId) {
        taskService.unclaim(taskId); //确保任务未签收
        taskService.claim(taskId, TaskUtil.getTaskUser());
        return R.success();
    }

    @Override
    @PostMapping(TASK_UNCLAIM)
    public R taskUnclaim(String taskId) {
        taskService.unclaim(taskId);
        return R.success();
    }

    @Override
    @PostMapping(TASK_RETURN)
    public R taskReturn(TaskBo bo) {
        // 当前任务 task
        Task task = taskService.createTaskQuery().taskId(bo.getTaskId()).singleResult();
        if (ObjectUtil.isNull(task)) {
            throw new RuntimeException("任务已被处理！");
        }
        if (task.isSuspended()) {
            throw new RuntimeException("任务处于挂起状态");
        }
        // 获取流程定义信息
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(task.getProcessDefinitionId()).singleResult();
        // 获取所有节点信息
        Process process = repositoryService.getBpmnModel(processDefinition.getId()).getProcesses().get(0);
        // 获取全部节点列表，包含子节点
        Collection<FlowElement> allElements = FlowableUtils.getAllElements(process.getFlowElements(), null);
        // 获取当前任务节点元素
        FlowElement source = null;
        // 获取跳转的节点元素
        FlowElement target = null;
        if (allElements != null) {
            for (FlowElement flowElement : allElements) {
                // 当前任务节点元素
                if (flowElement.getId().equals(task.getTaskDefinitionKey())) {
                    source = flowElement;
                }
                // 跳转的节点元素
                if (flowElement.getId().equals(bo.getTargetKey())) {
                    target = flowElement;
                }
            }
        }

        // 从当前节点向前扫描
        // 如果存在路线上不存在目标节点，说明目标节点是在网关上或非同一路线上，不可跳转
        // 否则目标节点相对于当前节点，属于串行
        Boolean isSequential = FlowableUtils.iteratorCheckSequentialReferTarget(source, bo.getTargetKey(), null, null);
        if (!isSequential) {
            throw new RuntimeException("当前节点相对于目标节点，不属于串行关系，无法回退");
        }


        // 获取所有正常进行的任务节点 Key，这些任务不能直接使用，需要找出其中需要撤回的任务
        List<Task> runTaskList = taskService.createTaskQuery().processInstanceId(task.getProcessInstanceId()).list();
        List<String> runTaskKeyList = new ArrayList<>();
        runTaskList.forEach(item -> runTaskKeyList.add(item.getTaskDefinitionKey()));
        // 需退回任务列表
        List<String> currentIds = new ArrayList<>();
        // 通过父级网关的出口连线，结合 runTaskList 比对，获取需要撤回的任务
        List<UserTask> currentUserTaskList = FlowableUtils.iteratorFindChildUserTasks(target, runTaskKeyList, null, null);
        currentUserTaskList.forEach(item -> currentIds.add(item.getId()));

        // 循环获取那些需要被撤回的节点的ID，用来设置驳回原因
        List<String> currentTaskIds = new ArrayList<>();
        currentIds.forEach(currentId -> runTaskList.forEach(runTask -> {
            if (currentId.equals(runTask.getTaskDefinitionKey())) {
                currentTaskIds.add(runTask.getId());
            }
        }));
        // 设置回退意见
        for (String currentTaskId : currentTaskIds) {
            taskService.addComment(currentTaskId, task.getProcessInstanceId(), FlowComment.REBACK.getType(), bo.getComment());
        }

        try {
            // 1 对 1 或 多 对 1 情况，currentIds 当前要跳转的节点列表(1或多)，targetKey 跳转到的节点(1)
            runtimeService.createChangeActivityStateBuilder()
                    .processInstanceId(task.getProcessInstanceId())
                    .moveActivityIdsToSingleActivityId(currentIds, bo.getTargetKey()).changeState();
        } catch (FlowableObjectNotFoundException e) {
            throw new RuntimeException("未找到流程实例，流程可能已发生变化");
        } catch (FlowableException e) {
            throw new RuntimeException("无法取消或开始活动");
        }
        // 设置任务节点名称
        bo.setTaskName(task.getName());
//        // 处理抄送用户
//        if (!copyService.makeCopy(bo)) {
//            throw new RuntimeException("抄送任务失败");
//        }
        return R.data(bo);
    }

    @Override
    @PostMapping(TASK_COMMENT)
    public R taskComment(String taskId, String type, String comment) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (ObjectUtil.isNull(task)) {
            throw new RuntimeException("任务已被处理！");
        }
        if (task.isSuspended()) {
            throw new RuntimeException("任务处于挂起状态");
        }
        if (FlowComment.NORMAL.getType().equals(type)) {
            taskService.addComment(taskId, task.getProcessInstanceId(), type, StringUtil.isBlank(comment) ? "审核通过" : comment);
        } else {
            taskService.addComment(taskId, task.getProcessInstanceId(), type, comment);
        }
        return R.success();
    }

    @Override
    @PostMapping(TASK_REJECT)
    public R taskReject(String taskId, String comment) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (ObjectUtil.isNull(task)) {
            throw new RuntimeException("任务已被处理！");
        }
        if (task.isSuspended()) {
            throw new RuntimeException("任务处于挂起状态");
        }
        // 获取流程定义信息
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(task.getProcessDefinitionId()).singleResult();
        // 获取所有节点信息
        Process process = repositoryService.getBpmnModel(processDefinition.getId()).getProcesses().get(0);
        // 获取全部节点列表，包含子节点
        Collection<FlowElement> allElements = FlowableUtils.getAllElements(process.getFlowElements(), null);
        // 获取当前任务节点元素
        FlowElement source = null;
        if (allElements != null) {
            for (FlowElement flowElement : allElements) {
                // 类型为用户节点
                if (flowElement.getId().equals(task.getTaskDefinitionKey())) {
                    // 获取节点信息
                    source = flowElement;
                }
            }
        }

        // 目的获取所有跳转到的节点 targetIds
        // 获取当前节点的所有父级用户任务节点
        // 深度优先算法思想：延边迭代深入
        List<UserTask> parentUserTaskList = FlowableUtils.iteratorFindParentUserTasks(source, null, null);
        if (parentUserTaskList == null || parentUserTaskList.size() == 0) {
            throw new RuntimeException("当前节点为初始任务节点，不能驳回");
        }
        // 获取活动 ID 即节点 Key
        List<String> parentUserTaskKeyList = new ArrayList<>();
        parentUserTaskList.forEach(item -> parentUserTaskKeyList.add(item.getId()));
        // 获取全部历史节点活动实例，即已经走过的节点历史，数据采用开始时间升序
        List<HistoricTaskInstance> historicTaskInstanceList = historyService.createHistoricTaskInstanceQuery().processInstanceId(task.getProcessInstanceId()).orderByHistoricTaskInstanceStartTime().asc().list();
        // 数据清洗，将回滚导致的脏数据清洗掉
        List<String> lastHistoricTaskInstanceList = FlowableUtils.historicTaskInstanceClean(allElements, historicTaskInstanceList);
        // 此时历史任务实例为倒序，获取最后走的节点
        List<String> targetIds = new ArrayList<>();
        // 循环结束标识，遇到当前目标节点的次数
        int number = 0;
        StringBuilder parentHistoricTaskKey = new StringBuilder();
        for (String historicTaskInstanceKey : lastHistoricTaskInstanceList) {
            // 当会签时候会出现特殊的，连续都是同一个节点历史数据的情况，这种时候跳过
            if (parentHistoricTaskKey.toString().equals(historicTaskInstanceKey)) {
                continue;
            }
            parentHistoricTaskKey = new StringBuilder(historicTaskInstanceKey);
            if (historicTaskInstanceKey.equals(task.getTaskDefinitionKey())) {
                number++;
            }
            // 在数据清洗后，历史节点就是唯一一条从起始到当前节点的历史记录，理论上每个点只会出现一次
            // 在流程中如果出现循环，那么每次循环中间的点也只会出现一次，再出现就是下次循环
            // number == 1，第一次遇到当前节点
            // number == 2，第二次遇到，代表最后一次的循环范围
            if (number == 2) {
                break;
            }
            // 如果当前历史节点，属于父级的节点，说明最后一次经过了这个点，需要退回这个点
            if (parentUserTaskKeyList.contains(historicTaskInstanceKey)) {
                targetIds.add(historicTaskInstanceKey);
            }
        }


        // 目的获取所有需要被跳转的节点 currentIds
        // 取其中一个父级任务，因为后续要么存在公共网关，要么就是串行公共线路
        UserTask oneUserTask = parentUserTaskList.get(0);
        // 获取所有正常进行的任务节点 Key，这些任务不能直接使用，需要找出其中需要撤回的任务
        List<Task> runTaskList = taskService.createTaskQuery().processInstanceId(task.getProcessInstanceId()).list();
        List<String> runTaskKeyList = new ArrayList<>();
        runTaskList.forEach(item -> runTaskKeyList.add(item.getTaskDefinitionKey()));
        // 需驳回任务列表
        List<String> currentIds = new ArrayList<>();
        // 通过父级网关的出口连线，结合 runTaskList 比对，获取需要撤回的任务
        List<UserTask> currentUserTaskList = FlowableUtils.iteratorFindChildUserTasks(oneUserTask, runTaskKeyList, null, null);
        currentUserTaskList.forEach(item -> currentIds.add(item.getId()));


        // 规定：并行网关之前节点必须需存在唯一用户任务节点，如果出现多个任务节点，则并行网关节点默认为结束节点，原因为不考虑多对多情况
        if (targetIds.size() > 1 && currentIds.size() > 1) {
            throw new RuntimeException("任务出现多对多情况，无法撤回");
        }

        // 循环获取那些需要被撤回的节点的ID，用来设置驳回原因
        List<String> currentTaskIds = new ArrayList<>();
        currentIds.forEach(currentId -> runTaskList.forEach(runTask -> {
            if (currentId.equals(runTask.getTaskDefinitionKey())) {
                currentTaskIds.add(runTask.getId());
            }
        }));
        // 设置驳回意见
        currentTaskIds.forEach(item -> taskService.addComment(item, task.getProcessInstanceId(), FlowComment.REJECT.getType(), comment));

        try {
            // 如果父级任务多于 1 个，说明当前节点不是并行节点，原因为不考虑多对多情况
            if (targetIds.size() > 1) {
                // 1 对 多任务跳转，currentIds 当前节点(1)，targetIds 跳转到的节点(多)
                runtimeService.createChangeActivityStateBuilder()
                        .processInstanceId(task.getProcessInstanceId()).
                        moveSingleActivityIdToActivityIds(currentIds.get(0), targetIds).changeState();
            }
            // 如果父级任务只有一个，因此当前任务可能为网关中的任务
            if (targetIds.size() == 1) {
                // 1 对 1 或 多 对 1 情况，currentIds 当前要跳转的节点列表(1或多)，targetIds.get(0) 跳转到的节点(1)
                runtimeService.createChangeActivityStateBuilder()
                        .processInstanceId(task.getProcessInstanceId())
                        .moveActivityIdsToSingleActivityId(currentIds, targetIds.get(0)).changeState();
            }
        } catch (FlowableObjectNotFoundException e) {
            throw new RuntimeException("未找到流程实例，流程可能已发生变化");
        } catch (FlowableException e) {
            throw new RuntimeException("无法取消或开始活动");
        }
        return R.success();
    }

    @Override
    public R taskFinish(String instanceId, String type, String comment) {
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(instanceId).singleResult();
        if (processInstance != null) {
            Task task = taskService.createTaskQuery().processInstanceId(instanceId).singleResult();
            if (StringUtil.isNoneBlank(comment) && ObjectUtil.isNotNull(task)) {
                taskService.addComment(task.getId(), instanceId, StringUtil.isNotBlank(type) ? type : FlowComment.STOP.getType(), comment);
            }
            List<EndEvent> endNodes = findEndFlowElement(processInstance.getProcessDefinitionId());
            String endId = endNodes.get(0).getId();
            //2、执行终止
            List<Execution> executions = runtimeService.createExecutionQuery().parentId(instanceId).list();
            List<String> executionIds = new ArrayList<>();
            executions.forEach(execution -> executionIds.add(execution.getId()));
            runtimeService.createChangeActivityStateBuilder().moveExecutionsToSingleActivityId(executionIds, endId).changeState();
        } else {
            return R.fail("不存在运行的流程实例");
        }
        return R.success();

    }

    @Override
    public R taskJump(String instanceId, String taskName, String type, String comment) {
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(instanceId).singleResult();
        if (processInstance != null) {
            List<FlowElement> taskNodes = findTaskFlowElement(processInstance.getProcessDefinitionId(), taskName);
            if (!taskNodes.isEmpty()) {
                String taskId = taskNodes.get(0).getId();
                Task task = taskService.createTaskQuery().processInstanceId(instanceId).singleResult();
                if (StringUtil.isNoneBlank(comment) && ObjectUtil.isNotNull(task)) {
                    taskService.addComment(task.getId(), instanceId, StringUtil.isNotBlank(type) ? type : FlowComment.NORMAL.getType(), comment);
                }
                List<Execution> executions = runtimeService.createExecutionQuery().parentId(instanceId).list();
                List<String> executionIds = new ArrayList<>();
                executions.forEach(execution -> executionIds.add(execution.getId()));
                runtimeService.createChangeActivityStateBuilder().moveExecutionsToSingleActivityId(executionIds, taskId).changeState();
            }
        } else {
            return R.fail("不存在运行的流程实例");
        }
        return R.success();
    }

    public List findEndFlowElement(String processDefId) {
        Process mainProcess = repositoryService.getBpmnModel(processDefId).getMainProcess();
        Collection<FlowElement> list = mainProcess.getFlowElements();
        if (CollectionUtils.isEmpty(list)) {
            return Collections.EMPTY_LIST;
        }
        return list.stream().filter(f -> f instanceof EndEvent).collect(Collectors.toList());
    }

    public List<FlowElement> findTaskFlowElement(String processDefId, String taskName) {
        Process mainProcess = repositoryService.getBpmnModel(processDefId).getMainProcess();
        Collection<FlowElement> list = mainProcess.getFlowElements();
        if (CollectionUtils.isEmpty(list)) {
            return Collections.EMPTY_LIST;
        }
        return list.stream().filter(f -> taskName.equals(f.getName())).collect(Collectors.toList());
    }
}
