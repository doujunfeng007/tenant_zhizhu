package com.minigod.zero.bpmn.module.edda.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.minigod.zero.bpmn.module.deposit.bo.HistoryRecordBo;
import com.minigod.zero.bpmn.module.edda.bo.ClientEddaFundListBO;
import com.minigod.zero.bpmn.module.edda.entity.ClientEddaFundApplicationEntity;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.tool.api.R;

import java.util.List;

/**
* @author dell
* @description 针对表【client_edda_fund_application(DBS edda入金流水表)】的数据库操作Service
* @createDate 2024-05-10 15:43:22
*/
public interface ClientEddaFundApplicationService extends IService<ClientEddaFundApplicationEntity> {

	R approval(String applicationId);

	IPage<ClientEddaFundApplicationEntity> queryPage(ClientEddaFundListBO bo, Query query);

    List<ClientEddaFundApplicationEntity> queryNotSendBankFundList();
}
