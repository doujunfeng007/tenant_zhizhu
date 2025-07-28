package com.minigod.zero.manage.service;

import com.minigod.zero.manage.entity.FileManageEntity;
import com.minigod.zero.manage.vo.FileManageVO;
import com.minigod.zero.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 文件管理 服务类
 *
 * @author 掌上智珠
 * @since 2023-03-22
 */
public interface IFileManageService extends BaseService<FileManageEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param fileManage
	 * @return
	 */
	IPage<FileManageVO> selectFileManagePage(IPage<FileManageVO> page, FileManageVO fileManage);


}
