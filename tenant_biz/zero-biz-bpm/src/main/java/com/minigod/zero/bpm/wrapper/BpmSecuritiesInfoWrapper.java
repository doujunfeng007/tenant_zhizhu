package com.minigod.zero.bpm.wrapper;

import com.minigod.zero.core.mp.support.BaseEntityWrapper;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.bpm.entity.BpmSecuritiesInfoEntity;
import com.minigod.zero.bpm.vo.BpmSecuritiesInfoVO;
import java.util.Objects;

/**
 * 证券客户资料表 包装类,返回视图层所需的字段
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
public class BpmSecuritiesInfoWrapper extends BaseEntityWrapper<BpmSecuritiesInfoEntity, BpmSecuritiesInfoVO>  {

	public static BpmSecuritiesInfoWrapper build() {
		return new BpmSecuritiesInfoWrapper();
 	}

	@Override
	public BpmSecuritiesInfoVO entityVO(BpmSecuritiesInfoEntity bpmSecuritiesInfo) {
	    BpmSecuritiesInfoVO bpmSecuritiesInfoVO = new BpmSecuritiesInfoVO();
    	if (bpmSecuritiesInfo != null) {
		    bpmSecuritiesInfoVO = Objects.requireNonNull(BeanUtil.copy(bpmSecuritiesInfo, BpmSecuritiesInfoVO.class));

		    //User createUser = UserCache.getUser(bpmSecuritiesInfo.getCreateUser());
		    //User updateUser = UserCache.getUser(bpmSecuritiesInfo.getUpdateUser());
		    //bpmSecuritiesInfoVO.setCreateUserName(createUser.getName());
		    //bpmSecuritiesInfoVO.setUpdateUserName(updateUser.getName());
        }
		return bpmSecuritiesInfoVO;
	}


}
