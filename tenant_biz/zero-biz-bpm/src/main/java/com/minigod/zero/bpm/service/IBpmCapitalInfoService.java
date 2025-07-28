package com.minigod.zero.bpm.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.bpm.entity.BpmCapitalInfoEntity;
import com.minigod.zero.bpm.vo.BpmCapitalInfoVO;
import com.minigod.zero.core.mp.base.BaseService;

/**
 * 客户资金账号信息表 服务类
 *
 * @author 掌上智珠
 * @since 2023-03-27
 */
public interface IBpmCapitalInfoService extends BaseService<BpmCapitalInfoEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param capitalInfo
	 * @return
	 */
	IPage<BpmCapitalInfoVO> selectBpmCapitalInfoPage(IPage<BpmCapitalInfoVO> page, BpmCapitalInfoVO capitalInfo);


}
