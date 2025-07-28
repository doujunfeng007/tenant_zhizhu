package com.minigod.zero.bpmn.module.account.service;

import com.minigod.zero.bpmn.module.account.bo.OpenAttachmentBo;
import com.minigod.zero.bpmn.module.account.bo.OpenImgBo;
import com.minigod.zero.bpmn.module.account.bo.OpenInfoBo;
import com.minigod.zero.bpmn.module.account.vo.OpenAttachmentVo;
import com.minigod.zero.bpmn.module.account.vo.OpenImgVo;

import java.util.List;

/**
 * 线下开户服务
 *
 * @author eric
 * @since 2024-06-12 17:49:36
 */
public interface IOpenAccountOfflineService {
	/**
	 * 提交线下开户资料
	 *
	 * @param params
	 */
	void submitOpenInfo(OpenInfoBo params);

	/**
	 * 上传附件
	 *
	 * @param params
	 * @return
	 */
	OpenAttachmentVo uploadAttachment(OpenAttachmentBo params);

	/**
	 * 保存用户开户图片数据
	 *
	 * @param params
	 * @return
	 */
	OpenImgVo saveOpenAccountImg(OpenImgBo params);

	/**
	 * 获取用户附件
	 *
	 * @param userId
	 * @return
	 */
	List<OpenAttachmentVo> queryAttachmentList(Long userId);
}
