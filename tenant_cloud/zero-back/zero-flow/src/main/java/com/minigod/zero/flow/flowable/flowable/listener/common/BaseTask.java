package com.minigod.zero.flow.flowable.flowable.listener.common;

import com.minigod.zero.core.tool.utils.SpringUtil;
import com.minigod.zero.flow.core.common.constant.TaskConstants;
import com.minigod.zero.flow.core.common.enums.FlowComment;
import com.minigod.zero.flow.core.entity.FlowTable;
import com.minigod.zero.flow.flowable.flowable.service.FlowTableService;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.TaskService;
import org.flowable.task.service.delegate.DelegateTask;
import org.flowable.engine.delegate.TaskListener;
import org.springframework.context.ApplicationContext;

/**
 * @ClassName: BaseTask
 * @Description: 封装任务监听工具类
 * @Author chenyu
 * @Date 2022/7/25
 * @Version 1.0
 */
@Slf4j
public  class BaseTask implements TaskListener {
    private final ApplicationContext applicationContext;
    private final TaskService taskService;
    private final FlowTableService flowTableService;



    public BaseTask() {
        this.applicationContext = SpringUtil.getContext();
        this.taskService = applicationContext.getBean(TaskService.class);
        this.flowTableService = applicationContext.getBean(FlowTableService.class);
    }

    /**
     * 重置流程异常变量
     *
     * @param delegateTask
     */
    public void restFail(DelegateTask delegateTask) {
        delegateTask.setVariable(TaskConstants.FAIL_NUM, 0);
        delegateTask.setVariable(TaskConstants.STATUS, 1);
    }

    public void flowFail(DelegateTask delegateTask) {
        delegateTask.setVariable(TaskConstants.FAIL_NUM, 1);
        delegateTask.setVariable(TaskConstants.STATUS, 0);
    }

    /**
     * 添加评论方法
     *
     * @param delegateTask
     * @param commentType
     * @param comment
     */
    public void addComment(DelegateTask delegateTask, String commentType, String comment) {
        taskService.addComment(delegateTask.getId(), delegateTask.getProcessInstanceId(), commentType, comment);
    }

    /**
     * 添加异常评论方法
     *
     * @param delegateTask
     * @param e
     */
    public void addExceptionComment(DelegateTask delegateTask, Exception e) {
        this.flowFail(delegateTask);
        this.addComment(delegateTask, FlowComment.EXCEPTION.getType(), e.getMessage());
        taskService.complete(delegateTask.getId(), delegateTask.getTransientVariables());
    }

    /**
     * 添加成功评论方法
     *
     * @param delegateTask
     */
    public void addSuccessComment(DelegateTask delegateTask) {
        this.restFail(delegateTask);
        this.addComment(delegateTask, FlowComment.NORMAL.getType(), "任务完成");
        taskService.complete(delegateTask.getId(), delegateTask.getTransientVariables());
    }

    @Override
    public void notify(DelegateTask delegateTask) {
        FlowTable flowTable = new FlowTable();
        String applicationId = (String) delegateTask.getVariable(TaskConstants.APPLICATION_ID);
        String tableName = (String) delegateTask.getVariable(TaskConstants.APPLICATION_TABLE);
        String tenantId = delegateTask.getTenantId();
        flowTable.setTableName(tableName);
        flowTable.setTaskId(delegateTask.getId());
        flowTable.setApplicationId(applicationId);
        flowTable.setTenantId(tenantId);
        flowTableService.updateByApplicationId(flowTable);

//        Integer failNum = delegateTask.getVariable(TaskConstants.FAIL_NUM) != null ? (Integer) delegateTask.getVariable(TaskConstants.FAIL_NUM) : 0;
//        if (failNum < 1) {
//            try {
////                this.business(applicationId);
//                this.addSuccessComment(delegateTask);
//            } catch (Exception e) {
//                this.addExceptionComment(delegateTask, e);
//            }
//        } else {
////            this.fail(applicationId);
//            this.restFail(delegateTask);
//        }
    }

//    /**
//     * 正常业务处理
//     *
//     * @param applicationId
//     */
//    protected abstract void business(String applicationId);
//
//    /**
//     * 失败业务处理
//     *
//     * @param applicationId
//     */
//    protected abstract void fail(String applicationId);
}
