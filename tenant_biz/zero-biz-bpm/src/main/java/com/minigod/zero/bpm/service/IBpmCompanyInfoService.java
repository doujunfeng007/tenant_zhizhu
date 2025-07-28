package com.minigod.zero.bpm.service;

import com.minigod.zero.bpm.entity.BpmCompanyInfoEntity;
import com.minigod.zero.bpm.vo.BpmCompanyInfoVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 公司户详细资料表 服务类
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
public interface IBpmCompanyInfoService extends IService<BpmCompanyInfoEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param BpmCompanyInfo
	 * @return
	 */
	IPage<BpmCompanyInfoVO> selectBpmCompanyInfoPage(IPage<BpmCompanyInfoVO> page, BpmCompanyInfoVO BpmCompanyInfo);


}
