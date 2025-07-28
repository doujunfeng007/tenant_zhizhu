
package com.minigod.zero.user.wrapper;

import com.minigod.zero.user.entity.Notice;
import com.minigod.zero.core.mp.support.BaseEntityWrapper;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.core.tool.utils.Func;
import com.minigod.zero.system.cache.DictCache;
import com.minigod.zero.system.enums.DictEnum;
import com.minigod.zero.user.vo.NoticeVO;

import java.util.Map;
import java.util.Objects;

/**
 * Notice包装类,返回视图层所需的字段
 *
 * @author Chill
 */
public class NoticeWrapper extends BaseEntityWrapper<Notice, NoticeVO> {

	public static NoticeWrapper build() {
		return new NoticeWrapper();
	}

	@Override
	public NoticeVO entityVO(Notice notice) {
		NoticeVO noticeVO = Objects.requireNonNull(BeanUtil.copy(notice, NoticeVO.class));
		String dictValue = DictCache.getValue(DictEnum.NOTICE, noticeVO.getCategory());
		noticeVO.setCategoryName(dictValue);
		return noticeVO;
	}

	/**
	 * 查询条件处理
	 */
	public void noticeQuery(Map<String, Object> notice) {
		// 此场景仅在 pg数据库 map类型传参的情况下需要处理，entity传参已经包含数据类型，则无需关心
		// 针对 pg数据库 int类型字段查询需要强转的处理示例
		String searchKey = "category";
		if (Func.isNotEmpty(notice.get(searchKey))) {
			// 数据库字段为int类型，设置"="查询，具体查询参数请见 @com.minigod.zero.core.mp.support.SqlKeyword
			notice.put(searchKey.concat("_equal"), Func.toInt(notice.get(searchKey)));
			// 默认"like"查询，pg数据库 场景会报错，所以将其删除
			notice.remove(searchKey);
		}
	}

}
