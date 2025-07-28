package com.minigod.zero.trade.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.minigod.zero.trade.entity.IpoOmsConfigEntity;
import com.minigod.zero.trade.vo.IpoOmsConfigVO;

import java.util.List;

/**
 * 新股ipo配置 服务类
 *
 * @author 掌上智珠
 * @since 2023-01-16
 */
public interface IIpoOmsConfigService extends IService<IpoOmsConfigEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param ipoOmsConfig
	 * @return
	 */
	IPage<IpoOmsConfigVO> selectIpoOmsConfigPage(IPage<IpoOmsConfigVO> page, IpoOmsConfigVO ipoOmsConfig);

	/**
	 * 检查数据库已经存在的assetId
	 *
	 * @param assetIds
	 * @return
	 */
	List<String> checkExistOmsConfigIpoAssetId(List<String> assetIds);


	/**
	 * 查询oms配置的ipo融资配置
	 * @param assetId
	 * @return
	 */
	IpoOmsConfigEntity getOmsConfigIpo(String assetId);
}
