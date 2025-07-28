package com.minigod.zero.bpm.service.openAccount;

import com.minigod.zero.bpm.dto.OpenAccountStatusRespDto;
import com.minigod.zero.bpm.dto.acct.req.CaCertificationDto;
import com.minigod.zero.bpm.dto.acct.req.OpenAccountImgReqDto;
import com.minigod.zero.bpm.dto.acct.req.OpenInfoTempDto;
import com.minigod.zero.bpm.dto.acct.resp.DataDictionaryDto;
import com.minigod.zero.bpm.vo.request.OpenAccResultCallbackProto;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.support.Kv;

import java.util.List;

public interface IOpenAccountService {

	R ocr(OpenAccountImgReqDto vo);

	R<OpenAccountStatusRespDto> openAcctStatus();

	R<Kv> openSaveImg(CaCertificationDto dto);

	R<Kv> realAuth(Long custId,CaCertificationDto dto);

	R saveOpenInfoTemp(Long custId, OpenInfoTempDto dto);

	R<Kv> getOpenInfoTemp(Long custId, OpenInfoTempDto dto);

	R saveOpenInfo(Long custId,OpenInfoTempDto dto);

	R updOpenEmail(Long custId,CaCertificationDto dto);


	R<List<DataDictionaryDto>> bpmDataCode(OpenInfoTempDto dto);

	/**
	 * 线上开户回调
	 * @param proto
	 * @return
	 */
	R updateOpenUserInfo(OpenAccResultCallbackProto proto);


	/**
	 * 线下开户回调
	 * @param proto
	 * @return
	 */
	R offlineOpenAccount(OpenAccResultCallbackProto  proto);

	R executeOpenAccount();

	R executeAccountCaAuth();


	R getUserNamePy(CaCertificationDto dto);

}
