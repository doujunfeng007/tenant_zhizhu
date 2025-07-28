package com.minigod.zero.bpmn.module.common.service;

import com.minigod.zero.bpmn.module.common.entity.FileUploadInfoEntity;
import com.minigod.zero.core.mp.base.BaseService;

import java.util.List;

/**
 * 附件上传表 服务类
 *
 * @author 掌上智珠
 * @since 2024-03-13
 */
public interface IFileUploadInfoService extends BaseService<FileUploadInfoEntity> {

	/**
	 * 根据业务id 和类型查询上传的文件列表
	 * @param id
	 * @param businessType
	 * @return
	 */
	List<FileUploadInfoEntity> selectListByBusinessType(Long id, Integer businessType);
}
