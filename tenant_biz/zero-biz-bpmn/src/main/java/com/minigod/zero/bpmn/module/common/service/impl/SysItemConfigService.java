package com.minigod.zero.bpmn.module.common.service.impl;

import com.minigod.zero.bpmn.module.common.dto.SysItemConfigDTO;
import com.minigod.zero.bpmn.module.common.dto.SysSubItemConfigDTO;
import com.minigod.zero.bpmn.module.common.entity.SysItemConfigEntity;
import com.minigod.zero.bpmn.module.common.entity.SysSubItemConfigEntity;
import com.minigod.zero.bpmn.module.common.mapper.SysItemConfigMapper;
import com.minigod.zero.bpmn.module.common.mapper.SysSubItemConfigMapper;
import com.minigod.zero.bpmn.module.common.service.ISysItemConfigService;
import com.minigod.zero.bpmn.module.common.vo.SysItemAndSubItemConfigVO;
import com.minigod.zero.bpmn.module.common.vo.SysItemConfigVO;
import com.minigod.zero.bpmn.module.common.vo.SysItemsAndSubItemConfigVO;
import com.minigod.zero.bpmn.module.common.vo.SysSubItemConfigVO;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.utils.BeanUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 系统通用配置
 *
 * @author eric
 * @since 2024-06-20 13:25:00
 */
@Service
@AllArgsConstructor
public class SysItemConfigService implements ISysItemConfigService {
	private final SysItemConfigMapper sysItemConfigMapper;
	private final SysSubItemConfigMapper sysSubItemConfigMapper;

	/**
	 * 根据主键查询
	 *
	 * @param id
	 * @return
	 */
	@Override
	public SysItemConfigVO selectItemByPrimaryKey(Long id) {
		SysItemConfigVO sysItemConfigVO = new SysItemConfigVO();
		if (id != null) {
			sysItemConfigVO = BeanUtil.copyProperties(sysItemConfigMapper.selectByPrimaryKey(id), SysItemConfigVO.class);
		}
		return sysItemConfigVO;
	}

	/**
	 * 根据主键查询配置子项
	 *
	 * @param id
	 * @return
	 */
	@Override
	public SysSubItemConfigVO selectSubItemByPrimaryKey(Long id) {
		SysSubItemConfigVO sysSubItemConfigVO = new SysSubItemConfigVO();
		if (id != null) {
			sysSubItemConfigVO = BeanUtil.copyProperties(sysSubItemConfigMapper.selectByPrimaryKey(id), SysSubItemConfigVO.class);
		}
		return sysSubItemConfigVO;
	}

	/**
	 * 根据配置项类型查询
	 *
	 * @param itemType
	 * @param lang
	 * @return
	 */
	@Override
	public List<SysItemConfigVO> selectItemByItemType(String itemType, String lang) {
		List<SysItemConfigVO> sysItemConfigVOList = new ArrayList<>();
		List<SysItemConfigEntity> sysItemConfigList = sysItemConfigMapper.selectByItemType(itemType, lang);
		sysItemConfigList.forEach(sysItemConfig -> {
			SysItemConfigVO sysItemConfigVO = new SysItemConfigVO();
			BeanUtil.copyProperties(sysItemConfig, sysItemConfigVO);
			sysItemConfigVOList.add(sysItemConfigVO);
		});
		return sysItemConfigVOList;
	}

	/**
	 * 根据配置项类型查询配置子项
	 *
	 * @param itemType
	 * @param lang
	 * @return
	 */
	@Override
	public List<SysSubItemConfigVO> selectSubItemByItemType(String itemType, String lang) {
		List<SysSubItemConfigVO> sysSubItemConfigVOList = new ArrayList<>();
		List<SysSubItemConfigEntity> sysSubItemConfigList = sysSubItemConfigMapper.selectByItemType(itemType, lang);
		sysSubItemConfigList.forEach(sysSubItemConfig -> {
			SysSubItemConfigVO sysSubItemConfigVO = new SysSubItemConfigVO();
			BeanUtil.copyProperties(sysSubItemConfig, sysSubItemConfigVO);
			sysSubItemConfigVOList.add(sysSubItemConfigVO);
		});

		return sysSubItemConfigVOList;
	}

	/**
	 * 根据配置项类型和配置项主键查询配置子项
	 *
	 * @param itemType
	 * @param itemId
	 * @param lang
	 * @return
	 */
	@Override
	public List<SysSubItemConfigVO> selectSubItemByItemTypeAndItemId(String itemType, Long itemId, String lang) {
		List<SysSubItemConfigVO> sysSubItemConfigVOList = new ArrayList<>();
		List<SysSubItemConfigEntity> sysSubItemConfigList = sysSubItemConfigMapper.selectSubItemByItemTypeAndItemId(itemType, itemId, lang);
		sysSubItemConfigList.forEach(sysSubItemConfig -> {
			SysSubItemConfigVO sysSubItemConfigVO = new SysSubItemConfigVO();
			BeanUtil.copyProperties(sysSubItemConfig, sysSubItemConfigVO);
			sysSubItemConfigVOList.add(sysSubItemConfigVO);
		});
		return sysSubItemConfigVOList;
	}

