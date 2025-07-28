package com.minigod.zero.manage.wrapper;

import com.minigod.zero.core.mp.support.BaseEntityWrapper;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.manage.entity.AdFrequencyInfoEntity;
import com.minigod.zero.manage.vo.AdFrequencyInfoVO;
import java.util.Objects;

/**
 * 广告频率表 包装类,返回视图层所需的字段
 *
 * @author 掌上智珠
 * @since 2023-03-18
 */
public class AdFrequencyInfoWrapper extends BaseEntityWrapper<AdFrequencyInfoEntity, AdFrequencyInfoVO>  {

	public static AdFrequencyInfoWrapper build() {
		return new AdFrequencyInfoWrapper();
 	}

	@Override
	public AdFrequencyInfoVO entityVO(AdFrequencyInfoEntity adFrequencyInfo) {
	    AdFrequencyInfoVO adFrequencyInfoVO = new AdFrequencyInfoVO();
    	if (adFrequencyInfo != null) {
		    adFrequencyInfoVO = Objects.requireNonNull(BeanUtil.copy(adFrequencyInfo, AdFrequencyInfoVO.class));

		    //User createUser = UserCache.getUser(adFrequencyInfo.getCreateUser());
		    //User updateUser = UserCache.getUser(adFrequencyInfo.getUpdateUser());
		    //adFrequencyInfoVO.setCreateUserName(createUser.getName());
		    //adFrequencyInfoVO.setUpdateUserName(updateUser.getName());
        }
		return adFrequencyInfoVO;
	}


}
