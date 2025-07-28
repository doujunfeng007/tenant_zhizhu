package com.minigod.zero.bpm.feign;

import com.minigod.zero.core.cloud.feign.ZeroFeignRequestInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.minigod.zero.bpm.dto.BpmAccountRespDto;
import com.minigod.zero.bpm.dto.BpmSecuritiesRespDto;
import com.minigod.zero.bpm.dto.BpmTradeAcctRespDto;
import com.minigod.zero.bpm.dto.OpenAccountStatusRespDto;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;

@FeignClient(
        value = AppConstant.SERVICE_BIZ_BPM_NAME,
	    configuration = ZeroFeignRequestInterceptor.class
)
public interface IBpmAccountClient {

    String API_PREFIX = AppConstant.FEIGN_API_PREFIX;
    String BPM_ACCOUNT_INFO = API_PREFIX + "/bpm_account_info";
    String BPM_TRADE_ACCT_INFO = API_PREFIX + "/bpm_trade_acct_info";
    String BPM_ACCOUNT_INFO_BY_ACCOUNT = API_PREFIX + "/bpm_account_info_by_account";
    String BPM_UPDATE_INFO = API_PREFIX + "/bpm_update_info";
    String ACCT_OPEN_STATUS = API_PREFIX + "/acct_open_status";
    String BPM_HKIDR_STATUS = API_PREFIX + "/need_grant_hkidr";
    String BPM_GRANT_HKIDR = API_PREFIX + "/grant_hkidr";

    @PostMapping(BPM_ACCOUNT_INFO)
    R<BpmAccountRespDto> bpmAccountInfo(@RequestParam("custId") Long custId);

    @PostMapping(BPM_TRADE_ACCT_INFO)
    R<BpmTradeAcctRespDto> bpmTradeAcctInfo();

    @PostMapping(BPM_ACCOUNT_INFO_BY_ACCOUNT)
    R<BpmAccountRespDto> bpmAccountInfoByAccount(@RequestBody BpmTradeAcctRespDto dto);


    @PostMapping(BPM_UPDATE_INFO)
    R bpmUpdateInfo(@RequestBody BpmSecuritiesRespDto dto);

    @GetMapping(ACCT_OPEN_STATUS)
    R<OpenAccountStatusRespDto> acctOpenStatus();

    @PostMapping(BPM_HKIDR_STATUS)
    R needGrantHkidr(@RequestParam("custId") Long custId);

    @PostMapping(BPM_GRANT_HKIDR)
    R grantHkidr(@RequestParam("custId") Long custId);

}
