package com.minigod.zero.manage.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.manage.entity.SysDocumentEntity;
import com.minigod.zero.manage.vo.request.SysDocumentReqVO;
import com.minigod.zero.core.tool.api.R;

import java.util.List;

/**
 * 平台文档Service接口
 *
 * @author jim
 * @date 2020-05-27
 */
public interface ISysDocumentService  {
	/**
	 * 查询平台文档
	 *
	 * @param id 平台文档ID
	 * @return 平台文档
	 */
	public SysDocumentEntity selectSysDocumentById(Long id);

	/**
	 * 查询平台文档列表
	 *
	 * @return 平台文档集合
	 */
	public List<SysDocumentEntity> selectSysDocumentList(IPage<SysDocumentEntity> page, SysDocumentEntity sysDocument);

	/**
	 * 新增平台文档
	 *
	 * @param sysDocument 平台文档
	 * @return 结果
	 */
	public int insertSysDocument(SysDocumentEntity sysDocument);

	/**
	 * 修改平台文档
	 *
	 * @param sysDocument 平台文档
	 * @return 结果
	 */
	public int updateSysDocument(SysDocumentEntity sysDocument);

	/**
	 * 批量删除平台文档
	 *
	 * @param ids 需要删除的平台文档ID
	 * @return 结果
	 */
	public boolean deleteSysDocumentByIds(Integer[] ids);

	/**
	 * 删除平台文档信息
	 *
	 * @param id 平台文档ID
	 * @return 结果
	 */
	public int deleteSysDocumentById(Integer id);

	/**
	 * 列表查询
	 *
	 * @param page
	 * @param sysDocument
	 * @return
	 */
	IPage<SysDocumentEntity> selectSysDocPage(IPage<SysDocumentEntity> page, SysDocumentEntity sysDocument);

	/**
	 * 保存或修改
	 *
	 * @param sysDocument
	 * @return
	 */
	boolean submit(SysDocumentEntity sysDocument);

	R<Object> fetchSysDocument(SysDocumentReqVO vo);
}
