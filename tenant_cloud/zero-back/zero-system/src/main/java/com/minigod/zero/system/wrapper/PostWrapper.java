
package com.minigod.zero.system.wrapper;

import com.minigod.zero.system.cache.DictCache;
import com.minigod.zero.system.enums.DictEnum;
import com.minigod.zero.system.vo.PostVO;
import com.minigod.zero.core.mp.support.BaseEntityWrapper;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.system.entity.Post;

import java.util.Objects;

/**
 * 岗位表包装类,返回视图层所需的字段
 *
 * @author Chill
 */
public class PostWrapper extends BaseEntityWrapper<Post, PostVO> {

	public static PostWrapper build() {
		return new PostWrapper();
	}

	@Override
	public PostVO entityVO(Post post) {
		PostVO postVO = Objects.requireNonNull(BeanUtil.copy(post, PostVO.class));
		String categoryName = DictCache.getValue(DictEnum.POST_CATEGORY, post.getCategory());
		postVO.setCategoryName(categoryName);
		return postVO;
	}

}
