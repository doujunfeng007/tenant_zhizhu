package com.minigod.zero.flow.flowable.flowable.condition;

import liquibase.pro.packaged.S;
import lombok.extern.slf4j.Slf4j;
import org.flowable.common.engine.api.delegate.Expression;
import org.flowable.common.engine.impl.el.ExpressionManager;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.impl.Condition;
import org.flowable.engine.impl.bpmn.helper.ErrorPropagation;
import org.flowable.engine.impl.util.CommandContextUtil;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: AutoBankCardConditionHandler
 * @Description:
 * @Author chenyu
 * @Date 2024/3/12
 * @Version 1.0
 */
@Slf4j
@Component("bankCardCondition")
public class BankCardCondition implements Condition {
    Map<String, String> condition = new HashMap<>();

    public BankCardCondition() {
        //自动审核条件 除了`other`的绑定来源的 并且 是绑定类型的审核 跳过审核
        condition.put("autoAudit", "${autoAudit}");
        //绑定/解绑/修改流转条件 1 绑定 2 解绑
        condition.put("add", "${ applicationType == 1 }");
        condition.put("edit", "${ applicationType == 3 }");
        condition.put("del", "${ applicationType == 2 }");
    }

    @Override
    public boolean evaluate(String s, DelegateExecution delegateExecution) {
        log.info("BankCardCondition key " + s);
        ExpressionManager expressionManager = CommandContextUtil.getProcessEngineConfiguration().getExpressionManager();
        Object result = expressionManager.createExpression(condition.getOrDefault(s,"${1==1}")).getValue(delegateExecution);
        if (!(result instanceof Boolean)) {
            throw new RuntimeException("Condition expression returns non-Boolean value: " + result);
        }
        return (Boolean) result;
    }
}
