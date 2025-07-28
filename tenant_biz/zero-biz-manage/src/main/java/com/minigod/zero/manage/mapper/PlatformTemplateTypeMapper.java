package com.minigod.zero.manage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.minigod.zero.manage.vo.MsgTemplateVO;
import com.minigod.zero.platform.entity.PlatformTemplateTypeEntity;

/**
 * 模块类型信息表 Mapper 接口
 *
 * @author 掌上智珠
 * @since 2023-03-16
 */
public interface PlatformTemplateTypeMapper extends BaseMapper<PlatformTemplateTypeEntity> {

//	/**
//	 * 自定义分页
//	 *
//	 * @param page
//	 * @param platformTemplateType
//	 * @return
//	 */
//	List<PlatformTemplateTypeVO> selectPlatformTemplateTypePage(IPage page, PlatformTemplateTypeVO platformTemplateType);

	MsgTemplateVO findTemplate(Long id);

	void update(PlatformTemplateTypeEntity entity);

}
