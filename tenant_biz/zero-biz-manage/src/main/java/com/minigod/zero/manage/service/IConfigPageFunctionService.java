package com.minigod.zero.manage.service;

import com.minigod.zero.manage.entity.ConfigPageFunctionEntity;
import com.minigod.zero.manage.entity.DiscoverIconEntity;
import com.minigod.zero.manage.vo.ConfigPageFunctionVO;
import com.minigod.zero.manage.vo.response.IconExtVO;
import com.minigod.zero.manage.vo.response.IndexVO;
import com.minigod.zero.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.core.tool.api.R;

import java.util.List;

/**
 * 配置页面组件 服务类
 *
 * @author 掌上智珠
 * @since 2023-03-21
 */
public interface IConfigPageFunctionService extends BaseService<ConfigPageFunctionEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param ConfigPageFunction
	 * @return
	 */
	IPage<ConfigPageFunctionVO> selectConfigPageFunctionPage(IPage<ConfigPageFunctionVO> page, ConfigPageFunctionVO ConfigPageFunction);

	/**
	 * 列表查询
	 * @param typeValue
	 * @return
	 */
	List<ConfigPageFunctionEntity> getConfigPageFunctionList(Integer typeValue);

	/**
	 * 功能区列表
	 * @return
	 */
    List<IndexVO> getIndex();

	List<IconExtVO> getList(Integer roleId, Integer showType);

	R<Object> updataDisplayStatus(Long id, boolean isDisplay);

	void sortIcons(Long[] intTemp);

	void saveDisplayStatusAndSort(Long Long, Boolean valueOf1);

	void sortConfigPageFunction(Long[] intTemp);

	List queryDiscoverIconList();

	void updateDiscoverIcon(List<DiscoverIconEntity> discoverIconList);

	int updateSort(Long id);
}
