package com.minigod.zero.manage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.manage.entity.HotRecommendEntity;
import com.minigod.zero.manage.vo.HotRecommendVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 热门推荐股票 Mapper 接口
 *
 * @author 掌上智珠
 * @since 2023-03-18
 */
public interface HotRecommendMapper extends BaseMapper<HotRecommendEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param mktHotRecommend
	 * @return
	 */
	List<HotRecommendVO> selectHotRecommendPage(IPage page, HotRecommendVO mktHotRecommend);

	/**
	 * 热门股票
	 * @param count
	 * @return
	 */
    List<HotRecommendEntity> findRandHotList(@Param("count") int count);
}
