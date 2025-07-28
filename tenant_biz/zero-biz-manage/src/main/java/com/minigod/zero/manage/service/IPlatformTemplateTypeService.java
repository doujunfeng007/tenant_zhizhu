package com.minigod.zero.manage.service;

import com.minigod.zero.core.mp.base.BaseService;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.manage.vo.MsgTemplateVO;
import com.minigod.zero.platform.entity.PlatformTemplateTypeEntity;

import java.util.List;

/**
 * 模块类型信息表 服务类
 *
 * @author 掌上智珠
 * @since 2023-03-16
 */
public interface IPlatformTemplateTypeService extends BaseService<PlatformTemplateTypeEntity> {
//	/**
//	 * 自定义分页
//	 *
//	 * @param page
//	 * @param platformTemplateType
//	 * @return
//	 */
//	IPage<PlatformTemplateTypeVO> selectPlatformTemplateTypePage(IPage<PlatformTemplateTypeVO> page, PlatformTemplateTypeVO platformTemplateType);

	/**
	 * 查询模板类型
	 * @param busType 业务类型 1-邮件 2-短信 3-系统通知 4-消息通知
	 * @param parentId 父模板类型ID
	 * @return
	 */
	List<PlatformTemplateTypeEntity> findInformTempTypeList(int busType, int parentId);

	/**
	 * 新增模板类型
	 * @param busType 业务类型 1-邮件 2-短信 3-系统通知 4-消息通知
	 * @param typeStr 简体中文，多个模板类型用分号隔开
	 * @return
	 */
	R addTypes(int busType, String typeStr);

	/**
	 * 根据类型名称查询
	 * @param tempName
	 * @return
	 */
	List<PlatformTemplateTypeEntity> findByTempName(String tempName);

	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	MsgTemplateVO findTemplate(Long id);

	/**
	 * 删除消息模板
	 * @param id
	 */
	void deleteTemplate(Long id);
}
