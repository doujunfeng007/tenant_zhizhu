package com.minigod.zero.bpmn.module.account.service;

import com.minigod.zero.core.mp.base.BaseService;
import com.minigod.zero.bpmn.module.account.entity.CustomOpenImgEntity;

import java.util.List;

/**
 *  开户资料文件上传服务
 *
 * @author Chill
 */
public interface ICustomOpenImgService extends BaseService<CustomOpenImgEntity> {

    List<CustomOpenImgEntity> selectByUserId(Long custId);
	List<CustomOpenImgEntity> selectByUserId(Long custId,String location,String type);
    CustomOpenImgEntity selectByUserId(Long custId,String type);
	List<CustomOpenImgEntity> selectByIds(List<Long> fileIds);
	int updateAttachmentUserId(List<Long> fileIds,Long userId);

	CustomOpenImgEntity selectByUserIdAndType(Long custId,String location,String type);


}
