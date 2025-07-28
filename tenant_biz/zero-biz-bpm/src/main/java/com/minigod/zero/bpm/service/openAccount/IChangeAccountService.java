package com.minigod.zero.bpm.service.openAccount;

import com.minigod.zero.bpm.dto.acct.req.CaCertificationDto;
import com.minigod.zero.bpm.dto.acct.req.ChangeSecuritiesDto;
import com.minigod.zero.bpm.vo.AcctChangeInfoTempVO;
import com.minigod.zero.bpm.vo.request.OpenAccountImgUpdateVo;
import com.minigod.zero.bpm.vo.request.SecUserInfoChangeCallbackProtocol;
import com.minigod.zero.bpm.vo.response.SecuritiesUserInfoFullResp;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.support.Kv;

public interface IChangeAccountService {

	R<AcctChangeInfoTempVO> getSecuritiesTemp(Long custId, ChangeSecuritiesDto dto);

	R updSecuritiesTemp(Long custId, ChangeSecuritiesDto dto);

	R removeImg(Long custId, ChangeSecuritiesDto dto);

	R<AcctChangeInfoTempVO> getSecuritiesInfoReal(Long custId, ChangeSecuritiesDto dto);

	R<AcctChangeInfoTempVO> getSecuritiesCheckStatus(Long custId, ChangeSecuritiesDto dto);

	R<Boolean> getSecuritiesChangingStatus(Long custId);

	R resetCheckStatus(Long custId, ChangeSecuritiesDto dto);

	R<Kv> changeSaveImg(CaCertificationDto dto);

	R executeChangeAccount();

	R changeAccountCheckStatus(SecUserInfoChangeCallbackProtocol protocol);

	R changeAccountImage(OpenAccountImgUpdateVo proto);

	SecuritiesUserInfoFullResp getSecuritiesFromBpm(Long custId);
}
