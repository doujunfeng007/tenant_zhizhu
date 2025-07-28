package com.minigod.zero.flow.workflow.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.minigod.zero.flow.workflow.domain.WfCategory;
import com.minigod.zero.flow.workflow.domain.bo.WfCategoryBo;
import com.minigod.zero.flow.workflow.domain.vo.WfCategoryVo;

import java.util.Collection;
import java.util.List;

/**
 * 流程分类Service接口
 *
 * @author zsdp
 * @date 2022-01-15
 */
public interface IWfCategoryService extends IService<WfCategory> {
	/**
	 * 查询单个
	 *
	 * @return
	 */
	WfCategoryVo queryVoById(Long categoryId);

	/**
	 * 查询列表
	 */
	IPage<WfCategoryVo> queryPageList(WfCategoryBo bo, IPage pageQuery);

	/**
	 * 查询列表
	 */
	List<WfCategoryVo> queryList(WfCategoryBo bo);

	/**
	 * 根据新增业务对象插入【请填写功能名称】
	 *
	 * @param bo 【请填写功能名称】新增业务对象
	 * @return
	 */
	Boolean insert(WfCategoryBo bo);

	/**
	 * 根据编辑业务对象修改【请填写功能名称】
	 *
	 * @param bo 【请填写功能名称】编辑业务对象
	 * @return
	 */
	Boolean update(WfCategoryBo bo);

	/**
	 * 校验并删除数据
	 *
	 * @param ids     主键集合
	 * @param isValid 是否校验,true-删除前校验,false-不校验
	 * @return
	 */
	Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

	/**
	 * 根据主键删除
	 *
	 * @param id
	 * @return
	 */
	Boolean deleteById(Long id);
}
