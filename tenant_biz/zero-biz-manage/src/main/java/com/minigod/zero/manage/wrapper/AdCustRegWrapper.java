package com.minigod.zero.manage.wrapper;

import com.minigod.zero.manage.entity.AdCustRegEntity;
import com.minigod.zero.manage.vo.AdCustRegVO;
import com.minigod.zero.core.mp.support.BaseEntityWrapper;
import com.minigod.zero.core.tool.utils.BeanUtil;

import java.util.Objects;

/**
 * 广告客户记录表 包装类,返回视图层所需的字段
 *
 * @author 掌上智珠
 * @since 2023-03-18
 */
public class AdCustRegWrapper extends BaseEntityWrapper<AdCustRegEntity, AdCustRegVO>  {

	public static AdCustRegWrapper build() {
		return new AdCustRegWrapper();
 	}

	@Override
	public AdCustRegVO entityVO(AdCustRegEntity adCustReg) {
	    AdCustRegVO adCustRegVO = new AdCustRegVO();
    	if (adCustReg != null) {
		    adCustRegVO = Objects.requireNonNull(BeanUtil.copy(adCustReg, AdCustRegVO.class));

		    //User createUser = UserCache.getUser(adCustReg.getCreateUser());
		    //User updateUser = UserCache.getUser(adCustReg.getUpdateUser());
		    //adCustRegVO.setCreateUserName(createUser.getName());
		    //adCustRegVO.setUpdateUserName(updateUser.getName());
        }
		return adCustRegVO;
	}


}
