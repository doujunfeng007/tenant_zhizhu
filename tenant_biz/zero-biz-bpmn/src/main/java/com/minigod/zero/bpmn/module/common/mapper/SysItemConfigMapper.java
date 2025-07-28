package com.minigod.zero.bpmn.module.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.minigod.zero.bpmn.module.common.entity.SysItemConfigEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author dell
 * @description 针对表【sys_item_config(系统通用文本配置表)】的数据库操作Mapper
 * @createDate 2024-06-20 10:01:35
 * @Entity generator.domain.SysItemConfig
 */
@Mapper
public interface SysItemConfigMapper extends BaseMapper<SysItemConfigEntity> {

	int deleteByPrimaryKey(Long id);

	int insert(SysItemConfigEntity record);

	int insertSelective(SysItemConfigEntity record);

	SysItemConfigEntity selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(SysItemConfigEntity record);

	int updateByPrimaryKey(SysItemConfigEntity record);

	List<SysItemConfigEntity> selectByItemType(String itemType, String lang);
}
