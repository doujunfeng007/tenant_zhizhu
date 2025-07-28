package com.minigod.zero.manage.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.manage.entity.HotRecommendEntity;
import com.minigod.zero.manage.vo.HotRecommendVO;
import com.minigod.zero.core.mp.base.BaseService;
import com.minigod.zero.core.tool.beans.StkInfo;
import com.minigod.zero.manage.vo.request.HotRecommendReqVO;

import java.util.List;

/**
 * 热门推荐股票 服务类
 *
 * @author 掌上智珠
 * @since 2023-03-18
 */
public interface IHotRecommendService extends BaseService<HotRecommendEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param mktHotRecommend
	 * @return
	 */
	IPage<HotRecommendVO> selectHotRecommendPage(IPage<HotRecommendVO> page, HotRecommendVO mktHotRecommend);

	/**
	 * 查询更新版本
	 * @param paramVersion
	 * @return
	 */
	Long findHotRecommendUpdateVersion(Long paramVersion);

	/**
	 * 热门股票
	 * @param vo
	 * @return
	 */
	List<StkInfo> findRandHotList(HotRecommendReqVO vo);
}
