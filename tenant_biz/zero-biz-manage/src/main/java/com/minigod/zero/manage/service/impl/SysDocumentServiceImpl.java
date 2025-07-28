package com.minigod.zero.manage.service.impl;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.manage.entity.SysDocumentEntity;
import com.minigod.zero.manage.service.ISysDocumentService;
import com.minigod.zero.manage.vo.request.SysDocumentReqVO;
import com.minigod.zero.manage.mapper.SysDocumentMapper;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.api.ResultCode;
import com.minigod.zero.core.tool.utils.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * 平台文档Service业务层处理
 *
 * @author jim
 * @date 2020-05-27
 */
@Service
public class SysDocumentServiceImpl implements ISysDocumentService {
	@Resource
	private SysDocumentMapper sysDocumentMapper;

	/**
	 * 查询平台文档
	 *
	 * @param id 平台文档ID
	 * @return 平台文档
	 */
	@Override
	public SysDocumentEntity selectSysDocumentById(Long id) {
		return sysDocumentMapper.selectSysDocumentById(id);
	}

	/**
	 * 查询平台文档列表
	 *
	 * @return 平台文档
	 */
	@Override
	public List<SysDocumentEntity> selectSysDocumentList(IPage<SysDocumentEntity> page, SysDocumentEntity sysDocument) {
		return sysDocumentMapper.selectSysDocumentList(page, sysDocument);
	}

	/**
	 * 新增平台文档
	 *
	 * @param sysDocument 平台文档
	 * @return 结果
	 */
	@Override
	public int insertSysDocument(SysDocumentEntity sysDocument) {
		sysDocument.setCreateTime(new Date());
		return sysDocumentMapper.insertSysDocument(sysDocument);
	}

	/**
	 * 修改平台文档
	 *
	 * @param sysDocument 平台文档
	 * @return 结果
	 */
	@Override
	public int updateSysDocument(SysDocumentEntity sysDocument) {
		sysDocument.setUpdateTime(new Date());
		return sysDocumentMapper.updateSysDocument(sysDocument);
	}

	/**
	 * 批量删除平台文档
	 *
	 * @param ids 需要删除的平台文档ID
	 * @return 结果
	 */
	@Override
	public boolean deleteSysDocumentByIds(Integer[] ids) {
		int i = sysDocumentMapper.deleteSysDocumentByIds(ids);
		return i > 0;
	}

	/**
	 * 删除平台文档信息
	 *
	 * @param id 平台文档ID
	 * @return 结果
	 */
	@Override
	public int deleteSysDocumentById(Integer id) {
		return sysDocumentMapper.deleteSysDocumentById(id);
	}

	@Override
	public IPage<SysDocumentEntity> selectSysDocPage(IPage<SysDocumentEntity> page, SysDocumentEntity sysDocument) {
		return page.setRecords(sysDocumentMapper.selectSysDocumentList(page, sysDocument));
	}

	@Override
	public boolean submit(SysDocumentEntity sysDocument) {
		int s = 0;
		if (null == sysDocument.getId()) {
			s = sysDocumentMapper.insertSysDocument(sysDocument);
		} else {
			s = sysDocumentMapper.updateSysDocument(sysDocument);
		}
		return s > 0 ? true : false;
	}

	@Override
	public R<Object> fetchSysDocument(SysDocumentReqVO vo) {
		SysDocumentEntity params = vo.getParams();
		if (null == params) {
			return R.fail(ResultCode.PARAMETER_ERROR);
		}
		SysDocumentEntity sysDocument = null;
		if (null != params.getId()) {
			sysDocument = sysDocumentMapper.selectSysDocumentById(params.getId());
		} else if (!StringUtil.isBlank(params.getDocKey())) {
			sysDocument = sysDocumentMapper.findDocumentByKey(params.getDocKey());
		} else {
			return R.fail(ResultCode.PARAMETER_ERROR);
		}

		if (null == sysDocument) {
			return R.fail("这篇文章飞走了，再去看看其他的吧");
		}

		if (StringUtil.isNotBlank(sysDocument.getDocContent())) {
			String content = sysDocument.getDocContent().replaceAll("(\\\\r\\\\n|\\\\n|\\\\r|\\r\\n|\\n|\\r)", "<br>");
			sysDocument.setDocContent(content);
		}
		return R.data(sysDocument);
	}

}
