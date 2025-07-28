package com.minigod.zero.trade.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.minigod.zero.trade.entity.IpoGeniusInfoEntity;
import com.minigod.zero.trade.vo.IpoGeniusInfoVO;

import java.util.List;

/**
 * IPO打新牛人信息表 服务类
 *
 * @author 掌上智珠
 * @since 2023-01-16
 */
public interface IIpoGeniusInfoService extends IService<IpoGeniusInfoEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param ipoGeniusInfo
	 * @return
	 */
	IPage<IpoGeniusInfoVO> selectIpoGeniusInfoPage(IPage<IpoGeniusInfoVO> page, IpoGeniusInfoVO ipoGeniusInfo);

	/**
	 * 查询全部牛人信息
	 *
	 * @param isStatus
	 * @return
	 */
	List<IpoGeniusInfoEntity> findIpoGeniusInfoList(boolean isStatus);

	/**
	 * 新增或修改打新牛人信息
	 *
	 * @param ipoGeniusInfo
	 */
	void saveOrUpdateIpoGeniusInfo(IpoGeniusInfoEntity ipoGeniusInfo);
}
