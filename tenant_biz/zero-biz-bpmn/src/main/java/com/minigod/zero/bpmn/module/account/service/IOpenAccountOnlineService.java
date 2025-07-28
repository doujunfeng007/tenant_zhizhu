package com.minigod.zero.bpmn.module.account.service;


import com.minigod.zero.bpmn.module.account.bo.*;
import com.minigod.zero.bpmn.module.account.vo.OpenCacheDataVo;
import com.minigod.zero.bpmn.module.account.vo.OpenImgVo;
import com.minigod.zero.bpmn.module.account.vo.OpenUserInfoVo;
import com.minigod.zero.bpmn.module.account.vo.OpenVideoVo;
import com.minigod.zero.bpmn.module.common.bo.OcrReqParams;
import com.minigod.zero.core.tool.api.R;

public interface IOpenAccountOnlineService {
	/**
	 * 获取开户进度
	 *
	 * @param bo
	 * @return
	 */
	OpenUserInfoVo getOpenProgress(OpenProgressBo bo);

	/**
	 * 获取开户缓存数据
	 *
	 * @param params
	 * @return
	 */
	OpenCacheDataVo getCacheData(OpenCacheDataBo params);

	/**
	 * 保存开户缓存数据
	 *
	 * @param params
	 */
	void saveOrUpdateCacheInfoStep(OpenCacheInfoBo params);

	/**
	 * 提交开户资料
	 *
	 * @param params
	 */
	void submitOpenInfo(OpenInfoBo params);

	/**
	 * 保存用户开户图片数据
	 *
	 * @param params
	 * @return
	 */
	OpenImgVo saveCacheImg(OpenImgBo params);

	/**
	 * 保存用户开户视频数据
	 *
	 * @param params
	 * @return
	 */
	OpenVideoVo saveCacheVideo(OpenVideoBo params);

	/**
	 * ocr识别
	 *
	 * @param ocrReqParams
	 * @return
	 */
	Object ocrByCardType(OcrReqParams ocrReqParams);


}
