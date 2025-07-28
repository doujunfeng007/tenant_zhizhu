package com.minigod.zero.bpmn.module.common.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.minigod.zero.bpmn.module.common.entity.SysSubItemConfigEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author dell
 * @description 针对表【sys_sub_item_config(系统通用文本项配置表)】的数据库操作Mapper
 * @createDate 2024-06-20 10:01:35
 * @Entity generator.domain.SysSubItemConfig
 */
@Mapper
public interface SysSubItemConfigMapper  extends BaseMapper<SysSubItemConfigEntity> {

	int deleteByPrimaryKey(Long id);

	int deleteByItemId(Long itemId);

	int insert(SysSubItemConfigEntity record);

	int insertSelective(SysSubItemConfigEntity record);

	SysSubItemConfigEntity selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(SysSubItemConfigEntity record);

	int updateByPrimaryKey(SysSubItemConfigEntity record);

	List<SysSubItemConfigEntity> selectByItemType(String itemType, String lang);

	List<SysSubItemConfigEntity> selectSubItemByItemTypeAndItemId(String itemType, Long itemId, String lang);
}
