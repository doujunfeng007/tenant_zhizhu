package com.minigod.zero.flow.workflow.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minigod.zero.core.log.exception.ServiceException;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.utils.Func;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.minigod.zero.flow.workflow.domain.WfCategory;
import com.minigod.zero.flow.workflow.domain.bo.WfCategoryBo;
import com.minigod.zero.flow.workflow.domain.vo.WfCategoryVo;
import com.minigod.zero.flow.workflow.mapper.WfCategoryMapper;
import com.minigod.zero.flow.workflow.service.IWfCategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * 流程分类Service业务层处理
 *
 * @author zsdp
 * @date 2022-01-15
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class WfCategoryServiceImpl extends BaseServiceImpl<WfCategoryMapper, WfCategory> implements IWfCategoryService {


	@Override
	public WfCategoryVo queryVoById(Long categoryId) {
		return baseMapper.selectVoById(categoryId);
	}

	@Override
	public IPage<WfCategoryVo> queryPageList(WfCategoryBo bo, IPage pageQuery) {
		Page<WfCategoryVo> result = baseMapper.selectVoPage(pageQuery, bo);
		return result;
	}

	@Override
	public List<WfCategoryVo> queryList(WfCategoryBo bo) {
		return baseMapper.selectVoList(bo);
	}

	private LambdaQueryWrapper<WfCategory> buildQueryWrapper(WfCategoryBo bo) {
		LambdaQueryWrapper<WfCategory> lqw = Wrappers.lambdaQuery();
		lqw.like(StringUtil.isNotBlank(bo.getCategoryName()), WfCategory::getCategoryName, bo.getCategoryName());
		lqw.eq(StringUtil.isNotBlank(bo.getCode()), WfCategory::getCode, bo.getCode());
		return lqw;
	}

	@Override
	public Boolean insert(WfCategoryBo bo) {
		WfCategory add = new WfCategory();
		BeanUtils.copyProperties(bo, add);
		validEntityBeforeSave(add);
		boolean flag = this.save(add);
		if (flag) {
			bo.setId(add.getId());
		}
		return flag;
	}

	@Override
	public Boolean update(WfCategoryBo bo) {
		WfCategory update = new WfCategory();
		BeanUtils.copyProperties(bo, update);
		validEntityBeforeSave(update);
		return updateById(update);
	}

	/**
	 * 保存前的数据校验
	 *
	 * @param entity 实体类数据
	 */
	private void validEntityBeforeSave(WfCategory entity) {
		//唯一约束
		if (Func.isNotEmpty(entity.getId())) {
			LambdaQueryWrapper<WfCategory> countWrapper = new LambdaQueryWrapper<>();
			countWrapper.ne(WfCategory::getId, entity.getId())
				.eq(WfCategory::getTenantId, AuthUtil.getTenantId())
				.eq(WfCategory::getCategoryName, entity.getCategoryName())
				.eq(WfCategory::getCode, entity.getCode());
			if (baseMapper.selectCount(countWrapper) > 0) {
				throw new ServiceException(String.format("类型名称:%s类型编码:%s的分类已经存在,名称和编码不能重复添加!", entity.getCategoryName(), entity.getCode()));
			}
		} else {
			LambdaQueryWrapper<WfCategory> countWrapper = new LambdaQueryWrapper<>();
			countWrapper.eq(WfCategory::getTenantId, AuthUtil.getTenantId())
				.eq(WfCategory::getCategoryName, entity.getCategoryName())
				.eq(WfCategory::getCode, entity.getCode());
			if (baseMapper.selectCount(countWrapper) > 0) {
				throw new ServiceException(String.format("类型名称:%s类型编码:%s的分类已经存在,名称和编码不能重复!", entity.getCategoryName(), entity.getCode()));
			}
		}
	}

	/**
	 * 校验并根据id删除数据
	 *
	 * @param ids     主键集合
	 * @param isValid 是否校验,true-删除前校验,false-不校验
	 * @return
	 */
	@Override
	public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
		if (isValid) {
			//TODO 做一些业务上的校验,判断是否需要校验
		}
		return baseMapper.deleteBatchIds(ids) > 0;
	}

	/**
	 * 根据id删除
	 *
	 * @param id
	 * @return
	 */
	@Override
	public Boolean deleteById(Long id) {
		int i = baseMapper.deleteById(id);
		return i > 0;
	}
}
