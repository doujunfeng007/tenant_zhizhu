/*
 *  Copyright (c) 2018-2028, MiniGod All rights reserved.
 */
package com.minigod.zero.system.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.system.entity.FuncConfig;
import com.minigod.zero.system.vo.FuncConfigVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 功能配置模块 Mapper 接口
 *
 * @author ZSDP
 * @since 2024-07-23
 */
public interface FuncConfigMapper extends BaseMapper<FuncConfig> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param funcConfig
	 * @return
	 */
	List<FuncConfigVO> selectFuncConfigPage(IPage page, FuncConfigVO funcConfig);


	@InterceptorIgnore(tenantLine = "true")
	List<FuncConfig> getByTenantId(@Param("tenantId") String tenantId);
}
