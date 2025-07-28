package com.minigod.zero.flow.flowable.flowable.listener.common;

import cn.hutool.extra.spring.SpringUtil;
import com.minigod.zero.core.log.exception.ServiceException;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.flow.core.common.constant.TaskConstants;
import com.minigod.zero.flow.core.entity.FlowTable;
import com.minigod.zero.flow.flowable.flowable.service.FlowTableService;
import com.minigod.zero.system.cache.DictCache;
import com.minigod.zero.system.feign.IDictBizClient;
import com.minigod.zero.system.feign.IDictClient;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.ExecutionListener;
import org.springframework.context.ApplicationContext;

import java.util.Map;

/**
 * @ClassName: BaseExecution
 * @Description: 封装执行器监听工具类
 * @Author chenyu
 * @Date 2022/7/25
 * @Version 1.0
 */
@Slf4j
public class BaseExecution implements ExecutionListener {
    private static final long serialVersionUID = 1905122041950251207L;
    private final ApplicationContext applicationContext;
    private final FlowTableService flowTableService;
    private final IDictClient iDictClient;


    public BaseExecution() {
        this.applicationContext = SpringUtil.getApplicationContext();
        this.flowTableService = this.applicationContext.getBean(FlowTableService.class);
        this.iDictClient = this.applicationContext.getBean(IDictClient.class);
    }

    @Override
    public void notify(DelegateExecution delegateExecution) {
        Map<String, Object> variables = delegateExecution.getVariables();
        log.info("流程变量:" + variables.toString());
        //获取当前流程的流水号
        this.commonUpdate(delegateExecution);
    }

    /**
     * 公共更新业务流程节点
     *
     * @param delegateExecution
     */
    public void commonUpdate(DelegateExecution delegateExecution) {
        //获取当前流程的流水号
        String applicationId = (String) delegateExecution.getVariable(TaskConstants.APPLICATION_ID);
        //获取当前流程的表
        String tableName = (String) delegateExecution.getVariable(TaskConstants.APPLICATION_TABLE);
        //获取当前任务的key
        String flowNodeDictKey = (String) delegateExecution.getVariable(TaskConstants.APPLICATION_DICT_KEY);
        //获取当前节点
        String elementName = delegateExecution.getCurrentFlowElement().getName();
        //租户 ID
        String tenantId = delegateExecution.getTenantId();
        //获取流程节点对应状态
        FlowTable flowTable = new FlowTable();
        flowTable.setApplicationId(applicationId);
        flowTable.setTableName(tableName);
        flowTable.setCurrentNode(elementName);
        flowTable.setTenantId(tenantId);
        R<String> r = iDictClient.getLabel(flowNodeDictKey, elementName);
        if (!r.isSuccess()) {
            throw new ServiceException(r.getMsg());
        }
        flowTable.setApplicationStatus(Integer.valueOf(r.getData().trim()));
        flowTableService.updateByApplicationId(flowTable);
//        this.remind(elementName,status,applicationId);
    }

//    /**
//     * 业务处理
//     *
//     * @param applicationId
//     */
//    protected abstract void business(String applicationId);
//
//    protected abstract void remind(String elementName, String status, String applicationId);

}
