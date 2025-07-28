package com.minigod.zero.platform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.minigod.zero.platform.entity.PlatformSmsTemplateEntity;

/**
 * 消息模板表 Mapper 接口
 *
 * @author 掌上智珠
 * @since 2023-02-27
 */
public interface PlatformSmsTemplateMapper extends BaseMapper<PlatformSmsTemplateEntity> {

//	/**
//	 * 自定义分页
//	 *
//	 * @param page
//	 * @param smsTemplate
//	 * @return
//	 */
//	List<SmsTemplateVO> selectSmsTemplatePage(IPage page, SmsTemplateVO smsTemplate);

	/**
	 * 通过消息代码查询对应的消息模板
	 *
	 * @param iCode 消息代码
	 * @return 消息模板对象
	 */
	PlatformSmsTemplateEntity findTemplateByCode(Integer iCode);

}
