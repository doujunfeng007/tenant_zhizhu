package com.minigod.zero.manage.mapper;

import com.minigod.zero.manage.entity.SysMaintenanceEntity;
import com.minigod.zero.manage.vo.SysMaintenanceVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统维护 Mapper 接口
 *
 * @author 掌上智珠
 * @since 2023-03-23
 */
public interface SysMaintenanceMapper extends BaseMapper<SysMaintenanceEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param sysMaintenance
	 * @return
	 */
	List<SysMaintenanceVO> selectSysMaintenancePage(@Param("page")IPage page, @Param("sysMaintenance") SysMaintenanceVO sysMaintenance);


}
