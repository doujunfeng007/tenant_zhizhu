package com.minigod.zero.manage.mapper;

import com.minigod.zero.manage.entity.FileManageEntity;
import com.minigod.zero.manage.vo.FileManageVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 文件管理 Mapper 接口
 *
 * @author 掌上智珠
 * @since 2023-03-22
 */
public interface FileManageMapper extends BaseMapper<FileManageEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param fileManage
	 * @return
	 */
	List<FileManageVO> selectFileManagePage(IPage page, @Param("fileManage")FileManageVO fileManage);


}