	/**
	 * 根据配置项类型查询配置项和配置子项
	 *
	 * @param itemId
	 * @param lang
	 * @return
	 */
	@Override
	public SysItemAndSubItemConfigVO selectItemAndSubItemByItemType(Long itemId, String lang) {
		SysItemAndSubItemConfigVO sysItemAndSubItemConfigVO = new SysItemAndSubItemConfigVO();
		SysItemConfigEntity sysItemConfig = sysItemConfigMapper.selectByPrimaryKey(itemId);
		if (sysItemConfig != null) {
			SysItemConfigVO sysItemConfigVO = BeanUtil.copyProperties(sysItemConfig, SysItemConfigVO.class);
			sysItemAndSubItemConfigVO.setSysItemConfigVO(sysItemConfigVO);
			List<SysSubItemConfigEntity> sysSubItemConfigList = sysSubItemConfigMapper.selectSubItemByItemTypeAndItemId(sysItemConfig.getItemType(), itemId, lang);
			sysSubItemConfigList.forEach(sysSubItemConfig -> {
				SysSubItemConfigVO sysSubItemConfigVO = BeanUtil.copyProperties(sysSubItemConfig, SysSubItemConfigVO.class);
				if (sysItemAndSubItemConfigVO.getSysItemConfigVO().getSysSubItemConfigVOs() == null) {
					sysItemAndSubItemConfigVO.getSysItemConfigVO().setSysSubItemConfigVOs(new ArrayList<>());
				}
				sysItemAndSubItemConfigVO.getSysItemConfigVO().getSysSubItemConfigVOs().add(sysSubItemConfigVO);
			});
		}

		return sysItemAndSubItemConfigVO;
	}

	/**
	 * 根据配置项类型查询配置项和配置子项
	 *
	 * @param itemType
	 * @param lang
	 * @return
	 */
	@Override
	public SysItemsAndSubItemConfigVO selectItemsAndSubItemByItemType(String itemType, String lang) {
		SysItemsAndSubItemConfigVO sysItemsAndSubItemConfigVO = new SysItemsAndSubItemConfigVO();
		List<SysItemConfigEntity> sysItemConfigList = sysItemConfigMapper.selectByItemType(itemType, lang);
		sysItemConfigList.forEach(sysItemConfig -> {
			SysItemConfigVO sysItemConfigVO = BeanUtil.copyProperties(sysItemConfig, SysItemConfigVO.class);
			if (sysItemsAndSubItemConfigVO.getSysItemConfigVOs() == null) {
				sysItemsAndSubItemConfigVO.setSysItemConfigVOs(new ArrayList<>());
			}
			sysItemsAndSubItemConfigVO.getSysItemConfigVOs().add(sysItemConfigVO);
		});

		if (sysItemsAndSubItemConfigVO.getSysItemConfigVOs() != null) {
			sysItemsAndSubItemConfigVO.getSysItemConfigVOs().forEach(sysItemConfigVO -> {
				List<SysSubItemConfigEntity> sysSubItemConfigList = sysSubItemConfigMapper.selectSubItemByItemTypeAndItemId(sysItemConfigVO.getItemType(), sysItemConfigVO.getItemId(), lang);
				sysSubItemConfigList.forEach(sysSubItemConfig -> {
					SysSubItemConfigVO sysSubItemConfigVO = new SysSubItemConfigVO();
					BeanUtil.copyProperties(sysSubItemConfig, sysSubItemConfigVO);
					if (sysItemConfigVO.getSysSubItemConfigVOs() == null) {
						sysItemConfigVO.setSysSubItemConfigVOs(new ArrayList<>());
					}
					sysItemConfigVO.getSysSubItemConfigVOs().add(sysSubItemConfigVO);
				});
			});
		}

		return sysItemsAndSubItemConfigVO;
	}

	/**
	 * 根据主键删除配置项同时删除配置项子项
	 *
	 * @param id
	 * @return
	 */
	@Override
	public int deleteItemByPrimaryKey(Long id) {
		return sysItemConfigMapper.deleteByPrimaryKey(id);
	}

	/**
	 * 根据主键删除配置项子项
	 *
	 * @param id
	 * @return
	 */
	@Override
	public int deleteSubItemByPrimaryKey(Long id) {
		return sysSubItemConfigMapper.deleteByPrimaryKey(id);
	}

	/**
	 * 根据配置项类型删除配置项
	 *
	 * @param itemId
	 * @return
	 */
	@Override
	public int deleteSubItemByItemId(Long itemId) {
		return sysSubItemConfigMapper.deleteByItemId(itemId);
	}

	/**
	 * 插入配置项
	 *
	 * @param itemConfig
	 * @return
	 */
	@Override
	public int insertItem(SysItemConfigDTO itemConfig) {
		SysItemConfigEntity sysItemConfig = BeanUtil.copyProperties(itemConfig, SysItemConfigEntity.class);
		sysItemConfig.setTenantId(AuthUtil.getTenantId());
		sysItemConfig.setCreateTime(new Date());
		sysItemConfig.setCreateUser(AuthUtil.getCustId());
		return sysItemConfigMapper.insertSelective(sysItemConfig);
	}

