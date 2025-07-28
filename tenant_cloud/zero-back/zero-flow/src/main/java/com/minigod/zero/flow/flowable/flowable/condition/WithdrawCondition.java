package com.minigod.zero.flow.flowable.flowable.condition;

import lombok.extern.slf4j.Slf4j;
import org.flowable.common.engine.impl.el.ExpressionManager;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.impl.Condition;
import org.flowable.engine.impl.util.CommandContextUtil;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: WithdrawCondition
 * @Description:
 * @Author chenyu
 * @Date 2024/3/15
 * @Version 1.0
 */
@Slf4j
@Component("withdrawCondition")
public class WithdrawCondition implements Condition {
    Map<String, String> condition = new HashMap<>();

    public WithdrawCondition() {
        //确认节点 海外银行卡（客户提交）
        condition.put("confirm", "${transferType==1 && applySource != 2}");
        //审核节点 香港本地银行（客户申请）
        condition.put("check", "${transferType!=1 && applySource != 2}");
        //复核节点 综合后台录入
        condition.put("compound", "${applySource==2}");
        condition.put("refund", "${isRefund}");

    }

    @Override
    public boolean evaluate(String s, DelegateExecution delegateExecution) {
        log.info("WithdrawCondition key " + s);
        ExpressionManager expressionManager = CommandContextUtil.getProcessEngineConfiguration().getExpressionManager();
        Object result = expressionManager.createExpression(condition.getOrDefault(s,"${1==1}")).getValue(delegateExecution);
        if (!(result instanceof Boolean)) {
            throw new RuntimeException("Condition expression returns non-Boolean value: " + result);
        }
        return (Boolean) result;
    }
}
