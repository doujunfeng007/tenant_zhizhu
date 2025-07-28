package com.minigod.zero.bpm.service.openAccount;

import com.minigod.zero.core.tool.api.R;

public interface IGdcaApiService {

	R unionPayVerify(String userName, String mobileNo, String bankcardNo, String idcard, String transactionId);


	R idNumberCheck(String idcard ,String userName,String transactionId);
}
