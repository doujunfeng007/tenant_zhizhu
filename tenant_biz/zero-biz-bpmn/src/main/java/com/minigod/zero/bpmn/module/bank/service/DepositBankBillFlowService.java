package com.minigod.zero.bpmn.module.bank.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.minigod.zero.bpmn.module.bank.entity.DepositBankBillFlow;
import com.minigod.zero.bpmn.module.bank.vo.DepositBankBillFlowVo;

import java.util.List;
import java.util.Set;

/**
 * @ClassName: DepositBankBillFlowService
 * @Description: ${description}
 * @Author chenyu
 * @Date 2024/3/7
 * @Version 1.0
 */
public interface DepositBankBillFlowService extends IService<DepositBankBillFlow> {


    int batchInsert(List<DepositBankBillFlow> list);

    /**
     * 查询是否有重复的流水
     *
     * @param depositBankBillFlow
     * @return
     */
    DepositBankBillFlow queryByFlow(DepositBankBillFlow depositBankBillFlow);

    IPage<DepositBankBillFlowVo> selectBillFlowPage(IPage page, DepositBankBillFlow applicationQuery);

    /**
     * 核对
     * @param refId
     */
    int checkFlow(String refId);

    /**
     * 取消核对
     * @param refId
     */
    int unCheckFlow(String refId);

    DepositBankBillFlow queryByRefId(String refId);

	/**
	 * 删除流水
	 * @param refId
	 * @return
	 */
	int delFlow(String refId,Integer  isDeleted);

    Set<String> selectRepeatByRefs(List<String> referenceNos);
}
