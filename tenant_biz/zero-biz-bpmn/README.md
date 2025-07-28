#流程操作
##  1.通用流程图设计层:
> 1.1 执行监听器配置节点监听：
>> 1.1.1 事件类型: start   
>> 1.1.2 监听器类型: Java类  
>> 1.1.3 JAVA类：com.minigod.zero.flow.flowable.flowable.listener.common.BaseExecution  

> 1.2 配置任务监听器：  
>> 1.2.1 事件类型: start   
>> 1.2.2监听器类型: Java类
>> 1.2.3 JAVA类: com.minigod.zero.flow.flowable.flowable.listener.common.BaseTask
 
> 1.3 执行器配置通知监听: 
>> 事件类型：end  
>> 监听器类型: 代理表达式  
>> 代理表达式: ${notifyService}

## 2 代码层
> 2.1 通知回调实现 需要配合 1.3 步骤
>> 2.1.1 实现 FlowNotifyService 接口  
>> 2.1.2 系统字典配置 notify_business_bean流程业务key对应的实现FlowNotifyService接口的BeanName  

## 3 说明
> 3.1 执行监听器 
>> 任务节点的名称通过字典找到对应的映射值，当执行器通过配置的事件类型触发时通过回调接口修改对应表的 current_node,application_status,flow_path等字段    

> 3.2 任务监听器
>> 任务监听器只在任务节点（审批）存在，当执行器通过配置的事件类型触发时通过回调接口修改对应表的  task_id 字段   