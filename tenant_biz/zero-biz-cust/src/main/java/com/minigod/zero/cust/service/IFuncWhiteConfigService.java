package com.minigod.zero.cust.service;

import com.minigod.zero.cust.entity.FuncWhiteConfigEntity;
import com.minigod.zero.cust.vo.FuncWhiteConfigVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 功能白名单启用配置 服务类
 *
 * @author 掌上智珠
 * @since 2023-03-16
 */
public interface IFuncWhiteConfigService extends IService<FuncWhiteConfigEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param funcWhiteConfig
	 * @return
	 */
	IPage<FuncWhiteConfigVO> selectFuncWhiteConfigPage(IPage<FuncWhiteConfigVO> page, FuncWhiteConfigVO funcWhiteConfig);


}
