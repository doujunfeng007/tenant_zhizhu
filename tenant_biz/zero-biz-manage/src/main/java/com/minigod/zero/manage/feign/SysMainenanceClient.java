package com.minigod.zero.manage.feign;

import com.minigod.zero.manage.entity.SysMaintenanceEntity;
import com.minigod.zero.manage.service.ISysMaintenanceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RestController
public class SysMainenanceClient implements ISysMainenanceClient {

	@Resource
	private ISysMaintenanceService sysMaintenanceService;

	@Override
	@GetMapping(UPDATE_STATUS)
	public void updateStatus() {
		List<SysMaintenanceEntity> list = sysMaintenanceService.waitUpdateList();
		if(null != list && list.size() > 0){
			for (SysMaintenanceEntity entity: list){
				Date now = new Date();
				if(1 == entity.getStatus()){ // 生效中
					if(now.after(entity.getDeadTime())){
						entity.setStatus(2);// 失效
						entity.setUpdateTime(new Date());
						sysMaintenanceService.updateById(entity);
					}
				}
				if(0 == entity.getStatus()){ //待生效
					if(now.after(entity.getForceTime())){
						entity.setStatus(1);
						entity.setUpdateTime(new Date());
						sysMaintenanceService.updateById(entity);
					}
				}
			}
		}
	}
}
