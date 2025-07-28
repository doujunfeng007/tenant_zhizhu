package com.minigod.zero.manage.service;

import com.minigod.zero.manage.dto.AppConfigSaveDTO;
import com.minigod.zero.manage.entity.AppConfigEntity;
import com.minigod.zero.manage.vo.AppConfigVO;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * APP配置 服务类
 *
 * @author 掌上智珠
 * @since 2023-03-23
 */
public interface IAppConfigService extends BaseService<AppConfigEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param appConfig
	 * @return
	 */
	IPage<AppConfigVO> selectAppConfigPage(IPage<AppConfigVO> page, AppConfigVO appConfig);


	/**
	 * 发布
	 * @param ids
	 * @return
	 */
	R<Object> publish(String ids);

	/**
	 * 保存
	 * @param appConfig
	 */
    void save(AppConfigSaveDTO appConfig);


	/**
	 * 通过url查询数据
	 * @param url
	 * @return
	 */
	R getDataFromUrl(String url);
}
