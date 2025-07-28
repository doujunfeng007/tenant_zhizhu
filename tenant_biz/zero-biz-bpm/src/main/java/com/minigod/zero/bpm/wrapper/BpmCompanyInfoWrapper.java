package com.minigod.zero.bpm.wrapper;

import com.minigod.zero.core.mp.support.BaseEntityWrapper;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.bpm.entity.BpmCompanyInfoEntity;
import com.minigod.zero.bpm.vo.BpmCompanyInfoVO;
import java.util.Objects;

/**
 * 公司户详细资料表 包装类,返回视图层所需的字段
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
public class BpmCompanyInfoWrapper extends BaseEntityWrapper<BpmCompanyInfoEntity, BpmCompanyInfoVO>  {

	public static BpmCompanyInfoWrapper build() {
		return new BpmCompanyInfoWrapper();
 	}

	@Override
	public BpmCompanyInfoVO entityVO(BpmCompanyInfoEntity BpmCompanyInfo) {
	    BpmCompanyInfoVO BpmCompanyInfoVO = new BpmCompanyInfoVO();
    	if (BpmCompanyInfo != null) {
		    BpmCompanyInfoVO = Objects.requireNonNull(BeanUtil.copy(BpmCompanyInfo, BpmCompanyInfoVO.class));

		    //User createUser = UserCache.getUser(BpmCompanyInfo.getCreateUser());
		    //User updateUser = UserCache.getUser(BpmCompanyInfo.getUpdateUser());
		    //BpmCompanyInfoVO.setCreateUserName(createUser.getName());
		    //BpmCompanyInfoVO.setUpdateUserName(updateUser.getName());
        }
		return BpmCompanyInfoVO;
	}


}
