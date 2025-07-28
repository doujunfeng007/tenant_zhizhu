package com.minigod.zero.manage.wrapper;

import com.minigod.zero.manage.entity.AdCustActInfoEntity;
import com.minigod.zero.manage.vo.AdCustActInfoVO;
import com.minigod.zero.core.mp.support.BaseEntityWrapper;
import com.minigod.zero.core.tool.utils.BeanUtil;

import java.util.Objects;

/**
 * 广告用户活动信息表 包装类,返回视图层所需的字段
 *
 * @author 掌上智珠
 * @since 2023-03-18
 */
public class AdCustActInfoWrapper extends BaseEntityWrapper<AdCustActInfoEntity, AdCustActInfoVO>  {

	public static AdCustActInfoWrapper build() {
		return new AdCustActInfoWrapper();
 	}

	@Override
	public AdCustActInfoVO entityVO(AdCustActInfoEntity adCustActInfo) {
	    AdCustActInfoVO adCustActInfoVO = new AdCustActInfoVO();
    	if (adCustActInfo != null) {
		    adCustActInfoVO = Objects.requireNonNull(BeanUtil.copy(adCustActInfo, AdCustActInfoVO.class));

		    //User createUser = UserCache.getUser(adCustActInfo.getCreateUser());
		    //User updateUser = UserCache.getUser(adCustActInfo.getUpdateUser());
		    //adCustActInfoVO.setCreateUserName(createUser.getName());
		    //adCustActInfoVO.setUpdateUserName(updateUser.getName());
        }
		return adCustActInfoVO;
	}


}
