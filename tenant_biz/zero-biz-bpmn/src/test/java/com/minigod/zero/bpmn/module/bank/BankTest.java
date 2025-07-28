package com.minigod.zero.bpmn.module.bank;

import com.minigod.zero.dbs.vo.AccountBalanceRequestVO;
import com.minigod.zero.bpmn.ZeroBpmnApplication;
import com.minigod.zero.bpmn.module.bank.feign.DbsService;
import com.minigod.zero.core.test.ZeroBootTest;
import com.minigod.zero.core.test.ZeroSpringExtension;
import com.minigod.zero.core.tool.api.R;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @ClassName: BankTest
 * @Description:
 * @Author chenyu
 * @Date 2024/3/21
 * @Version 1.0
 */
@ExtendWith(ZeroSpringExtension.class)
@SpringBootTest(classes = ZeroBpmnApplication.class)
@ZeroBootTest(appName = "zero-biz-bpmn", profile = "sit", enableLoader = true)
public class BankTest {
    @Autowired
    DbsService dbsService;

    @Test
    public void test() {
        AccountBalanceRequestVO vo = new AccountBalanceRequestVO();
        vo.setAccountCcy("HKD");
        vo.setAccountNo("1231313");
        vo.setMsgId("12313213131313213131");
        vo.setTenantId("000000");
        R r = dbsService.sendBleQuery(vo);
        if(!r.isSuccess()){
            System.out.println(r.getMsg());
        }
    }
}
