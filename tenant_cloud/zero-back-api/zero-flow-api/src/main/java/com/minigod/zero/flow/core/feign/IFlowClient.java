
package com.minigod.zero.flow.core.feign;

import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.flow.core.entity.TaskBo;
import com.minigod.zero.flow.core.entity.ZeroFlow;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * 工作流远程调用接口.
 *
 * @author Chill
 */
@FeignClient(
        value = AppConstant.APPLICATION_FLOW_NAME
)
public interface IFlowClient {

    String START_PROCESS_INSTANCE_BY_ID = AppConstant.FEIGN_API_PREFIX + "/start-process-instance-by-id";
    String START_PROCESS_INSTANCE_BY_KEY = AppConstant.FEIGN_API_PREFIX + "/start-process-instance-by-key";
    String COMPLETE_TASK = AppConstant.FEIGN_API_PREFIX + "/complete-task";
    String TASK_VARIABLE = AppConstant.FEIGN_API_PREFIX + "/task-variable";
    String TASK_VARIABLES = AppConstant.FEIGN_API_PREFIX + "/task-variables";
    String TASK_CLAIM = AppConstant.FEIGN_API_PREFIX + "/task-claim";
    String TASK_UNCLAIM = AppConstant.FEIGN_API_PREFIX + "/task-unclaim";
    String TASK_RETURN = AppConstant.FEIGN_API_PREFIX + "/task-return";
    String TASK_COMMENT = AppConstant.FEIGN_API_PREFIX + "/task-comment";
    String TASK_REJECT = AppConstant.FEIGN_API_PREFIX + "/task-reject";
    String INSTANCE_TERMINATE = AppConstant.FEIGN_API_PREFIX + "/instance-terminate";
    String TASK_JUMP = AppConstant.FEIGN_API_PREFIX + "/task-jump";

    /**
     * 开启流程
     *
     * @param processDefinitionId 流程id
     * @param variables           参数
     * @return ZeroFlow
     */
    @PostMapping(START_PROCESS_INSTANCE_BY_ID)
    R<ZeroFlow> startProcessInstanceById(@RequestParam("processDefinitionId") String processDefinitionId, @RequestBody Map<String, Object> variables);

    /**
     * 开启流程
     *
     * @param businessKey 业务key
     * @param variables   参数
     * @return ZeroFlow
     */
    @PostMapping(START_PROCESS_INSTANCE_BY_KEY)
    R<ZeroFlow> startProcessInstanceByKey(@RequestParam("businessKey") String businessKey, @RequestBody Map<String, Object> variables);

    /**
     * 完成任务
     *
     * @param taskId    任务id
     * @param comment   评论
     * @param variables 参数
     * @return R
     */
    @PostMapping(COMPLETE_TASK)
    R completeTask(@RequestParam("taskId") String taskId, @RequestParam("comment") String comment, @RequestBody(required = false) Map<String, Object> variables);

    /**
     * 获取流程变量
     *
     * @param taskId       任务id
     * @param variableName 变量名
     * @return R
     */
    @GetMapping(TASK_VARIABLE)
    R<Object> taskVariable(@RequestParam("taskId") String taskId, @RequestParam("variableName") String variableName);

    /**
     * 获取流程变量集合
     *
     * @param taskId 任务id
     * @return R
     */
    @GetMapping(TASK_VARIABLES)
    R<Map<String, Object>> taskVariables(@RequestParam("taskId") String taskId);

    /**
     * 任务认领
     * @param taskId
     * @return
     */
    @PostMapping(TASK_CLAIM)
    R taskClaim(@RequestParam("taskId") String taskId);

    /**
     * 任务取消认领
     * @param taskId
     * @return
     */
    @PostMapping(TASK_UNCLAIM)
    R taskUnclaim(@RequestParam("taskId") String taskId);


    /**
     * 任务退回
     * @param taskBo
     * @return
     */
    @PostMapping(TASK_RETURN)
    R taskReturn(@RequestBody TaskBo taskBo);

    /**
     * 任务评论
     * @param taskId
     * @param type
     * @param comment
     * @return
     */
    @PostMapping(TASK_COMMENT)
    R taskComment(@RequestParam("taskId") String taskId, @RequestParam("type") String type, @RequestParam("comment") String comment);

    /**
     * 任务拒绝
     * @param taskId
     * @param comment
     * @return
     */
    @PostMapping(TASK_REJECT)
    R taskReject(@RequestParam("taskId") String taskId, @RequestParam("comment") String comment);

    /**
     * 终止实例
     * @param instanceId 实例 ID
     * @param type 评论类型
     * @param comment 评论
     * @return
     */
    @PostMapping(INSTANCE_TERMINATE)
    R taskFinish(@RequestParam("instanceId") String instanceId, @RequestParam(value = "type", required = false) String type, @RequestParam(value = "comment", required = false) String comment);

    /**
     * 跳转到指定节点
     * @param instanceId 实例 ID
     * @param taskName 节点名称
     * @param type 评论类型
     * @param comment 评论
     * @return
     */
    @PostMapping(TASK_JUMP)
    R taskJump(@RequestParam("instanceId") String instanceId, @RequestParam("taskName") String taskName, @RequestParam(value = "type", required = false) String type, @RequestParam(value = "comment", required = false) String comment);


}
