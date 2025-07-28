package com.minigod.zero.trade.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.minigod.zero.trade.entity.IpoPredictConfigEntity;
import com.minigod.zero.trade.vo.IpoPredictConfigVO;

import java.util.List;

/**
 * 新股预约ipo配置 服务类
 *
 * @author 掌上智珠
 * @since 2023-01-16
 */
public interface IIpoPredictConfigService extends IService<IpoPredictConfigEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param ipoRredictConfig
	 * @return
	 */
	IPage<IpoPredictConfigVO> selectIpoRredictConfigPage(IPage<IpoPredictConfigVO> page, IpoPredictConfigVO ipoRredictConfig);


	/**
	 * 获取 预约新股配置
	 *
	 * @return
	 */
	List<IpoPredictConfigEntity> getPredictIpoConfig(IpoPredictConfigEntity ipoPredictConfig);

	/**
	 * 获取预约新股配置
	 * @param predictIpoConfigId
	 * @param aTrue
	 * @return
	 */
	IpoPredictConfigEntity getPredictIpoConfigById(Long predictIpoConfigId, Boolean aTrue);
}
