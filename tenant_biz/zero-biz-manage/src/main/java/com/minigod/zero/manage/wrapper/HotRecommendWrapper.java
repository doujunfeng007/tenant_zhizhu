package com.minigod.zero.manage.wrapper;

import com.minigod.zero.core.mp.support.BaseEntityWrapper;
import com.minigod.zero.manage.entity.HotRecommendEntity;
import com.minigod.zero.manage.vo.HotRecommendVO;
import com.minigod.zero.core.tool.utils.BeanUtil;

import java.util.Objects;

/**
 * 热门推荐股票 包装类,返回视图层所需的字段
 *
 * @author 掌上智珠
 * @since 2023-03-18
 */
public class HotRecommendWrapper extends BaseEntityWrapper<HotRecommendEntity, HotRecommendVO> {

	public static HotRecommendWrapper build() {
		return new HotRecommendWrapper();
 	}

	@Override
	public HotRecommendVO entityVO(HotRecommendEntity mktHotRecommend) {
	    HotRecommendVO mktHotRecommendVO = new HotRecommendVO();
    	if (mktHotRecommend != null) {
		    mktHotRecommendVO = Objects.requireNonNull(BeanUtil.copy(mktHotRecommend, HotRecommendVO.class));

		    //User createUser = UserCache.getUser(mktHotRecommend.getCreateUser());
		    //User updateUser = UserCache.getUser(mktHotRecommend.getUpdateUser());
		    //mktHotRecommendVO.setCreateUserName(createUser.getName());
		    //mktHotRecommendVO.setUpdateUserName(updateUser.getName());
        }
		return mktHotRecommendVO;
	}


}
