/*
 *  Copyright (c) 2018-2028, MiniGod All rights reserved.
 */
package com.minigod.zero.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.system.entity.FuncConfig;
import com.minigod.zero.system.mapper.FuncConfigMapper;
import com.minigod.zero.system.service.IFuncConfigService;
import com.minigod.zero.system.vo.FuncConfigVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 功能配置模块 服务实现类
 *
 * @author ZSDP
 * @since 2024-07-23
 */
@Service
public class FuncConfigServiceImpl extends BaseServiceImpl<FuncConfigMapper, FuncConfig> implements IFuncConfigService {

	@Override
	public IPage<FuncConfigVO> selectFuncConfigPage(IPage<FuncConfigVO> page, FuncConfigVO funcConfig) {
		return page.setRecords(baseMapper.selectFuncConfigPage(page, funcConfig));
	}

	@Override
	public List<FuncConfigVO> getByTenantId(String tenantId) {

		//组装树形结构
		List<FuncConfig> funcConfigs = baseMapper.getByTenantId(tenantId);
		return listTree(funcConfigs);
	}

	public List<FuncConfigVO> listTree(List<FuncConfig> funcConfigs) {
		//转成VO实体集合类
		List<FuncConfigVO> categoryVOS = new ArrayList<>();
		for (FuncConfig category : funcConfigs) {
			FuncConfigVO categoryVO = new FuncConfigVO();
			BeanUtils.copyProperties(category, categoryVO);
			categoryVOS.add(categoryVO);
		}
		//组装成父子的树形结构
		List<FuncConfigVO> collect = categoryVOS.stream().filter(categoryVO -> {
			//找到所有的一级分类
			return categoryVO.getParentId() == 0;//一级分类父id=0
		}).map(menu -> {
			menu.setChildren(getChildrens(menu, categoryVOS));
			return menu;
		}).collect(Collectors.toList());

		return collect;
	}

	//递归查找所有节点的子节点
	//root 当前节点，funcConfigVOS
	private List<FuncConfigVO> getChildrens(FuncConfigVO root, List<FuncConfigVO> funcConfigVOS) {
		//找出当前节点子节点
		List<FuncConfigVO> children = funcConfigVOS.stream().filter(categoryVO -> {
			//当前菜单root的id等于（是）节点集合中节点的父Id
			//当前节点root的id,是其他节点的父id
			return categoryVO.getParentId() == root.getId();
		}).map(categoryVO -> {
			//找到子
			categoryVO.setChildren(getChildrens(categoryVO, funcConfigVOS));
			return categoryVO;
		}).collect(Collectors.toList());

		return children;
	}

}
