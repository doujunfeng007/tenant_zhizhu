package com.minigod.zero.manage.service.impl;

import com.minigod.zero.manage.entity.FileManageEntity;
import com.minigod.zero.manage.mapper.FileManageMapper;
import com.minigod.zero.manage.service.ICustInfoService;
import com.minigod.zero.manage.service.IFileManageService;
import com.minigod.zero.manage.vo.FileManageVO;
import com.minigod.zero.user.entity.User;
import com.minigod.zero.core.mp.base.BaseEntity;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 文件管理 服务实现类
 *
 * @author 掌上智珠
 * @since 2023-03-22
 */
@Service
public class FileManageServiceImpl extends BaseServiceImpl<FileManageMapper, FileManageEntity> implements IFileManageService {

	@Resource
	private ICustInfoService custInfoService;

	@Override
	public IPage<FileManageVO> selectFileManagePage(IPage<FileManageVO> page, FileManageVO fileManage) {
		List<FileManageVO> list = baseMapper.selectFileManagePage(page, fileManage);
		List<Long> updateUserIds = null;
		if (null != list) {
			updateUserIds = list.stream().filter(item -> item.getUpdateUser() != null).map(BaseEntity::getUpdateUser).collect(Collectors.toList());

			Map<Long, User> updateUserMap = custInfoService.userInfoByIds(updateUserIds);
			if (MapUtils.isNotEmpty(updateUserMap)) {
				for (FileManageVO fileManageVO : list) {
					User user = updateUserMap.get(fileManageVO.getUpdateUser());
					fileManageVO.setUpdateUserName(user != null ? user.getName() : null);
				}
			}
		}
		return page.setRecords(list);
	}

}
