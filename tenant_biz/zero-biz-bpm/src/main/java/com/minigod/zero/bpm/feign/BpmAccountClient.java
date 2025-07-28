package com.minigod.zero.bpm.feign;

import com.minigod.zero.bpm.dto.BpmAccountRespDto;
import com.minigod.zero.bpm.dto.BpmSecuritiesRespDto;
import com.minigod.zero.bpm.dto.BpmTradeAcctRespDto;
import com.minigod.zero.bpm.dto.OpenAccountStatusRespDto;
import com.minigod.zero.bpm.service.openAccount.IBpmAccountService;
import com.minigod.zero.bpm.service.openAccount.IOpenAccountService;
import com.minigod.zero.bpm.vo.UserHkidrVo;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
public class BpmAccountClient implements IBpmAccountClient {

	@Resource
	private IBpmAccountService bpmAccountService;
	@Resource
	private IOpenAccountService openAccountService;

	@Override
	@PostMapping(BPM_ACCOUNT_INFO)
	public R<BpmAccountRespDto> bpmAccountInfo(Long custId) {
		if(null == custId){
			custId = AuthUtil.getTenantCustId();
		}
		return bpmAccountService.getAccountRespDto(custId);
	}

	@Override
	public R<BpmTradeAcctRespDto> bpmTradeAcctInfo() {
		Long custId = AuthUtil.getTenantCustId();
		return bpmAccountService.bpmTradeAcctInfo(custId);
	}

	@Override
	public R<BpmAccountRespDto> bpmAccountInfoByAccount(BpmTradeAcctRespDto dto) {
		return bpmAccountService.getAccountRespDto(dto.getTradeAccount(),dto.getCapitalAccount());
	}

	@Override
	public R bpmUpdateInfo(BpmSecuritiesRespDto dto) {
		//修改证券手机号
		return bpmAccountService.bpmUpdateInfo(dto);
	}

	@Override
	public R<OpenAccountStatusRespDto> acctOpenStatus() {
		return openAccountService.openAcctStatus();
	}

	@Override
	public R needGrantHkidr(Long custId) {
		return bpmAccountService.needGrantHkidr(custId);
	}

	@Override
	public R grantHkidr(Long custId) {
		UserHkidrVo vo = new UserHkidrVo();
		vo.setUserId(custId.intValue());
		vo.setHkidrStatusApproach(0);
		return bpmAccountService.grantHkidr(vo);
	}

}
