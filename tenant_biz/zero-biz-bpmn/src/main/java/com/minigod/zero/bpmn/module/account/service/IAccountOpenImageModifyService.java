package com.minigod.zero.bpmn.module.account.service;


import com.minigod.zero.bpmn.module.account.entity.AccountOpenImageModifyEntity;
import com.minigod.zero.bpmn.module.account.entity.CustomOpenImgEntity;
import com.minigod.zero.bpmn.module.account.vo.AccountOpenImageModifyVO;
import com.minigod.zero.core.mp.base.BaseService;

import java.util.List;

/**
* @author Administrator
* @description 针对表【customer_account_open_image_modify(客户开户申请图片信息表)】的数据库操作Service
* @createDate 2024-08-13 13:46:02
*/
public interface IAccountOpenImageModifyService extends BaseService<AccountOpenImageModifyEntity> {

	AccountOpenImageModifyEntity selectByApplicationId(String applicationId, String type);

	/**
	  * 根据申请记录id查询图片详情
	  *
	  * @param
	  * @return
	  */
	List<AccountOpenImageModifyVO> getOpenImageModifyByApplyId(Long applyId);
}
