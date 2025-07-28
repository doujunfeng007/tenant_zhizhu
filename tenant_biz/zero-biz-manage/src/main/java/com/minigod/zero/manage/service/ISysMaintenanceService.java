package com.minigod.zero.manage.service;

import com.minigod.zero.manage.entity.SysMaintenanceEntity;
import com.minigod.zero.manage.vo.SysMaintenanceVO;
import com.minigod.zero.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 系统维护 服务类
 *
 * @author 掌上智珠
 * @since 2023-03-23
 */
public interface ISysMaintenanceService extends BaseService<SysMaintenanceEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param sysMaintenance
	 * @return
	 */
	IPage<SysMaintenanceVO> selectSysMaintenancePage(IPage<SysMaintenanceVO> page, SysMaintenanceVO sysMaintenance);

	/**
	 * 查询状态待更新的数据
	 * @return
	 */
	List<SysMaintenanceEntity> waitUpdateList();
}
