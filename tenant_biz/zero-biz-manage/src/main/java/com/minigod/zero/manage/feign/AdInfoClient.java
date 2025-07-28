package com.minigod.zero.manage.feign;

import com.minigod.zero.manage.entity.AdInfoEntity;
import com.minigod.zero.manage.service.IAdInfoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RestController
public class AdInfoClient implements IAdInfoClient {

	@Resource
	private IAdInfoService iAdInfoService;

	@Override
	@GetMapping(UPDATE_STATUS)
	public void updateStatus() {
		List<AdInfoEntity> list = iAdInfoService.findByState(1);
		if(null != list && list.size() > 0){
			for (AdInfoEntity entity: list){
				// 设置为失效
				Date now = new Date();
				if(1 == entity.getStatus()){ // 生效中
					if(now.after(entity.getEndTime())){
						entity.setStatus(0);
						entity.setUpdateTime(new Date());
						iAdInfoService.updateById(entity);
					}
				}
			}
		}
	}
}
