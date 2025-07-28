package com.minigod.zero.cms.feign.oms;

import com.minigod.zero.cms.entity.oms.SysMaintenanceEntity;
import com.minigod.zero.cms.entity.oms.MrWhiteEntity;
import com.minigod.zero.cms.vo.SysMaintenanceRespVo;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author 掌上智珠
 * @since 2023/9/26 15:13
 */
@FeignClient(
	value = AppConstant.SERVICE_BIZ_CMS_NAME
)
public interface ISysMaintenanceClient {
	String SYS_MAINTENANCE_LAST = AppConstant.FEIGN_API_PREFIX + "/sys_maintenance_last";
	String SYS_MAINTENANCE_WHITE_LIST = AppConstant.FEIGN_API_PREFIX + "/sys_maintenance_white_list";


	@PostMapping(SYS_MAINTENANCE_LAST)
	R<SysMaintenanceRespVo> sysMaintenanceLast(@RequestBody SysMaintenanceEntity sysMaintenanceEntity);

	@PostMapping(SYS_MAINTENANCE_WHITE_LIST)
	R<List<MrWhiteEntity>> sysMaintenancewhiteList();
}