	/**
	 * 插入配置项子项
	 *
	 * @param subItemConfig
	 * @return
	 */
	@Override
	public int insertSubItem(SysSubItemConfigDTO subItemConfig) {
		SysSubItemConfigEntity sysSubItemConfig = BeanUtil.copyProperties(subItemConfig, SysSubItemConfigEntity.class);
		sysSubItemConfig.setTenantId(AuthUtil.getTenantId());
		sysSubItemConfig.setCreateUser(AuthUtil.getCustId());
		sysSubItemConfig.setCreateTime(new Date());
		return sysSubItemConfigMapper.insertSelective(sysSubItemConfig);
	}

	/**
	 * 插入配置项
	 *
	 * @param itemConfig
	 * @return
	 */
	@Override
	public int insertItemSelective(SysItemConfigDTO itemConfig) {
		int rows = 0;
		if (itemConfig != null) {
			SysItemConfigEntity sysItemConfig = BeanUtil.copyProperties(itemConfig, SysItemConfigEntity.class);
			sysItemConfig.setTenantId(AuthUtil.getTenantId());
			sysItemConfig.setCreateTime(new Date());
			sysItemConfig.setCreateUser(AuthUtil.getCustId());
			rows = sysItemConfigMapper.insertSelective(sysItemConfig);
		}
		return rows;
	}

	/**
	 * 插入配置项子项
	 *
	 * @param subItemConfig
	 * @return
	 */
	@Override
	public int insertSubItemSelective(SysSubItemConfigDTO subItemConfig) {
		int rows = 0;
		if (subItemConfig != null) {
			SysSubItemConfigEntity sysSubItemConfig = BeanUtil.copyProperties(subItemConfig, SysSubItemConfigEntity.class);
			sysSubItemConfig.setTenantId(AuthUtil.getTenantId());
			sysSubItemConfig.setCreateTime(new Date());
			sysSubItemConfig.setCreateUser(AuthUtil.getCustId());
			rows = sysSubItemConfigMapper.insertSelective(sysSubItemConfig);
		}
		return rows;
	}

	/**
	 * 根据主键更新配置项
	 *
	 * @param itemConfig
	 * @return
	 */
	@Override
	public int updateItemByPrimaryKeySelective(SysItemConfigDTO itemConfig) {
		int rows = 0;
		SysItemConfigEntity sysItemConfig = BeanUtil.copyProperties(itemConfig, SysItemConfigEntity.class);
		sysItemConfig.setTenantId(AuthUtil.getTenantId());
		sysItemConfig.setUpdateTime(new Date());
		sysItemConfig.setUpdateUser(AuthUtil.getCustId());
		if (sysItemConfig != null) {
			rows = sysItemConfigMapper.updateByPrimaryKey(sysItemConfig);
		}
		return rows;
	}

	/**
	 * 根据主键更新配置项子项
	 *
	 * @param subItemConfig
	 * @return
	 */
	@Override
	public int updateSubItemByPrimaryKeySelective(SysSubItemConfigDTO subItemConfig) {
		int rows = 0;
		SysSubItemConfigEntity sysSubItemConfig = BeanUtil.copyProperties(subItemConfig, SysSubItemConfigEntity.class);
		sysSubItemConfig.setTenantId(AuthUtil.getTenantId());
		sysSubItemConfig.setUpdateTime(new Date());
		sysSubItemConfig.setUpdateUser(AuthUtil.getCustId());
		if (sysSubItemConfig != null) {
			rows = sysSubItemConfigMapper.updateByPrimaryKeySelective(sysSubItemConfig);
		}
		return rows;
	}

	/**
	 * 根据主键更新配置项
	 *
	 * @param itemConfig
	 * @return
	 */
	@Override
	public int updateItemByPrimaryKey(SysItemConfigDTO itemConfig) {
		int rows = 0;
		if (itemConfig != null) {
			SysItemConfigEntity sysItemConfig = BeanUtil.copyProperties(itemConfig, SysItemConfigEntity.class);
			sysItemConfig.setTenantId(AuthUtil.getTenantId());
			sysItemConfig.setUpdateTime(new Date());
			sysItemConfig.setUpdateUser(AuthUtil.getCustId());
			rows = sysItemConfigMapper.updateByPrimaryKey(sysItemConfig);
		}
		return rows;
	}

	/**
	 * 根据主键更新配置项子项
	 *
	 * @param subItemConfig
	 * @return
	 */
	@Override
	public int updateSubItemByPrimaryKey(SysSubItemConfigDTO subItemConfig) {
		int rows = 0;
		if (subItemConfig != null) {
			SysSubItemConfigEntity sysSubItemConfig = BeanUtil.copyProperties(subItemConfig, SysSubItemConfigEntity.class);
			sysSubItemConfig.setTenantId(AuthUtil.getTenantId());
			sysSubItemConfig.setUpdateTime(new Date());
			sysSubItemConfig.setUpdateUser(AuthUtil.getCustId());
			rows = sysSubItemConfigMapper.updateByPrimaryKey(sysSubItemConfig);
		}
		return rows;
	}
}
