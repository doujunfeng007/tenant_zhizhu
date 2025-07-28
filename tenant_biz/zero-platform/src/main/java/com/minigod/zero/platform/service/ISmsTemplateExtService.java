package com.minigod.zero.platform.service;

import com.minigod.zero.core.mp.base.BaseService;
import com.minigod.zero.platform.entity.PlatformSmsTemplateExtEntity;

import java.util.List;
import java.util.Map;

/**
 * 消息模板扩展表 服务类
 *
 * @author 掌上智珠
 * @since 2023-02-27
 */
public interface ISmsTemplateExtService extends BaseService<PlatformSmsTemplateExtEntity> {

//	/**
//	 * 自定义分页
//	 *
//	 * @param page
//	 * @param smsTemplateExt
//	 * @return
//	 */
//	IPage<SmsTemplateExtVO> selectSmsTemplateExtPage(IPage<SmsTemplateExtVO> page, SmsTemplateExtVO smsTemplateExt);

	/**
	 * 通过消息代码查询对应的模板扩展信息
	 *
	 * @param iCode 消息代码
	 * @return 模板扩展信息集合
	 */
	List<Map<String, Object>> findTemplateExtByCode(Integer iCode);

}
