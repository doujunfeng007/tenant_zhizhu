package com.minigod.zero.manage.service.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.minigod.zero.manage.entity.SysMaintenanceEntity;
import com.minigod.zero.manage.mapper.SysMaintenanceMapper;
import com.minigod.zero.manage.service.ICustInfoService;
import com.minigod.zero.manage.service.ISysMaintenanceService;
import com.minigod.zero.manage.vo.SysMaintenanceVO;
import com.minigod.zero.user.entity.User;
import com.minigod.zero.core.mp.base.BaseEntity;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 系统维护 服务实现类
 *
 * @author 掌上智珠
 * @since 2023-03-23
 */
@Service
public class SysMaintenanceServiceImpl extends BaseServiceImpl<SysMaintenanceMapper, SysMaintenanceEntity> implements ISysMaintenanceService {

	@Resource
	private ICustInfoService custInfoService;

	@Override
	public IPage<SysMaintenanceVO> selectSysMaintenancePage(IPage<SysMaintenanceVO> page, SysMaintenanceVO sysMaintenance) {
		List<SysMaintenanceVO> list = baseMapper.selectSysMaintenancePage(page, sysMaintenance);
		List<Long> updateUserIds = null;
		if (null != list) {
			updateUserIds = list.stream().filter(item -> item.getUpdateUser() != null).map(BaseEntity::getUpdateUser).collect(Collectors.toList());
			if (null != updateUserIds && updateUserIds.size() > 0) {
				Map<Long, User> updateUserMap = custInfoService.userInfoByIds(updateUserIds);
				if (MapUtils.isNotEmpty(updateUserMap)) {
					for (SysMaintenanceVO sysMaintenanceVO : list) {
						sysMaintenanceVO.setUpdateUserName(updateUserMap.get(sysMaintenanceVO.getUpdateUser()).getName());
					}
				}
			}
		}
		return page.setRecords(list);
	}

	@Override
	public List<SysMaintenanceEntity> waitUpdateList() {
		return new LambdaQueryChainWrapper<>(baseMapper).ne(SysMaintenanceEntity::getStatus, 2).list();
	}


}
