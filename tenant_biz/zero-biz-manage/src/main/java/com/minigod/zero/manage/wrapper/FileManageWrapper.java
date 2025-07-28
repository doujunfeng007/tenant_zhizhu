package com.minigod.zero.manage.wrapper;

import com.minigod.zero.manage.entity.FileManageEntity;
import com.minigod.zero.manage.vo.FileManageVO;
import com.minigod.zero.core.mp.support.BaseEntityWrapper;
import com.minigod.zero.core.tool.utils.BeanUtil;

import java.util.Objects;

/**
 * 文件管理 包装类,返回视图层所需的字段
 *
 * @author 掌上智珠
 * @since 2023-03-22
 */
public class FileManageWrapper extends BaseEntityWrapper<FileManageEntity, FileManageVO>  {

	public static FileManageWrapper build() {
		return new FileManageWrapper();
 	}

	@Override
	public FileManageVO entityVO(FileManageEntity fileManage) {
	    FileManageVO fileManageVO = new FileManageVO();
    	if (fileManage != null) {
		    fileManageVO = Objects.requireNonNull(BeanUtil.copy(fileManage, FileManageVO.class));

		    //User createUser = UserCache.getUser(fileManage.getCreateUser());
		    //User updateUser = UserCache.getUser(fileManage.getUpdateUser());
		    //fileManageVO.setCreateUserName(createUser.getName());
		    //fileManageVO.setUpdateUserName(updateUser.getName());
        }
		return fileManageVO;
	}


}
