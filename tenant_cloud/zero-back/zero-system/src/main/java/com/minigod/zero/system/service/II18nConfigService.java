package com.minigod.zero.system.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.minigod.zero.system.entity.I18nConfigEntity;

import java.util.List;
import java.util.Map;

/**
 * 国际化配置表 服务类
 *
 * @author 掌上智珠
 * @since 2023-03-08
 */
public interface II18nConfigService extends IService<I18nConfigEntity> {

	/**
	 * 查询模块对应语言配置项
	 * @param moduleName
	 * @return
	 */
	List<I18nConfigEntity> selectI18nConfigByModule(String moduleName) ;

}
