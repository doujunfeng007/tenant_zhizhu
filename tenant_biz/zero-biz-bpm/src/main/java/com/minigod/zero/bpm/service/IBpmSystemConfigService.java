package com.minigod.zero.bpm.service;

import com.minigod.zero.bpm.entity.BpmSystemConfigEntity;
import com.minigod.zero.bpm.vo.BpmSystemConfigVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 系统配置信息表 服务类
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
public interface IBpmSystemConfigService extends IService<BpmSystemConfigEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param bpmSystemConfig
	 * @return
	 */
	IPage<BpmSystemConfigVO> selectBpmSystemConfigPage(IPage<BpmSystemConfigVO> page, BpmSystemConfigVO bpmSystemConfig);

	/**
	 * 通过Key获取参数值，自定义默认值
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	String getSysConfigValue(String key, String defaultValue);

}
