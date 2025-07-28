package com.minigod.zero.bpmn.module.fundTrans.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.minigod.zero.bpmn.module.fundTrans.domain.ClientFundTransApplication;
import com.minigod.zero.bpmn.module.fundTrans.domain.bo.ClientFundTransApplicationQuery;
import com.minigod.zero.bpmn.module.fundTrans.domain.vo.ClientFundTransApplicationVO;

import java.util.List;

/**
 * @ClassName: ClientFundTransApplicationService
 * @Description: ${description}
 * @Author chenyu
 * @Date 2024/12/11
 * @Version 1.0
 */
public interface ClientFundTransApplicationService extends IService<ClientFundTransApplication> {



    ClientFundTransApplicationVO queryByApplicationId(String applicationId);

    IPage<ClientFundTransApplicationVO> selectFundTransApplicationPage(IPage<ClientFundTransApplicationVO> page, ClientFundTransApplicationQuery applicationQuery);
    IPage<ClientFundTransApplicationVO> selectFundTransApplicationPage(IPage<ClientFundTransApplicationVO> page);

    String batchClaim(Integer applicationStatus, List<String> list);

    String batchUnClaim(List<String> list);

    void rejectNode(String applicationId, String instanceId, String reason);

    void passNode(String applicationId, String taskId, String reason);

    List<ClientFundTransApplication> queryListByNode(String nodeName);
}
