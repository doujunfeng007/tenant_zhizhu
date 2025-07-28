package com.minigod.zero.bpmn.module.deposit.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.minigod.zero.bpmn.module.deposit.bo.BankCardApplicationQuery;
import com.minigod.zero.bpmn.module.deposit.entity.BankCardApplication;
import com.minigod.zero.bpmn.module.deposit.vo.BankCardApplicationVO;

import java.util.List;

/**
 * @ClassName: BankCardApplicationService
 * @Description: ${description}
 * @Author chenyu
 * @Date 2024/3/11
 * @Version 1.0
 */
public interface BankCardApplicationService extends IService<BankCardApplication> {


    int batchInsert(List<BankCardApplication> list);

    BankCardApplicationVO queryByApplicationId(String applicationId);

    IPage<BankCardApplicationVO> selectBankCardApplicationPage(IPage<BankCardApplicationVO> page, BankCardApplicationQuery applicationQuery);

    IPage<BankCardApplicationVO> selectBankCardApplicationPage(IPage<BankCardApplicationVO> page);

    String batchClaim(Integer applicationStatus, List<String> asList);

    String batchUnClaim(List<String> asList);

    void rejectNode(String applicationId, String instanceId, String reason);

    void passNode(String applicationId, String taskId, String reason);

    List<BankCardApplication> queryListByNode(String node);
}
