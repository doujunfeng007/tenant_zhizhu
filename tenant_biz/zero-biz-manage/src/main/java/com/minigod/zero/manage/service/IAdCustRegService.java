package com.minigod.zero.manage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.minigod.zero.manage.entity.AdCustRegEntity;
import com.minigod.zero.manage.vo.AdCustRegVO;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 广告客户记录表 服务类
 *
 * @author 掌上智珠
 * @since 2023-03-18
 */
public interface IAdCustRegService extends IService<AdCustRegEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param adCustReg
	 * @return
	 */
	IPage<AdCustRegVO> selectAdCustRegPage(IPage<AdCustRegVO> page, AdCustRegVO adCustReg);

	/**
	 * 获取用户广告信息
	 *
	 * @param userId
	 * @param adId
	 * @return
	 */
	AdCustRegEntity findUserAdReg(Long userId, Long adId);
}
