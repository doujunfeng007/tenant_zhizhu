
package com.minigod.zero.resource.wrapper;

import com.minigod.zero.resource.entity.Oss;
import com.minigod.zero.core.mp.support.BaseEntityWrapper;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.resource.vo.OssVO;
import com.minigod.zero.system.cache.DictCache;
import com.minigod.zero.system.enums.DictEnum;

import java.util.Objects;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author minigod
 */
public class OssWrapper extends BaseEntityWrapper<Oss, OssVO> {

    public static OssWrapper build() {
        return new OssWrapper();
    }

    @Override
    public OssVO entityVO(Oss oss) {
        OssVO ossVO = Objects.requireNonNull(BeanUtil.copy(oss, OssVO.class));
        String categoryName = DictCache.getValue(DictEnum.OSS, oss.getCategory());
//		String statusName = DictCache.getValue(DictEnum.YES_NO, oss.getStatus());
        ossVO.setCategoryName(categoryName);
		// 框架自带的枚举是 1-禁用 2-启用
        ossVO.setStatusName(Objects.isNull(oss.getStatus()) ? "" : Objects.equals(1, oss.getStatus()) ? "否" : "是");
        return ossVO;
    }

}
