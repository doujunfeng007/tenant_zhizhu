/*
 *  Copyright (c) 2018-2028, MiniGod All rights reserved.
 */
package com.minigod.zero.system.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.core.mp.base.BaseService;
import com.minigod.zero.system.entity.FuncConfig;
import com.minigod.zero.system.vo.FuncConfigVO;

import java.util.List;

/**
 * 功能配置模块 服务类
 *
 * @author ZSDP
 * @since 2024-07-23
 */
public interface IFuncConfigService extends BaseService<FuncConfig> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param funcConfig
	 * @return
	 */
	IPage<FuncConfigVO> selectFuncConfigPage(IPage<FuncConfigVO> page, FuncConfigVO funcConfig);


	List<FuncConfigVO> getByTenantId(String tenantId);
}
