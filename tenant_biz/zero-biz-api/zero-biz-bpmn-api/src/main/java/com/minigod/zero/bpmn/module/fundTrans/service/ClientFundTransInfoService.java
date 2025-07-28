package com.minigod.zero.bpmn.module.fundTrans.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.minigod.zero.bpmn.module.fundTrans.domain.ClientFundTransInfo;
import com.minigod.zero.bpmn.module.fundTrans.domain.bo.FundTransInfoBo;
import com.minigod.zero.bpmn.module.fundTrans.domain.bo.FundTransQuery;
import com.minigod.zero.bpmn.module.fundTrans.domain.vo.ClientFundTransInfoVO;

/**
 * @ClassName: ClientFundTransInfoService
 * @Description: ${description}
 * @Author chenyu
 * @Date 2024/12/11
 * @Version 1.0
 */
public interface ClientFundTransInfoService extends IService<ClientFundTransInfo> {

	void submitInfo(FundTransInfoBo bo);

	IPage<ClientFundTransInfoVO> queryInfoPage(IPage<ClientFundTransInfoVO> page, FundTransQuery bo);

}
