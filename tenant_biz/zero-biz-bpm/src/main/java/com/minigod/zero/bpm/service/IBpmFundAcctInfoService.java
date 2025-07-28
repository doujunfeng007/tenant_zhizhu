package com.minigod.zero.bpm.service;

import com.minigod.zero.bpm.entity.BpmFundAcctInfoEntity;
import com.minigod.zero.bpm.vo.BpmFundAcctInfoVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 基金账户信息表 服务类
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
public interface IBpmFundAcctInfoService extends IService<BpmFundAcctInfoEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param bpmFundAcctInfo
	 * @return
	 */
	IPage<BpmFundAcctInfoVO> selectBpmFundAcctInfoPage(IPage<BpmFundAcctInfoVO> page, BpmFundAcctInfoVO bpmFundAcctInfo);


}
