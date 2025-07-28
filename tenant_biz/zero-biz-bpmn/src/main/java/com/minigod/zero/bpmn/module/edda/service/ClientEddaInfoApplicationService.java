package com.minigod.zero.bpmn.module.edda.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.minigod.zero.bpmn.module.edda.bo.*;
import com.minigod.zero.bpmn.module.edda.entity.ClientEddaInfoApplicationEntity;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.cms.vo.PayeeBankListVO;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
* @author dell
* @description 针对表【client_edda_info_application(DBS edda授权申请表)】的数据库操作Service
* @createDate 2024-05-10 15:44:59
*/
public interface ClientEddaInfoApplicationService extends IService<ClientEddaInfoApplicationEntity> {

    R submit(FundDepositEddaBO fundDepositEddaBO);

	R eddaCustInfo();

	R eddaAccountList(FundDepositEddaAccountListBO fundDepositEddaAccountListBO);

	R approval(EddaInfoApprovalBO eddaInfoApprovalBO);

	List<ClientEddaInfoApplicationEntity> queryNotStateEddaListInfo();

	R eddaDel(String applicationId);

	R authSubmit(FundDepositAuthEddaBO fundDepositAuthEddaBO);

	R<List<PayeeBankListVO>> infoBankList();

	IPage<ClientEddaInfoApplicationEntity> pages(ClientEddaInfoListBO bo, Query query);

	R refreshStatus(String applicationId);

	R eddaInfo(String applicationId);
}
