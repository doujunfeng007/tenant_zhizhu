package com.minigod.zero.bpm.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.bpm.vo.BpmAccountInfoVO;
import com.minigod.zero.core.mp.base.BaseService;
import com.minigod.zero.bpm.entity.BpmAccountInfoEntity;

/**
 * 交易账户信息表 服务类
 *
 * @author 掌上智珠
 * @since 2023-03-27
 */
public interface IBpmAccountInfoService extends BaseService<BpmAccountInfoEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param acctInfo
	 * @return
	 */
	IPage<BpmAccountInfoVO> selectBpmAccountInfoPage(IPage<BpmAccountInfoVO> page, BpmAccountInfoVO acctInfo);

}
