package com.minigod.zero.bpm.service;

import com.minigod.zero.bpm.dto.acct.req.CommonImgDto;
import com.minigod.zero.bpm.dto.acct.resp.CommonImgRespDto;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.support.Kv;

import java.util.List;

public interface BpmCommonService {

	/**
	 * 上传图片
	 * @param dto
	 * @return R
	 */
	R<Kv> uploadImg(CommonImgDto dto);


	R<List<CommonImgRespDto>> downloadImg(CommonImgDto dto);
}
