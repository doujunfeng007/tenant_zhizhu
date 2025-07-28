package com.minigod.zero.bpmn.module.account.service;

import com.minigod.zero.bpmn.module.account.api.ImageInfo;
import com.minigod.zero.bpmn.module.account.vo.AccountOpenImageVO;
import com.minigod.zero.core.mp.base.BaseService;
import com.minigod.zero.bpmn.module.account.entity.AccountOpenImageEntity;

import java.util.List;

/**
 *  服务类
 *
 * @author Chill
 */
public interface IAccountOpenImageService extends BaseService<AccountOpenImageEntity> {

    List<AccountOpenImageVO> queryListByApplicationId(String applicationId, Integer imageLocation);

	/**
	 * 查询这个用户的所有图片
	 * @param applicationId
	 * @return
	 */
	List<AccountOpenImageVO> queryListByApplicationId(String applicationId);

    AccountOpenImageVO queryOneByApplicationId(String applicationId,Integer imageLocation,Integer imageLocationType);

    Boolean uploadImageInfoList(List<ImageInfo> imageInfos, String applicationId,String tenantId);

    void deleteByApplicationId(String applicationId);

}
