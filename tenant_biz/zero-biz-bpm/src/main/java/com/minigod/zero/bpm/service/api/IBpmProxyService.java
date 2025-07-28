package com.minigod.zero.bpm.service.api;

import com.minigod.zero.bpm.dto.acct.resp.SecuritiesCacheDto;
import com.minigod.zero.bpm.dto.acct.resp.YfundInfoCacheDto;
import com.minigod.zero.core.tool.api.R;

import java.util.List;

/**
 *   提供服务API给BPM调用
 */


public interface IBpmProxyService {

	R<List<YfundInfoCacheDto>> yfundInfo(List<String> fundAccounts);

	R<SecuritiesCacheDto> getSecUserInfo(Long custId);

}
