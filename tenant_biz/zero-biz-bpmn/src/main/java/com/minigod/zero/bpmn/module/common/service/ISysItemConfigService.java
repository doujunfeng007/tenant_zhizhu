package com.minigod.zero.bpmn.module.common.service;

import com.minigod.zero.bpmn.module.common.dto.SysItemConfigDTO;
import com.minigod.zero.bpmn.module.common.dto.SysSubItemConfigDTO;
import com.minigod.zero.bpmn.module.common.vo.SysItemAndSubItemConfigVO;
import com.minigod.zero.bpmn.module.common.vo.SysItemConfigVO;
import com.minigod.zero.bpmn.module.common.vo.SysItemsAndSubItemConfigVO;
import com.minigod.zero.bpmn.module.common.vo.SysSubItemConfigVO;

import java.util.List;

/**
 * 系统配置项服务
 *
 * @author eric
 * @since 2024-06-20 13:23:15
 */
public interface ISysItemConfigService {
	/**
	 * 根据主键查询
	 *
	 * @param id
	 * @return
	 */
	SysItemConfigVO selectItemByPrimaryKey(Long id);

	/**
	 * 根据主键查询配置子项
	 *
	 * @param id
	 * @return
	 */
	SysSubItemConfigVO selectSubItemByPrimaryKey(Long id);

	/**
	 * 根据配置项类型查询
	 *
	 * @param itemType
	 * @param lang
	 * @return
	 */
	List<SysItemConfigVO> selectItemByItemType(String itemType, String lang);

	/**
	 * 根据配置项类型查询配置子项
	 *
	 * @param itemType
	 * @param lang
	 * @return
	 */
	List<SysSubItemConfigVO> selectSubItemByItemType(String itemType, String lang);

	/**
	 * 根据配置项类型和配置项主键查询配置子项
	 *
	 * @param itemType
	 * @param itemId
	 * @param lang
	 * @return
	 */
	List<SysSubItemConfigVO> selectSubItemByItemTypeAndItemId(String itemType, Long itemId, String lang);

	/**
	 * 根据配置项类型查询配置项和配置子项
	 *
	 * @param itemId
	 * @param lang
	 * @return
	 */
	SysItemAndSubItemConfigVO selectItemAndSubItemByItemType(Long itemId, String lang);

	/**
	 * 根据配置项类型查询配置项和配置子项
	 *
	 * @param itemType
	 * @param lang
	 * @return
	 */
	SysItemsAndSubItemConfigVO selectItemsAndSubItemByItemType(String itemType, String lang);

	/**
	 * 根据主键删除配置项同时删除配置项子项
	 *
	 * @param id
	 * @return
	 */
	int deleteItemByPrimaryKey(Long id);

	/**
	 * 根据主键删除配置项子项
	 *
	 * @param id
	 * @return
	 */
	int deleteSubItemByPrimaryKey(Long id);

	/**
	 * 根据配置项类型删除配置项
	 *
	 * @param itemId
	 * @return
	 */
	int deleteSubItemByItemId(Long itemId);

	/**
	 * 插入配置项
	 *
	 * @param itemConfig
	 * @return
	 */
	int insertItem(SysItemConfigDTO itemConfig);

	/**
	 * 插入配置项子项
	 *
	 * @param subItemConfig
	 * @return
	 */
	int insertSubItem(SysSubItemConfigDTO subItemConfig);

	/**
	 * 插入配置项
	 *
	 * @param itemConfig
	 * @return
	 */
	int insertItemSelective(SysItemConfigDTO itemConfig);

	/**
	 * 插入配置项子项
	 *
	 * @param subItemConfig
	 * @return
	 */
	int insertSubItemSelective(SysSubItemConfigDTO subItemConfig);

	/**
	 * 根据主键更新配置项
	 *
	 * @param itemConfig
	 * @return
	 */
	int updateItemByPrimaryKeySelective(SysItemConfigDTO itemConfig);

	/**
	 * 根据主键更新配置项子项
	 *
	 * @param subItemConfig
	 * @return
	 */
	int updateSubItemByPrimaryKeySelective(SysSubItemConfigDTO subItemConfig);

	/**
	 * 根据主键更新配置项
	 *
	 * @param itemConfig
	 * @return
	 */
	int updateItemByPrimaryKey(SysItemConfigDTO itemConfig);

	/**
	 * 根据主键更新配置项子项
	 *
	 * @param subItemConfig
	 * @return
	 */
	int updateSubItemByPrimaryKey(SysSubItemConfigDTO subItemConfig);
}
